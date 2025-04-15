package com.arif.demo.controller.example.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserExampleModels {
    public static final String GET_USER_RESPONSE = """
            {
                 "userKey": "1992c9fb-b0e8-416f-a1d9-ad5355507ed0",
                 "username": "test_user",
                 "name": "Arif",
                 "surname": "Halıcı",
                 "tckn": "11111111111"
            }""";
}
