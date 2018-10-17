package com.strawberryapp.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.strawberryapp.qa.base.Testbase;

public class LoginPageTests extends Testbase {
	
	public LoginPageTests() {
		super();
	}
	
	@Test
	public void testTitle() {
		String actual = login.getTitle();
		String expected = "CRMPRO";
		Assert.assertEquals(actual, expected);
	}

}
