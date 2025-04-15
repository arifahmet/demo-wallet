package com.arif.demo.controller.example.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserExampleModels {
    public static final String GET_USER_RESPONSE = """
            {
                "userName": "arif",
                "userKey": "82638e6b-75d9-4ee4-a213-cc9f190500f8"
            }""";
}
