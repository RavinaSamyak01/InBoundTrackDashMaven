package inBoundTDash;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;


public class InBoundTrackDashBoard {
	public static StringBuilder msg = new StringBuilder();
	public static WebDriver Driver;
	public static Logger logger;

	@BeforeSuite
	public void beforeMethod() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		// options.addArguments("headless");
		// options.addArguments("--incognito");
		options.addArguments("--test-type");
		options.addArguments("--no-proxy-server");
		options.addArguments("--proxy-bypass-list=*");
		options.addArguments("--disable-extensions");
		options.addArguments("--no-sandbox");
		options.addArguments("--start-maximized");
		// options.addArguments("--start-fullscreen");

		// options.addArguments("--headless");
		// options.addArguments("window-size=1366x788");
		capabilities.setPlatform(Platform.ANY);
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		Driver = new ChromeDriver(options);
		// Default size
		Dimension currentDimension = Driver.manage().window().getSize();
		int height = currentDimension.getHeight();
		int width = currentDimension.getWidth();
		System.out.println("Current height: " + height);
		System.out.println("Current width: " + width);
		System.out.println("window size==" + Driver.manage().window().getSize());

		// Set new size
		/*
		 * Dimension newDimension = new Dimension(1366, 788);
		 * Driver.manage().window().setSize(newDimension);
		 */
		// Getting
		Dimension newSetDimension = Driver.manage().window().getSize();
		int newHeight = newSetDimension.getHeight();
		int newWidth = newSetDimension.getWidth();
		System.out.println("Current height: " + newHeight);
		System.out.println("Current width: " + newWidth);

	}

	@Test
	public void inBTDashBoard() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);

		// --Production
		Driver.get("https://trackingdash.nglog.com/inbound/dashboard/OTUwNzY0");
		// --Staging
		// Driver.get("http://10.20.205.70:5071/inbound/dashboard/OTUwNTcz");
		System.out.println("Load the URL");
		Thread.sleep(5000);

		try {
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("ScrollWoSubGrid")));
			System.out.println("InBound Tracking DashBoard page is Opened");
			msg.append("**********InBound Tracking DashBoard page is Opened **********" + "\n");

		} catch (Exception e) {
			System.out.println("InBound Tracking DashBoard page is giving error");
			msg.append("**********InBound Tracking DashBoard page is giving error **********" + "\n");

		}
		// --Take the screenshot
		Screenshots.takeSnapShot(Driver, ".\\src\\main\\resources\\Screenshots\\result.jpg");


	}

	@AfterTest
	public void Complete() throws Exception {
		Driver.close();
		Driver.quit();

	}

	@AfterSuite
	public void SendEmail() throws Exception {
		System.out.println("====Sending Email=====");
		// Send Details email

		msg.append("*** This is automated generated email and send through automation script ***" + "\n");
		msg.append("********** Please check the screenshot for the result **********" + "\n");
		String subject = "Selenium Automation Script: InBound Tracking DashBoard";
		String File = ".\\src\\main\\resources\\Screenshots\\result.jpg";

		try {
//			/kunjan.modi@samyak.com, pgandhi@samyak.com,parth.doshi@samyak.com
			SendEmail.sendMail("ravina.prajapati@samyak.com,asharma@samyak.com,parth.doshi@samyak.com", subject,
					msg.toString(), File);

			// SendEmail.sendMail("ravina.prajapati@samyak.com, asharma@samyak.com
			// ,parth.doshi@samyak.com", subject, msg.toString(), File);

		} catch (Exception ex) {
			Logger.getLogger(InBoundTrackDashBoard.class.getName()).log(Level.SEVERE, null, ex);

		}

	}
}
