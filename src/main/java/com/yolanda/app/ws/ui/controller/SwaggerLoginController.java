package com.yolanda.app.ws.ui.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yolanda.app.ws.ui.model.request.UserLoginRequestModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/users")
@Tag(name = "登入驗證 Authentication", description = "使用者登入與身份驗證 User login and authentication")
public class SwaggerLoginController {
	
	    @PostMapping("/login")
	    @Operation(summary = "使用者登入 Login", description = "用戶使用電子郵件與密碼登入 User login with email and password")
	    public ResponseEntity<String> fakeLogin(@RequestBody UserLoginRequestModel loginRequest) {
	        return ResponseEntity
	                .status(HttpStatus.UNAUTHORIZED)
	                .body("This is a fake login endpoint for Swagger files, please use the real login process.");
	    }
	

}
