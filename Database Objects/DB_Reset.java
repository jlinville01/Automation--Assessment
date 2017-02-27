package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.openqa.selenium.WebDriver;

import page_objects.AbstractPageObject;

public class DB_Reset extends AbstractPageObject
{
	private Statement stmt;
	private ResultSet result = null;
	private ResultSet dA = null;
	private ResultSet cA = null;
	private ResultSet aR = null;
	
	public DB_Reset(WebDriver driver, java.sql.Connection connection) throws Exception 
	{
		super(driver, connection);
	}
	
	/**
	 * Deletes and creates six accounts.
	 * Script adds 1-6 for each type of account.
	 *
	 * @param email		the mailbox for the accounts to be reset
	 * @param endEmail	the domain name for the accounts to be reset
	 * @param clientID	the client id for the account to be reset
	 * @throws SQLException 
	 */
	public void resetAccount(String email, String endEmail, String clientID) throws SQLException
	{
		try
		{
			stmt = con.createStatement();
			String runReset = "CALL Proc_ResetPersonaAccounts('" + email + "', '" + endEmail + "', '" + clientID + "');";
			result = stmt.executeQuery(runReset);
			stmt.close();
			result.close();
			this.con.commit();
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
			result.close();
		}
	}
	
	/**
	 * Deletes and creates six accounts without site onboarding.
	 * Script adds 1-6 for each type of account. 
	 *
	 * @param email		the mailbox for the accounts to be reset
	 * @param endEmail	the domain name for the accounts to be reset
	 * @param clientID	the client id for the account to be reset
	 */
	public void resetAccountNoOnboarding(String email, String endEmail, String clientID) throws SQLException
	{
		try
		{
			stmt = con.createStatement();
			String runReset = "CALL Proc_ResetPersonaAccountsWithoutOnBoarding('" + email + "', '" + endEmail + "', '" + clientID + "');";
			result = stmt.executeQuery(runReset);
			result.close();
			this.con.commit();
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
			result.close();
		}
	}
	
	/**
	 * Deletes and creates a base account which has not taken the Assessment.
	 *
	 * @param email		the email for the account to be reset
	 * @param clientID	the client id for the account to be reset
	 */
	public void resetAssessment(String email, String clientID) throws SQLException
	{
		try
		{
			stmt = con.createStatement();
			String deleteAccount = "CALL Proc_DeleteAccount('" + email + "');";
			dA = stmt.executeQuery(deleteAccount);
			this.con.commit();
			dA.close();
			String createAccount = "CALL Proc_CreateTestAccount('persona (test)', 'uno (test)', '"
					+ email + "', 'superman', '" + clientID + "', null, null, null, 2);";
			cA = stmt.executeQuery(createAccount);
			this.con.commit();
			cA.close();
			String assignRole = "CALL Proc_AssignRoleToPerson('" + email + "', 1);";
			aR = stmt.executeQuery(assignRole);
			this.con.commit();
			aR.close();
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
			dA.close();
			cA.close();
			aR.close();
		}
	}
	
	/**
	 * Deletes an account permanently.
	 *
	 * @param email		the email for the account to be reset
	 */
	public void deleteAccount(String email) throws SQLException
	{
		try
		{
			stmt = con.createStatement();
			String deleteAccount = "CALL Proc_DeleteAccount('" + email + "');";
			dA = stmt.executeQuery(deleteAccount);
			this.con.commit();
			dA.close();
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
			dA.close();
		}
	}
}
