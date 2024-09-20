package guat.tsdrs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result {
    private int code;
    private String msg;
    private Object data;

    public static Result success(int code, String msg, Object data) {
        return new Result(code, msg, data);
    }

    public static Result success(int code, String msg) {
        return new Result(code, msg, null);
    }

    public static Result error(int code, String msg) {
        return new Result(code, msg, null);
    }
}
