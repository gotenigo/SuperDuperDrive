package cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;



public class HomePage {

    // Logout
    @FindBy(css="#LogoutBtn")
    private WebElement LogoutButton;



    // ==================== Note Element
    //action Element -> to click on
    @FindBy(css="#nav-notes-tab")
    private WebElement ShowNoteTab;

    @FindBy(css="#AddNoteBtn")
    private WebElement AddNewNoteButton;

    @FindBy(css="#NoteDeleteBtn")
    private WebElement FirstNoteDelete;

    @FindBy(css="#NoteEditBtn")
    private WebElement FirstNoteEdit;

    @FindBy(css="#SaveNoteChangeBtn")
    private WebElement SaveNoteChangeButton;


    //View Element -> to read and check against
    @FindBy(css="#ViewNoteTitleID")
    private WebElement ViewFirstNoteTitle;

    @FindBy(css="#ViewNoteDescID")
    private WebElement ViewFirstNoteDesc;


    //input Element  -> to inject
    @FindBy(css="input#note-title")
    private WebElement InputNoteTitle;

    @FindBy(css="textarea#note-description")
    private WebElement InputNoteDescription;





    // ====================Credential Element
    //action Element -> to click on
    @FindBy(css="#nav-credentials-tab")
    private WebElement ShowCredentialTab;


    @FindBy(css="#AddCredentialBtn")
    private WebElement AddCredentialButton;

    @FindBy(css="#CredentialDeleteBtn")
    private WebElement FirstCredDeleteButton;

    @FindBy(css="#CredentialEditBtn")
    private WebElement FirstCredEditButton;

    @FindBy(css="#SaveCredentialChangeBtn")
    private WebElement SaveCredentialChangeButton;

    @FindBy(css="#CloseCredentialModalBtn")
    private WebElement CloseCredentialModalButton;



    //View Element -> to read and check against
    @FindBy(css="#ViewCredentialUrlID")
    private WebElement FirstCredUrlID;

    @FindBy(css="#ViewCredentialUsernameID")
    private WebElement ViewFirstCredUsername;

    @FindBy(css="#ViewCredentialPasswordID")
    private WebElement ViewFirstCredPassword;


    //input Element -> to inject
    @FindBy(css="input#credential-url")
    private WebElement InputCredUrl;

    @FindBy(css="input#credential-username")
    private WebElement InputCredUsername;

    @FindBy(css="input#credential-password")
    private WebElement InputCredRealPassword;








    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }


    public void Logout() {
        LogoutButton.click();
    }

    // Note Method

    public void AddNote(String Title, String Description ) throws InterruptedException {

        this.ShowNoteTab.click();
        Thread.sleep(2000);
        this.AddNewNoteButton.click();
        Thread.sleep(2000);
        this.InputNoteTitle.sendKeys(Title);
        this.InputNoteDescription.sendKeys(Description);
        this.SaveNoteChangeButton.click();

    }


    public void EditNote(String Title, String Description) throws InterruptedException {

        this.ShowNoteTab.click();
        Thread.sleep(2000);
        this.FirstNoteEdit.click();
        Thread.sleep(2000);
        this.InputNoteTitle.sendKeys(Title);
        this.InputNoteDescription.sendKeys(Description);
        this.SaveNoteChangeButton.click();
    }


    public void DeleteNote() {
        this.FirstNoteDelete.click();
    }



    //** getter and setter
    public String getViewFirstNoteTitle() {
        String value="";
        try {
            System.out.println("...ViewFirstNoteTitle.getText()="+ViewFirstNoteTitle.getText());
             value = ViewFirstNoteTitle.getText();
        }
        catch (NoSuchElementException e) {
            System.out.println("No Element !");
        }
        return value;
    }



    public String getViewFirstNoteDesc() {
        return ViewFirstNoteDesc.getText();
    }







// Credential Method

    public void AddCredential(String url, String username, String password ) throws InterruptedException {

        this.ShowCredentialTab.click();
        Thread.sleep(2000);
        this.AddCredentialButton.click();
        Thread.sleep(2000);
        this.InputCredUrl.sendKeys(url);
        this.InputCredUsername.sendKeys(username);
        this.InputCredRealPassword.sendKeys(password);
        this.SaveCredentialChangeButton.click();

    }


    public void EditCredential(String url, String username, String password) throws InterruptedException {

        this.ShowCredentialTab.click();
        Thread.sleep(2000);
        this.FirstCredEditButton.click();
        Thread.sleep(2000);
        this.InputCredUrl.sendKeys(url);
        this.InputCredUsername.sendKeys(username);
        this.InputCredRealPassword.sendKeys(password);
        this.SaveCredentialChangeButton.click();

    }



    public void DeleteCredential() {
        this.FirstCredDeleteButton.click();
    }



    //** getter and setter
    public String getFirstCredUrlID() {

        System.out.println("...getFirstCredUrlID="+FirstCredUrlID.getText());
        return FirstCredUrlID.getText();
    }


    public String getViewFirstCredUsername() {

        String value="";
        try {
            System.out.println("...ViewFirstCredUsername.getText()="+ViewFirstCredUsername.getText());
            value = ViewFirstCredUsername.getText();
        }
        catch (NoSuchElementException e) {
            System.out.println("No Element !");
        }
        return value;
    }




    public String getViewFirstCredPassword() {
        return ViewFirstCredPassword.getText();
    }



    public String ViewDecryptedPassword() throws InterruptedException {
        this.ShowCredentialTab.click();
        Thread.sleep(2000);
        this.FirstCredEditButton.click();
        Thread.sleep(2000);
        String value=InputCredRealPassword.getAttribute("value"); // for the Modal you need the attribute
        System.out.println("...InputCredRealPassword.getAttribute(value)="+value);
        CloseCredentialModalButton.click();

        return value;
    }




}
