package guat.tsdrs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String avatar;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
