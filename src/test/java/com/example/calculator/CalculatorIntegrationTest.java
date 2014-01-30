/**
   Copyright 2010 David Green

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.example.calculator;
import java.io.*;
import org.apache.commons.io.FileUtils;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
/**
 * Integration test for calculator.
 * 
 * @author GreenD
 *
 */
public class CalculatorIntegrationTest {

	// The web driver
	private WebDriver webDriver;
	
	// The port to use
	private String testServerPort;
	
	// The display to use (when running headless)
	private String display;
	
	@Before
	public void openWebDriver() {
		testServerPort = System.getProperty("test.server.port","8080");
		String display = System.getProperty("test.xvfb.display",null);
		FirefoxBinary firefox = new FirefoxBinary();
		if( display != null ) {
			firefox.setEnvironmentProperty("DISPLAY", display);
		}
		webDriver = new FirefoxDriver(firefox,null);
	}
	
	@After
	public void closeWebDriver() {
		webDriver.close();
	}
	
	private CalculatorPage getHomePage() {
		webDriver.get("http://localhost:"+testServerPort+"/calculator-web/");
		CalculatorPage page = new CalculatorPage(webDriver);
		return page;
	}

	@Test
	public void testEnterDigits() {
		CalculatorPage page = getHomePage();
		
File scrFile = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
FileUtils.copyFile(scrFile, new File("screenshot.png"));		
		
		page.pressButton('1');
		page.pressButton('2');
		page.pressButton('3');
		
		assertEquals("123",page.getDisplayAmount());
	}

	@Test
	public void testAddTwoNumbers() {
		CalculatorPage page = getHomePage();
		
		page.pressButton('1');
		page.pressButton('+');
		page.pressButton('2');
		page.pressButton('=');
		
		assertEquals("3",page.getDisplayAmount());
	}
	
	@Test
	public void testSubtractTwoNumbers() {
		CalculatorPage page = getHomePage();
		
		page.pressButton('3');
		page.pressButton('-');
		page.pressButton('2');
		page.pressButton('=');
		
		assertEquals("1",page.getDisplayAmount());
	}
	
	@Test
	public void testMultiplyTwoNumbers() {
		CalculatorPage page = getHomePage();
		
		page.pressButton('3');
		page.pressButton('*');
		page.pressButton('2');
		page.pressButton('=');
		
		assertEquals("6",page.getDisplayAmount());
	}
	
	@Test
	public void testDivideTwoNumbers() {
		CalculatorPage page = getHomePage();
		
		page.pressButton('6');
		page.pressButton('/');
		page.pressButton('2');
		page.pressButton('=');
		
		assertEquals("3",page.getDisplayAmount());
	}
	
	@Test
	public void testClear() {
		CalculatorPage page = getHomePage();
		
		page.pressButton('3');
		page.pressButton('*');
		page.pressButton('C');
		
		assertEquals("0",page.getDisplayAmount());
	}
		
	
}
