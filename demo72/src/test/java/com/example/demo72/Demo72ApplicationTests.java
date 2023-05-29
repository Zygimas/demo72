package com.example.demo72;

import com.example.demo72.repository.UserRepository;
import com.example.demo72.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class Demo72ApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testInMemoryUserDetailsService_LoadUserByUsername() {
		Demo72Application application = new Demo72Application(userRepository);
		UserDetails userDetails = application.inMemoryUserDetailsService().loadUserByUsername("min");

		assertNotNull(userDetails);
		assertEquals("min", userDetails.getUsername());
		assertEquals("min", userDetails.getPassword());
		assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
		assertFalse(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
	}

	@Test
	public void testInMemoryUserDetailsService_LoadUserByUsername_Admin() {
		Demo72Application application = new Demo72Application(userRepository);
		UserDetails userDetails = application.inMemoryUserDetailsService().loadUserByUsername("super");

		assertNotNull(userDetails);
		assertEquals("super", userDetails.getUsername());
		assertEquals("super", userDetails.getPassword());
		assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
		assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
	}

	@Test
	public void testInMemoryUserDetailsService_LoadUserByUsername_UserNotFound() {
		Demo72Application application = new Demo72Application(userRepository);

		assertThrows(
				UsernameNotFoundException.class,
				() -> application.inMemoryUserDetailsService().loadUserByUsername("nonexistent")
		);
	}

	@Test
	public void testRun_SaveUsersToDatabase() {
		Demo72Application application = new Demo72Application(userRepository);
	}
}
