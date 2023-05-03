package com.example.dsa;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Verify {
    public boolean VerifyMessage(byte[] message, BigInteger s1, BigInteger s2, BigInteger p, BigInteger q, BigInteger h, BigInteger publicKey) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

        BigInteger hashMessage = new BigInteger(1, messageDigest.digest(message));
        BigInteger sPrim = s2.modInverse(q);
        BigInteger u1 = hashMessage.multiply(sPrim).mod(q);
        BigInteger u2 = sPrim.multiply(s1).mod(q);
        BigInteger t = h.mod(p).modPow(u1, p).multiply(publicKey.mod(p).modPow(u2, p)).mod(p).mod(q);

        if(t.compareTo(s1) == 0)
        {
            return true;
        }
        return false;
    }
}
