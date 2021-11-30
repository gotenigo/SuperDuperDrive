package cloudstorage.services;

import cloudstorage.DAO_Mapper.FileMapper;
import cloudstorage.DAO_Mapper.NoteMapper;
import cloudstorage.Model.DAO.File;
import cloudstorage.Model.DAO.Note;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class FileService {


    @Autowired
    private FileMapper fileMapper;


    public int createFile(File file) {
        log.info("==>GG.....createFile called :"+file+" \n");
        return fileMapper.insertFile(file);
    }



    public void DeleteFile(Integer fileId) {
        log.info("==>GG.....DeleteFile called :"+fileId+" \n");
        fileMapper.delete(fileId);
    }


}
