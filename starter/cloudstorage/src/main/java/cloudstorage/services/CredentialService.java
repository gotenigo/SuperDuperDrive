package cloudstorage.services;

import cloudstorage.DAO_Mapper.CredentialMapper;
import cloudstorage.Model.DAO.Credential;
import cloudstorage.Model.DAO.Note;
import cloudstorage.services.Security.EncryptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;


@Slf4j
@Service
public class CredentialService {


    @Autowired
    private CredentialMapper credentialMapper;

    @Autowired
    private EncryptionService encryptionService;



    public int createCredential(Credential cred) {
        log.info("==>GG.....createCredential called :"+cred+" \n");

        String key= getKey();
        String encryptedPassword = encryptionService.encryptValue(cred.getPassword() ,key);

        return credentialMapper.insertCredential(new Credential(cred.getCredentialid(),cred.getUrl(),cred.getUsername(),key,encryptedPassword,cred.getUserid()));
    }







    public int UpdateCredential(Credential cred) {
        log.info("==>GG.....Service UpdateCredential called :"+cred+" \n");

        String key= getKey();
        String encryptedPassword = encryptionService.encryptValue(cred.getPassword() ,key);

        return credentialMapper.UpdateCredential(new Credential(cred.getCredentialid(),cred.getUrl(),cred.getUsername(),key,encryptedPassword,cred.getUserid()));
    }






    public void DeleteCredential(Integer noteid) {
        log.info("==>GG.....DeleteCredential  called :"+noteid+" \n");
        credentialMapper.delete(noteid);
    }






    public List<Credential> GetDecryptedCrendentialsList(Integer userid) {
        log.info("==>GG.....GetCrendentialsList called :"+userid+" \n");


        List DecryptedCredList = credentialMapper.findCredentials(userid);

        //Decrypt the Credential before returning
        for (Iterator<Credential> iter = DecryptedCredList.iterator(); iter.hasNext(); ) {
            Credential e = iter.next();

            String pass = e.getPassword();
            String key =e.getKey();
            String DecryptedPassword = encryptionService.decryptValue(pass ,key);
            e.setPassword(DecryptedPassword);
        }

        return DecryptedCredList;
    }





    public List<Credential> GetCrendentialsList(Integer userid) {
        log.info("==>GG.....GetCrendentialsList called :"+userid+" \n");

        return credentialMapper.findCredentials(userid);
    }









    public Credential ViewCrendentials(Integer credentialid) {
        log.info("==>GG.....ViewNote called :"+credentialid+" \n");

        Credential CVred = credentialMapper.GetCredential(credentialid);

        String decryptedPassword = encryptionService.decryptValue(CVred.getPassword(), CVred.getKey());
        CVred.setPassword(decryptedPassword);

        return CVred;
    }







    public String getKey(){

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);

        return encodedKey;
    }



}
