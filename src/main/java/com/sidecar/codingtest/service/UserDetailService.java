package com.sidecar.codingtest.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sidecar.codingtest.VO.UserVO;
import com.sidecar.codingtest.model.UserEntity;
import com.sidecar.codingtest.repository.UserRepository;

@Service
public class UserDetailService implements UserDetailsService{
	@Autowired
	UserRepository userRepository;
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
		public UserVO usersignUp(UserVO user){
			LOGGER.info("in usersignUp in UserDetailService");
			UserEntity entity = convertToUserEntity(user);
			entity = userRepository.save(entity);
			UserVO userVO = convertToUserVO(entity);
			return userVO;
			}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.info("in loadUserByUsername -- in UserDetailService");
			UserEntity userData = userRepository.findByUsername(username);
			if(userData == null) {
				LOGGER.error("no record found for this user: ",username);
				throw new UsernameNotFoundException(username);
			}
		return new User(userData.getUsername(),userData.getPassword(), new ArrayList<>());
	}
	
	private UserEntity convertToUserEntity(UserVO userVo) {
		UserEntity user = new UserEntity();
		user.setId(userVo.getId());
		user.setUsername(userVo.getUsername());
		user.setPassword(userVo.getPassword());
		return user ;
	}
	
	private UserVO convertToUserVO(UserEntity userEntity) {
		UserVO userVO = new UserVO();
		userVO.setId(userEntity.getId());
		userVO.setUsername(userEntity.getUsername());
		userVO.setPassword(userEntity.getPassword());
		return userVO ;
	}


}
