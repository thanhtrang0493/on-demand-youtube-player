package com.vcoders.on_demand_youtube_player.audio.youtubeExtractor;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.SparseArray;

import com.evgenii.jsevaluator.JsEvaluator;
import com.evgenii.jsevaluator.interfaces.JsCallback;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class YouTubeExtractor extends AsyncTask<String, Void, SparseArray<YtFile>>
{

    private final static boolean CACHING = true;

    protected static boolean LOGGING = false;

    private final static String LOG_TAG = "YouTubeExtractor";
    private final static String CACHE_FILE_NAME = "decipher_js_funct";
    private final static int DASH_PARSE_RETRIES = 5;

    protected WeakReference<Context> weakContext;
    //    private Context context;
    private String videoID;
    private VideoMeta videoMeta;
    private boolean includeWebM = true;
    private boolean useHttp = false;
    private boolean parseDashManifest = false;

    private String decipheredSignature;

    private static String decipherJsFileName;
    private static String decipherFunctions;
    private static String decipherFunctionName;

    private final Lock lock = new ReentrantLock();
    private final Condition jsExecuting = lock.newCondition();

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.36";

    private static final Pattern patYouTubePageLink = Pattern.compile("(http|https)://(www\\.|m.|)youtube\\.com/watch\\?v=(.+?)( |\\z|&)");
    private static final Pattern patYouTubeShortLink = Pattern.compile("(http|https)://(www\\.|)youtu.be/(.+?)( |\\z|&)");

    private static final Pattern patDashManifest1 = Pattern.compile("dashmpd=(.+?)(&|\\z)");
    private static final Pattern patDashManifest2 = Pattern.compile("\"dashmpd\":\"(.+?)\"");
    private static final Pattern patDashManifestEncSig = Pattern.compile("/s/([0-9A-F|\\.]{10,}?)(/|\\z)");

    private static final Pattern patTitle = Pattern.compile("title=(.*?)(&|\\z)");
    private static final Pattern patAuthor = Pattern.compile("author=(.+?)(&|\\z)");
    private static final Pattern patChannelId = Pattern.compile("ucid=(.+?)(&|\\z)");
    private static final Pattern patLength = Pattern.compile("length_seconds=(\\d+?)(&|\\z)");
    private static final Pattern patViewCount = Pattern.compile("view_count=(\\d+?)(&|\\z)");

    private static final Pattern patHlsvp = Pattern.compile("hlsvp=(.+?)(&|\\z)");
    private static final Pattern patHlsItag = Pattern.compile("/itag/(\\d+?)/");

    private static final Pattern patItag = Pattern.compile("itag=([0-9]+?)(&|,)");
    private static final Pattern patEncSig = Pattern.compile("s=([0-9A-F|\\.]{10,}?)(&|,|\")");
    private static final Pattern patUrl = Pattern.compile("url=(.+?)(&|,)");

    private static final Pattern patVariableFunction = Pattern.compile("(\\{|;| |=)([a-zA-Z$][a-zA-Z0-9$]{0,2})\\.([a-zA-Z$][a-zA-Z0-9$]{0,2})\\(");
    private static final Pattern patFunction = Pattern.compile("(\\{|;| |=)([a-zA-Z$_][a-zA-Z0-9$]{0,2})\\(");
    private static final Pattern patDecryptionJsFile = Pattern.compile("jsbin\\\\/(player-(.+?).js)");
//    private static final Pattern patSignatureDecFunction = Pattern.compile("\\(\"signature\",(.{1,3}?)\\(.{1,10}?\\)");

    private static final Pattern patSignatureDecFunction = Pattern.compile("([\"\\'])signature\\1\\s*,\\s*(?<sig>[a-zA-Z0-9$]+)\\(");

    private static final SparseArray<Format> FORMAT_MAP = new SparseArray<>();

    static {
        // Video and Audio
        // http://en.wikipedia.org/wiki/YouTube#Quality_and_formats

        FORMAT_MAP.put(5, new Format(5, "flv", 240, Format.VCodec.H263, Format.ACodec.MP3, 64, false));
        FORMAT_MAP.put(6, new Format(6, "flv", 270, Format.VCodec.H263, Format.ACodec.MP3, 64, false));
        FORMAT_MAP.put(17, new Format(17, "3gp", 144, Format.VCodec.MPEG4, Format.ACodec.AAC, 24, false));
        FORMAT_MAP.put(18, new Format(18, "mp4", 360, Format.VCodec.H264, Format.ACodec.AAC, 96, false));
        FORMAT_MAP.put(22, new Format(22, "mp4", 720, Format.VCodec.H264, Format.ACodec.AAC, 192, false));
        FORMAT_MAP.put(34, new Format(36, "flv", 360, Format.VCodec.MPEG4, Format.ACodec.AAC, 128, false));
        FORMAT_MAP.put(35, new Format(36, "flv", 480, Format.VCodec.MPEG4, Format.ACodec.AAC, 128, false));
        FORMAT_MAP.put(36, new Format(36, "3gp", 240, Format.VCodec.MPEG4, Format.ACodec.AAC, 32, false));
        FORMAT_MAP.put(37, new Format(37, "mp4", 1080, Format.VCodec.H264, Format.ACodec.AAC, 192, false));
        FORMAT_MAP.put(38, new Format(38, "mp4", 3072, Format.VCodec.H264, Format.ACodec.AAC, 192, false));
        FORMAT_MAP.put(43, new Format(43, "webm", 360, Format.VCodec.VP8, Format.ACodec.VORBIS, 128, false));
        FORMAT_MAP.put(44, new Format(44, "webm", 480, Format.VCodec.VP8, Format.ACodec.VORBIS, 128, false));
        FORMAT_MAP.put(45, new Format(45, "webm", 720, Format.VCodec.VP8, Format.ACodec.VORBIS, 192, false));
        FORMAT_MAP.put(46, new Format(46, "webm", 1080, Format.VCodec.VP8, Format.ACodec.VORBIS, 192, false));
        FORMAT_MAP.put(59, new Format(59, "mp4", 480, Format.VCodec.H264, Format.ACodec.AAC, 128, false));
        FORMAT_MAP.put(78, new Format(78, "mp4", 480, Format.VCodec.H264, Format.ACodec.AAC, 128, false));

        // Dash Video
        FORMAT_MAP.put(133, new Format(133, "mp4", 240, Format.VCodec.H264, Format.ACodec.NONE, true));
        FORMAT_MAP.put(134, new Format(134, "mp4", 360, Format.VCodec.H264, Format.ACodec.NONE, true));
        FORMAT_MAP.put(135, new Format(135, "mp4", 480, Format.VCodec.H264, Format.ACodec.NONE, true));
        FORMAT_MAP.put(136, new Format(136, "mp4", 720, Format.VCodec.H264, Format.ACodec.NONE, true));
        FORMAT_MAP.put(137, new Format(137, "mp4", 1080, Format.VCodec.H264, Format.ACodec.NONE, true));
        //itag 138 videos are either 3840x2160 or 7680x4320 (sLprVF6d7Ug)
        FORMAT_MAP.put(138, new Format(138, "mp4", 4320, Format.VCodec.H264, Format.ACodec.NONE, true));
        FORMAT_MAP.put(160, new Format(160, "mp4", 144, Format.VCodec.H264, Format.ACodec.NONE, true));
        FORMAT_MAP.put(160, new Format(160, "mp4", 144, Format.VCodec.H264, Format.ACodec.NONE, true));
        FORMAT_MAP.put(264, new Format(264, "mp4", 1440, Format.VCodec.H264, Format.ACodec.NONE, true));
        FORMAT_MAP.put(266, new Format(266, "mp4", 2160, Format.VCodec.H264, Format.ACodec.NONE, true));

        FORMAT_MAP.put(298, new Format(298, "mp4", 720, Format.VCodec.H264, 60, Format.ACodec.NONE, true));
        FORMAT_MAP.put(299, new Format(299, "mp4", 1080, Format.VCodec.H264, 60, Format.ACodec.NONE, true));

        // Dash Audio
        FORMAT_MAP.put(139, new Format(139, "m4a", Format.VCodec.NONE, Format.ACodec.AAC, 48, true));
        FORMAT_MAP.put(140, new Format(140, "m4a", Format.VCodec.NONE, Format.ACodec.AAC, 128, true));
        FORMAT_MAP.put(141, new Format(141, "m4a", Format.VCodec.NONE, Format.ACodec.AAC, 256, true));
//        FORMAT_MAP.put(256, new Format(256, "m4a", Format.VCodec.NONE, Format.ACodec.AAC, XX, true));
//        FORMAT_MAP.put(258, new Format(258, "m4a", Format.VCodec.NONE, Format.ACodec.AAC, XX, true));

        // WEBM Dash Video
        FORMAT_MAP.put(167, new Format(167, "webm", 360, Format.VCodec.VP9, Format.ACodec.NONE, true));
        FORMAT_MAP.put(168, new Format(168, "webm", 480, Format.VCodec.VP9, Format.ACodec.NONE, true));
        FORMAT_MAP.put(169, new Format(169, "webm", 720, Format.VCodec.VP9, Format.ACodec.NONE, true));
        FORMAT_MAP.put(170, new Format(170, "webm", 1080, Format.VCodec.VP9, Format.ACodec.NONE, true));
        FORMAT_MAP.put(218, new Format(218, "webm", 480, Format.VCodec.VP9, Format.ACodec.NONE, true));
        FORMAT_MAP.put(219, new Format(219, "webm", 480, Format.VCodec.VP9, Format.ACodec.NONE, true));
        FORMAT_MAP.put(278, new Format(278, "webm", 144, Format.VCodec.VP9, Format.ACodec.NONE, true));
        FORMAT_MAP.put(242, new Format(242, "webm", 240, Format.VCodec.VP9, Format.ACodec.NONE, true));
        FORMAT_MAP.put(243, new Format(243, "webm", 360, Format.VCodec.VP9, Format.ACodec.NONE, true));
        FORMAT_MAP.put(244, new Format(244, "webm", 480, Format.VCodec.VP9, Format.ACodec.NONE, true));
        FORMAT_MAP.put(247, new Format(247, "webm", 720, Format.VCodec.VP9, Format.ACodec.NONE, true));
        FORMAT_MAP.put(248, new Format(248, "webm", 1080, Format.VCodec.VP9, Format.ACodec.NONE, true));
        FORMAT_MAP.put(271, new Format(271, "webm", 1440, Format.VCodec.VP9, Format.ACodec.NONE, true));
        //itag 272 videos are either 3840x2160 (e.g. RtoitU2A-3E) or 7680x4320 (sLprVF6d7Ug)
        FORMAT_MAP.put(272, new Format(272, "webm", 2160, Format.VCodec.VP9, Format.ACodec.NONE, true));
        FORMAT_MAP.put(313, new Format(313, "webm", 2160, Format.VCodec.VP9, Format.ACodec.NONE, true));

        FORMAT_MAP.put(302, new Format(302, "webm", 720, Format.VCodec.VP9, 60, Format.ACodec.NONE, true));
        FORMAT_MAP.put(303, new Format(303, "webm", 1080, Format.VCodec.VP9, 60, Format.ACodec.NONE, true));
        FORMAT_MAP.put(308, new Format(308, "webm", 1440, Format.VCodec.VP9, 60, Format.ACodec.NONE, true));
        FORMAT_MAP.put(315, new Format(315, "webm", 2160, Format.VCodec.VP9, 60, Format.ACodec.NONE, true));

        // WEBM Dash Audio
        FORMAT_MAP.put(171, new Format(171, "webm", Format.VCodec.NONE, Format.ACodec.VORBIS, 128, true));
        FORMAT_MAP.put(172, new Format(172, "webm", Format.VCodec.NONE, Format.ACodec.VORBIS, 128, true));

        // WEBM Dash audio with opus inside
        FORMAT_MAP.put(249, new Format(249, "webm", Format.VCodec.NONE, Format.ACodec.OPUS, 50, true));
        FORMAT_MAP.put(250, new Format(250, "webm", Format.VCodec.NONE, Format.ACodec.OPUS, 70, true));
        FORMAT_MAP.put(251, new Format(251, "webm", Format.VCodec.NONE, Format.ACodec.OPUS, 160, true));

        // HLS Live Stream
        FORMAT_MAP.put(91, new Format(91, "mp4", 144, Format.VCodec.H264, Format.ACodec.AAC, 48, false, true));
        FORMAT_MAP.put(92, new Format(92, "mp4", 240, Format.VCodec.H264, Format.ACodec.AAC, 48, false, true));
        FORMAT_MAP.put(93, new Format(93, "mp4", 360, Format.VCodec.H264, Format.ACodec.AAC, 128, false, true));
        FORMAT_MAP.put(94, new Format(94, "mp4", 480, Format.VCodec.H264, Format.ACodec.AAC, 128, false, true));
        FORMAT_MAP.put(95, new Format(95, "mp4", 720, Format.VCodec.H264, Format.ACodec.AAC, 256, false, true));
        FORMAT_MAP.put(96, new Format(96, "mp4", 1080, Format.VCodec.H264, Format.ACodec.AAC, 256, false, true));
        FORMAT_MAP.put(132, new Format(132, "mp4", 240, Format.VCodec.H264, Format.ACodec.AAC, 48, false, true));
        FORMAT_MAP.put(151, new Format(151, "mp4", 72, Format.VCodec.H264, Format.ACodec.AAC, 24, false, true));
    }

    public YouTubeExtractor(Context con)
    {
//        context = con;
        weakContext = new WeakReference<>(con);
    }

    @Override
    protected void onPostExecute(SparseArray<YtFile> ytFiles)
    {
        onExtractionComplete(ytFiles, videoMeta);
    }


    /**
     * Start the extraction.
     *
     * @param youtubeLink       the youtube page link or video id
     * @param parseDashManifest true if the dash manifest should be downloaded and parsed
     * @param includeWebM       true if WebM streams should be extracted
     */
    public void extract(String youtubeLink, boolean parseDashManifest, boolean includeWebM)
    {
        this.parseDashManifest = parseDashManifest;
        this.includeWebM = includeWebM;
        this.execute(youtubeLink);
    }

    protected abstract void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta videoMeta);

    @Override
    protected SparseArray<YtFile> doInBackground(String... params)
    {
        videoID = null;
        String ytUrl = params[0];
        if (ytUrl == null) {
            return null;
        }
        Matcher mat = patYouTubePageLink.matcher(ytUrl);
        if (mat.find()) {
            videoID = mat.group(3);
        } else {
            mat = patYouTubeShortLink.matcher(ytUrl);
            if (mat.find()) {
                videoID = mat.group(3);
            } else if (ytUrl.matches("\\p{Graph}+?")) {
                videoID = ytUrl;
            }
        }
        if (videoID != null) {
            try {
                return getStreamUrls();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.e(LOG_TAG, "Wrong YouTube link format");
        }
        return null;
    }

    private SparseArray<YtFile> getStreamUrls() throws IOException, InterruptedException
    {

        String ytInfoUrl = (useHttp) ? "http://" : "https://";
        ytInfoUrl += "www.youtube.com/get_video_info?video_id=" + videoID + "&eurl="
                + URLEncoder.encode("https://youtube.googleapis.com/v/" + videoID, "UTF-8");

        String dashMpdUrl = null;
        String streamMap = null;
        BufferedReader reader = null;
        URL getUrl = new URL(ytInfoUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) getUrl.openConnection();
        urlConnection.setRequestProperty("User-Agent", USER_AGENT);
        try {
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            streamMap = reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null)
                reader.close();
            urlConnection.disconnect();
        }
        Matcher mat;
        String curJsFileName = null;
        String[] streams;
        SparseArray<String> encSignatures = null;

        parseVideoMeta(streamMap);

        if (videoMeta.isLiveStream()) {
            mat = patHlsvp.matcher(streamMap);
            if (mat.find()) {
                String hlsvp = URLDecoder.decode(mat.group(1), "UTF-8");
                SparseArray<YtFile> ytFiles = new SparseArray<>();

                getUrl = new URL(hlsvp);
                urlConnection = (HttpURLConnection) getUrl.openConnection();
                urlConnection.setRequestProperty("User-Agent", USER_AGENT);
                try {
                    reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("https://") || line.startsWith("http://")) {
                            mat = patHlsItag.matcher(line);
                            if (mat.find()) {
                                int itag = Integer.parseInt(mat.group(1));
                                YtFile newFile = new YtFile(FORMAT_MAP.get(itag), line);
                                ytFiles.put(itag, newFile);
                            }
                        }
                    }
                } finally {
                    if (reader != null)
                        reader.close();
                    urlConnection.disconnect();
                }

                if (ytFiles.size() == 0) {
                    if (LOGGING)
                        Log.d(LOG_TAG, streamMap);
                    return null;
                }
                return ytFiles;
            }
            return null;
        }


        // Some videos are using a ciphered signature we need to get the
        // deciphering js-file from the youtubepage.
        if (streamMap == null || !streamMap.contains("use_cipher_signature=False")) {
            // Get the video directly from the youtubepage
            if (CACHING
                    && (decipherJsFileName == null || decipherFunctions == null || decipherFunctionName == null)) {
                readDecipherFunctFromCache();
            }
            getUrl = new URL("https://youtube.com/watch?v=" + videoID);
            urlConnection = (HttpURLConnection) getUrl.openConnection();
            urlConnection.setRequestProperty("User-Agent", USER_AGENT);
            try {
                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    // Log.d("line", line);
                    if (line.contains("url_encoded_fmt_stream_map")) {
                        streamMap = line.replace("\\u0026", "&");
                        break;
                    }
                }
            } finally {
                if (reader != null)
                    reader.close();
                urlConnection.disconnect();
            }
            encSignatures = new SparseArray<>();

            mat = patDecryptionJsFile.matcher(streamMap);
            if (mat.find()) {
                curJsFileName = mat.group(1).replace("\\/", "/");
                if (decipherJsFileName == null || !decipherJsFileName.equals(curJsFileName)) {
                    decipherFunctions = null;
                    decipherFunctionName = null;
                }
                decipherJsFileName = curJsFileName;
            }

            if (parseDashManifest) {
                mat = patDashManifest2.matcher(streamMap);
                if (mat.find()) {
                    dashMpdUrl = mat.group(1).replace("\\/", "/");
                    mat = patDashManifestEncSig.matcher(dashMpdUrl);
                    if (mat.find()) {
                        encSignatures.append(0, mat.group(1));
                    } else {
                        dashMpdUrl = null;
                    }
                }
            }
        } else {
            if (parseDashManifest) {
                mat = patDashManifest1.matcher(streamMap);
                if (mat.find()) {
                    dashMpdUrl = URLDecoder.decode(mat.group(1), "UTF-8");
                }
            }
            streamMap = URLDecoder.decode(streamMap, "UTF-8");
        }

        streams = streamMap.split(",|url_encoded_fmt_stream_map|&adaptive_fmts=");
        SparseArray<YtFile> ytFiles = new SparseArray<>();
        for (String encStream : streams) {
            encStream = encStream + ",";
            if (!encStream.contains("itag%3D")) {
                continue;
            }
            String stream;
            stream = URLDecoder.decode(encStream, "UTF-8");

            mat = patItag.matcher(stream);
            int itag;
            if (mat.find()) {
                itag = Integer.parseInt(mat.group(1));
                if (LOGGING)
                    Log.d(LOG_TAG, "Itag found:" + itag);
                if (FORMAT_MAP.get(itag) == null) {
                    if (LOGGING)
                        Log.d(LOG_TAG, "Itag not in list:" + itag);
                    continue;
                } else if (!includeWebM && FORMAT_MAP.get(itag).getExt().equals("webm")) {
                    continue;
                }
            } else {
                continue;
            }

            if (curJsFileName != null) {
                mat = patEncSig.matcher(stream);
                if (mat.find()) {
                    encSignatures.append(itag, mat.group(1));
                }
            }
            mat = patUrl.matcher(encStream);
            String url = null;
            if (mat.find()) {
                url = mat.group(1);
            }

            if (url != null) {
                Format format = FORMAT_MAP.get(itag);
                String finalUrl = URLDecoder.decode(url, "UTF-8");
                YtFile newVideo = new YtFile(format, finalUrl);
                ytFiles.put(itag, newVideo);
            }
        }

        if (encSignatures != null) {
            if (LOGGING)
                Log.d(LOG_TAG, "Decipher signatures");
            String signature;
            decipheredSignature = null;
            if (decipherSignature(encSignatures)) {
                lock.lock();
                try {
                    jsExecuting.await(7, TimeUnit.SECONDS);
                } finally {
                    lock.unlock();
                }
            }
            signature = decipheredSignature;
            if (signature == null) {
                return null;
            } else {
                String[] sigs = signature.split("\n");
                for (int i = 0; i < encSignatures.size() && i < sigs.length; i++) {
                    int key = encSignatures.keyAt(i);
                    if (key == 0) {
                        dashMpdUrl = dashMpdUrl.replace("/s/" + encSignatures.get(key), "/signature/" + sigs[i]);
                    } else {
                        String url = ytFiles.get(key).getUrl();
                        url += "&signature=" + sigs[i];
                        YtFile newFile = new YtFile(FORMAT_MAP.get(key), url);
                        ytFiles.put(key, newFile);
                    }
                }
            }
        }

        if (parseDashManifest && dashMpdUrl != null) {
            for (int i = 0; i < DASH_PARSE_RETRIES; i++) {
                try {
                    // It sometimes fails to connect for no apparent reason. We just retry.
                    parseDashManifest(dashMpdUrl, ytFiles);
                    break;
                } catch (IOException io) {
                    Thread.sleep(5);
                    if (LOGGING)
                        Log.d(LOG_TAG, "Failed to parse dash manifest " + (i + 1));
                }
            }
        }

        if (ytFiles.size() == 0) {
            if (LOGGING)
                Log.d(LOG_TAG, streamMap);
            return null;
        }
        return ytFiles;
    }

    private boolean decipherSignature(final SparseArray<String> encSignatures) throws IOException
    {
        // Assume the functions don't change that much
        if (decipherFunctionName == null || decipherFunctions == null) {
            String decipherFunctUrl = "https://s.ytimg.com/yts/jsbin/" + decipherJsFileName;

            BufferedReader reader = null;
            String javascriptFile = null;
            URL url = new URL(decipherFunctUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("User-Agent", USER_AGENT);
            try {
                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder sb = new StringBuilder("");
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append(" ");
                }
                javascriptFile = sb.toString();
            } finally {
                if (reader != null)
                    reader.close();
                urlConnection.disconnect();
            }

            if (LOGGING)
                Log.d(LOG_TAG, "Decipher FunctionURL: " + decipherFunctUrl);
            Matcher mat = patSignatureDecFunction.matcher(javascriptFile);
            if (mat.find()) {
                decipherFunctionName = mat.group(2);
                if (LOGGING)
                    Log.d(LOG_TAG, "Decipher FunctionName: " + decipherFunctionName);

                Pattern patMainVariable = Pattern.compile("(var |\\s|,|;)" + decipherFunctionName.replace("$", "\\$") +
                        "(=function\\((.{1,3})\\)\\{)");

                String mainDecipherFunct;

                mat = patMainVariable.matcher(javascriptFile);
                if (mat.find()) {
                    mainDecipherFunct = "var " + decipherFunctionName + mat.group(2);
                } else {
                    Pattern patMainFunction = Pattern.compile("function " + decipherFunctionName.replace("$", "\\$") +
                            "(\\((.{1,3})\\)\\{)");
                    mat = patMainFunction.matcher(javascriptFile);
                    if (!mat.find())
                        return false;
                    mainDecipherFunct = "function " + decipherFunctionName + mat.group(2);
                }

                int startIndex = mat.end();

                for (int braces = 1, i = startIndex; i < javascriptFile.length(); i++) {
                    if (braces == 0 && startIndex + 5 < i) {
                        mainDecipherFunct += javascriptFile.substring(startIndex, i) + ";";
                        break;
                    }
                    if (javascriptFile.charAt(i) == '{')
                        braces++;
                    else if (javascriptFile.charAt(i) == '}')
                        braces--;
                }
                decipherFunctions = mainDecipherFunct;
                // Search the main function for extra functions and variables
                // needed for deciphering
                // Search for variables
                mat = patVariableFunction.matcher(mainDecipherFunct);
                while (mat.find()) {
                    String variableDef = "var " + mat.group(2) + "={";
                    if (decipherFunctions.contains(variableDef)) {
                        continue;
                    }
                    startIndex = javascriptFile.indexOf(variableDef) + variableDef.length();
                    for (int braces = 1, i = startIndex; i < javascriptFile.length(); i++) {
                        if (braces == 0) {
                            decipherFunctions += variableDef + javascriptFile.substring(startIndex, i) + ";";
                            break;
                        }
                        if (javascriptFile.charAt(i) == '{')
                            braces++;
                        else if (javascriptFile.charAt(i) == '}')
                            braces--;
                    }
                }
                // Search for functions
                mat = patFunction.matcher(mainDecipherFunct);
                while (mat.find()) {
                    String functionDef = "function " + mat.group(2) + "(";
                    if (decipherFunctions.contains(functionDef)) {
                        continue;
                    }
                    startIndex = javascriptFile.indexOf(functionDef) + functionDef.length();
                    for (int braces = 0, i = startIndex; i < javascriptFile.length(); i++) {
                        if (braces == 0 && startIndex + 5 < i) {
                            decipherFunctions += functionDef + javascriptFile.substring(startIndex, i) + ";";
                            break;
                        }
                        if (javascriptFile.charAt(i) == '{')
                            braces++;
                        else if (javascriptFile.charAt(i) == '}')
                            braces--;
                    }
                }

                if (LOGGING)
                    Log.d(LOG_TAG, "Decipher Function: " + decipherFunctions);
                decipherViaWebView(encSignatures);
                if (CACHING) {
                    writeDeciperFunctToChache();
                }
            } else {
                return false;
            }
        } else {
            decipherViaWebView(encSignatures);
        }
        return true;
    }

    private void parseDashManifest(String dashMpdUrl, SparseArray<YtFile> ytFiles) throws IOException
    {
        Pattern patBaseUrl = Pattern.compile("<BaseURL yt:contentLength=\"[0-9]+?\">(.+?)</BaseURL>");
        String dashManifest;
        BufferedReader reader = null;
        URL getUrl = new URL(dashMpdUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) getUrl.openConnection();
        urlConnection.setRequestProperty("User-Agent", USER_AGENT);
        try {
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            reader.readLine();
            dashManifest = reader.readLine();

        } finally {
            if (reader != null)
                reader.close();
            urlConnection.disconnect();
        }
        if (dashManifest == null)
            return;
        Matcher mat = patBaseUrl.matcher(dashManifest);
        while (mat.find()) {
            int itag;
            String url = mat.group(1);
            Matcher mat2 = patItag.matcher(url);
            if (mat2.find()) {
                itag = Integer.parseInt(mat2.group(1));
                if (FORMAT_MAP.get(itag) == null)
                    continue;
                if (!includeWebM && FORMAT_MAP.get(itag).getExt().equals("webm"))
                    continue;
            } else {
                continue;
            }
            url = url.replace("&amp;", "&").replace(",", "%2C").
                    replace("mime=audio/", "mime=audio%2F").
                    replace("mime=video/", "mime=video%2F");
            YtFile yf = new YtFile(FORMAT_MAP.get(itag), url);
            ytFiles.append(itag, yf);
        }

    }

    private void parseVideoMeta(String getVideoInfo) throws UnsupportedEncodingException
    {
        boolean isLiveStream = false;
        String title = null, author = null, channelId = null;
        long viewCount = 0, length = 0;
        Matcher mat = patTitle.matcher(getVideoInfo);
        if (mat.find()) {
            title = URLDecoder.decode(mat.group(1), "UTF-8");
        }

        mat = patHlsvp.matcher(getVideoInfo);
        if (mat.find())
            isLiveStream = true;

        mat = patAuthor.matcher(getVideoInfo);
        if (mat.find()) {
            author = URLDecoder.decode(mat.group(1), "UTF-8");
        }
        mat = patChannelId.matcher(getVideoInfo);
        if (mat.find()) {
            channelId = mat.group(1);
        }
        mat = patLength.matcher(getVideoInfo);
        if (mat.find()) {
            length = Long.parseLong(mat.group(1));
        }
        mat = patViewCount.matcher(getVideoInfo);
        if (mat.find()) {
            viewCount = Long.parseLong(mat.group(1));
        }
        videoMeta = new VideoMeta(videoID, title, author, channelId, length, viewCount, isLiveStream);

    }

    private void readDecipherFunctFromCache()
    {
        Context context = weakContext.get();
        if (context != null) {
            File cacheFile = new File(context.getCacheDir().getAbsolutePath() + "/" + CACHE_FILE_NAME);
            // The cached functions are valid for 2 weeks
            if (cacheFile.exists() && (System.currentTimeMillis() - cacheFile.lastModified()) < 1209600000) {
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(new FileInputStream(cacheFile), "UTF-8"));
                    decipherJsFileName = reader.readLine();
                    decipherFunctionName = reader.readLine();
                    decipherFunctions = reader.readLine();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * Parse the dash manifest for different dash streams and high quality audio. Default: false
     */
    public void setParseDashManifest(boolean parseDashManifest)
    {
        this.parseDashManifest = parseDashManifest;
    }


    /**
     * Include the webm format files into the result. Default: true
     */
    public void setIncludeWebM(boolean includeWebM)
    {
        this.includeWebM = includeWebM;
    }


    /**
     * Set default protocol of the returned urls to HTTP instead of HTTPS.
     * HTTP may be blocked in some regions so HTTPS is the default value.
     * <p/>
     * Note: Enciphered videos require HTTPS so they are not affected by
     * this.
     */
    public void setDefaultHttpProtocol(boolean useHttp)
    {
        this.useHttp = useHttp;
    }

    private void writeDeciperFunctToChache()
    {
        Context context = weakContext.get();
        if (context != null) {
            File cacheFile = new File(context.getCacheDir().getAbsolutePath() + "/" + CACHE_FILE_NAME);
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cacheFile), "UTF-8"));
                writer.write(decipherJsFileName + "\n");
                writer.write(decipherFunctionName + "\n");
                writer.write(decipherFunctions);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void decipherViaWebView(final SparseArray<String> encSignatures)
    {
        final Context context = weakContext.get();
        if (context == null) {
            return;
        }

        final StringBuilder stb = new StringBuilder(decipherFunctions + " function decipher(");
        stb.append("){return ");

        for (int i = 0; i < encSignatures.size(); i++) {
            int key = encSignatures.keyAt(i);
            if (i < encSignatures.size() - 1) {
                stb.append(decipherFunctionName)
                        .append("('")
                        .append(encSignatures.get(key))
                        .append("')+\"\\n\"+");
            } else {
                stb.append(decipherFunctionName)
                        .append("('")
                        .append(encSignatures.get(key))
                        .append("')");
            }
        }
        stb.append("};decipher();");

        new Handler(Looper.getMainLooper()).post(() -> {
            JsEvaluator js = new JsEvaluator(context);
            js.evaluate(stb.toString(), new JsCallback() {
                @Override
                public void onResult(String s) {
                    lock.lock();
                    try {
                        decipheredSignature = s;
                        jsExecuting.signal();
                    } finally {
                        lock.unlock();
                    }
                }

                @Override
                public void onError(String s) {

                }
            });
        });
    }

}