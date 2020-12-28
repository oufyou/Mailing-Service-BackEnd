/*
 * MNS - Novelis Mail System - API REST
 * COded By Alias King - Younes OUFRID !!
 * Mail : oufridyounes@gmail.com
 * MNS team coders
 * */

package io.novelis.email.ms;

/**
 * Test unitaires with Junit
 *
 * @author Alias King - Younes OUFRID
 */

import io.novelis.email.ms.model.User;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetAllUsers() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "api/users",
				HttpMethod.GET, entity, String.class);

		Assert.assertNotNull(response.getBody());
	}

	@Test
	public void testGetUserById() {
		User user = restTemplate.getForObject(getRootUrl() + "api/users/1", User.class);
		System.out.println(user.getFirstName());
		Assert.assertNotNull(user);
	}

	@Test
	public void testCreateUser() {
		User user = new User();
		user.setEmail("admin@gmaill.com");
		user.setUsername("king");
		user.setFirstName("admin");
		user.setLastName("admin");
		user.setCreatedBy("admin");
		user.setUpdatedBy("admin");

		ResponseEntity<User> postResponse = restTemplate.postForEntity(getRootUrl() + "api/users", user, User.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdatePost() {
		int id = 1;
		User user = restTemplate.getForObject(getRootUrl() + "api/users/" + id, User.class);
		user.setFirstName("admin1");
		user.setLastName("admin2");

		restTemplate.put(getRootUrl() + "api/users/" + id, user);

		User updatedUser = restTemplate.getForObject(getRootUrl() + "api/users/" + id, User.class);
		Assert.assertNotNull(updatedUser);
	}

	@Test
	public void testDeletePost() {
		int id = 2;
		User user = restTemplate.getForObject(getRootUrl() + "api/users/" + id, User.class);
		Assert.assertNotNull(user);

		restTemplate.delete(getRootUrl() + "api/users/" + id);

		try {
			user = restTemplate.getForObject(getRootUrl() + "api/users/" + id, User.class);
		} catch (final HttpClientErrorException e) {
			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}

}