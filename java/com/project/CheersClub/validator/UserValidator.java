package com.project.CheersClub.validator;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.project.CheersClub.pojo.Address;
import com.project.CheersClub.pojo.User;

@Component
public class UserValidator implements Validator {

	 	@Override
	    public boolean supports(Class<?> arg0) {
	        
	       //return true; always works
	       
	       return User.class.isAssignableFrom(arg0); //user and all its subclasses
	    }

	    @Override
	    public void validate(Object arg0, Errors error) {
	        
	        ValidationUtils.rejectIfEmptyOrWhitespace(error, "firstName", "empty-first", "Firstname cannot be empty");
	        ValidationUtils.rejectIfEmptyOrWhitespace(error, "lastName", "empty-last", "Lastname cannot be empty");
	        ValidationUtils.rejectIfEmpty(error, "userType", "empty-userType","Enter User Type");
	        ValidationUtils.rejectIfEmpty(error, "email", "empty-email","Email cannot be empty");
	        ValidationUtils.rejectIfEmpty(error, "password", "empty-password","Password cannot be empty");
	        ValidationUtils.rejectIfEmpty(error, "address.streetName", "empty-streetName","Address cannot be empty");
	        ValidationUtils.rejectIfEmpty(error, "address", "empty-address","Address cannot be empty");
	        
	    }
	    
}
