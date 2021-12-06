package cloudstorage.DAO_Mapper;


import cloudstorage.Model.DAO.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {


    @Select("SELECT fileId,filename,contenttype,filesize,userid,filedata FROM FILES WHERE userid = #{userid}")
    List<File> findFiles(Integer userid);


    @Select("SELECT fileId,filename,contenttype,filesize,userid,filedata FROM FILES WHERE userid = #{userid} AND filename = #{filename}")
    File findFilePerName(String filename,Integer userid);


    @Select("SELECT fileId,filename,contenttype,filesize,userid,filedata FROM FILES WHERE fileId = #{fileId}")
    File GetFile(Integer fileId);

    @Insert("INSERT INTO FILES (filename,contenttype,filesize,userid,filedata) VALUES(#{filename}, #{contenttype},#{filesize}, #{userid},#{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insertFile(File files);


    @Delete("DELETE FROM FILES WHERE fileId = #{fileId} AND userid = #{userid}")
    void delete(Integer fileId,Integer userid);



}
