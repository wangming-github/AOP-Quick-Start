package com.maizi.author.service;

import com.maizi.common.dto.UserDTO;
import com.maizi.common.utils.R;

public interface LoginService {

    R login(UserDTO userDTO);

    R logout();
}

