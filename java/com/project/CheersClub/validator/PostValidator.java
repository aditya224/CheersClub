package com.project.CheersClub.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.project.CheersClub.pojo.User;

@Component
public class PostValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return User.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object arg0, Errors error) {
		// TODO Auto-generated method stub
		ValidationUtils.rejectIfEmpty(error, "postTitle", "empty-postTitle","Write Title");
        ValidationUtils.rejectIfEmpty(error, "postContent", "empty-postContent","Write Content");
	}

}
