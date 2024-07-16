package com.maizi.authorizationmodule.service;

import com.maizi.authorizationmodule.module.UserOfRequest;
import com.maizi.common.utils.R;

import java.util.Map;

public interface LoginService {

    R login(UserOfRequest userOfRequest);

    R logout();
}

