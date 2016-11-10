/*
 * Page Object for StandOut platform
 */

package page_objects;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tmbc.ConfigurationSetter;
import com.tmbc.JDBCUtils;

public abstract class StandOut 
{
	public abstract void runTest() throws Exception;
	public abstract void tearDown() throws Exception;
	protected WebDriverWait wait;
	
	public WebDriver driver;
	public java.util.Properties props;
	public Login login;
	public java.sql.Connection con;
	public int clientId;
	public String baseUrl;
	public StringBuffer testResults = new StringBuffer();

	public int errorCount = 0;
	public java.util.Vector<Exception> exceptions = new java.util.Vector<Exception>();

	/**
	 * Establishes a universal setup() to be used initialize all test cases.
	 * Includes setting up properties for database connection, driver and wait.
	 */
	public void setUp()
	{
		this.props = ConfigurationSetter.loadProperties("config/TmbcSelenium.properties");
		JDBCUtils.initialize(props.getProperty("DbConnectionString"), props.getProperty("DbUser"), props.getProperty("DbPassword"));
		this.con = JDBCUtils.getConnection();
		this.baseUrl = props.getProperty("selenium.baseUrl");
	
		System.setProperty(props.getProperty("selenium.driverType"), props.getProperty("selenium.driverLocation"));
		driver = new ChromeDriver();
		
		driver.get(this.baseUrl);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		this.wait = new WebDriverWait(driver, 30);
	}

	/**
	 * Initializes database connection and passes properties, webdriver and baseurl to overloaded initialize method.
	 *
	 * @param pr	properties variable passed to initialize method
	 * @param dr	webdriver variable passed to initialize method
	 * @param url	baseurl variable passed to initialize method
	 * @param db	establishes database connection for case
	 */
	public void initialize(java.util.Properties pr, WebDriver dr, String url, java.sql.Connection db)
	{
		this.con = db;
		this.initialize(pr, dr, url);
	}
	
	/**
	 * Initializes the properties, webdriver, baseurl and a wait.
	 *
	 * @param pr	properties variable passed to initialize method
	 * @param dr	webdriver variable passed to initialize method
	 * @param url	baseurl variable passed to initialize method
	 * @param db	establishes database connection for case
	 */
	public void initialize(Properties pr, WebDriver wd, String url)
	{
		this.props = pr;
		this.driver = wd;
		this.baseUrl = url;
		
		this.wait = new WebDriverWait(driver, 30);
	}
	
	/**
	 * Checks to verify all Ajax calls have completed.
	 *
	 * @param pr	properties variable passed to initialize method
	 * @param dr	webdriver variable passed to initialize method
	 * @param url	baseurl variable passed to initialize method
	 * @param db	establishes database connection for case
	 */
	public static ExpectedCondition<Boolean> jQueryAJAXCallsHaveCompleted() 
	{
	    return new ExpectedCondition<Boolean>() 
	    {
	        @Override
	        public Boolean apply(WebDriver driver) 
	        {
	            return (Boolean) ((JavascriptExecutor) driver).executeScript("return (window.jQuery != null) && (jQuery.active === 0);");
	        }
	    };
	}
	
	/**
	 * Passes the driver and wait to the overloaded waitForLoadingOverlayToDisappear method
	 *
	 * @param driver	driver variable passed to initialize method
	 * @param wait		wait variable passed to initialize method
	 */
	public static void waitForLoadingOverlayToDisappear(WebDriver driver, WebDriverWait wait)
	{
		StandOut.waitForLoadingOverlayToDisappear(driver, wait, "div.loadingspinner.loading");
	}

	/**
	 * Checks and waits for loading spinner image to become invisible
	 *
	 * @param driver	driver variable passed to initialize method
	 * @param wait		wait variable passed to initialize method
	 * @param cssSel	css locator variable passed to initialize method
	 */
	public static void waitForLoadingOverlayToDisappear(WebDriver driver, WebDriverWait wait, String cssSel)
	{
		java.util.List<WebElement> spinner = driver.findElements(By.cssSelector(cssSel));
		
		if (spinner.size() > 0 && spinner.get(0).isDisplayed())
		{
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(cssSel)));
		}
	}
	
	public String printTestResults()
	{
		return this.testResults.toString();
	}
}
