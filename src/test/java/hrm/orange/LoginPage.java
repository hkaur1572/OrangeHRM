package hrm.orange;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Set;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginPage {
	WebDriver driver;

	@BeforeMethod
	public void before() {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://opensource-demo.orangehrmlive.com/");
		driver.manage().window().maximize();
	}

	@Test
	public void forgetPassword() {

	WebElement forgetPass=	driver.findElement(By.id("forgotPasswordLink"));
	Assert.assertEquals(forgetPass.isEnabled(), true);
	forgetPass.click();
		
		WebDriverWait webDriverWait = new WebDriverWait(driver, 5);

	}

	@Test
	public void ValidUserInvalidPass() {

		driver.findElement(By.id("txtUsername")).sendKeys("admin");
		WebElement pass = driver.findElement(By.id("txtPassword"));
		pass.sendKeys("123");
		driver.findElement(By.id("btnLogin")).click();

		WebElement invalidPass = driver.findElement(By.id("spanMessage"));
		System.out.println("Message getting after entering wrong password " + invalidPass.getText());
		Assert.assertEquals(invalidPass.getText(), "Invalid credentials");

	}

	@Test
	public void emptyUsername() {
		driver.findElement(By.id("txtUsername")).sendKeys("");
		driver.findElement(By.id("txtPassword")).sendKeys("admin");
		driver.findElement(By.id("btnLogin")).click();
		WebElement userMessage = driver.findElement(By.id("spanMessage"));

		Assert.assertEquals(userMessage.getText(), "Username cannot be empty");
		System.out.println("Message Displayed when you don not enter user Name: " + userMessage.getText());
	}

	@Test
	public void invalidUserName() {
		driver.findElement(By.id("txtUsername")).sendKeys("ad113");
		driver.findElement(By.id("txtPassword")).sendKeys("admin123");
		driver.findElement(By.id("btnLogin")).click();

		WebElement invaliduser = driver.findElement(By.id("spanMessage"));
		System.out.println("Message getting after entering invalid UserName " + invaliduser.getText());
		Assert.assertEquals(invaliduser.getText(), "Invalid credentials");

	}

	@Test
	public void validData() {
		WebElement userName = driver.findElement(By.id("txtUsername"));

		WebElement password = driver.findElement(By.id("txtPassword"));

		WebElement loginButton = driver.findElement(By.id("btnLogin"));
		userName.sendKeys("Admin");
		Assert.assertEquals(userName.getAttribute("value"), "Admin");
		password.sendKeys("admin123");
		Assert.assertEquals(password.getAttribute("value"), "admin123");
		String currenthandle = driver.getWindowHandle();
		System.out.println("current handle " + currenthandle);
		Assert.assertEquals(loginButton.isEnabled(), true);
		loginButton.click();
		String newhandle = handle(currenthandle);

		driver.switchTo().window(currenthandle);

	}

	public String handle(String currenthandle) {
		// TODO Auto-generated method stub
		Set<String> allWindowHandles = driver.getWindowHandles();
		for (String handle : allWindowHandles) {
			if (!handle.equals(currenthandle))
				return handle;
		}
		return null;
	}

	@AfterMethod
	public void after() {
		driver.quit();
	}
}
