package com.chatApp.TeamTalk.authentication.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterBody {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
