package cloudstorage.Controller;


import cloudstorage.Model.DAO.File;
import cloudstorage.Model.DAO.User;
import cloudstorage.services.CredentialService;
import cloudstorage.services.FileService;
import cloudstorage.services.NoteService;
import cloudstorage.services.UserService;
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
public class FileController {


    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;


    @Autowired
    private CredentialService credentialService;
    @Autowired
    private NoteService noteService;






    //this function CReate a new view. So Tab management is not required !
    @GetMapping("/File/View")
    public void ViewFile(HttpServletResponse response, @RequestParam Integer fileId /* URL param :: ?id=fileId*/, Model model, Authentication authentication) throws IOException {

        /*******************************************************************
         !!! Add  here an IN-MEMORY security check that the fileid belongs to that user
         ****************************************************************************/



        log.info("===========> in Home -  Get =>ViewFile called   with Param fileId="+fileId);

        File thefile = fileService.ViewFile(fileId);
        log.info("===========> File ="+thefile);


        byte[] Databytes = thefile.getFiledata();
        InputStream DataStream = ByteSource.wrap(Databytes).openStream();


        try {
            response.setContentType(thefile.getContenttype());
            org.apache.commons.io.IOUtils.copy(DataStream, response.getOutputStream());
            DataStream.close();
            response.flushBuffer();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }







    @GetMapping("/File/Delete")
    public String DeleteFile(@RequestParam Integer fileId /* URL param :: ?id=fileId*/, Model model,Authentication authentication) {

        log.info("===========> in Home -  Get =>LoadHomePage called  ");


        /*******************************************************************
         !!! Add  here an IN-MEMORY security check that the fileid belongs to that user
         ****************************************************************************/


        String username=authentication.getName();
        User user =userService.getUser(username);
        Integer userid=user.getUserid();

        log.info("===========> username ="+username);


        fileService.DeleteFile(fileId,userid);

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
    @PostMapping("/Uploadfile")
    //@RequestParam("file") +> so a file is expected, otherwise throw an error
    public String FileUpload(@RequestParam("fileUpload") MultipartFile file , Model model, HttpServletRequest req, Authentication authentication) {

        log.info("===========> in Home -  POST =>FileUpload called : "+req.getRequestURL());

        String username=authentication.getName();
        Integer userid;

        User user =userService.getUser(username);
        userid=user.getUserid();
        log.info("===========> userid ="+userid);

        if (!file.isEmpty()) {

            try {

                // Get the file data
                byte[] Databytes = file.getBytes();

                log.info("=> Databytes =" + Databytes);

                log.info("===========> file.getName() " + file.getName());
                log.info("===========> file.getOriginalFilename() " + file.getOriginalFilename());
                log.info("===========> file.getContentType() " + file.getContentType());
                log.info("===========> file.getSize() " + Long.toString(file.getSize()));

                //save file data into the database under BLOB
                int re =fileService.createFile(new File(null, file.getOriginalFilename(), file.getContentType(), Long.toString(file.getSize()), userid, Databytes));

                log.info("===========> Return on Insert re = " + re);

                model.addAttribute("FileMessageReturn",
                        "You successfully uploaded file '" + file.getOriginalFilename() + "'");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {

            model.addAttribute("FileMessageReturn", "Incorrect File Upload");
            log.error("File is empty !" );
        }

        RefreshUserView( model,  userid );

        return "home";
    }








    public Model RefreshUserView(Model model, Integer userid ){

        model.addAttribute("ViewFileTab", true);
        model.addAttribute("ViewNoteTab", false);
        model.addAttribute("ViewCredTab", false);

        model.addAttribute("FileList", fileService.GetFileList(userid) );
        model.addAttribute("NoteList", noteService.GetNoteList(userid) );
        model.addAttribute("CredentialList", credentialService.GetDecryptedCrendentialsList(userid) );

        return model;
    }






}
