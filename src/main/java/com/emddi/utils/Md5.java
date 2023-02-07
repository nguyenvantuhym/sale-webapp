package com.emddi.utils;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
    public static String hash(String s) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.reset();
        md.update(s.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();

        String hash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();

        return hash;
    }
}
