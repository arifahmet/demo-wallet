package com.arif.demo.controller.example.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CredentialExampleModels {
    public static final String SIGN_IN_REQUEST = """
            {
                 "username":"test_user",
                 "password":"123456"
            }""";
    public static final String SIGN_IN_RESPONSE = """
            {
              "accessToken": "<jwt_token>>"
            }""";
    public static final String SIGN_UP_USER_REQUEST = """
            {
                 "username":"test_user",
                 "password":"123456",
                 "name": "Arif",
                 "surname": "Halıcı",
                 "tckn": "11111111111"
            }""";
    public static final String SING_UP_USER_RESPONSE = """
            {
                 "userName": "test_user",
                 "userKey": "f3bce47d-43e2-4715-b3d6-8ef979c9d675"
            }""";
}
