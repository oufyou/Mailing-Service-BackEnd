/*
 * MNS - Novelis Mail System - API REST
 * COded By Alias King - Younes OUFRID !!
 * Mail : oufridyounes@gmail.com
 * MNS team coders
 * */

package io.novelis.email.ms.service;
/**
 * User Service
 *
 * @author Alias King - Younes OUFRID
 */
import io.novelis.email.ms.model.User;
import io.novelis.email.ms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;


	public List<User> retrieveAllUsers() {
		return userRepository.findAll();
	}

	public User getUser(Long id) {
		return userRepository.getOne(id);
	}

	public User createUser(User user) {
		return userRepository.save(user);
	}
	
	@Transactional
	public void deleteUser(Long id) throws Exception {

		User user = userRepository.getOne(id);
		if (user != null) {
			userRepository.deleteById(id);
		} else {
			throw new RuntimeException("Record not found");
		}
	}
	
	public User updateUser(User user) {
		User editedUser = userRepository.getOne(user.getId());
		editedUser.setEmail(user.getEmail());
		editedUser.setFirstName(user.getFirstName());
		editedUser.setLastName(user.getLastName());
		return userRepository.save(editedUser);
	}
}
