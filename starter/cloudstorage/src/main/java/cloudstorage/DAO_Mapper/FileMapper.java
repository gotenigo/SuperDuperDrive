package cloudstorage.DAO_Mapper;


import cloudstorage.Model.DAO.File;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FileMapper {


    @Select("SELECT fileId,filename,contenttype,filesize,userid,filedata FROM FILES WHERE userid = #{userid}")
    File findFiles(Integer userid);

    @Insert("INSERT INTO FILES (filename,contenttype,filesize,userid,filedata) VALUES(#{filename}, #{contenttype},#{filesize}, #{userid},#{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insertFile(File files);


    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void delete(Integer fileId);



}
