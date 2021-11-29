package cloudstorage.services;


import cloudstorage.DAO_Mapper.UserMapper;
import cloudstorage.Model.DAO.user;
import cloudstorage.services.Security.HashService;
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



    public int createUser(user Users) {

        log.info("==>GG.....createUser called :"+Users+" \n");

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(Users.getPassword(), encodedSalt); // made from HASH(orignal password , Salt)
        return userMapper.insertUser(new user(null, Users.getUsername(), encodedSalt, hashedPassword, Users.getFirstname(), Users.getLastname()));
    }


}
