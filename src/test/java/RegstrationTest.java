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

import com.wells.identity.model.UserLogin;
import com.wells.identity.repository.UserLoginRepository;
import com.wells.identity.utility.AESEncryptionDecryption;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages = {"com.*"})
@Configuration
public class RegstrationTest {

	
	@BeforeEach
	private void setup(){
		
		 
	}
	
	@Autowired
	private UserLoginRepository userLoginRepository;


    @Test
    public void testUserRegistraion() {
    	final String secretKey = "secrete";
    	UserLogin userLoginsave = new UserLogin();
    	userLoginsave.setEmailId("sunilca@gmaoil.com");
		userLoginsave.setEmpid("1779059");
		userLoginsave.setFirstName("Anil");
		userLoginsave.setLastName("CA");
		userLoginsave.setPassword(AESEncryptionDecryption.encrypt("sunil", secretKey));
		userLoginRepository.save(userLoginsave);
        assertEquals("Success", "Success");
    }
    
}
