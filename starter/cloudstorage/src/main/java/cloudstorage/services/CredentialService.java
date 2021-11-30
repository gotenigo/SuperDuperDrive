package cloudstorage.services;

import cloudstorage.DAO_Mapper.CredentialMapper;
import cloudstorage.Model.DAO.Credential;
import cloudstorage.Model.DAO.Note;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CredentialService {


    @Autowired
    private CredentialMapper credentialMapper;



    public int createCredential(Credential cred) {
        log.info("==>GG.....createCredential called :"+cred+" \n");
        return credentialMapper.insertCredential(cred);
    }



    public void DeleteCredential(Integer noteid) {
        log.info("==>GG.....DeleteCredential  called :"+noteid+" \n");
        credentialMapper.delete(noteid);
    }



}
