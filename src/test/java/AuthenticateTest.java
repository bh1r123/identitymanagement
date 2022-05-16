package com.wells.identity.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import com.wells.identity.common.exceptions.InvalidCredientialsExcepion;
import com.wells.identity.model.UserLogin;
import com.wells.identity.repository.UserLoginRepository;
import com.wells.identity.utility.AESEncryptionDecryption;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages = { "com.*" })
@Configuration
public class AuthenticateTest {

	@BeforeEach
	private void setup() {

	}

	@Autowired
	private UserLoginRepository userLoginRepository;

	@Test
	public void testUserAuthenticate() {
		final String secretKey = "secrete";
		String empid = "1779057";
		UserLogin userLogindetail = userLoginRepository.findById(empid)
				.orElseThrow(() -> new InvalidCredientialsExcepion("User not found :: " + empid));
		String decryptedString = AESEncryptionDecryption.decrypt(userLogindetail.getPassword(), secretKey);
		assertEquals("sunil", decryptedString);
	}

}
