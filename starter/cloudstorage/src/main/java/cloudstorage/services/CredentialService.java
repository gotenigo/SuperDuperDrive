package cloudstorage.services;

import cloudstorage.DAO_Mapper.CredentialMapper;
import cloudstorage.Model.DAO.Credential;
import cloudstorage.Model.DAO.Note;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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



    public List<Credential> GetCrendentialsList(Integer userid) {
        log.info("==>GG.....GetNoteList called :"+userid+" \n");
        return credentialMapper.findCredentials(userid);
    }



    public Credential ViewCrendentials(Integer credentialid) {
        log.info("==>GG.....ViewNote called :"+credentialid+" \n");
        return credentialMapper.GetCredential(credentialid);
    }



}
