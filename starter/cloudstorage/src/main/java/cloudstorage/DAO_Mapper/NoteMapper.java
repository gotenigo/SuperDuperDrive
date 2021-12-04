package cloudstorage.DAO_Mapper;



import cloudstorage.Model.DAO.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {


    @Select("SELECT noteid,notetitle,notedescription,userid FROM NOTES WHERE userid = #{userid}")
    List<Note> findNotes(Integer userid);

    @Select("SELECT noteid,notetitle,notedescription,userid FROM NOTES WHERE noteid = #{noteid}")
    Note GetNote(Integer noteid);


    @Insert("INSERT INTO NOTES (notetitle,notedescription,userid) VALUES(#{notetitle}, #{notedescription},#{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insertNote(Note notes);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid} AND userid = #{userid}")
    void delete(Integer noteid,Integer userid);


    @Update("UPDATE NOTES SET notetitle=#{notetitle},notedescription=#{notedescription} WHERE noteid = #{noteid}")
    int UpdateNote(Note notes);




}
