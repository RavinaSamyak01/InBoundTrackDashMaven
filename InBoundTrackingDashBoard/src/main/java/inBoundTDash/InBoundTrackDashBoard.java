package inBoundTDash;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
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

		// --Opening Chrome Browser
		DesiredCapabilities capabilities = new DesiredCapabilities();
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless", "--window-size=1920,1200");
		options.addArguments("--incognito");
		options.addArguments("--test-type");
		options.addArguments("--no-proxy-server");
		options.addArguments("--proxy-bypass-list=*");
		options.addArguments("--disable-extensions");
		options.addArguments("--no-sandbox");
		options.addArguments("enable-automation");
		options.addArguments("--dns-prefetch-disable");
		options.addArguments("--disable-gpu");
		String downloadFilepath = System.getProperty("user.dir") + "\\src\\main\\resources\\Downloads";
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.prompt_for_download", "false");
		chromePrefs.put("safebrowsing.enabled", "false");
		chromePrefs.put("download.default_directory", downloadFilepath);
		options.setExperimentalOption("prefs", chromePrefs);
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		capabilities.setPlatform(Platform.ANY);
		Driver = new ChromeDriver(options);

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
			SendEmail.sendMail("ravina.prajapati@samyak.com,asharma@samyak.com", subject, msg.toString(), File);

			// SendEmail.sendMail("ravina.prajapati@samyak.com, asharma@samyak.com
			// ,parth.doshi@samyak.com", subject, msg.toString(), File);

		} catch (Exception ex) {
			Logger.getLogger(InBoundTrackDashBoard.class.getName()).log(Level.SEVERE, null, ex);

		}

	}
}
