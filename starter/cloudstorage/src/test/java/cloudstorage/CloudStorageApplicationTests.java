package cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	public String baseURL;


	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}






	@Test  // test we can access to login page as per Spring Security setup
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Sign In Form", driver.getTitle());
	}


	@Test // test we can singup page  as per Spring Security setup
	public void getSignUPPage() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up Form", driver.getTitle());
	}


	@Test // Test UnauthorizedPageAccess get redirected to Login page
	public void getHomePage() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Sign In Form", driver.getTitle());
	}












	//1. Write Tests for User Signup, Login, and Unauthorized Access Restrictions.
	@Test
	public void testUserSignupLoginAndLogout() {
		baseURL="http://localhost:"+ this.port;

		String username = "gg";
		String password = "password";

		//Sign up 1st
		driver.get(baseURL + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Gothard1", "GOTENI1", username, password);
		Assertions.assertEquals("Sign In Form", driver.getTitle());

		//Login 2nd
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		Assertions.assertEquals("Home", driver.getTitle());

		//Logout
		HomePage homePage = new HomePage(driver);
		homePage.Logout();

		Assertions.assertEquals("Sign In Form", driver.getTitle());
		driver.get(baseURL + "/home");
		Assertions.assertEquals("Sign In Form", driver.getTitle());

	}











	//2. Write Tests for Note Creation, Viewing, Editing, and Deletion.
	@Test
	public void testNoteCreationViewingEditingAndDelete() throws InterruptedException {


		baseURL="http://localhost:"+ this.port;

		String username = "gg1";
		String password = "password1";

		//Sign up 1st
		driver.get(baseURL + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Gothard1", "GOTENI1", username, password);
		Assertions.assertEquals("Sign In Form", driver.getTitle());

		//Login 2nd
		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		Assertions.assertEquals("Home", driver.getTitle());


		//Add a New Note
		HomePage homePage = new HomePage(driver);
		homePage.AddNote("Hello Note","I am the just a test !");
		Assertions.assertEquals("Hello Note", homePage.getViewFirstNoteTitle());
		Assertions.assertEquals("I am the just a test !", homePage.getViewFirstNoteDesc());

		//Edit Existing Note
		homePage.EditNote("-","-");
		Assertions.assertEquals("Hello Note-", homePage.getViewFirstNoteTitle());
		Assertions.assertEquals("I am the just a test !-", homePage.getViewFirstNoteDesc());


		//Delete Existing Note
		homePage.DeleteNote();
		Assertions.assertEquals("", homePage.getViewFirstNoteTitle() );


	}





















	//3. Write Tests for Credential Creation, Viewing, Editing, and Deletion.
	@Test
	public void testCredentialCreationViewingEditingAndDelete() throws InterruptedException {


		baseURL="http://localhost:"+ this.port;

		String username = "gg3";
		String password = "password2";

		//Sign up 1st
		System.out.println(" 1 >> SIng up  <<");
		driver.get(baseURL + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Gothard1", "GOTENI1", username, password);
		Assertions.assertEquals("Sign In Form", driver.getTitle());

		//Login 2nd
		System.out.println("2 >> Login  <<");
		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		Assertions.assertEquals("Home", driver.getTitle());


		//Add a New Credential
		System.out.println("3 >> Add a New Credential  <<");
		HomePage homePage = new HomePage(driver);
		homePage.AddCredential("www.google.com","gg","password");
		Assertions.assertEquals("www.google.com", homePage.getFirstCredUrlID());
		Assertions.assertEquals("gg", homePage.getViewFirstCredUsername());
		Assertions.assertNotEquals("password", homePage.getViewFirstCredPassword());
		Assertions.assertEquals("password", homePage.ViewDecryptedPassword());


		//Edit Existing Credential
		System.out.println("4 >> Edit Existing Credential  <<");
		homePage.EditCredential("-","-","-");
		Assertions.assertEquals("www.google.com-", homePage.getFirstCredUrlID());
		Assertions.assertEquals("gg-", homePage.getViewFirstCredUsername());
		Assertions.assertNotEquals("password-", homePage.getViewFirstCredPassword());
		Assertions.assertEquals("password-", homePage.ViewDecryptedPassword());



		//Delete Existing Credential
		System.out.println("5 >> Delete Existing Credential  <<");
		homePage.DeleteCredential();
		Assertions.assertEquals("", homePage.getViewFirstNoteTitle() );


	}






}
