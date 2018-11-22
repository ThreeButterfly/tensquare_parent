package com.xuan.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * <p>Description: token测试 </p>
 *
 * @Created with IDEA
 * @author: Yi-Xuan
 * @Date: 2018/11/22 0022
 * @Time: 15:35
 */
public class CreateJwt {
    public static void main(String[] args) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("123")
                .setSubject("小马")
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 120000))
                .claim("role", "admin")
                .signWith(SignatureAlgorithm.HS256, "itcast");
        System.out.println(jwtBuilder.compact());
    }
}
