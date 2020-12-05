package com.entian.common.standard.session.jwt;

import com.alibaba.fastjson.JSON;
import com.entian.common.standard.resp.exception.APIException;
import com.entian.common.standard.session.SessionContext;
import com.entian.common.standard.session.SessionContextModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

/**
 * JWT操作工具
 *
 * @author jianggangli
 */
@RefreshScope
@Component
@Slf4j
public class JwtHandler {
    private static final String SECRET = "XX#$%()(#*!()!KL<><MQLMNQNQJQK sdfkjsdrow32234545fdf>?N<:{LWPW";

    private static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    public static final String TOKEN_HEADER   = "X-EnTianCloud-Token";
    public static final String PAYLOAD_HEADER = "X-EnTianCloud-Payload";

    @Value("${gateway.token.expirationInSec:2592000}")
    private int expirationInSec=0;

    /**
     * 生成token
     *
     * @param payloadContent 负载内容
     * @return token
     */
    public String generateToken(String payloadContent) {
        return Jwts.builder()
                .setClaims(Jwts.claims()
                        .setExpiration(Date.from(Instant.now().plusSeconds(expirationInSec)))
                        .setSubject(payloadContent))
                .signWith(KEY)
                .compact();

    }

    public static void main(String[] args) {
        SessionContext sessionContext = new SessionContextModel("", "002", "zhangsan", "888", "客服经理");
        JwtHandler jwtHandler = new JwtHandler();
        String token = jwtHandler.generateToken(JSON.toJSONString(sessionContext));
        log.info("token=[{}]", token);

    }

    /**
     * 解析token
     *
     * @param token token
     * @return payload
     */
    public String parseToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token).getBody();
        } catch (MalformedJwtException malformedJwtException) {
            malformedJwtException.printStackTrace();
            throw new APIException("非法token", "无法解析token,请重新登入");
        } catch (ExpiredJwtException expiredJwtException) {
            expiredJwtException.printStackTrace();
            throw new APIException("token过期", "token时间过期,请重新登入");
        }
        Date expiration = claims.getExpiration();
        return claims.getSubject();
    }

}
