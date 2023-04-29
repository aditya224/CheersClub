package com.project.CheersClub.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.project.CheersClub.pojo.User;

@Component
public class LoginValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return User.class.isAssignableFrom(arg0); 
	}

	@Override
	public void validate(Object arg0, Errors error) {
		// TODO Auto-generated method stub
		
        ValidationUtils.rejectIfEmpty(error, "email", "empty-email","Email cannot be empty");
        ValidationUtils.rejectIfEmpty(error, "password", "empty-password","Password cannot be empty");
        
		
	}
	

}
