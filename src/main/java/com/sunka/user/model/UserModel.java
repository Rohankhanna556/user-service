package com.sunka.user.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String role;

}
