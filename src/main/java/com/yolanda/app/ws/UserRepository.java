package com.yolanda.app.ws;

import org.springframework.data.repository.CrudRepository;

import com.yolanda.app.ws.io.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
	UserEntity findByEmail(String email);

	UserEntity findByUserId(String userId);
}
