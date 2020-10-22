package coreImplementation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import coreImplementation.Application;

class ApplicationTest {
	
	Application testApp;
	
	@BeforeEach
	void init() {
		testApp = new Application();
	}

	@Test
	void testCheckStringInMessage() {
		assertEquals(true,testApp.checkStringInMessage("CHILD_ADDED"));
		assertEquals(false,testApp.checkStringInMessage("cHILD_ADDED"));
	}
	
	@Test
	void testcheckStringInGender() {
		assertEquals(true,testApp.checkStringInGender("Male"));
		assertEquals(true,testApp.checkStringInGender("Female"));
		assertEquals(false,testApp.checkStringInGender("1"));
	}
	
	
	@Test
	void testIfTextFile() {
		assertEquals(true,testApp.ifTextFile("pooja.txt"));
		assertEquals(false,testApp.ifTextFile("pooja.jpg"));
	}
	

}
