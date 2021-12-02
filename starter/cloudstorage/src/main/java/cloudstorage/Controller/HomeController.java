package cloudstorage.Controller;

import cloudstorage.Model.DAO.File;
import cloudstorage.Model.DAO.Note;
import cloudstorage.Model.DAO.User;
import cloudstorage.services.CredentialService;
import cloudstorage.services.FileService;
import cloudstorage.services.NoteService;
import cloudstorage.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private FileService fileService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private CredentialService credentialService;

    @Autowired
    private UserService userService;




    @ModelAttribute("Session_User")
    private String  GetCurrentUserSession() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }




    @GetMapping
    public String LoadHomePage(/*Authentication authentication, HomeForm chatForm,*/ Model model,Authentication authentication) {

        log.info("===========> in Home -  Get =>LoadHomePage called  ");


        String username=authentication.getName();
        Integer userid;
        log.info("===========> username ="+username);





        User user =userService.getUser(username);
        userid=user.getUserid();
        log.info("===========> userid ="+userid);
        //log.info("===========> FileList ="+FileList);
        model.addAttribute("FileList", fileService.GetFileList(userid) );


        return "home.html";
    }





    @GetMapping("/File/View")
    public String ViewFile( @RequestParam Integer fileId /* URL param :: ?id=fileId*/, Model model,Authentication authentication) {

        log.info("===========> in Home -  Get =>ViewFile called   with Param fileId="+fileId);

        String username=authentication.getName();
        Integer userid;
        log.info("===========> username ="+username);



        File thefile = fileService.ViewFile(fileId);
        log.info("===========> File ="+thefile);


        model.addAttribute("FileList", thefile );



        User user =userService.getUser(username);
        userid=user.getUserid();
        log.info("===========> userid ="+userid);
        model.addAttribute("FileList", fileService.GetFileList(userid) );


        return "home.html";
    }







    @GetMapping("/File/Delete")
    public String DeleteFile(@RequestParam Integer fileId /* URL param :: ?id=fileId*/, Model model,Authentication authentication) {

        log.info("===========> in Home -  Get =>LoadHomePage called  ");

        String username=authentication.getName();
        Integer userid;
        log.info("===========> username ="+username);



        fileService.DeleteFile(fileId);


        User user =userService.getUser(username);
        userid=user.getUserid();
        log.info("===========> userid ="+userid);
        model.addAttribute("FileList", fileService.GetFileList(userid) );

        return "home.html";
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
    @PostMapping("/Uploadfile")
    //@RequestParam("file") +> so a file is expected, otherwise throw an error
    public String FileUpload(@RequestParam("fileUpload") MultipartFile file , Model model, HttpServletRequest req,Authentication authentication) {

        log.info("===========> in Home -  POST =>FileUpload called : "+req.getRequestURL());

        String username=authentication.getName();
        Integer userid;

        if (file.isEmpty()) {
            model.addAttribute("message", "Incorrect File Upload");
            log.error("File is empty !" );
            return "home.html";
        }


        try {

            // Get the file data
            byte[] Databytes = file.getBytes();

            log.info("=> Databytes ="+Databytes);

            log.info("===========> file.getName() "+file.getName() );
            log.info("===========> file.getOriginalFilename() "+file.getOriginalFilename() );
            log.info("===========> file.getContentType() "+file.getContentType() );
            log.info("===========> file.getSize() "+Long.toString(file.getSize()) );

            //save file data into the database under BLOB
            fileService.createFile(new File(null,  file.getOriginalFilename(), file.getContentType(), Long.toString(file.getSize()), 1, Databytes) );

            model.addAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }



        User user =userService.getUser(username);
        userid=user.getUserid();
        model.addAttribute("FileList", fileService.GetFileList(userid) );


        return "home.html";
    }





}
