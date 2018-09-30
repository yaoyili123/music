package utils;

import java.security.MessageDigest;

//TODO:加密算法SHA1、MD5
public class EncoderHandler {

    public static String sha1(String text) {
        MessageDigest md = null;
        String outStr = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(text.getBytes());
            outStr = byteToString(digest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return outStr;
    }

    private static String byteToString(byte[] digest) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            String tempStr = Integer.toHexString(digest[i] & 0xff);
            if (tempStr.length() == 1) {
                buf.append("0").append(tempStr);
            } else {
                buf.append(tempStr);
            }
        }
        return buf.toString().toLowerCase();
    }

}
