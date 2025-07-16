package guat.tsdrs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private Integer userId;
    private String username;
    private String password;
    private String avatar;
    private String regdate;

    public User(String username, String password, String regdate) {
        this.username = username;
        this.password = password;
        this.regdate = regdate;
    }
}
