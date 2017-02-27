package page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;


public class App 
{
	private WebDriver driver;
	private java.sql.Connection con;
	private java.util.Properties props;
	
	public StandOut(WebDriver driver, java.sql.Connection con, java.util.Properties props)
	{
		PageFactory.initElements(driver, this);
		this.driver = driver;
		this.con = con;
		this.props = props;
	}
	
	public DB_Assessment dB_Assessment() throws Exception { return new DB_Assessment(driver, con); }
	public DB_Reset dB_Reset() throws Exception { return new DB_Reset(driver, con); }
	
	public Assessment assessment() throws Exception { return new Assessment(driver); }
	public Login login() throws Exception { return new Login(driver, props); }
	public MyResources myResources() throws Exception { return new MyResources(driver); }
}
