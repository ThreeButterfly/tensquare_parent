package com.xuan.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;
import java.util.logging.SimpleFormatter;

/**
 * <p>Description: 解析 </p>
 *
 * @Created with IDEA
 * @author: Yi-Xuan
 * @Date: 2018/11/22 0022
 * @Time: 16:44
 */
public class ParseJwtTest {
    public static void main(String[] args) {

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMiLCJzdWIiOiLlsI_pqawiLCJpYXQiOjE1NDI4NzczNTcsImV4cCI6MTU0Mjg3NzQ3Nywicm9sZSI6ImFkbWluIn0.ZQBQC2pIozW17oIIuWg2bWdCaJyjKNUVvsTZb4k7dfQ";

        Claims claims = Jwts.parser()
                .setSigningKey("itcast")
                .parseClaimsJws(token)
                .getBody();
        System.out.println("用户id：" + claims.getId() +
                "\n用户名：" + claims.getSubject() +
                "\n用户角色：" + claims.get("role") +
                "\n登录时间：" + new SimpleDateFormat("yyyy-MM-dd HH::mm:ss").format(claims.getIssuedAt()) +
                "\n过期时间：" + new SimpleDateFormat("yyyy-MM-dd HH::mm:ss").format(claims.getExpiration()));
    }
}
