package com.test.identity_service.controller;

import com.test.identity_service.dto.request.PermissionRequest;
import com.test.identity_service.dto.response.ApiResponse;
import com.test.identity_service.dto.response.PermissionResponse;
import com.test.identity_service.service.IPermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class PermissionController {

    IPermissionService permissionService;

    @PostMapping()
    public ResponseEntity<ApiResponse<PermissionResponse>> create (@RequestBody PermissionRequest permissionRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<PermissionResponse>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Created Permission Successfully")
                        .data(this.permissionService.create(permissionRequest))
                        .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PermissionResponse>>> getAll(){
        return ResponseEntity.ok().body(
                ApiResponse.<List<PermissionResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get Permissions Successfully")
                        .data(this.permissionService.getAll())
                        .build()
        );
    }

    @DeleteMapping("/{permissionId}")
    ResponseEntity<ApiResponse<Void>> delete (@PathVariable String permissionId){
        this.permissionService.delete(permissionId);
        return ResponseEntity.ok().body(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Deleted Permission Successfully")
                        .build()
        );
    }
}
