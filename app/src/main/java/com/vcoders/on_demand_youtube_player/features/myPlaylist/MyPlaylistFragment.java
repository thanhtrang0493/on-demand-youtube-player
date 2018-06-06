package com.vcoders.on_demand_youtube_player.features.myPlaylist;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.common.io.BaseEncoding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.adapter.GridPlayListAdapter;
import com.vcoders.on_demand_youtube_player.adapter.ListPlayListAdapter;
import com.vcoders.on_demand_youtube_player.adapter.RecentlyPlayedAdapter;
import com.vcoders.on_demand_youtube_player.architecture.BaseFragment;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.enums.TypeActionBar;
import com.vcoders.on_demand_youtube_player.features.home.HomeActivity;
import com.vcoders.on_demand_youtube_player.features.home.HomeComponent;
import com.vcoders.on_demand_youtube_player.features.playlistDetail.PlaylistDetailFragment;
import com.vcoders.on_demand_youtube_player.model.PlayList;
import com.vcoders.on_demand_youtube_player.model.Video;

import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.TokenResponse;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;


public class MyPlaylistFragment extends BaseFragment<HomeComponent> implements MyPlaylistView {

    @Inject
    MyPlaylistPresenter myPlaylistPresenter;

    @Inject
    MyPlaylistRouter myPlaylistRouter;

    @BindView(R.id.rvRecentlyPlayed)
    RecyclerView rvRecentlyPlayed;

    @BindView(R.id.rvPlaylist)
    RecyclerView rvPlaylist;

    RecentlyPlayedAdapter recentlyPlayedAdapter;
    ListPlayListAdapter listPlayListAdapter;
    GridPlayListAdapter gridPlayListAdapter;
    List<PlayList> playListRecentlies;
    List<Video> playLists;

    @Override
    protected void initializeView(Bundle savedInstanceState) {
        playListRecentlies = myPlaylistPresenter.getListPlayListRecently();
        playLists = myPlaylistPresenter.getListPlayList();

//        initRecentlyPlayedAdapter();
//        initPlayListAdapter();

        myPlaylistPresenter.getMyPlaylist();
    }

    private void initRecentlyPlayedAdapter() {
        recentlyPlayedAdapter = new RecentlyPlayedAdapter(getActivity(), playListRecentlies);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvRecentlyPlayed.setLayoutManager(manager);
        rvRecentlyPlayed.setAdapter(recentlyPlayedAdapter);
    }

    private void initPlayListAdapter() {
        listPlayListAdapter = new ListPlayListAdapter(getActivity(), playLists, this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvPlaylist.setLayoutManager(manager);
        rvPlaylist.setAdapter(listPlayListAdapter);
        rvPlaylist.setPadding(0, 0, 0, 0);
    }

    private void initGridPlayListAdapter() {
        gridPlayListAdapter = new GridPlayListAdapter(getActivity(), playLists, this);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        rvPlaylist.setLayoutManager(manager);
        rvPlaylist.setAdapter(gridPlayListAdapter);
        rvPlaylist.setPadding(10, 0, 10, 0);
    }

    @Override
    protected int getViewResource() {
        return R.layout.fragment_my_playlist;
    }

    @Override
    protected String getTitleActionBar() {
        return null;
    }

    @Override
    protected TypeActionBar[] getTypeActionBar() {
        return new TypeActionBar[0];
    }

    @Override
    protected BasePresenter getPresenter() {
        return myPlaylistPresenter;
    }

    @Override
    protected BaseRouter getRouter() {
        return myPlaylistRouter;
    }

    @Override
    protected void inject() {
        ((HomeComponent) getAcitivyComponent()).inject(this);
    }

    @OnClick(R.id.imgGrid)
    public void onImgGridClick() {
        initGridPlayListAdapter();
    }

    @OnClick(R.id.imgList)
    public void onImgListClick() {
        initPlayListAdapter();
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showLoading(boolean isShow) {

    }

    @Override
    public void onSelectPlayList(int position) {
        ((HomeActivity) getActivity()).changeFragment(new PlaylistDetailFragment(), null);
    }

    @Override
    public void onMorePlayList(int position) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200){
            AuthorizationResponse resp = AuthorizationResponse.fromIntent(data);
        }
    }

    public void notifyUserAgentResponse(Intent data, int returnCode) {
        if (returnCode != 200) {
            Toast.makeText(getActivity(),"User authorization was cancelled", Toast.LENGTH_SHORT).show();
            return;
        }

        AuthorizationResponse resp = AuthorizationResponse.fromIntent(data);
        AuthorizationException ex = AuthorizationException.fromIntent(data);
        if (resp == null) {
            Toast.makeText(getActivity(),"User authorization failed", Toast.LENGTH_SHORT).show();
            return;
        } else {
            myPlaylistPresenter.authState.update(resp, ex);
            finishUserAuth();
        }
    }

    private void finishUserAuth() {
        Log.i(TAG, "Finishing user auth");

//        loginListener.onEvent(AuthRepo.this, AUTH_USER_AUTH_FINISH);

        startCodeExchange();
    }

