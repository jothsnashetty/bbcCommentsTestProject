package stepDefinition;

import java.util.ArrayList;
import org.junit.Assert;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import common.CommentsElements;
import common.Driver;
import common.TestProperties;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class CommentStepDefs {
	private Driver driver;
	CommentsElements commentsElements;

	@Before
	public void setUp() {
		driver = new Driver(TestProperties.get("browser"));
		commentsElements = new CommentsElements(driver);
	}

	@Given("^I am on bbc news page$")
	public void i_am_on_bbc_news_page() throws Throwable {
		goToBBCNewsPage();
		driver.maximizeWindow();
	}

	@When("^I go to comments$")
	public void i_go_to_comments() throws Throwable {
		goToBBCComments();
	}

	@When("^I Register to comment$")
	public void i_Register_to_comment() throws Throwable {
		switchToCommentsIframe();
		if (isAddCommentBoxAbsent()) {
			if (isSignInLinkPresent()) {
				gotToSignInLink();
				signInToBBCAccount();
				if (isUsernameErrorPresent()) {
					goToBBCComments();
					switchToCommentsIframe();
					gotToRegisterLink();
					registerToBBCAccount();
				}
			}
		}
	}

	@Then("^I add a comment and post that comment$")
	public void i_add_a_comment_and_post_that_comment() throws Throwable {
		switchToCommentsIframe();
		if(isCreateDisplayNamePresent()){
		   clickOnCreateDisplayName();
			addDisplayName();
		}
		addComment();
		clickPostComment();
	}

	@Then("^I should see the success message$")
	public void i_should_see_the_success_message() throws Throwable {
	 Assert.assertTrue(verifyThatCommentSuccessMessagePresent());
	}
	
	@When("^I Sign in to comment$")
	public void i_Sign_in_to_comment() throws Throwable {
		switchToCommentsIframe();
		if (isAddCommentBoxAbsent()) {
			if (isSignInLinkPresent()) {
				gotToSignInLink();
				signInToBBCAccount();
			}
		}
	}

	@Given("^I Sign in$")
	public void i_Sign_in() throws Throwable {
		goToSignInInBBCNewsPage();
		signInToBBCAccount();
	}
	
	
	@After
	public void tearDown() throws InterruptedException {
		driver.close();
	}

	
	private void signInToBBCAccount() {
		driver.waitForElementToBeVisible(commentsElements.usernameField);
		driver.sendKeys(commentsElements.usernameField, TestProperties.get("accountUsername"));
		driver.sendKeys(commentsElements.passwordField, TestProperties.get("accountPassword"));
		driver.click(commentsElements.signInButton);
		if(isDisplayNamePresent())
			addDisplayName();
	}


	private void registerToBBCAccount() throws InterruptedException {
		enterBirthDetailsInBBCRegistrationForm();
		clickNext();
		enterAccountBBCRegistrationForm();
		goToGmailAndActivateBBCAccount();
	}

	private void enterBirthDetailsInBBCRegistrationForm() {
		driver.refresh();
		driver.sendKeys(commentsElements.birthDayField, TestProperties.get("birthDay"));
		driver.sendKeys(commentsElements.birthMonthField, TestProperties.get("birthMonth"));
		driver.sendKeys(commentsElements.birthYearField, TestProperties.get("birthYear"));
	}
	private void enterAccountBBCRegistrationForm() {
		driver.waitForElementToBeVisible(commentsElements.emailField);
		driver.sendKeys(commentsElements.emailField, TestProperties.get("accountUsername"));
		driver.sendKeys(commentsElements.passwordField, TestProperties.get("accountPassword"));
		driver.sendKeys(commentsElements.postcodeField, TestProperties.get("postcode"));
		driver.scrollPageToElement(commentsElements.postcodeField);
		driver.click(commentsElements.genderSelectField);
		driver.selectDropDownOption(commentsElements.genderSelectField, TestProperties.get("gender"));
		driver.click(commentsElements.noThanksField);
		driver.click(commentsElements.registerButton);
	}

	private void clickNext() {
		driver.click(commentsElements.nextButton);	
	}
	
	private void goToGmailAndActivateBBCAccount() throws InterruptedException {
		driver.goTo(TestProperties.get("gmailUrl"));
		driver.waitForElementToBeVisible(commentsElements.gmailUsernameField);
		driver.sendKeys(commentsElements.gmailUsernameField, TestProperties.get("gmailUsername"));
		driver.click(commentsElements.nextButton);
		driver.waitForElementToBeVisible(commentsElements.gmailPasswordField);
		driver.sendKeys(commentsElements.gmailPasswordField, TestProperties.get("gmailPassword"));
		driver.click(commentsElements.nextButton);
		driver.waitForElementToBeVisible(commentsElements.gmailPrimarySection,20);
		driver.click(commentsElements.activationEmailSubject);
		driver.waitForElementToBeVisible(commentsElements.activationLink);
		driver.click(commentsElements.activationLink);
		ArrayList<String> tabs = new ArrayList<String> (driver.getWebDriver().getWindowHandles());
		driver.getWebDriver().switchTo().window(tabs.get(1));
		if(driver.isElementPresent(commentsElements.usernameField))
		   signInToBBCAccount();
		Thread.sleep(2000);
		 driver.close();
		 driver.getWebDriver().switchTo().window(tabs.get(0));
	}

	private void gotToSignInLink() {
		driver.click(commentsElements.signInLink);
	}

	private void gotToRegisterLink() {
		driver.click(commentsElements.registerLink);
	}

	private void goToSignInInBBCNewsPage() {
		driver.click(commentsElements.bbcSignInLink);
	}

	private void goToBBCNewsPage() {
		driver.goTo(TestProperties.get("bbcNewsUrl"));
	}

	private void goToBBCComments() {
		driver.goTo(TestProperties.get("bbcCommentsUrl"));
		driver.click(commentsElements.commentsLink);
		driver.refresh();
	}

	private void switchToCommentsIframe() {
		driver.switchToIframByXPath(commentsElements.commentsIframe);
	}

	private void addComment() {
		driver.sendKeys(commentsElements.addCommentTextBox, TestProperties.get("commentText"));
	}

	private void clickPostComment() {
		driver.click(commentsElements.postCommentButton);
	}

	private boolean isAddCommentBoxAbsent() {
		return driver.isElementAbsent(commentsElements.addCommentTextBox);
	}

	private boolean isSignInLinkPresent() {
		return driver.isElementPresent(commentsElements.signInLink);
	}

	private boolean isUsernameErrorPresent() {
		return driver.isElementPresent(commentsElements.usernameError);
	}

	private boolean isCreateDisplayNamePresent() {
		return driver.isElementPresent(commentsElements.createDisplayNameLink);
	}
	
	private void clickOnCreateDisplayName() {
		driver.click(commentsElements.createDisplayNameLink);
	}

	private void addDisplayName() {
       driver.sendKeys(commentsElements.displayNameField, TestProperties.get("displayName"));
	   driver.click(commentsElements.continueButton);
	}
	
	private boolean isDisplayNamePresent() {
		return driver.isElementPresent(commentsElements.displayNameField);
	}
	
	private boolean verifyThatCommentSuccessMessagePresent() {
		switchToCommentsIframe();
		return driver.isElementPresent(commentsElements.commentSuccessMessage);
	}
}
