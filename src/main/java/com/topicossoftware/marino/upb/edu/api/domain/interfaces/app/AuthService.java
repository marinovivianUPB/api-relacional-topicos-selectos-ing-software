package com.topicossoftware.marino.upb.edu.api.domain.interfaces.app;

import com.topicossoftware.marino.upb.edu.api.domain.model.LoginRequest;
import com.topicossoftware.marino.upb.edu.api.domain.model.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
}
