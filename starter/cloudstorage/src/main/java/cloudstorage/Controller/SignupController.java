package cloudstorage.Controller;

import cloudstorage.Model.DAO.User;
import com.google.common.base.Strings;
import cloudstorage.DAO_Mapper.UserMapper;
import cloudstorage.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/signup")
public class SignupController {



    @Autowired
    private UserService userService;


    @GetMapping
    public String signupView(/*RegisterForm registerform, Model model*/) {

        return "signup.html";
    }


    @PostMapping
    public String signupUser(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {

        System.out.println("=> We are in the signupUser !");

        String signupError = null;


        if(!userService.isUserValid(user)){
            signupError="Please Enter Valid Details";
        }


        if (!userService.isUsernameAvailable(user.getUsername())) {
            signupError = "The username already exists.";
        }

        if (signupError == null) {
            int rowsAdded = userService.createUser(user);
            if (rowsAdded < 0) {
                signupError = "There was an error signing you up. Please try again.";
            }
        }

        if (signupError == null) {
            model.addAttribute("signupSuccess", true); // not so needed
            redirectAttributes.addFlashAttribute("signupSuccess", true);
            return "redirect:/login" ;
        } else {
            model.addAttribute("signupError", signupError);
        }

        return "signup.html";
    }


}
