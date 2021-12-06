package cloudstorage.services;

import cloudstorage.DAO_Mapper.FileMapper;
import cloudstorage.DAO_Mapper.NoteMapper;
import cloudstorage.Model.DAO.File;
import cloudstorage.Model.DAO.Note;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class FileService {


    @Autowired
    private FileMapper fileMapper;


    public int createFile(File file) {
        //log.debug("==>.....createFile called :"+file+" \n");
        return fileMapper.insertFile(file);
    }


    public void DeleteFile(Integer fileId,Integer userid) {
        //log.debug("==>.....DeleteFile called :"+fileId+" \n");
        fileMapper.delete(fileId,userid);
    }


    public List<File> GetFileList(Integer userid) {
        //log.debug("==>.....GetFiles called :"+userid+" \n");
        return fileMapper.findFiles(userid);
    }


    public File ViewFile(Integer fileId) {
        //log.debug("==>.....ViewFile called :"+fileId+" \n");
        return fileMapper.GetFile(fileId);
    }


    public boolean CheckFileAlreadyExist(String filename,Integer userid) {
        log.debug("==>.....CheckFileAlreadyExist called :"+filename+" \n");
        boolean value=false;
        if (fileMapper.findFilePerName(filename,userid) !=null ){
            value=true;
        }
         return value;
    }



}
