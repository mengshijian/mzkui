package com.cootf.log4droid.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.cootf.log4droid.common.consts.Constant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    public static Claims parseJWT(String jsonWebToken) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(Constant.BASE64_SECURITY))
                    .parseClaimsJws(jsonWebToken).getBody();
        } catch (Exception ex) {
            logger.debug("parseJWT ({})", ex);
        }
        return claims;
    }

    public static String createJWT(String userName, String password, long TTLMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        //生成签名密钥 就是一个base64加密后的字符串？
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(Constant.BASE64_SECURITY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName", userName);
        jsonObject.put("password", password);
        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .setId(String.valueOf(System.currentTimeMillis()))          // JWT_ID
                .setAudience(userName)                                      // 接受者
                //.setClaims(null)                                          // 自定义属性
                .setSubject(jsonObject.toString())                          // 主题
                .setIssuer(Constant.JWT_ISSUER)                             // 签发者
                .setIssuedAt(new Date())                                    // 签发时间
                //.setNotBefore(new Date())                                 // 失效时间
                //.setExpiration(DateUtils.addDays(new Date(),10))          // 过期时间
                .signWith(signatureAlgorithm, signingKey);

        //添加Token过期时间
        if (TTLMillis >= 0) {
            //过期时间
            long expMillis = nowMillis + TTLMillis;
            //现在是什么时间
            Date exp = new Date(expMillis);
            //系统时间之前的token都是不可以被承认的
            builder.setExpiration(DateUtils.addDays(new Date(), -10));
        }
        //生成JWT
        return builder.compact();
    }


    public static void main(String[] args) {
        String jwtStr = JwtUtils.createJWT("mengshijian", "123456", 36000000);

        Claims claims = JwtUtils.parseJWT(jwtStr);
        String subject = null;
        try {
            subject = claims.getSubject();
            System.out.print(subject);
        } catch (Exception ex) {
            System.out.print("yi guo qi");
        }
        System.out.print(subject == null);
    }
}
