package cloudstorage.Controller;

import cloudstorage.Model.DAO.users;
import com.google.common.base.Strings;
import cloudstorage.DAO_Mapper.UserMapper;
import cloudstorage.Model.Form.AuthUserForm;
import cloudstorage.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/register")
public class RegisterController {



    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;


    @GetMapping
    public String getRegisterPage(/*RegisterForm registerform, Model model*/) {

        return "register.html";
    }


    @PostMapping
    public String postResgisterUser(AuthUserForm registerform, Model model, BindingResult result) {

        String password=registerform.getPassword();
        String username=registerform.getUsername();
        String firstname=registerform.getFirstname();
        String lastname=registerform.getLastname();


        if ( !Strings.isNullOrEmpty( username )  && !Strings.isNullOrEmpty( password )  && !Strings.isNullOrEmpty( firstname ) && !Strings.isNullOrEmpty( lastname )   ){

            if(userMapper.findUser(username)== null    ) {
                userService.createUser(new users(registerform.getUsername(), null, registerform.getPassword(), registerform.getFirstname(), registerform.getLastname())); // INsert Message into the database via MyBatis
            }
            else if (userMapper.findUser(username)!= null && password!=null  && firstname!=null && lastname!=null ){
                result.rejectValue("username", null, "There is already an account registered with that username");
                model.addAttribute("RegisterStatus", "Username already taken !");
            }
        }
        else{

            result.rejectValue("username", null, "Wrong register details");
            model.addAttribute("RegisterStatus", "Wrong register details");
        }


        if (result.hasErrors()){
            return "register";
        }

        return "redirect:/login" ;
    }


}
