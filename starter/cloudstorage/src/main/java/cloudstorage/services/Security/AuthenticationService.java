package cloudstorage.services.Security;

import cloudstorage.Model.DAO.User;
import cloudstorage.DAO_Mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
public class AuthenticationService  implements AuthenticationProvider {



    /*
    Full UserDetails Workflow: HTTP Basic Authentication
Now think back to your HTTP Basic Authentication, that means you are securing your application with Spring Security and Basic Auth.
This is what happens when you specify a UserDetailsService and try to login:

1)
Extract the username/password combination from the HTTP Basic Auth header in a filter.
You don’t have to do anything for that, it will happen under the hood.

2)
Call your MyDatabaseUserDetailsService to load the corresponding user from the database, wrapped as a UserDetails object, which exposes the user’s hashed password.

3)
Take the extracted password from the HTTP Basic Auth header, hash it automatically and compare it with the hashed password from your UserDetails object.
If both match, the user is successfully authenticated.
    */


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HashService hashService;



    /**
     * Performs authentication with the same contract as
     * {@link AuthenticationManager#authenticate(Authentication)}
     * .
     *
     * @param authentication the authentication request object.
     * @return a fully authenticated object including credentials. May return
     * <code>null</code> if the <code>AuthenticationProvider</code> is unable to support
     * authentication of the passed <code>Authentication</code> object. In such a case,
     * the next <code>AuthenticationProvider</code> that supports the presented
     * <code>Authentication</code> class will be tried.
     * @throws AuthenticationException if authentication fails.
     */

    @Override
    //The authenticate() method first loads the user by its username and then verifies if
    //the password matches the hash stored in the database (listing 6.13). The verification
    //depends on the algorithm used to hash the user’s password.
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {


        String username = authentication.getName(); // Credentail used by the user on the Login form
        String password = authentication.getCredentials().toString(); // Credentail used by the user on the Login form.  !!! This a clear  password

        User user = userMapper.getUser(username);
        if (user != null) {
            String encodedSalt = user.getSalt();
            String hashedPassword = hashService.getHashedValue(password, encodedSalt);
            if (user.getPassword().equals(hashedPassword)) {


                return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
            }
        }
        return null;
    }










    /**
     * Returns <code>true</code> if this <Code>AuthenticationProvider</code> supports the
     * indicated <Code>Authentication</code> object.
     * <p>
     * Returning <code>true</code> does not guarantee an
     * <code>AuthenticationProvider</code> will be able to authenticate the presented
     * instance of the <code>Authentication</code> class. It simply indicates it can
     * support closer evaluation of it. An <code>AuthenticationProvider</code> can still
     * return <code>null</code> from the {@link #authenticate(Authentication)} method to
     * indicate another <code>AuthenticationProvider</code> should be tried.
     * </p>
     * <p>
     * Selection of an <code>AuthenticationProvider</code> capable of performing
     * authentication is conducted at runtime the <code>ProviderManager</code>.
     * </p>
     *
     * @param authentication
     * @return <code>true</code> if the implementation can more closely evaluate the
     * <code>Authentication</code> class presented
     */
    //We implement the supports() method to specify that the supported Authentication
    //implementation type is UsernamePasswordAuthenticationToken.
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }


}
