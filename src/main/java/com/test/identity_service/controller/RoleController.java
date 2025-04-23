package com.test.identity_service.controller;

import com.test.identity_service.dto.request.PermissionRequest;
import com.test.identity_service.dto.request.RoleRequest;
import com.test.identity_service.dto.response.ApiResponse;
import com.test.identity_service.dto.response.PermissionResponse;
import com.test.identity_service.dto.response.RoleResponse;
import com.test.identity_service.service.IPermissionService;
import com.test.identity_service.service.IRoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class RoleController {

    IRoleService iRoleService;

    @PostMapping()
    public ResponseEntity<ApiResponse<RoleResponse>> create (@RequestBody RoleRequest roleRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<RoleResponse>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Created Role Successfully")
                        .data(this.iRoleService.create(roleRequest))
                        .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getAll(){
        return ResponseEntity.ok().body(
                ApiResponse.<List<RoleResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get Roles Successfully")
                        .data(this.iRoleService.getAll())
                        .build()
        );
    }

    @DeleteMapping("/{roleName}")
    ResponseEntity<ApiResponse<Void>> delete (@PathVariable String roleName){
        this.iRoleService.delete(roleName);
        return ResponseEntity.ok().body(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Deleted Role Successfully")
                        .build()
        );
    }
}
