package cloudstorage.Controller;

import cloudstorage.Model.DAO.File;
import cloudstorage.Model.DAO.Note;
import cloudstorage.Model.DAO.User;
import cloudstorage.services.CredentialService;
import cloudstorage.services.FileService;
import cloudstorage.services.NoteService;
import cloudstorage.services.UserService;
import com.google.common.base.Strings;
import com.google.common.io.ByteSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;


@Slf4j
@Controller
@RequestMapping("/home")
public class NoteController {



    @Autowired
    private NoteService noteService;
    @Autowired
    private UserService userService;












    @GetMapping("/Note/Edit")
    public String  EditNote(HttpServletResponse response, @RequestParam Integer noteid /* URL param :: ?id=fileId*/, Model model, Authentication authentication) throws IOException {

        /*******************************************************************
         !!! Add  here an IN-MEMORY security check that the fileid belongs to that user
         ****************************************************************************/

        log.info("===========> in Home -  Get =>EditNote called   with Param noteid="+noteid);

        return "redirect:/home";

    }









    @GetMapping("/Note/Delete")
    public String DeleteNote(@RequestParam Integer noteid /* URL param :: ?id=fileId*/, Model model,Authentication authentication) {

        log.info("===========> in Home -  Get =>DeleteNote called  ");


        /*******************************************************************
         !!! Add  here an IN-MEMORY security check that the fileid belongs to that user
         ****************************************************************************/


        String username=authentication.getName();
        Integer userid;
        log.info("===========> username ="+username);

        noteService.DeleteNote(noteid);

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
    @PostMapping("/AddNote")
    //@RequestParam("file") +> so a file is expected, otherwise throw an error
    public String AddNote(@RequestParam("noteTitle") String noteTitle,@RequestParam("noteDescription") String noteDescription, Model model, HttpServletRequest req, Authentication authentication) throws IOException {

        log.info("*******************> in Home -  POST => !!UploadNote!! called : "+req.getRequestURL());

        log.info("*******************> in Home -  POST => notetitle : "+noteTitle);
        log.info("*******************> in Home -  POST => notetitle : "+noteDescription);

        String username=authentication.getName();
        Integer userid;

        User user =userService.getUser(username);
        userid=user.getUserid();
        log.info("===========> userid ="+userid);


        if (!Strings.isNullOrEmpty( noteTitle )  && !Strings.isNullOrEmpty( noteDescription ) ) {

            //save file data into the database under BLOB
            int re = noteService.createNote(new Note(null, noteTitle, noteDescription, userid));

            log.info("===========> Return on Insert re = " + re);

            model.addAttribute("message",
                    "You successfully uploaded Note '" + noteTitle + "'");

        }else {

            model.addAttribute("message", "Incorrect Note Upload");
            log.error("File is empty !" );
        }


        log.info("*********************END of UploadNote");
        return "redirect:/home";
    }






}
