package com.test.identity_service.service;

import java.util.List;

import com.test.identity_service.dto.request.PermissionRequest;
import com.test.identity_service.dto.response.PermissionResponse;

public interface IPermissionService {
    PermissionResponse create(PermissionRequest request);

    List<PermissionResponse> getAll();

    void delete(String permission);
}
