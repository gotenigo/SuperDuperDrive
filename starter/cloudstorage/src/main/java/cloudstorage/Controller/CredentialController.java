package cloudstorage.Controller;


import cloudstorage.Model.DAO.Credential;
import cloudstorage.Model.DAO.Note;
import cloudstorage.Model.DAO.User;
import cloudstorage.Model.Form.CredentialForm;
import cloudstorage.services.CredentialService;
import cloudstorage.services.NoteService;
import cloudstorage.services.UserService;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/home")
public class CredentialController {



    @Autowired
    private CredentialService credentialService;
    @Autowired
    private UserService userService;







    @GetMapping("/Credential/Edit")
    public String  EditCredential(HttpServletResponse response, @RequestParam Integer credentialid /* URL param :: ?id=fileId*/, Model model, Authentication authentication) throws IOException {

        /*******************************************************************
         !!! Add  here an IN-MEMORY security check that the fileid belongs to that user
         ****************************************************************************/

        log.info("===========> in Home -  Get =>EditCredential called   with Param noteid="+credentialid);

        return "redirect:/home";

    }









    @GetMapping("/Credential/Delete")
    public String DeleteCredential(@RequestParam Integer credentialid /* URL param :: ?id=fileId*/, Model model,Authentication authentication) {

        log.info("===========> in Home -  Get =>DeleteCredential called  ");


        /*******************************************************************
         !!! Add  here an IN-MEMORY security check that the fileid belongs to that user
         ****************************************************************************/


        String username=authentication.getName();
        Integer userid;
        log.info("===========> username ="+username);

        credentialService.DeleteCredential(credentialid);

        return "redirect:/home";
    }


















    /**************************************************************************
     //https://spring.io/guides/gs/uploading-files/
     //https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
     When you make a POST request, you have to encode the data that forms the body of the request in some way.
     HTML forms provide three methods of encoding:
     =>application/x-www-form-urlencoded (the default). has a single byte boundary per field (&), but adds a linear overhead factor of 3x for every non-printable character. Therefore, even if we could send files with application/x-www-form-urlencoded, we wouldn't want to, because it is so inefficient. But for printable characters found in text fields, it does not matter and generates less overhead, so we just use it.
     =>multipart/form-data  :: when your form includes any <input type="file"> elements. it adds a few bytes of boundary overhead to the message, and must spend some time calculating it, but sends each byte in one byte.
     =>text/plain :: Never use Text plain. This is "not reliably interpretable by computer", so it should never be used in production
     *******************************************************************************/
    @PostMapping("/AddCredential")
    //@RequestParam("file") +> so a file is expected, otherwise throw an error
    public String AddCredential(Credential credential, Model model, HttpServletRequest req, Authentication authentication) throws IOException {

        log.info("*******************> in Home -  POST => !!AddCredential!! called : "+req.getRequestURL());


        String username=authentication.getName();
        Integer userid;

        User user =userService.getUser(username);
        userid=user.getUserid();
        log.info("===========> userid ="+userid);

        credential.setUserid(userid);


        credentialService.createCredential(credential);





        log.info("*********************END of UploadNote");
        return "redirect:/home";
    }












}
