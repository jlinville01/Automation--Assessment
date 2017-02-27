/*
 * Test completing the assessment as a new user
 */

package tests;

import org.junit.Test;

public class SubmitAssessment extends AbstractTest 
{
	@Test
	public void runTest() throws Exception 
	{
		// Reset Assessment Account
		app.dB_Reset().resetAssessment(props.getProperty("assessmentuser"), props.getProperty("defaultclientID"));
		
		// Login
		app.login().login(props.getProperty("assessmentuser"), props.getProperty("defaultpassword"));

		// Complete Assessment
		app.assessment().completeAssessment();

		// Logout
		app.mySnapshot().onboardingMySnapshotShort();
		app.login().logout();
		
		// Validate Assessment Results
		app.dB_Assessment().validateAssessment(props.getProperty("assessmentuser"));
	}
}
