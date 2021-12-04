package cloudstorage.Controller;


import cloudstorage.Model.DAO.Credential;
import cloudstorage.Model.DAO.Note;
import cloudstorage.Model.DAO.User;
import cloudstorage.Model.Form.CredentialModal;
import cloudstorage.services.CredentialService;
import cloudstorage.services.FileService;
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
    private UserService userService;

    @Autowired
    private NoteService noteService;
    @Autowired
    private FileService fileService;
    @Autowired
    private CredentialService credentialService;





    @GetMapping("/Credential/Delete")
    public String DeleteCredential(@RequestParam Integer credentialid /* URL param :: ?id=fileId*/, Model model,Authentication authentication) {

        log.info("===========> in Home -  Get =>DeleteCredential called  ");


        /*******************************************************************
         !!! Add  here an IN-MEMORY security check that the fileid belongs to that user
         ****************************************************************************/


        String username=authentication.getName();
        Integer userid;
        log.info("===========> username ="+username);



        model.addAttribute("ViewFileTab", false);
        model.addAttribute("ViewNoteTab", false);
        model.addAttribute("ViewCredTab", true);


        credentialService.DeleteCredential(credentialid);

        return "home";
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
    public String AddCredential(CredentialModal credentialModal, Model model, HttpServletRequest req, Authentication authentication) throws IOException {

        log.info("*******************> in Home -  POST => !!AddCredential!! called : "+req.getRequestURL());
        log.info("***>credentialModal="+credentialModal);

        Integer userid;
        String username=authentication.getName();


        User user =userService.getUser(username);
        userid=user.getUserid();

        log.info("***>credentialModal.getCredentialId()="+credentialModal.getCredentialId());
        Credential credential= new Credential(credentialModal.getCredentialId(), credentialModal.getUrl(),
                credentialModal.getUsername(), null, credentialModal.getPassword(),userid);

        int re;
        if (credentialModal.getCredentialId()==null) {
            re=credentialService.createCredential(credential);
        }else{
            re=credentialService.UpdateCredential(credential);
        }
        log.info("===========> Return on Update re = " + re);


        model.addAttribute("CredMessageReturn",
                "You successfully uploaded Crendential for username '" + credentialModal.getUsername() + "'");


        // for security reason. The GC will dispose from here
        credentialModal.setPassword(null);


        model.addAttribute("ViewFileTab", false);
        model.addAttribute("ViewNoteTab", false);
        model.addAttribute("ViewCredTab", true);


        model.addAttribute("FileList", fileService.GetFileList(userid) );
        model.addAttribute("NoteList", noteService.GetNoteList(userid) );
        model.addAttribute("CredentialList", credentialService.GetCrendentialsList(userid) );


        log.info("*********************END of AddCredential");
        return "home";
    }












}
