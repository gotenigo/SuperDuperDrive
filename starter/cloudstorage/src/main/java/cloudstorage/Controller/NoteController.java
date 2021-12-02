package cloudstorage.Controller;

import cloudstorage.Model.DAO.File;
import cloudstorage.Model.DAO.User;
import cloudstorage.services.CredentialService;
import cloudstorage.services.FileService;
import cloudstorage.services.NoteService;
import cloudstorage.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;



@Slf4j
@Controller
@RequestMapping("/home")
public class NoteController {


    @Autowired
    private FileService fileService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private CredentialService credentialService;

    @Autowired
    private UserService userService;







    /**************************************************************************
     //https://spring.io/guides/gs/uploading-files/
     //https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
     When you make a POST request, you have to encode the data that forms the body of the request in some way.
     HTML forms provide three methods of encoding:
     =>application/x-www-form-urlencoded (the default). has a single byte boundary per field (&), but adds a linear overhead factor of 3x for every non-printable character. Therefore, even if we could send files with application/x-www-form-urlencoded, we wouldn't want to, because it is so inefficient. But for printable characters found in text fields, it does not matter and generates less overhead, so we just use it.
     =>multipart/form-data  :: when your form includes any <input type="file"> elements. it adds a few bytes of boundary overhead to the message, and must spend some time calculating it, but sends each byte in one byte.
     =>text/plain :: Never use Text plain. This is "not reliably interpretable by computer", so it should never be used in production
     *******************************************************************************/
    @PostMapping("/UploadNote")
    //@RequestParam("file") +> so a file is expected, otherwise throw an error
    public String UploadNote(@RequestParam("fileUpload") MultipartFile file , Model model, HttpServletRequest req, Authentication authentication) {

        log.info("===========> in Home -  POST =>FileUpload called : "+req.getRequestURL());

        String username=authentication.getName();
        Integer userid;



        // !!!! Check if this could be worked around by in-memory process !!!! rather than calling DB all the time
        User user =userService.getUser(username);
        userid=user.getUserid();
        model.addAttribute("FileList", fileService.GetFileList(userid) );


        return "home.html";
    }






}
