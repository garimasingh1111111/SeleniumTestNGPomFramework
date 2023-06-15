package com.myApp.qa.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.myApp.qa.base.Base;
import com.myApp.qa.utils.Utilities;

public class RegisterTest extends Base {

	public WebDriver driver;

	public RegisterTest() {
		super();
	}

	@BeforeMethod
	public void setUp() {
		driver = initializeWebBrowerAndOpenApplication(prop.getProperty("browserName"));
		driver.findElement(By.xpath("//span[text()='My Account']")).click();
		driver.findElement(By.xpath("//a[text()='Register']")).click();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test
	public void verifyRegisteringAnAccountWithMandatoryField() {
		driver.findElement(By.id("input-firstname")).sendKeys(dataProp.getProperty("firstName"));
		driver.findElement(By.id("input-lastname")).sendKeys(dataProp.getProperty("lastName"));
		driver.findElement(By.id("input-email")).sendKeys(Utilities.generateEmailWithTimeStamp());
		driver.findElement(By.id("input-telephone")).sendKeys(dataProp.getProperty("telephoneNumber"));
		driver.findElement(By.id("input-password")).sendKeys("12345");
		driver.findElement(By.id("input-confirm")).sendKeys("12345");
		driver.findElement(By.name("agree")).click();
		driver.findElement(By.xpath("//input[@value='Continue']")).click();

		String actualSuccessHeading = driver.findElement(By.xpath("//div[@id='content']/h1")).getText();
		Assert.assertEquals(actualSuccessHeading, dataProp.getProperty("accountSuccessfullyCreatedHeading"));
	}

	@Test
	public void verifyRegisteringAnAccountWithEmptyFields() throws InterruptedException {
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		String actualPrivacyErrMsg = driver
				.findElement(By.xpath("//div[text()='Warning: You must agree to the Privacy Policy!']")).getText();

		Assert.assertEquals(actualPrivacyErrMsg, dataProp.getProperty("privacyPolicyWarning"));

		String actualFnameErrMsg = driver.findElement(By.xpath("(//div[@class='text-danger'])[1]")).getText();
		// Thread.sleep(2000);
		Assert.assertEquals(actualFnameErrMsg, dataProp.getProperty("firstNameWarning"));

		String actuallnameErrMsg = driver.findElement(By.xpath("(//div[@class='text-danger'])[2]")).getText();
		// Thread.sleep(2000);
		Assert.assertEquals(actuallnameErrMsg, dataProp.getProperty("lastNameWarning"));

		// validate rest of the other msgs for mandatory fields
	}

}
