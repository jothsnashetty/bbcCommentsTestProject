package common;

public class CommentsElements {
	private Driver driver;
	public CommentsElements(Driver driver) {
		this.driver = driver;
	}
	public final static String bbcSignInLink = "//a//span[text()='Sign in']";
	public final static String commentsLink = "//a[@class='blogs-comments-link']";
	public final static String addCommentTextBox = "//textarea[@class='cmts-post-box'][@name='comment']";
	public final static String commentsSection = "//div[@class='cmts-header cmts-clearfix']//h3[text()='Comments']";
	public final static String commentsIframe = "//iframe[@id='bbc-blogs-comments-iframe']";
	public final static String signInLink = "//div[@class='cmts-header cmts-clearfix']//a[text()='Sign in']";
	public final static String registerLink = "//div[@class='cmts-header cmts-clearfix']//a[text()='Register']";
	public final static String usernameField = "//input[@id='username-input']";
	public final static String passwordField = "//input[@id='password-input']";
	public final static String signInButton = "//button//span[text()='Sign in']";
	public final static String createDisplayNameLink = "//div[@class='cmts-header cmts-clearfix']//a[text()='Create a display name']";
	public final static String displayNameField = "//input[@id='displayName-input']";
	public final static String continueButton = "//span[text()='Continue']";
	public final static String postCommentButton = "//input[@class='cmts-submit cmts-button']";
	public final static String birthDayField = "//input[@id='day-input']";
	public final static String birthMonthField = "//input[@id='month-input']";
	public final static String birthYearField = "//input[@id='year-input']";
	public final static String infoButton = "//button[@class='field-explainer__toggle']";
	public final static String nextButton = "//span[text()='Next']";
	public final static String emailField = "//input[@id='email-input']";
	public final static String postcodeField = "//input[@id='postcode-input']";
	public final static String genderSelectField = "//select[@id='gender-input']";
	public final static String noThanksField = "//span[text()='No, thanks']";
	public final static String registerButton = "//button[@id='submit-button']";
	public final static String passwordError = "//span[@id='form-error-password']";
	public final static String usernameError = "//span[@id='form-error-username']";
	public final static String commentSuccessMessage = "//div[@class='cmts-message cmts-message-success']";
	public final static String closePopup = "//a[@href='javascript:eDRLayer.destroy();'][@class='close']";
	
	//gmail
	public final static String gmailUsernameField = "//input[@id='identifierId']";
	public final static String gmailPasswordField = "//input[@name='password']";
	public final static String activationEmailSubject = "//span[@class='bog']//b[text()='Please verify your email address']";
	public final static String activationLink = "//a[text()='Verify your email address']";
    public static final String profileIcon = ".//*[@id='gb']//span[@class='gb_8a gbii']";
	public static final String signOutButton = ".//*[@id='gb_71']";
	public static final String gmailPrimarySection = "//div[text()='Primary']";
	public static final String selectAll = "//span[@class='T-Jo J-J5-Ji T-Jo-aMF T-Jo-auq']";
	public static final String BBCAccountActivationMail = "//div[contains(text(),'unread')]//span[text()='BBC Account']";


}
