package com.moviebooking.moviebookinguser.service;

import com.moviebooking.moviebookinguser.dto.UserResponse;
import org.springframework.data.domain.Page;
import com.moviebooking.moviebookinguser.entity.User;

import java.util.List;

public interface UserService {

    UserResponse createUser(User user);

    UserResponse getUserById(Long id);

    public Page<UserResponse> getAllUsers(int page, int size);

    UserResponse updateUser(Long id, User user);

    void deleteUser(Long id);
}