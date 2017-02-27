package database;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.openqa.selenium.WebDriver;

import page_objects.AbstractPageObject;

public class DB_Assessment extends AbstractPageObject
{
	private Statement stmt;
	private ResultSet result = null;
	
	public DB_Assessment(WebDriver driver, java.sql.Connection connection) throws Exception 
	{
		super(driver, connection);
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
				
				if (Integer.parseInt(question_id) != 14)
				{
					assertThat(order_label, is("A"));
				}
				else
				{
					assertThat(order_label, is("B"));
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