    private void startCodeExchange() {
        Log.i(TAG, "Starting code exchange");

//        loginListener.onEvent(AuthRepo.this, AUTH_CODE_EXCHANGE_START);

        AuthorizationResponse resp = myPlaylistPresenter.authState.getLastAuthorizationResponse();
        myPlaylistPresenter.authService.performTokenRequest(
                resp.createTokenExchangeRequest(), this::onTokenRequestCompleted);
    }

    private void onTokenRequestCompleted(TokenResponse resp, AuthorizationException ex) {
        if (resp == null) {
            Toast.makeText(getActivity(),ex.getMessage() ,Toast.LENGTH_SHORT).show();
            return;
        }

        myPlaylistPresenter.authState.update(resp, ex);
        finishCodeExchange();
    }

    private void finishCodeExchange() {
        Log.i(TAG, "Finishing code exchange");

//        loginListener.onEvent(AuthRepo.this, AUTH_CODE_EXCHANGE_FINISH);

        startUserInfo();
    }

    private void startUserInfo() {
        Log.i(TAG, "Starting user info");

//        loginListener.onEvent(AuthRepo.this, AUTH_USER_INFO_START);
        fetchUserInfo(this::onUserInfoCompleted);
    }

    private void onUserInfoCompleted(UserInfo userInfo, AuthException ex) {
        if (userInfo == null) Log.i(TAG, "Unable to obtain user info.");

        finishUserInfo();
    }

    private void finishUserInfo() {
        Log.i(TAG, "Finishing user info");

//        loginListener.onEvent(AuthRepo.this, AUTH_USER_INFO_FINISH);

        finishLogin();
    }

    private void failLogin(AuthException ex) {
        Log.i(TAG, "Failing login");

//        loginListener.onFailure(AuthRepo.this, AUTH_LOGIN_FAILURE, ex);

//        unlockLogins();
    }

    private void finishLogin() {
        Log.i(TAG, "Finishing login");

//        loginListener.onSuccess(AuthRepo.this, AUTH_LOGIN_SUCCESS);

//        unlockLogins();
    }

    public void fetchUserInfo(UserInfoCallback callback) {
        if (callback == null) throw new RuntimeException("fetchUserInfo: null callback");

        if (!isAuthorized()) {
            callback.call(null, new AuthException("Not authorized"));
            return;
        }

        new UserInfoTask(callback).execute();
    }

    public String getSignature() {
        PackageManager pm = getActivity().getPackageManager();
        String packageName = getActivity().getPackageName();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            if (packageInfo == null
                    || packageInfo.signatures == null
                    || packageInfo.signatures.length == 0
                    || packageInfo.signatures[0] == null) {
                return null;
            }
            return signatureDigest(packageInfo.signatures[0]);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    private static String signatureDigest(Signature sig) {
        byte[] signature = sig.toByteArray();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] digest = md.digest(signature);
            return BaseEncoding.base16().lowerCase().encode(digest);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public Interceptor getAccessTokenInterceptor() {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                request = request.newBuilder()
                        .header("X-Android-Package", getActivity().getPackageName())
                        .header("X-Android-Cert", getSignature())
                        .header("Authorization", "Bearer " + getAccessToken())
                        .build();

                Log.i(TAG, "token: " + getAccessToken());

                return chain.proceed(request);
            }
        };
    }

    public boolean isAuthorized() {
        return (myPlaylistPresenter.authState != null && myPlaylistPresenter.authState.isAuthorized());
    }

    private String accessToken;
    private String getAccessToken() {
        if (!isAuthorized()) return null;

        CountDownLatch actionComplete = new CountDownLatch(1);

        myPlaylistPresenter.authState.performActionWithFreshTokens(myPlaylistPresenter.authService, (String authToken,
                                                             String idToken,
                                                             AuthorizationException ex) -> {
            accessToken = authToken;
            actionComplete.countDown();
        });

        boolean complete;
        try {
            complete = actionComplete.await(5000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            complete = false;
        }
        if (!complete) accessToken = null;

        String token = accessToken;
        this.accessToken = null;
        return token;
    }

    private UserInfoAPI createUserInfoAPI() {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient authClient = new OkHttpClient().newBuilder()
                .addInterceptor(getAccessTokenInterceptor())
                .addInterceptor(logger)
                .build();

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(myPlaylistPresenter.userInfoUrl)
                .client(authClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(UserInfoAPI.class);
    }

    private UserInfo userInfo;

    class UserInfoTask extends AsyncTask<Void, Void, UserInfo> {
        private UserInfoCallback callback;
        UserInfoTask(UserInfoCallback callback) { this.callback = callback; }

        protected UserInfo doInBackground(Void... params) {
            UserInfoAPI userInfoAPI = createUserInfoAPI();
            Call<UserInfoResult> call = userInfoAPI.getUserInfo();
            try {
                Response<UserInfoResult> response = call.execute();

                if (response.isSuccessful()) {
                    UserInfoResult result = response.body();
                    userInfo = new UserInfo(result.getFamilyName(), result.getGivenName(),
                            result.getPicture());
                } else {
                    userInfo = null;
                }
            } catch (IOException e) {
                userInfo = null;
            }

            return userInfo;
        }

        protected void onPostExecute(UserInfo userInfo) {
            if (userInfo == null) {
                callback.call(null, new AuthException("Unable to retrieve user info"));
                return;
            }

            callback.call(userInfo, null);
        }
    }

}
