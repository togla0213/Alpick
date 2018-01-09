package yalantis.com.sidemenu.sample.com.VO;

/**
 * Created by togla on 2018-01-09.
 */

public class MemberVO {

    private String id;
    private String pw;
    private String nickname;
    private String year;
    private String user_type;

    public void setId(String id) {
        this.id = id;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public String getNickname() {
        return nickname;
    }

    public String getYear() {
        return year;
    }

    public String getUser_type() {
        return user_type;
    }

    public MemberVO(String id, String pw, String nickname, String year, String user_type) {
        super();
        this.id = id;
        this.pw = pw;
        this.nickname = nickname;
        this.year = year;
        this.user_type = user_type;
    }


}
