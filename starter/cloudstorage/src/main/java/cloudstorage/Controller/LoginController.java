package cloudstorage.Controller;

import cloudstorage.DAO_Mapper.UserMapper;
import cloudstorage.Model.Form.AuthUserForm;
import cloudstorage.services.Security.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    AuthenticationService authenticationService;


    @GetMapping
    public String getLoginPage(AuthUserForm registerform, CsrfToken token, Model model, @RequestParam(value = "error", required = false) Boolean error) {
        // the token will be injected automatically


        if (error != null  && error ) {
            model.addAttribute("LongonStatus", "Logon_Failure");
        }
        return "login.html";
    }



    @PostMapping
    public String postLoginPage(Authentication authentication, AuthUserForm registerform, CsrfToken token, Model model) {

        System.out.println("===========> ZOZO");
        /*	We use Thymeleaf to automatically add the CSRF token to our form.
        If we were not using Thymleaf or Spring MVCs taglib we could also manually add the CSRF token using <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>.*/

        return "login.html";
    }



}
