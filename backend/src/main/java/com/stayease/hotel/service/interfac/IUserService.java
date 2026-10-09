package com.stayease.hotel.service.interfac;

import com.stayease.hotel.dto.LoginRequest;
import com.stayease.hotel.dto.Response;
import com.stayease.hotel.entity.User;

public interface IUserService {
    Response register(User user);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response getUserBookingHistory(String userId);

    Response deleteUser(String userId);

    Response getUserById(String userId);

    Response getMyInfo(String email);

}
