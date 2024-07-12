package com.example.authorizationmodule.service;

import com.example.authorizationmodule.module.UserOfRequest;
import com.example.authorizationmodule.utils.R;

import java.util.Map;

public interface LoginService {

    R<Map<String, String>> login(UserOfRequest userOfRequest);

    R logout();
}

