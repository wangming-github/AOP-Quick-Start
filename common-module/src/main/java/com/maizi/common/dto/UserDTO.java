package com.maizi.common.dto;

import lombok.*;

/**
 * @author maizi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDTO {
    private String username;
    private String password;

}
