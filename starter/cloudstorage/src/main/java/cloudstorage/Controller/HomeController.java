package cloudstorage.Controller;

import cloudstorage.Model.DAO.File;
import cloudstorage.Model.DAO.Note;
import cloudstorage.Model.DAO.User;
import cloudstorage.services.CredentialService;
import cloudstorage.services.FileService;
import cloudstorage.services.NoteService;
import cloudstorage.services.UserService;
import com.google.common.io.ByteSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/home")
public class HomeController {


    @Autowired
    private UserService userService;

    @Autowired
    private NoteService noteService;
    @Autowired
    private FileService fileService;
    @Autowired
    private CredentialService credentialService;




    /*
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
    }*/








    @GetMapping
    public String LoadHomePage(/*Authentication authentication, HomeForm chatForm,*/ Model model,Authentication authentication) {

        log.info("===========> in Home -  Get =>LoadHomePage called  ");


        String username=authentication.getName();
        Integer userid;
        log.info("===========> username ="+username);

        User user =userService.getUser(username);
        userid=user.getUserid();
        log.info("===========> userid ="+userid);

        RefreshUserView( model,  userid );


        return "home.html";
    }








    public Model RefreshUserView(Model model, Integer userid ){

        model.addAttribute("ViewFileTab", true); // File view is activated by default
        model.addAttribute("ViewNoteTab", false);
        model.addAttribute("ViewCredTab", false);

        model.addAttribute("FileList", fileService.GetFileList(userid) );
        model.addAttribute("NoteList", noteService.GetNoteList(userid) );
        model.addAttribute("CredentialList", credentialService.GetCrendentialsList(userid) );

        return model;
    }






}
