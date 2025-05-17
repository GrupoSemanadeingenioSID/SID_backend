package com.sid.portal_web.service.userWithProfile;


import com.sid.portal_web.core.User;
import com.sid.portal_web.dto.request.RegisterRequest;
import com.sid.portal_web.dto.response.UserWithProfileResponse;

import java.util.List;
import java.util.Optional;

public interface UserWithProfileService {

    List<UserWithProfileResponse> findAll();
    Optional<UserWithProfileResponse> getById(int id);
    User save(RegisterRequest user);
    boolean deleteById(int id);
}
