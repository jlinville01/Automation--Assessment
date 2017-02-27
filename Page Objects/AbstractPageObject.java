package page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.Annotations;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractPageObject
{
	protected WebDriver driver;
	protected WebDriverWait wait;
	public java.sql.Connection con;
	public java.util.Properties props;
	
	public AbstractPageObject(WebDriver driver) throws Exception
	{
		PageFactory.initElements(driver, this);
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 30);
	}
	
	public AbstractPageObject(WebDriver driver, java.sql.Connection connection) throws Exception
	{
		PageFactory.initElements(driver, this);
		this.driver = driver;
		this.con = connection;
		this.wait = new WebDriverWait(driver, 30);
	}
	
	/**
	 * Checks to verify all Ajax calls have completed.
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
	 * Passes the driver and wait to the overloaded waitForLoadingOverlayToDisappear method.
	 *
	 * @param driver	driver variable passed to initialize method
	 * @param wait		wait variable passed to initialize method
	 */
	public static void waitForLoadingOverlayToDisappear(WebDriver driver, WebDriverWait wait)
	{
		waitForLoadingOverlayToDisappear(driver, wait, "div.loadingspinner.loading");
	}

	/**
	 * Checks and waits for loading spinner image to become invisible.
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
	
	/**
	 * Returns element specified by the proxied webelement.
	 *
	 * @param fieldName		proxied webelement being passed
	 * @throws Exception
	 */
	public By getBy(String fieldName) throws Exception
	{
		try
		{
			return new Annotations(this.getClass().getDeclaredField(fieldName)).buildBy();
		}
		catch (NoSuchFieldException e)
		{
			return null;
		}
	}
}
