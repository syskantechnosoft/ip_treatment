package com.cognizant.iptreatment.controller;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.iptreatment.repository.UserRepository;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class AuthenticationController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	UserRepository userRepository;

	@GetMapping("/authenticate")
	public Map<String, String> authenticate(@RequestHeader("Authorization") String authHeader) {
		Map<String, String> authenticationMap = new HashMap<String, String>();
		LOGGER.info("authentication START");
		LOGGER.debug("authHeader " + authHeader);
		String user = getUser(authHeader);
		String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString();
		LOGGER.debug(role);
		authenticationMap.put("user", user);
		authenticationMap.put("id", "" + userRepository.findByUsername(user).getId());
		authenticationMap.put("token", generateJwt(user));
		authenticationMap.put("role", role);
		LOGGER.info("authentication END");
		return authenticationMap;
	}

	private String getUser(String authHeader){
		LOGGER.info("getUser START");
//		String encodedCredentials = authHeader.substring(authHeader.indexOf(" ") + 1);
//		LOGGER.info(encodedCredentials);
//		byte[] decodedStringAsByteArray = Base64.getDecoder().decode("cGFzc3dvcmQ=");
//		try {
//			String encodedString = new String(decodedStringAsByteArray, "UTF-8");
//			LOGGER.info("decoded String :" + encodedString);
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		LOGGER.info("decoded String :" + decodedStringAsByteArray);
		
		StringTokenizer headerTokens = new StringTokenizer(authHeader, ",");
		System.out.println(headerTokens.countTokens());
		String user = null;
		String encodedpassword = null;
				
		while(headerTokens.hasMoreTokens()) {
			String token = headerTokens.nextToken();
			if (token.startsWith("username"))
				user = token.substring(token.indexOf(":")+1);
			if (token.startsWith("password"))
				encodedpassword = token.substring(token.indexOf(":")+1);
		}
		System.out.println("username=" + user +" Encoded password =" + encodedpassword);
		byte[] decodedText = Base64.getDecoder().decode(encodedpassword);
		
		
		LOGGER.debug("Decoded text " + decodedText);
//		String decodedString = new String(decodedText);
//		LOGGER.debug("Decoded String " + decodedString);
//		String user = decodedString.substring(0, decodedString.indexOf(":"));
		LOGGER.debug("User " + user);
		LOGGER.info("getUser END");
		return user;
	}

	private String generateJwt(String user) {
		LOGGER.info("generateJWT START");

		JwtBuilder builder = Jwts.builder();
		builder.setSubject(user);
		// Set the token issue time as current time
		builder.setIssuedAt(new Date());
		// Set the token expiry as 20 minutes from now
		builder.setExpiration(new Date((new Date()).getTime() + 1200000));
		builder.signWith(SignatureAlgorithm.HS256, "secretkey");

		String token = builder.compact();
		LOGGER.info("generateJWT END");
		return token;
	}

}
