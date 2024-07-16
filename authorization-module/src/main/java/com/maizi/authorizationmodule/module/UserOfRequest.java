package com.maizi.authorizationmodule.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author maizi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserOfRequest {
    private String username;
    private String password;

}
