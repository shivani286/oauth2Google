package com.syncgoogle.springoauth2project.config.service.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.syncgoogle.springoauth2project.config.service.OuthService;
import com.syncgoogle.springoauth2project.constants.DirectoryScopes;

import org.springframework.security.authentication.BadCredentialsException;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.CredentialRefreshListener;
import com.google.api.client.auth.oauth2.TokenErrorResponse;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;


@Service
public class OuthServiceImpl implements OuthService {
	private static final Logger logger = LoggerFactory.getLogger(OuthServiceImpl.class);
	@Value("${client_id}")
	private String clientId;
	@Value("${client_secret}")
	private String clientSecret;
	@Value("${redirect_uri}")
	private String redirectUri;
	@Value("${scope}")
	private String scope;

	@Override
	public String getAuthorizationUrl() {
		
		List<String> SCOPES = Arrays.asList(DirectoryScopes.USERINFO_PROFILE, DirectoryScopes.USERINFO_EMAIL);
		
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(new NetHttpTransport(),
				new JacksonFactory(),clientId, clientSecret, SCOPES).setApprovalPrompt("force")
				.setAccessType("offline").build();

		String url = flow.newAuthorizationUrl().setRedirectUri(redirectUri).build();
		return url;
	}
	
	@Override
	public GoogleTokenResponse getAccessTokenForAuthorizationCode(String code) {
		
		if (Objects.isNull(code)) {
			throw new NullPointerException("Auth code is null");
		}
		
		logger.info("authCode" + code);
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(new NetHttpTransport(),
				new JacksonFactory(), clientId, clientSecret, Arrays.asList(scope)).setApprovalPrompt("force")
						.setAccessType("offline").build();
		
		logger.info("flow" + flow);
		
		GoogleTokenResponse tokenResponse = null;
		try {
			tokenResponse = flow.newTokenRequest(code).setRedirectUri(redirectUri).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		GoogleCredential credential = new GoogleCredential.Builder().setTransport(new NetHttpTransport())
				.setJsonFactory(new JacksonFactory()).setClientSecrets(clientId, clientSecret)
				.addRefreshListener(new CredentialRefreshListener() {
					@Override
					public void onTokenResponse(Credential credential, TokenResponse tokenResponse) {
						System.out.println("Credential was refreshed successfully.");
					}

					@Override
					public void onTokenErrorResponse(Credential credential, TokenErrorResponse tokenErrorResponse) {
						System.err.println("Credential was not refreshed successfully. "
								+ "Redirect to error page or login screen.");
					}
				}).build();

		credential.setFromTokenResponse(tokenResponse);
		 logger.info("credential logger");
		 /*GoogleCredentialForUser credentialForUser = new GoogleCredentialForUser();
		 credentialForUser.setAccessToken(tokenResponse.getAccessToken());
		 credentialForUser.setExpiresIn(tokenResponse.getExpiresInSeconds());
		 credentialForUser.setIdToken(tokenResponse.getIdToken());
		 credentialForUser.setRefreshToken(tokenResponse.getRefreshToken());
		 credentialForUser.setTokenType(tokenResponse.getTokenType());
		 credentialForUser.setUser(authenticationDetails.getUser());
		 credentialForUser.setScope(tokenResponse.getScope());
		 googleCredentialForUserDao.save(credentialForUser);*/
		//calenderEvents(credential);
		
		return tokenResponse;
	}

	@Override
	public GoogleIdToken getUserDetailByIdToken(String idToken) throws GeneralSecurityException, IOException {

		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
				.setAudience(Collections.singletonList(clientId)).build();
		
		GoogleIdToken googleIdToken = verifier.verify(idToken);
		logger.info("googleIdToken: " + googleIdToken);

		if (Objects.isNull(googleIdToken)) {
			logger.info("is null condition");
			throw new BadCredentialsException("Unable to verify token");
		}
		if (googleIdToken != null) {
			GoogleIdToken.Payload payload = googleIdToken.getPayload();

			logger.info("payload:" + payload);
			String userId = payload.getSubject();
			
			System.out.println("User ID: " + userId);
			String email = payload.getEmail();
			System.out.println("email : " + email);
			boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
			System.out.println("emailVerified : " + emailVerified);
			String name = (String) payload.get("name");
			System.out.println("name : " + name);
			String pictureUrl = (String) payload.get("picture");
			System.out.println("pictureUrl : " + pictureUrl);
			String locale = (String) payload.get("locale");
			System.out.println("locale : " + locale);
			String familyName = (String) payload.get("family_name");
			System.out.println("familyName : " + familyName);
			String givenName = (String) payload.get("given_name");
			System.out.println("givenName : " + givenName);

		} else {
			System.out.println("Invalid ID token.");
		}
		return googleIdToken;
	}

	

}
