package com.uitgis.kras.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uitgis.kras.jwt.JwtTokenProvider;
import com.uitgis.kras.model.User;
import com.uitgis.kras.service.UserService;
import com.uitgis.kras.util.ValidUtil;


@RestController
@RequestMapping("jwt")
public class JwtLoginController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
    private JwtTokenProvider jwtTokenProvider;
	
	@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {
		User user = userService.findByLoginId(loginUser.getLogin_id());
		
		if (ValidUtil.empty(user) || !passwordEncoder.matches(loginUser.getPasswd(), user.getPassword())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		return ResponseEntity.ok(jwtTokenProvider.createToken(user.getUsername(), user.getRole_type()));
    }
}
