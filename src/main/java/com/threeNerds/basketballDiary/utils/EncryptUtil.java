package com.threeNerds.basketballDiary.utils;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Slf4j
public class EncryptUtil {
    /**
     * SHA-256 암호화
     * @param message
     * @return
     * @throws NoSuchAlgorithmException
     */
/*    public static String getEncrypt(String message){
        return getEncrypt(message,getSalt());
    }*/

    /**
     * SHA-256 암호화 Getter 함수
     * @param message
     * @param salt : salt는 userId
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String getEncrypt(String message,String salt) {
        String result="";
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] passBytes = (message+getConverHex(salt.getBytes())).getBytes();
            md.update(passBytes);

            byte[] byteData = md.digest();

            StringBuilder sb = new StringBuilder();
            for(int i=0;i<byteData.length;i++){
                sb.append(Integer.toString((byteData[i] & 0xFF) + 256, 16).substring(1));
            }
            result = sb.toString();
        }catch (NoSuchAlgorithmException e){
            log.error("",e);
        }
        return result;
    }

    /**
     * Salt Getter 함수
     * @return
     */
    public static String getSalt(){
        Random random = new Random();

        byte[] salt = new byte[8];
        random.nextBytes(salt);

        String strSalt = getConverHex(salt);
        return strSalt;
    }

    /**
     * Hex로 변환
     * @param salt
     * @return
     */
    public static String getConverHex(byte[] salt){
        StringBuilder sb = new StringBuilder();
        for(byte b : salt){
            sb.append(String.format("%02x",b));
        }
        return sb.toString();
    }
}
