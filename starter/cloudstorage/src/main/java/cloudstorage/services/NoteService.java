package cloudstorage.services;


import cloudstorage.DAO_Mapper.NoteMapper;
import cloudstorage.Model.DAO.Note;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NoteService {


    @Autowired
    private NoteMapper noteMapper;



    public int createNote(Note note) {
        //log.debug("==>.....createNote called :"+note+" \n");
        return noteMapper.insertNote(note);
    }


    public int UpdateNote(Note note) {
        //log.debug("==>.....UpdateNote called :"+note+" \n");
        return noteMapper.UpdateNote(note);
    }



    public void DeleteNote(Integer noteid,Integer userid) {
        //log.debug("==>.....DeleteNote called :"+noteid+" \n");
        noteMapper.delete(noteid,userid);
    }



    public List<Note> GetNoteList(Integer userid) {
        //log.debug("==>.....GetNoteList called :"+userid+" \n");
         return noteMapper.findNotes(userid);
    }



    public Note ViewNote(Integer noteid) {
        //log.debug("==>.....ViewNote called :"+noteid+" \n");
        return noteMapper.GetNote(noteid);
    }






}
