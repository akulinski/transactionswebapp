package com.mrowka.transactionswebapp.util;

import java.security.SecureRandom;

public class RandomStringGenerator {

    private static String alphabet ="QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm";
    private static SecureRandom random = new SecureRandom();

    public static String generateRandomString(int size){

        StringBuilder stringBuilder = new StringBuilder();

        for(int i=0;i<size;i++){
            stringBuilder.append(alphabet.charAt(random.nextInt(alphabet.length()-1)));
        }
        return stringBuilder.toString();
    }
}
