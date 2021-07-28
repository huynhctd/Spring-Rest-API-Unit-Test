package com.sundaydevblog.springrestapitest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.math.BigInteger;
import java.util.Random;

/**
 * Abstract implementation for integration test.
 *
 */
public class AbstractIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    private static final String FORMAT = "%05d";

    private static final Random RAND = new Random();


    /**
     * Generate random numeric code with max given length.
     *
     * @param maxLen max length
     * @return code
     */
    protected static String randomCode(int maxLen) {
        String randomCode = randomCode();
        if (randomCode.length() > maxLen) {
            randomCode = randomCode.substring(randomCode.length() - maxLen);
        }
        return randomCode;
    }

    /**
     * Create credentials header for API requests.
     *
     * @return {@link HttpHeaders}
     */
    protected static HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * Generate random numeric code.
     *
     * @return code
     */
    protected static String randomCode() {
        return String.format(FORMAT, new BigInteger(64, RAND).intValue());
    }
}