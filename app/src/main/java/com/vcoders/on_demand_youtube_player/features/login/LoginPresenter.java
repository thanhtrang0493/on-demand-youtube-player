package com.vcoders.on_demand_youtube_player.features.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.auth.AuthEvent;
import com.vcoders.on_demand_youtube_player.auth.AuthException;
import com.vcoders.on_demand_youtube_player.auth.AuthLoginListener;
import com.vcoders.on_demand_youtube_player.auth.AuthLogoutListener;
import com.vcoders.on_demand_youtube_player.auth.AuthRepo;
import com.vcoders.on_demand_youtube_player.auth.YoutubeApp;

import javax.inject.Inject;

import static android.content.ContentValues.TAG;


public class LoginPresenter extends BasePresenter<LoginView, LoginRouter> {

    private Context context;
    public YoutubeApp app;
    private AuthRepo authRepo;

    @Inject
    public LoginPresenter(Context context){
        this.context= context;
        app = (YoutubeApp) context.getApplicationContext();
        authRepo = app.getAuthRepo();
    }

    public void login() {
        authRepo.login(loginListener);
    }

    private final AuthLoginListener loginListener =  new AuthLoginListener() {
        public void onStart(AuthRepo repo, AuthEvent event) {
            String description = event.getDescription();
            Log.i(TAG, description);
//            progressObservable.postValue(new ProgressState(true, description));
//            actionObservable.postValue(new ActionState(app.getString(R.string.search_title),
//                    false, false, false, true, null));
        }

        public void onEvent(AuthRepo repo, AuthEvent event) {
            String description = event.getDescription();
            switch (event) {
                case AUTH_SERVICE_DISCOVERY_START:
                    Log.i(TAG, description);
//                    progressObservable.postValue(new ProgressState(true, description));
                    break;
                case AUTH_SERVICE_DISCOVERY_FINISH:
                    Log.i(TAG, description);
//                    progressObservable.postValue(new ProgressState());
                    break;
                case AUTH_USER_AUTH_START:
                    Log.i(TAG, description);
//                    progressObservable.postValue(new ProgressState(true, description));
                    break;
                case AUTH_USER_AUTH_FINISH:
                    Log.i(TAG, description);
//                    progressObservable.postValue(new ProgressState());
                    break;
                case AUTH_CODE_EXCHANGE_START:
                    Log.i(TAG, description);
//                    progressObservable.postValue(new ProgressState(true, description));
                    break;
                case AUTH_CODE_EXCHANGE_FINISH:
                    Log.i(TAG, description);
//                    progressObservable.postValue(new ProgressState());
                    break;
                case AUTH_USER_INFO_START:
                    Log.i(TAG, description);
//                    progressObservable.postValue(new ProgressState(true, description));
                    break;
                case AUTH_USER_INFO_FINISH:
                    Log.i(TAG, description);
//                    progressObservable.postValue(new ProgressState());
                    break;
                default:
                    Log.i(TAG, description);
//                    progressObservable.postValue(new ProgressState());
                    break;
            }
        }

        public void onUserAgentRequest(AuthRepo repo, Intent intent) {

            Log.i(TAG, "User Agent Request!");
            ((Activity)context).startActivityForResult(intent, app.RC_AUTH);
//            activityObservable.postValue(new ActivityRequest(intent, app.RC_AUTH));
        }

        public void onSuccess(AuthRepo repo, AuthEvent event) {
            String description = event.getDescription();
            Log.i(TAG, description);
//            actionObservable.postValue(new ActionState(app.getString(R.string.search_title),
//                    true, true, true, false, null));
//            progressObservable.postValue(new ProgressState());
        }

        public void onFailure(AuthRepo repo, AuthEvent event, AuthException ex) {
            String description = event.getDescription() + ": " + ex.getMessage();
            Log.i(TAG, description);
//            alertObservable.postValue(new AlertTrigger(description, null));
//            actionObservable.postValue(new ActionState(app.getString(R.string.search_title),
//                    true, false, true, true, null));
//            progressObservable.postValue(new ProgressState());
        }
    };

    public void logout() {
        authRepo.logout(logoutListener);
    }

    private final AuthLogoutListener logoutListener =  new AuthLogoutListener() {
        public void onStart(AuthRepo repo, AuthEvent event) {
            String description = event.getDescription();
            Log.i(TAG, description);
//            progressObservable.postValue(new ProgressState(true, description));
//            actionObservable.postValue(new ActionState(app.getString(R.string.search_title),
//                    false, false, false, false, null));
        }

        public void onSuccess(AuthRepo repo, AuthEvent event) {
            String description = event.getDescription();
            Log.i(TAG, description);
//            actionObservable.postValue(new ActionState(app.getString(R.string.search_title),
//                    true, false, true, true, null));
//            progressObservable.postValue(new ProgressState());
        }

        public void onFailure(AuthRepo repo, AuthEvent event, AuthException ex) {
            String description = event.getDescription() + ": " + ex.getMessage();
            Log.i(TAG, description);
//            actionObservable.postValue(new ActionState(app.getString(R.string.search_title),
//                    true, true, true, false, null));
//            progressObservable.postValue(new ProgressState());
//            alertObservable.postValue(new AlertTrigger(description, null));
        }
    };

    public void notifyActivityResponse(Intent data, int resultCode) {
        authRepo.notifyUserAgentResponse(data, resultCode);
    }

}
