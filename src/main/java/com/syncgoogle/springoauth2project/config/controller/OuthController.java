package com.syncgoogle.springoauth2project.config.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.syncgoogle.springoauth2project.config.service.OuthService;

@RestController
@RequestMapping("/oauth2/v1")
public class OuthController {
	private static final Logger logger = LoggerFactory.getLogger(OuthController.class);
	@Autowired
	private OuthService outhService;

	@GetMapping
	public String getgoogleToken() {
		return outhService.getAuthorizationUrl();
	}
	
	@PostMapping("/user/create")
	public GoogleIdToken getUserDetailByIdToken(@RequestParam("idToken") String idToken) throws GeneralSecurityException, IOException {
		return outhService.getUserDetailByIdToken(idToken);
	}
	@PostMapping("/auth/code")
	public GoogleTokenResponse getAccessTokenForAuthorizationCode(@RequestParam("code") String code) throws GeneralSecurityException, IOException {
	logger.info("authCode"+code);
		return outhService.getAccessTokenForAuthorizationCode(code);
		
	}
}
