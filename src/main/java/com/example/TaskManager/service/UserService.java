package com.example.TaskManager.service;


import com.example.TaskManager.dto.user.UserRequest;
import com.example.TaskManager.dto.user.UserResponse;
import com.example.TaskManager.mapper.UserMapper;
import com.example.TaskManager.model.User;
import com.example.TaskManager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);

    }
    @Transactional(rollbackFor = DataAccessException.class)
    public UserResponse createUser(UserRequest request) {
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    public User processOAuthUser(String email, String firstName, String lastName) {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email.toLowerCase());
                    newUser.setOauth2Provider("GOOGLE");
                    newUser.setVerified(true);
                    return userRepository.save(newUser);
                });
    }

}