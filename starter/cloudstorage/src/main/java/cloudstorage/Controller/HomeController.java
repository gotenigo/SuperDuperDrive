package cloudstorage.Controller;


import cloudstorage.Model.Form.AuthUserForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/home")
public class HomeController {


    @GetMapping
    public String getLoginPage(/*Authentication authentication, HomeForm chatForm,*/ Model model) {

        System.out.println("===========> in Home - GET ZOZO");

        return "home.html";
    }



    @PostMapping
    public String postLoginPage(/*Authentication authentication, HomeForm chatForm,*/ Model model) {

        System.out.println("===========> in Home -  POST ZOZO");
        /*	We use Thymeleaf to automatically add the CSRF token to our form.
        If we were not using Thymleaf or Spring MVCs taglib we could also manually add the CSRF token using <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>.*/

        return "home.html";
    }
}
