/*
 * Database methods for resetting accounts
 */

package database;

import java.sql.ResultSet;
import java.sql.Statement;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import page_objects.StandOut;

public class DB_Reset extends StandOut
{
	private Statement stmt;
	
	public void runTest() throws Exception {};
	public void tearDown() throws Exception {};
	
	public DB_Reset(WebDriver driver, java.sql.Connection connection) throws Exception 
	{
		PageFactory.initElements(driver, this);
		this.wait = new WebDriverWait(driver, 30);
		this.con = connection;
	}
	
	/**
	 * Deletes and creates six new Persona accounts.
	 * Script adds 1-6 for each Persona account. 
	 *
	 * @param email		the mailbox for the accounts to be reset
	 * @param endEmail	the domain name for the accounts to be reset
	 * @param clientID	the client id for the account to be reset
	 */
	public void resetPersona(String email, String endEmail, String clientID)
	{
		try
	    {
	      stmt = con.createStatement();
	      String runReset = "CALL strengthaccelerator.Proc_ResetPersonaAccounts('" + email + "', '" + endEmail + "', '" + clientID + "');";
	      ResultSet result = stmt.executeQuery(runReset);
	      this.con.commit();
	      result.close();
	    }
	    catch (Exception exc)
	    {
	      exc.printStackTrace();
	    }
	}
	
	/**
	 * Deletes and creates six Persona accounts without site onboarding.
	 * Script adds 1-6 for each Persona account. 
	 *
	 * @param email		the mailbox for the accounts to be reset
	 * @param endEmail	the domain name for the accounts to be reset
	 * @param clientID	the client id for the account to be reset
	 */
	public void resetPersonaNoOnboarding(String email, String endEmail, String clientID)
	{
		try
	    {
	      stmt = con.createStatement();
	      String runReset = "CALL strengthaccelerator.Proc_ResetPersonaAccountsWithoutOnBoarding('" + email + "', '" + endEmail + "', '" + clientID + "');";
	      ResultSet result = stmt.executeQuery(runReset);
	      this.con.commit();
	      result.close();
	    }
	    catch (Exception exc)
	    {
	      exc.printStackTrace();
	    }
	}
	
	/**
	 * Deletes and creates a base account which has not taken the Assessment.
	 *
	 * @param email		the email for the account to be reset
	 * @param clientID	the client id for the account to be reset
	 */
	public void resetAssessment(String email, String clientID) throws Exception
	{
		try 
		{
			stmt = con.createStatement();
			String deleteAccount = "CALL `strengthaccelerator`.`Proc_DeleteAccount`('" + email + "');";
			ResultSet dA = stmt.executeQuery(deleteAccount);
			this.con.commit();
			dA.close();
			String createAccount = "CALL `strengthaccelerator`.`Proc_CreateTestAccount`('persona (test)', 'uno (test)', '"
					+ email + "', 'superman', '" + clientID + "', null, null, null, 2);";
			ResultSet cA = stmt.executeQuery(createAccount);
			this.con.commit();
			cA.close();
			String assignRole = "CALL `strengthaccelerator`.`Proc_AssignRoleToPerson`('" + email + "', 1);";
			ResultSet aR = stmt.executeQuery(assignRole);
			this.con.commit();
			aR.close();
		} 
		catch (Exception exc) 
		{
			exc.printStackTrace();
		}
	}
}
