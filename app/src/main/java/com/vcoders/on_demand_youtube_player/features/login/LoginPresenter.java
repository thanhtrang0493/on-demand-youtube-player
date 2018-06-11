package com.vcoders.on_demand_youtube_player.features.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.auth.AuthEvent;
import com.vcoders.on_demand_youtube_player.auth.AuthException;
import com.vcoders.on_demand_youtube_player.auth.AuthLoginListener;
import com.vcoders.on_demand_youtube_player.auth.AuthLogoutListener;
import com.vcoders.on_demand_youtube_player.auth.AuthRepo;
import com.vcoders.on_demand_youtube_player.auth.AuthResponse;
import com.vcoders.on_demand_youtube_player.auth.YoutubeApp;
import com.vcoders.on_demand_youtube_player.utils.AccountUtils;

import javax.inject.Inject;


public class LoginPresenter extends BasePresenter<LoginView, LoginRouter> {

    private Context context;
    public YoutubeApp app;
    private AuthRepo authRepo;

    @Inject
    public LoginPresenter(Context context) {
        this.context = context;
        app = (YoutubeApp) context.getApplicationContext();
        authRepo = app.getAuthRepo();
    }

    public void login() {
        authRepo.login(loginListener);
    }

    private final AuthLoginListener loginListener = new AuthLoginListener() {
        public void onStart(AuthRepo repo, AuthEvent event) {
        }

        public void onEvent(AuthRepo repo, AuthEvent event) {
            String description = event.getDescription();
            switch (event) {
                case AUTH_SERVICE_DISCOVERY_START:
                    break;
                case AUTH_SERVICE_DISCOVERY_FINISH:
                    break;
                case AUTH_USER_AUTH_START:
                    break;
                case AUTH_USER_AUTH_FINISH:
                    break;
                case AUTH_CODE_EXCHANGE_START:
                    break;
                case AUTH_CODE_EXCHANGE_FINISH:
                    break;
                case AUTH_USER_INFO_START:
                    break;
                case AUTH_USER_INFO_FINISH:
                    break;
                default:
                    break;
            }
        }

        public void onUserAgentRequest(AuthRepo repo, Intent intent) {
            ((Activity) context).startActivityForResult(intent, app.RC_AUTH);
        }

        public void onSuccess(AuthRepo repo, AuthEvent event, AuthResponse response) {
            if (response != null) {
                AccountUtils.getInstance().setToken(response.getToken());
                AccountUtils.getInstance().setPackageName(response.getPackageName());
                AccountUtils.getInstance().setSignature(response.getSignature());
                AccountUtils.getInstance().setLogin(true);
                ((Activity) context).finish();
            } else {
                getView().showError(event.getDescription());
            }
        }

        public void onFailure(AuthRepo repo, AuthEvent event, AuthException ex) {
            String description = event.getDescription() + ": " + ex.getMessage();
            getView().showError(description);
        }
    };

    public void logout() {
        authRepo.logout(logoutListener);
    }

    private final AuthLogoutListener logoutListener = new AuthLogoutListener() {
        public void onStart(AuthRepo repo, AuthEvent event) {
            String description = event.getDescription();
        }

        public void onSuccess(AuthRepo repo, AuthEvent event) {
            AccountUtils.getInstance().setToken(null);
            AccountUtils.getInstance().setPackageName(null);
            AccountUtils.getInstance().setSignature(null);
            AccountUtils.getInstance().setLogin(false);
        }

        public void onFailure(AuthRepo repo, AuthEvent event, AuthException ex) {
            String description = event.getDescription() + ": " + ex.getMessage();
            getView().showError(description);
        }
    };

    public void notifyActivityResponse(Intent data, int resultCode) {
        authRepo.notifyUserAgentResponse(data, resultCode);
    }

}
