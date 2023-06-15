package com.myApp.qa.testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Login1Test {
	public WebDriver driver;

	@BeforeMethod
	public WebDriver setUp() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
		driver.get("https://tutorialsninja.com/demo/");
		driver.findElement(By.xpath("//span[text()='My Account']")).click();
		driver.findElement(By.xpath("//a[text()='Login']")).click();
		return driver;
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test(priority = 1, dataProvider = "validCredential")
	public void verifyValidLoginCredential() {

		driver.findElement(By.id("input-email")).sendKeys("amotooricap9@gmail.com");
		driver.findElement(By.id("input-password")).sendKeys("12345");
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='Edit your account information']")).isDisplayed());
	}

	@DataProvider(name = "validCredential")
	public Object[][] supplyTestData() {
		Object[][] data = { { "amotooricap9@gmail.com", "12345" }, { "amotooricap9@gmail.com", "12345" },
				{ "amotooricap9@gmail.com", "12345" } };
		return data;

	}

	// @Test(priority = 3)
	public void verifyInValidUseIdAndPasswordLoginCredential() {
		driver.findElement(By.id("input-email")).sendKeys("amotooricap9272dsds@gmail.com");
		driver.findElement(By.id("input-password")).sendKeys("1234545");
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		Assert.assertTrue(
				driver.findElement(By.xpath("//div[text()='Warning: No match for E-Mail Address and/or Password.']"))
						.isDisplayed());
	}

	// @Test(priority = 4)
	public void verifyInValidUserIdLoginCredential() {
		driver.findElement(By.id("input-email")).sendKeys("amotooricap9225re@gmail.com");
		driver.findElement(By.id("input-password")).sendKeys("12345");
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		Assert.assertTrue(
				driver.findElement(By.xpath("//div[text()='Warning: No match for E-Mail Address and/or Password.']"))
						.isDisplayed());
	}

	// @Test(priority = 2)
	public void verifyInValidUserIdAndPassLoginCredential() {
		driver.findElement(By.id("input-email")).sendKeys("amotooricap9456456@gmail.com");
		driver.findElement(By.id("input-password")).sendKeys("1234545564");
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		String actualResult = "Warning: No match for E-Mail Address and/or Password.";
		String expectedResult = driver
				.findElement(By.xpath("//div[text()='Warning: No match for E-Mail Address and/or Password.']"))
				.getText();
		Assert.assertEquals(actualResult, expectedResult);
		// Assert.assertFalse(driver.findElement(By.xpath("//a[text()='Edit your account
		// information']")).isDisplayed());
	}

}
