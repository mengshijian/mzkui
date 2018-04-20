package com.cootf.log4droid.common.utils;

import com.sun.xml.internal.messaging.saaj.util.Base64;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/***/
public class MD5Encrypt {

    /***/
    public static final String CLEARTEXT_ALGORITHM = "TXT";

    public static String encryptCheckSum(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] strBytes = str.getBytes("utf-8");
            md.update(strBytes);
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();                     //32位

            //return buf.toString().substring(8,24);   //16位

        } catch (NoSuchAlgorithmException e) {
            // TODO: handle exception

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /***************************************************************************
     * MD5加密方法
     * @param password
     * @return
     */
    public static String encrypt(String password, int salt) {
        try {
            String encodedPwd = calculateDigest(salt, password, "MD5");
            // because of the encoding bug the password's last character should
            // be cut out.
            return encodedPwd.substring(5, encodedPwd.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Do the actual digest calculation.
     */
    private static String calculateDigest(int salt, String password, String algorithm)
            throws NoSuchAlgorithmException,
            UnsupportedEncodingException {
        byte[] bytes;

        if (!CLEARTEXT_ALGORITHM.equals(algorithm)) {
            // do the hashing
            MessageDigest md = MessageDigest.getInstance(algorithm);
            if (salt != 0) {
                byte[] s = new byte[4];
                s[0] = (byte) (salt & 0xff);
                s[1] = (byte) ((salt >> 8) & 0xff);
                s[2] = (byte) ((salt >> 16) & 0xff);
                s[3] = (byte) ((salt >> 24) & 0xff);
                md.update(s);
            }

            md.update(password.getBytes("UTF8"));
            bytes = md.digest();
        } else {
            // no hashing
            bytes = password.getBytes("UTF8");
        }

        StringBuilder buf = new StringBuilder(32);
        buf.append("{");
        buf.append(algorithm);
        buf.append("}");
        buf.append(Base64.encode(bytes));

        String digest = buf.toString();

        return digest;
    }

    public static void main(String[] args) {

        System.out.println("ok=" + encrypt("888888", 60));
    }

}

