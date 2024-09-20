package guat.tsdrs.exception;

import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = true)
public class CustomAuthenticationException extends AuthenticationException {

    public CustomAuthenticationException(String msg) {
        super(msg);
    }
}
