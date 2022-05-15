package com.wells.identity.registrationcontroller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wells.identity.common.exceptions.DatbaseconnectivityException;
import com.wells.identity.common.exceptions.InvalidCredientialsExcepion;
import com.wells.identity.constants.Constants;
import com.wells.identity.model.UserLogin;
import com.wells.identity.repository.UserLoginRepository;
import com.wells.identity.request.LoginRequest;
import com.wells.identity.request.UserRegisterRequest;
import com.wells.identity.response.LoginResponse;
import com.wells.identity.response.UserRegisterReponse;
import com.wells.identity.services.JWTService;
import com.wells.identity.utility.AESEncryptionDecryption;

@RestController
@RequestMapping("/api/v1")
public class UserController {

	final String secretKey = "secrete";

	@Autowired
	private UserLoginRepository userLoginRepository;

	@Autowired
	JWTService jwtService;

	
	@PostMapping("/user/registeration")
	public ResponseEntity<UserRegisterReponse> createUserRegistration(@Valid @RequestBody UserRegisterRequest userRegisterRequest)
			throws DatbaseconnectivityException {
		boolean userLogindetail = userLoginRepository.findById(userRegisterRequest.getEmpId()).isPresent();
		if (userLogindetail) {
			String generateToken = jwtService.generateTokenforRegisteration(userRegisterRequest);
			return ResponseEntity.ok().body(new UserRegisterReponse("User alreday exist", generateToken));
		} else {
			try {
				String encryptedString = AESEncryptionDecryption.encrypt(userRegisterRequest.getPassword(), secretKey);
				UserLogin userLoginsave = new UserLogin();
				userLoginsave.setEmailId(userRegisterRequest.getEmailId());
				userLoginsave.setEmpid(userRegisterRequest.getEmpId());
				userLoginsave.setFirstName(userRegisterRequest.getFirstName());
				userLoginsave.setLastName(userRegisterRequest.getLastName());
				userLoginsave.setPassword(encryptedString);
				userLoginRepository.save(userLoginsave);

			} catch (Exception e) {

				throw new DatbaseconnectivityException("Datbase connection or sql issue :: ");

			}
		}
		String generateToken = jwtService.generateTokenforRegisteration(userRegisterRequest);
		return ResponseEntity.ok().body(new UserRegisterReponse("Success", generateToken));
	}


	@PostMapping("/user/authenticate")
	public ResponseEntity<LoginResponse> getUserByLoginwithEmpId(@Valid @RequestBody LoginRequest loginRequest) {
		UserLogin userLogindetail = userLoginRepository.findById(loginRequest.getEmpId().trim())
				.orElseThrow(() -> new InvalidCredientialsExcepion("User not found :: " + loginRequest.getEmpId()));
		String decryptedString = AESEncryptionDecryption.decrypt(userLogindetail.getPassword(), secretKey);
		if (!loginRequest.getPassword().trim().equalsIgnoreCase(decryptedString)) {
			throw new InvalidCredientialsExcepion("Invalid Password");
		}
		String generateToken = jwtService.generateToken(userLogindetail);
		return ResponseEntity.ok().body(new LoginResponse("Success", generateToken));
	}

	@PostMapping("/admin/authenticate")
	public ResponseEntity<LoginResponse>  getUserByLoginwithAdmin(@Valid @RequestBody LoginRequest loginRequest) {
		if ( loginRequest.getEmpId().trim().equalsIgnoreCase(Constants.ADMIN) && loginRequest.getPassword().trim().equalsIgnoreCase(Constants.ADMIN)) {
			String generateToken = jwtService.generateTokenforAdmin(loginRequest);
			return ResponseEntity.ok().body(new LoginResponse("Success", generateToken));
			
		} else{
			throw new InvalidCredientialsExcepion("Invalid Password");
		}
	
	}

}




