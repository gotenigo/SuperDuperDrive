package cloudstorage.Controller;

import cloudstorage.DAO_Mapper.UserMapper;
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
    public String loginView( ) {

        return "login.html";
    }




}
