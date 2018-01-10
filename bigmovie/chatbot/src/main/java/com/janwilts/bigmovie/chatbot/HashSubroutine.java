package com.janwilts.bigmovie.chatbot;

import com.rivescript.RiveScript;
import com.rivescript.macro.Subroutine;

import java.math.BigInteger;
import java.security.MessageDigest;

public class HashSubroutine implements Subroutine {
    
    @Override
    public String call(RiveScript rs, String[] args) {
        if (args.length == 0) return null;
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(args[0].getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String hashtext = bigInt.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
