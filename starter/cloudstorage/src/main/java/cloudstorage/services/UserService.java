package cloudstorage.services;


import cloudstorage.DAO_Mapper.UserMapper;
import cloudstorage.Model.DAO.User;
import cloudstorage.services.Security.HashService;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HashService hashService;



    public boolean isUsernameAvailable(String username) {
        return userMapper.getUser(username) == null;
    }



    public int createUser(User user) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        return userMapper.insert(new User(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstName(), user.getLastName()));
    }








    public boolean isUserValid(User user){

        boolean value=false;

        //log.debug("=> We are in isUserValid !");
        //log.debug("user is ="+user);

        String password=user.getPassword();
        String username=user.getUsername();
        String firstname=user.getLastName();
        String lastname=user.getLastName();

        if ( !Strings.isNullOrEmpty( username )  && !Strings.isNullOrEmpty( password )
                && !Strings.isNullOrEmpty( firstname ) && !Strings.isNullOrEmpty( lastname )   ){
            value=true;
        }

        return value;
    }

    public User getUser(String username) {
        return userMapper.getUser(username);
    }

}
