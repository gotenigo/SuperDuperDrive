package cloudstorage.DAO_Mapper;



import cloudstorage.Model.DAO.Note;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NoteMapper {


    @Select("SELECT noteid,notetitle,notedescription,userid FROM Notes WHERE userid = #{userid}")
    Note findNotes(Integer userid);

    @Insert("INSERT INTO user (notetitle,notedescription,userid) VALUES(#{notetitle}, #{notedescription},#{userid}")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insertNote(Note notes);

    @Delete("DELETE FROM Notes WHERE noteid = #{noteid}")
    void delete(Integer noteid);


}
