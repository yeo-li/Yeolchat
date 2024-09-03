package com.yeo_li.yeolchat.util;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class Sha256PasswordEncoder {

    // 비밀번호를 SHA-256으로 해싱하는 메소드
    public String encode(String password) {
        try {
            // SHA-256 해시 인스턴스 생성
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // 비밀번호를 바이트 배열로 변환하고 해싱
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            // 해시 결과를 Base64로 인코딩하여 문자열로 반환
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found.", e);
        }
    }

    // 해싱된 비밀번호와 입력된 비밀번호가 일치하는지 확인하는 메소드
    public boolean matches(String rawPassword, String encodedPassword) {
        // 입력된 비밀번호를 해싱하고, 저장된 해시와 비교
        String hashedRawPassword = encode(rawPassword);
        return hashedRawPassword.equals(encodedPassword);
    }
}
