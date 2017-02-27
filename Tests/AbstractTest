package tests;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tmbc.ConfigurationSetter;
import com.tmbc.JDBCUtils;

import page_objects.App;

public abstract class AbstractTest
{
	protected WebDriver driver;
	protected App app;
	WebDriverWait wait;
	public java.util.Properties props;
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
	@Before
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
		
		app = new App(driver, con, props);
	}
	
	public void setUp(java.util.Properties pr, WebDriver dr, String url, java.sql.Connection db)
	{
		this.initialize(pr, dr, url, db);
	}
	
	public void setUp(java.util.Properties pr, WebDriver dr, String url)
	{
		this.initialize(pr, dr, url);
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
	}
	
	@After
	public void tearDown()
	{
		driver.quit();
	}
	
	public String printTestResults()
	{
		return this.testResults.toString();
	}
}
