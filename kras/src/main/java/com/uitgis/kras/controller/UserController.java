package com.uitgis.kras.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uitgis.kras.model.User;
import com.uitgis.kras.service.UserService;
import com.uitgis.kras.util.KeyUtil;
import com.uitgis.kras.util.ValidUtil;

@RestController
@RequestMapping("api/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("")
	public ResponseEntity<?> userList() {
		List<User> userList = userService.selectUserList();
		
		if(ValidUtil.empty(userList)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.ok(userList);
	}
	
	@GetMapping("{loginId}")
	public ResponseEntity<?> viewUser(@PathVariable String loginId) {
		User user = new User();
		user.setLogin_id(loginId);
		
		User userInfo = userService.selectUser(user);
		if (ValidUtil.empty(userInfo)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.ok(userInfo);
	}
	
	@PostMapping("{loginId}")
	public ResponseEntity<?> insertUser(@PathVariable String loginId, @RequestBody User insertInfo) {
		User userInfo = userService.findByLoginId(loginId);
		
		if (!ValidUtil.empty(userInfo)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		insertInfo.setUser_id(KeyUtil.getUUID());
		insertInfo.setLogin_id(loginId);
		
		String tempPw = insertInfo.getPasswd();
		insertInfo.setPasswd(passwordEncoder.encode(tempPw));
		
		int rs = userService.insertUser(insertInfo);
		
		return new ResponseEntity<>(rs > 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("{loginId}")
	public ResponseEntity<?> updateUser(@PathVariable String loginId, @RequestBody User updateInfo) {		
		User userInfo = userService.findByLoginId(loginId);
		
		if (ValidUtil.empty(userInfo)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		updateInfo.setUser_id(userInfo.getUser_id());
		String tempPw = updateInfo.getPasswd();
		updateInfo.setPasswd(passwordEncoder.encode(tempPw));
		
		int rs = userService.updateUser(updateInfo);
		
		return new ResponseEntity<>(rs > 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("{loginId}")
	public ResponseEntity<?> deleteUser(@PathVariable String loginId) {
		User userInfo = userService.findByLoginId(loginId);
		if (ValidUtil.empty(userInfo)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		User deleteInfo = new User();
		deleteInfo.setUser_id(userInfo.getUser_id());
		
		int rs = userService.deleteUser(deleteInfo);
		
		return new ResponseEntity<>(rs > 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
}
	