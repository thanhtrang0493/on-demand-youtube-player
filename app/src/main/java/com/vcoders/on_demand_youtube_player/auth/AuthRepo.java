package com.vcoders.on_demand_youtube_player.auth;

import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.util.Log;
import android.webkit.URLUtil;

import com.vcoders.on_demand_youtube_player.R;

import net.openid.appauth.AppAuthConfiguration;
import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.AuthorizationServiceDiscovery;
import net.openid.appauth.ResponseTypeValues;
import net.openid.appauth.TokenResponse;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static com.vcoders.on_demand_youtube_player.auth.AuthEvent.AUTH_CODE_EXCHANGE_START;
import static com.vcoders.on_demand_youtube_player.auth.AuthEvent.AUTH_LOGIN_FAILURE;
import static com.vcoders.on_demand_youtube_player.auth.AuthEvent.AUTH_LOGIN_START;
import static com.vcoders.on_demand_youtube_player.auth.AuthEvent.AUTH_LOGIN_SUCCESS;
import static com.vcoders.on_demand_youtube_player.auth.AuthEvent.AUTH_LOGOUT_START;
import static com.vcoders.on_demand_youtube_player.auth.AuthEvent.AUTH_LOGOUT_SUCCESS;
import static com.vcoders.on_demand_youtube_player.auth.AuthEvent.AUTH_SERVICE_DISCOVERY_FINISH;
import static com.vcoders.on_demand_youtube_player.auth.AuthEvent.AUTH_SERVICE_DISCOVERY_START;
import static com.vcoders.on_demand_youtube_player.auth.AuthEvent.AUTH_USER_AUTH_FINISH;
import static com.vcoders.on_demand_youtube_player.auth.AuthEvent.AUTH_USER_AUTH_START;


public class AuthRepo {
    private final String TAG = AuthRepo.class.getSimpleName();

    YoutubeApp app;

    private Semaphore loginLock;

    private AuthLoginListener loginListener;
    private AuthLogoutListener logoutListener;

    private AuthorizationService authService;
    private AuthState authState;
    private String userInfoUrl;
    String clientId;
    String redirectUri;
    String authScope;
    String authScopeProfile;

    public AuthRepo(YoutubeApp app) {
        this.app = app;

        loginLock = new Semaphore(1);

        loginListener = null;
        logoutListener = null;

        AppAuthConfiguration.Builder builder = new AppAuthConfiguration.Builder();
        authService = new AuthorizationService(app, builder.build());
        authState = null;
        userInfoUrl = null;
        clientId = null;
        redirectUri = null;
        authScope = null;
        authScopeProfile = null;
    }


    public boolean isConfigured() {
        return (authState != null &&
                authState.getAuthorizationServiceConfiguration() != null &&
                clientId != null &&
                redirectUri != null &&
                authScope != null && authScopeProfile != null);
    }

    public boolean isAuthorized() {
        return (authState != null && authState.isAuthorized());
    }

    private void lockLogins() {
        try {
            loginLock.acquire();
        } catch (InterruptedException ex) {
            throw new RuntimeException("Unexpected interrupt", ex);
        }
    }

    private void unlockLogins() {
        loginLock.release();
    }

    public void login(AuthLoginListener loginListener) {
        Log.i(TAG, "login called");

        lockLogins();

        if (isAuthorized()) {
            unlockLogins();
            return;
        }

        this.loginListener = loginListener;
        loginListener.onStart(AuthRepo.this, AUTH_LOGIN_START);

        if (!isConfigured()) {
            startServiceConfig();
        } else {
            startUserAuth();
        }
    }

    private void startServiceConfig() {
        Log.i(TAG, "Starting service config");

        String discoveryEndpoint = app.getString(R.string.discovery_endpoint);
        if (discoveryEndpoint.trim().length() == 0 || !URLUtil.isValidUrl(discoveryEndpoint)) {
            Log.i(TAG, "Using static service config");
            AuthorizationServiceConfiguration serviceConfig =
                    new AuthorizationServiceConfiguration(
                            Uri.parse(app.getString(R.string.authorization_endpoint)),
                            Uri.parse(app.getString(R.string.token_endpoint)));
            authState = new AuthState(serviceConfig);
            userInfoUrl = app.getString(R.string.user_info_endpoint);

            finishServiceConfig();
        } else {
            Log.i(TAG, "Using discovery service config");
            Uri discoveryUri = Uri.parse(discoveryEndpoint);
            loginListener.onEvent(AuthRepo.this, AUTH_SERVICE_DISCOVERY_START);

            AuthorizationServiceConfiguration.fetchFromUrl(discoveryUri, this::finishServiceDiscovery);
        }
    }

    private void finishServiceDiscovery(AuthorizationServiceConfiguration config,
                                        AuthorizationException ex) {
        if (config == null) {
            failLogin(new AuthException("Failed to retrieve authorization service discovery document"));
            return;
        }

        authState = new AuthState(config);
        AuthorizationServiceDiscovery discovery = config.discoveryDoc;
        userInfoUrl = discovery.getUserinfoEndpoint().toString();

        loginListener.onEvent(AuthRepo.this, AUTH_SERVICE_DISCOVERY_FINISH);

        finishServiceConfig();
    }

