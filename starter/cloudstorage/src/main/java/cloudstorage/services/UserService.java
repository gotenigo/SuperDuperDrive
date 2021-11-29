package cloudstorage.services;

import cloudstorage.Model.Form.DAO.user;
import cloudstorage.DAO_Mapper.UserMapper;
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



    public int createUser(user  user) {

        log.info("==>GG.....createUser called :"+user+" \n");

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt); // made from HASH(orignal password , Salt)
        return userMapper.insertUser(new user(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstname(), user.getLastname()));
    }


}
