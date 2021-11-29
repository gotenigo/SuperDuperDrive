package cloudstorage.DAO_Mapper;


import cloudstorage.Model.DAO.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT userid,username,salt,password,firstname,lastname FROM USERS WHERE username = #{username}")
    User getUser(String username);

    @Insert("INSERT INTO USERS (username,salt,password,firstname,lastname) VALUES(#{username}, #{salt},#{password}, #{firstname},#{lastname})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    int insert(User Theusers);

}
