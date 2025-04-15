package com.arif.demo.controller.example.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserExampleModels {
    public static final String GET_USER_RESPONSE = """
            {
                 "username": "test_user",
                 "userKey": "f3bce47d-43e2-4715-b3d6-8ef979c9d675"
            }""";
}
