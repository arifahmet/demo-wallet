package com.arif.demo.controller.example.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CredentialExampleModels {
    public static final String SIGN_IN_REQUEST = """
            {
                "username":"arif",
                "password":"test12"
            }""";
    public static final String SIGN_IN_RESPONSE = """
            {
              "accessToken": " ",
              "refreshToken": " "
            }""";
    public static final String SIGN_UP_USER_REQUEST = """
            {
                 "username":"test_user",
                 "password":"123456"
            }""";
    public static final String SING_UP_USER_RESPONSE = """
            {
                 "userName": "arif",
                 "userKey": "82638e6b-75d9-4ee4-a213-cc9f190500f8"
            }""";
}
