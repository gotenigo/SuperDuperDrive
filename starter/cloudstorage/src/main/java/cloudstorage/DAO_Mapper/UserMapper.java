package cloudstorage.DAO_Mapper;


import cloudstorage.Model.Form.DAO.user;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {


    @Select("SELECT userid,username,salt,password,firstname,lastname FROM user WHERE username = #{username}")
    user findUser(String username);

    @Insert("INSERT INTO user (username,salt,password,firstname,lastname) VALUES(#{username}, #{salt},#{password}, #{firstname},#{lastname})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    Integer insertUser(user Theusers);


}
