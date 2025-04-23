package com.test.identity_service.service;

import com.test.identity_service.dto.request.PermissionRequest;
import com.test.identity_service.dto.response.PermissionResponse;

import java.util.List;

public interface IPermissionService {
    PermissionResponse create (PermissionRequest request);
    List<PermissionResponse> getAll();
    void delete(String permission);
}
