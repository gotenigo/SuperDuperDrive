package cloudstorage.services;


import cloudstorage.DAO_Mapper.NoteMapper;
import cloudstorage.DAO_Mapper.UserMapper;
import cloudstorage.Model.DAO.Note;
import cloudstorage.Model.DAO.users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NoteService {


    @Autowired
    private NoteMapper notesMapper;



    public int createNote(Note note) {

        log.info("==>GG.....createNote called :"+note+" \n");


        return notesMapper.insertNote(note);


    }





}
