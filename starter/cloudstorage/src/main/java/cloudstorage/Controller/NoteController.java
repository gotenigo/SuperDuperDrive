package cloudstorage.Controller;


import cloudstorage.Model.DAO.Note;
import cloudstorage.Model.DAO.User;
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
public class NoteController {




    @Autowired
    private UserService userService;


    @Autowired
    private NoteService noteService;
    @Autowired
    private FileService fileService;
    @Autowired
    private CredentialService credentialService;








    @GetMapping("/Note/Delete")
    public String DeleteNote(@RequestParam Integer noteid /* URL param :: ?id=fileId*/, Model model,Authentication authentication) {

       // log.info("===========> in Home -  Get =>DeleteNote called  ");

        String username=authentication.getName();
        User user =userService.getUser(username);
        Integer userid=user.getUserid();

        //log.info("===========> username ="+username);

        noteService.DeleteNote(noteid,userid);

        RefreshUserView( model,  userid );


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
    @PostMapping("/AddNote")
    //@RequestParam("file") +> so a file is expected, otherwise throw an error
    public String AddNote(@RequestParam("noteId") Integer noteId,@RequestParam("noteTitle") String noteTitle,@RequestParam("noteDescription") String noteDescription, Model model, HttpServletRequest req, Authentication authentication) throws IOException {

        //log.info("*******************> in Home -  POST => !!UploadNote!! called : "+req.getRequestURL());
        //log.info("*******************> in Home -  POST => notetitle : "+noteTitle);
        //log.info("*******************> in Home -  POST => notetitle : "+noteDescription);
        //log.info("*******************> in Home -  POST => noteId : "+noteId);


        /*******************************************************************
         !!! Add  here an IN-MEMORY cache solution to reduce the Workload if implemented with real Database
         ****************************************************************************/


        String username=authentication.getName();
        Integer userid;
        User user =userService.getUser(username);
        userid=user.getUserid();


        if (!Strings.isNullOrEmpty( noteTitle )  /*&& !Strings.isNullOrEmpty( noteDescription )*/ ) {

            int re;
            if (noteId==null) {
                 re= noteService.createNote(new Note(null, noteTitle, noteDescription, userid));
            }else{
                re= noteService.UpdateNote(new Note(noteId, noteTitle, noteDescription, userid));
            }

            log.info("===========> Return on Insert re = " + re);
            model.addAttribute("NoteMessageReturn",
                    "You successfully uploaded Note Titled '" + noteTitle + "'");

        }else {

            model.addAttribute("NoteMessageReturn", "Incorrect Note Upload");
            log.error("File is empty !" );
        }


        RefreshUserView( model,  userid );

        log.info("*********************END of UploadNote");
        return "home";
    }






    public Model RefreshUserView(Model model, Integer userid ){

        model.addAttribute("ViewFileTab", false);
        model.addAttribute("ViewNoteTab", true);
        model.addAttribute("ViewCredTab", false);

        model.addAttribute("FileList", fileService.GetFileList(userid) );
        model.addAttribute("NoteList", noteService.GetNoteList(userid) );
        model.addAttribute("CredentialList", credentialService.GetDecryptedCrendentialsList(userid) );

        return model;
    }








}
