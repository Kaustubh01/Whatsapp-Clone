package com.kaustubh.whatsappclone.user;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private LocalDateTime lastSeen;

    private boolean isOnline;
}
