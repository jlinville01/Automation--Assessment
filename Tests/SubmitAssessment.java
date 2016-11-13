/*
 * Test completing the assessment as a new user
 */

package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import database.DB_Assessment;
import database.DB_Reset;
import page_objects.Assessment;
import page_objects.Login;
import page_objects.StandOut;

public class SubmitAssessment extends StandOut 
{
	@Before
	public void setUp() 
	{
		super.setUp();
	}

	public void initialize(java.util.Properties pr, WebDriver dr, String url, java.sql.Connection db) 
	{
		super.initialize(pr, dr, url, db);
	}

	@Test
	public void runTest() throws Exception 
	{
		// Reset Assessment Account
		DB_Reset db_reset = new DB_Reset(this.driver, this.con);
		db_reset.resetAssessment(props.getProperty("assessmentuser"), props.getProperty("defaultclientID"));
		
		// Login
		this.login = new Login();
		this.login.initialize(this.props, this.driver, this.baseUrl);
		this.login.login(props.getProperty("assessmentuser"), props.getProperty("defaultpassword"));

		// Complete Assessment
		Assessment assessment = new Assessment(this.driver);
		assessment.completeAssessment();

		// Logout
		this.login.logout();
		
		// Validate Assessment Results
		DB_Assessment db = new DB_Assessment(this.driver, this.con);
		db.validateAssessment(props.getProperty("assessmentuser"));
	}

	@After
	public void tearDown() 
	{
		driver.quit();
	}
}
