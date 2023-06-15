package com.myApp.qa.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.myApp.qa.base.Base;

public class SearchTest extends Base {

	public WebDriver driver;

	public SearchTest() {
		super();
	}

	@BeforeMethod
	public void setUp() {
		// driver = initializeWebBrowerAndOpenApplication("chrome");
		driver = initializeWebBrowerAndOpenApplication(prop.getProperty("browserName"));
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test(priority = 1)
	public void searchWithValidProduct() {
		driver.findElement(By.name("search")).sendKeys(dataProp.getProperty("validProduct"));
		driver.findElement(By.xpath("//button[contains(@class,'btn btn-default btn-lg')]")).click();
		// driver.findElement(By.xpath("//button[contains(@class,'//div[@id='search']/descendant::button')]"));
		Assert.assertTrue(driver.findElement(By.linkText("HP LP3065")).isDisplayed());

	}

	@Test(priority = 2)
	public void searchWithInValidProduct() {
		driver.findElement(By.name("search")).sendKeys(dataProp.getProperty("invalidProduct"));
		driver.findElement(By.xpath("//button[contains(@class,'btn btn-default btn-lg')]")).click();
		// driver.findElement(By.xpath("//button[contains(@class,'//div[@id='search']/descendant::button')]"));
		String actualSearchMsg = driver.findElement(By.xpath("//div[@id='content']/h2/following-sibling::p")).getText();
		Assert.assertEquals(actualSearchMsg, dataProp.getProperty("NoProductTextInSearchResults"));
	}

	@Test(priority = 3)
	public void searchWithEmptyProduct() {
		driver.findElement(By.name("search")).sendKeys("");
		driver.findElement(By.xpath("//button[contains(@class,'btn btn-default btn-lg')]")).click();
		// driver.findElement(By.xpath("//button[contains(@class,'//div[@id='search']/descendant::button')]"));
		String actualSearchMsg = driver.findElement(By.xpath("//div[@id='content']/h2/following-sibling::p")).getText();
		Assert.assertEquals(actualSearchMsg, dataProp.getProperty("NoProductTextInSearchResults"));
	}

}
