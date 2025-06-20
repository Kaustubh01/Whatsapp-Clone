package com.kaustubh.whatsappclone.user;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponse> getAllUsersExceptSelf(Authentication connectedUser){
        return userRepository.findAllUsersExceptSelf(connectedUser.getName())
            .stream()
            .map(userMapper::toUserResponse)
            .toList();
    }
}
