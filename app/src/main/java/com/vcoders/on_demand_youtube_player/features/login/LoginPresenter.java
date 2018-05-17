package com.vcoders.on_demand_youtube_player.features.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.utils.AccountUtils;

import javax.inject.Inject;


public class LoginPresenter extends BasePresenter<LoginView, LoginRouter> implements GoogleApiClient.OnConnectionFailedListener {

    private Context context;
    private GoogleApiClient googleApiClient;
//https://developers.google.com/youtube/v3/guides/auth/installed-apps
    @Inject
    public LoginPresenter(Context context) {
        this.context = context;
    }

    public void initGoogleApi() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getResources().getString(R.string.google_client_id))
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by googleSignInOptions.
        googleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage((FragmentActivity) context, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
    }

    public void signInGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        ((Activity) context).startActivityForResult(signInIntent, LoginActivity.GOOGLE_SIGN_IN_CODE);
    }

    public void handleSignInResult(Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);

            AccountUtils.getInstance().setEmail(account.getEmail());
            AccountUtils.getInstance().setLogin(true);
            AccountUtils.getInstance().setToken(account.getIdToken());

            getView().onLoginSuccess();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            getView().onLoginFail(e.getStatusCode(), e.getMessage());
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        getView().onLoginFail(connectionResult.getErrorCode(), connectionResult.getErrorMessage());
    }
}