    private void finishServiceConfig() {
        URL test;
        try {
            test = new URL(userInfoUrl);
            if (!userInfoUrl.endsWith("/")) userInfoUrl += "/";
        } catch (MalformedURLException urlEx) {
            userInfoUrl = null;
        }

        Log.i(TAG, "Finishing service config");
        Log.i(TAG, "  authorization endpoint: " + authState.getAuthorizationServiceConfiguration().authorizationEndpoint);
        Log.i(TAG, "  token endpoint: " + authState.getAuthorizationServiceConfiguration().tokenEndpoint);
        Log.i(TAG, "  user info endpoint: " + userInfoUrl);

        startClientConfig();
    }

    private void startClientConfig() {
        Log.i(TAG, "Starting client config");

        clientId = app.getString(R.string.client_id);
        redirectUri = app.getString(R.string.redirect_uri);
        authScope = app.getString(R.string.authorization_scope);
        authScopeProfile = app.getString(R.string.authorization_scope_profile);

        finishClientConfig();
    }

    private void finishClientConfig() {
        Log.i(TAG, "Finishing client config");
        Log.i(TAG, "  client id: " + clientId);
        Log.i(TAG, "  redirect uri: " + redirectUri);
        Log.i(TAG, "  auth scope: " + authScope);

        startUserAuth();
    }

    private void startUserAuth() {
        Log.i(TAG, "Starting user auth");

        loginListener.onEvent(AuthRepo.this, AUTH_USER_AUTH_START);

        // may need to do this off UI thread?

        Collection<String> scopes = new ArrayList<>(Arrays.asList(authScope, authScopeProfile));
        AuthorizationRequest.Builder authRequestBuilder = new AuthorizationRequest.Builder(
                authState.getAuthorizationServiceConfiguration(),
                clientId,
                ResponseTypeValues.CODE,
                Uri.parse(redirectUri))
                .setScopes(scopes);
        AuthorizationRequest authRequest = authRequestBuilder.build();

        CustomTabsIntent.Builder intentBuilder =
                authService.createCustomTabsIntentBuilder(authRequest.toUri());
        intentBuilder.setToolbarColor(app.getColorValue(R.color.colorAccent));
        CustomTabsIntent authIntent = intentBuilder.build();

        Intent intent = authService.getAuthorizationRequestIntent(authRequest, authIntent);

        loginListener.onUserAgentRequest(AuthRepo.this, intent);
    }

    public void notifyUserAgentResponse(Intent data, int returnCode) {
        if (returnCode != app.RC_AUTH) {
            failLogin(new AuthException("User authorization was cancelled"));
            return;
        }

        AuthorizationResponse resp = AuthorizationResponse.fromIntent(data);
        AuthorizationException ex = AuthorizationException.fromIntent(data);
        if (resp == null) {
            failLogin(new AuthException("User authorization failed"));
            return;
        } else {
            authState.update(resp, ex);
            finishUserAuth();
        }
    }

    private void finishUserAuth() {
        Log.i(TAG, "Finishing user auth");

        loginListener.onEvent(AuthRepo.this, AUTH_USER_AUTH_FINISH);

        startCodeExchange();
    }

    private void startCodeExchange() {
        Log.i(TAG, "Starting code exchange");

        loginListener.onEvent(AuthRepo.this, AUTH_CODE_EXCHANGE_START);

        AuthorizationResponse resp = authState.getLastAuthorizationResponse();
        authService.performTokenRequest(
                resp.createTokenExchangeRequest(), this::onTokenRequestCompleted);
    }

    private void onTokenRequestCompleted(TokenResponse resp, AuthorizationException ex) {
        if (resp == null) {
            failLogin(new AuthException(ex.getMessage()));
            return;
        }

        authState.update(resp, ex);
        finishCodeExchange();
    }

    private void finishCodeExchange() {
        AuthResponse response = new AuthResponse();
        response.setToken(getAccessToken());
        response.setPackageName(app.getPackageName());
        response.setSignature(app.getSignature());
        loginListener.onSuccess(AuthRepo.this, AUTH_LOGIN_SUCCESS, response);

        unlockLogins();
    }

    private void failLogin(AuthException ex) {
        Log.i(TAG, "Failing login");

        loginListener.onFailure(AuthRepo.this, AUTH_LOGIN_FAILURE, ex);

        unlockLogins();
    }

    public void logout(AuthLogoutListener logoutListener) {
        lockLogins();

        if (!isAuthorized()) {
            unlockLogins();
            return;
        }

        logoutListener.onStart(AuthRepo.this, AUTH_LOGOUT_START);

        if (isConfigured()) {
            authState = new AuthState(authState.getAuthorizationServiceConfiguration());
        } else {
            authState = null;
            clientId = null;
            redirectUri = null;
            userInfoUrl = null;
        }

        logoutListener.onSuccess(AuthRepo.this, AUTH_LOGOUT_SUCCESS);

        unlockLogins();
    }

    private String accessToken;

    // dangerous; do not call on UI thread.
    private String getAccessToken() {
        if (!isAuthorized()) return null;

        CountDownLatch actionComplete = new CountDownLatch(1);

        authState.performActionWithFreshTokens(authService, (String authToken,
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

}
