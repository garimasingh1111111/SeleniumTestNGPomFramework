package com.myApp.qa.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.myApp.qa.base.Base;
import com.myApp.qa.pages.AccountPage;
import com.myApp.qa.pages.HomePage;
import com.myApp.qa.pages.LoginPage;
import com.myApp.qa.utils.Utilities;

public class LoginTest extends Base {
	public WebDriver driver;

	public LoginTest() {
		super();
	}

	@BeforeMethod
	public void setUp() {
		driver = initializeWebBrowerAndOpenApplication(prop.getProperty("browserName"));
		HomePage homePage = new HomePage(driver);
		homePage.clickOnMyAccount();
		homePage.selectLoginOption();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test(priority = 1, dataProvider = "validCredential")
	public void verifyValidLoginCredential(String Email, String Password) {

		LoginPage loginPage = new LoginPage(driver);
		loginPage.enterEmailAddress(Email);
		loginPage.enterPassword(Password);
		loginPage.clickOnLoginButton();
		AccountPage accountPage = new AccountPage(driver);
		Assert.assertTrue(accountPage.getDisplayStatusOfEditYourAccountInformationOption());
	}

	@DataProvider(name = "validCredential")
	public Object[][] supplyTestData1() {
		Object[][] data = Utilities.getTestDataFromExcel("Login");
		return data;

	}

	@Test(priority = 3, dependsOnMethods = { "verifyInValidUserIdAndPassLoginCredential" })
	public void verifyInValidUseIdAndPasswordLoginCredential() {
		driver.findElement(By.id("input-email")).sendKeys("amotooricap9272dsds@gmail.com");
		driver.findElement(By.id("input-password")).sendKeys(dataProp.getProperty("invalidPassword"));
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		Assert.assertTrue(
				driver.findElement(By.xpath("//div[text()='Warning: No match for E-Mail Address and/or Password.']"))
						.isDisplayed());
	}

//	@Test(priority = 4)
	public void verifyInValidUserIdLoginCredential() {
		driver.findElement(By.id("input-email")).sendKeys("amotooricap9225re@gmail.com");
		driver.findElement(By.id("input-password")).sendKeys("12345");
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		Assert.assertTrue(
				driver.findElement(By.xpath("//div[text()='Warning: No match for E-Mail Address and/or Password.']"))
						.isDisplayed());
	}

	@Test(priority = 2)
	public void verifyInValidUserIdAndPassLoginCredential() {
		driver.findElement(By.id("input-email")).sendKeys("amotooricap9@gmail.com");
		driver.findElement(By.id("input-password")).sendKeys("12345");
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		String actualResult = driver
				.findElement(By.xpath("//div[text()='Warning: No match for E-Mail Address and/or Password.']"))
				.getText();
		String expectedResult = dataProp.getProperty("emailPasswordNoMatchWarning");
		Assert.assertEquals(actualResult, expectedResult);
		// Assert.assertFalse(driver.findElement(By.xpath("//a[text()='Edit your account
		// information']")).isDisplayed());
	}

}
