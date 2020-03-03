package com.syncgoogle.springoauth2project.config.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;

public interface OuthService {

	GoogleTokenResponse getAccessTokenForAuthorizationCode(String code);

	GoogleIdToken getUserDetailByIdToken(String idToken) throws GeneralSecurityException, IOException;

	String getAuthorizationUrl();

}
