package cloudstorage.DAO_Mapper;



import cloudstorage.Model.DAO.Credential;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CredentialMapper {


    @Select("SELECT credentialid,url,username,key,password,userid FROM CREDENTIALS WHERE userid = #{userid}")
    Credential findCredentials(Integer userid);

    @Insert("INSERT INTO user (url,username,key,password,userid FROM CREDENTIALS) VALUES(#{url}, #{username},#{key}, #{password},#{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insertCredential(Credential credentials);


    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    void delete(Integer credentialid);



}
