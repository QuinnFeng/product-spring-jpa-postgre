package com.bezkoder.spring.jpa.postgresql.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;

import jakarta.annotation.PostConstruct;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
	
	private static RSAPrivateKey privateKey;
    private static RSAPublicKey publicKey;
    
    @PostConstruct
    private void generateKeys() {
    	KeyPairGenerator keyPairGenerator;
		try {
			keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048, new SecureRandom());  // 2048-bit key size is commonly used
	        KeyPair keyPair = keyPairGenerator.generateKeyPair();
	        privateKey=(RSAPrivateKey) keyPair.getPrivate();
	        publicKey=(RSAPublicKey) keyPair.getPublic();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
    }
    

	public static String createJwt() {
	        try {
	            // Use the RS256 signing algorithm
	            Algorithm algorithm = Algorithm.RSA256(null,privateKey);  // Use public key as null for signing

	            // Create the JWT token
	            return JWT.create()
	                    .withIssuer("auth0") // Set the issuer
	                    .withSubject("user123") // Set a subject (or any other claims you need)
	                    .sign(algorithm); // Sign with the private key

	        } catch (JWTCreationException exception) {
	            // Invalid signing configuration
	            exception.printStackTrace();
	            return null;
	        }
	    }
	
    // Method to verify the JWT
    public static DecodedJWT verifyJwt(String token) {
        try {
            // Use the RS256 algorithm and provide the public key for verification
            Algorithm algorithm = Algorithm.RSA256(publicKey, null);

            // Create a JWT verifier instance
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0") // Verify the issuer claim
                    .build();

            // Verify the token and decode it
            return verifier.verify(token); // If valid, returns decoded JWT

        } catch (JWTVerificationException exception) {
            // Invalid token or claims
            exception.printStackTrace();
            return null;
        }
    }
}
