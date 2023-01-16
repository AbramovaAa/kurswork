package kurswork.hospital.service;

import java.util.List;

import kurswork.hospital.dto.UserDto;
import kurswork.hospital.entity.User;

public interface UserService {

	 User saveUser(UserDto userDto);

	    User findByEmail(String email);

	    List<UserDto> findAllUsers();
}
