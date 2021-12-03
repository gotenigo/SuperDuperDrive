package cloudstorage.DAO_Mapper;



import cloudstorage.Model.DAO.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {


    @Select("SELECT credentialid,url,username,key,password,userid FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credential> findCredentials(Integer userid);

    @Select("SELECT credentialid,url,username,key,password,userid FROM CREDENTIALS WHERE credentialid = #{credentialid})")
    Credential GetCredential(Integer credentialid);

    @Insert("INSERT INTO CREDENTIALS (url,username,key,password,userid) VALUES(#{url}, #{username},#{key}, #{password},#{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insertCredential(Credential credentials);


    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    void delete(Integer credentialid);


    @Update("UPDATE CREDENTIALS SET url=#{url},username=#{username},key=#{key},password=#{password} WHERE credentialid = #{credentialid}")
    int UpdateCredential(Integer credentialid);



}
