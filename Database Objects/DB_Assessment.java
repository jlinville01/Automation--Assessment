/*
 * Database methods for site Assessment
 */

package database;

import java.sql.ResultSet;
import java.sql.Statement;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import page_objects.StandOut;

public class DB_Assessment extends StandOut
{
	private Statement stmt;
	
	public void runTest() throws Exception {};
	public void tearDown() throws Exception {};
	
	/**
	 * Instantiates the DB_Assessment page object.
	 * 
	 * @param driver	the driver for this class.
	 * @param connection	the db connection for this class.
	 * @throws Exception
	 */
	public DB_Assessment(WebDriver driver, java.sql.Connection connection) throws Exception 
	{
		PageFactory.initElements(driver, this);
		this.wait = new WebDriverWait(driver, 30);
		this.con = connection;
	}
	
	/**
	 * Compares the expected value entered during the site Assessment against the database value saved. 
	 *
	 * @param email		the email used to call the database for data.
	 */
	public void validateAssessment(String email) throws SQLException 
	{
		try
		{
			stmt = this.con.createStatement();
			String validate = "SELECT r.question_id, r.answer_id, order_label FROM response r JOIN question_answers qa ON r.answer_id = qa.answer_id JOIN person_token pt ON pt.token_id = r.token_id JOIN person p ON pt.person_id = p.uid WHERE p.email = '"
					+ email + "';";
			result = stmt.executeQuery(validate);
			this.con.commit();

			while (result.next())
			{
				// Fetch values from DB to compare
				String question_id = result.getString("question_id");
				String order_label = result.getString("order_label");

				if (Integer.parseInt(question_id) = 14)
				{
					assertThat(order_label, is("B"));
				}
				else
				{
					assertThat(order_label, is("A"));
				}
			}
			result.close();
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
			result.close();
		}
	}
}
