package cloudstorage.Model.DAO;

public class Credential {


    private Integer credentialid;
    private String url;
    private String username;
    private String key;
    private String password;
    private Integer userid;
    private String Decrypted_pass;


    public Credential(Integer credentialid, String url, String username, String key, String password, Integer userid) {
        this.credentialid = credentialid;
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.userid = userid;
    }



    @Override
    public String toString() {
        return "Credential{" +
                "credentialid=" + credentialid +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", key='" + key + '\'' +
                ", password='" + password + '\'' +
                ", userid=" + userid +
                '}';
    }

    public String getDecrypted_pass() {
        return Decrypted_pass;
    }

    public void setDecrypted_pass(String decrypted_pass) {
        Decrypted_pass = decrypted_pass;
    }

    public Integer getCredentialid() {
        return credentialid;
    }

    public void setCredentialid(Integer credentialid) {
        this.credentialid = credentialid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
