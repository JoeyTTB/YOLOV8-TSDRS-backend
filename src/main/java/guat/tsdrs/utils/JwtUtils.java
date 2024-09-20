package guat.tsdrs.utils;

import guat.tsdrs.pojo.vo.LoginUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class JwtUtils {

    private static final String signKey = "GUATYoloV8TSDRSSecretKeySignWithUsernameAndPassword";

    public static String createJwt(String loginUserString) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, signKey)
                .setSubject(loginUserString)
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))
                .compact();
    }

    public static Claims parseJwt(String token) throws Exception {
        return Jwts.parserBuilder()
                .setSigningKey(signKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
