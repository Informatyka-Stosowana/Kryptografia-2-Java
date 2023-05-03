package com.example.dsa;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Sign {
    private BigInteger p;
    private BigInteger q;
    private BigInteger h;
    private BigInteger publicKey;


    public BigInteger[] SignMessage(byte[] message) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

        BigInteger hashMessage = new BigInteger(1, messageDigest.digest(message));
        BigInteger[] digital_signature = new BigInteger[2];
        int lenght = GenerateLength();

        // Wygenerowanie wartości początkowych p, q, h
        q = GenerateQ();
        p = GenerateP(q, lenght);
        h = GenerateH(lenght, p, q);

        // Wygenerowanie klucza prywatnego a i wyliczenie na jego podstawie klucz publicznego b
        BigInteger privateKey = GeneratePrivateKey(q);
        publicKey = GeneratePublicKey(privateKey, h, p);

        BigInteger s1;
        BigInteger s2;

        do {
            BigInteger r = GenerateR(q);
            BigInteger r_prim = r.modInverse(q);

            // Obliczenie podpisu cyfrowego
            s1 = h.modPow(r, p).mod(q);
            s2 = r_prim.multiply(hashMessage.add(privateKey.multiply(s1))).mod(q);

            if (!(s1.compareTo(BigInteger.ZERO) == 0) && !(s2.compareTo(BigInteger.ZERO) == 0)) {
                break;
            }
        }
        while (true);


        digital_signature[0] = s1;
        digital_signature[1] = s2;

        return digital_signature;
    }

    private int GenerateLength() {
        Random random = new Random();

        // Generowanie długości p (między 512 a 1024 bitami) podzielny przez 64
        int length = 512 + random.nextInt(9) * 64;
        return length;
    }

    private BigInteger GenerateQ() {
        Random random = new Random();
        BigInteger q = BigInteger.probablePrime(160, random);
        return q;
    }

    private BigInteger GenerateP(BigInteger q, int length) {
        Random random = new Random();

        // Choose another primer number p, such that p-1 mod q = 0. p is called the prime modulus
        do
        {
            p = BigInteger.probablePrime(length, random);
            p = p.subtract(p.subtract(BigInteger.ONE).remainder(q));
        }
        while (!(p.isProbablePrime(4)));
        return p;
    }

    private BigInteger GenerateH(int length, BigInteger p, BigInteger q) {
        Random random = new Random();
        BigInteger g;
        BigInteger temp = p.subtract(BigInteger.ONE).divide(q);
        do
        {
            g = new BigInteger(length, random).mod(p.subtract(BigInteger.valueOf(3))).add(BigInteger.TWO);
            h = g.mod(p).modPow(temp, p);
        }
        while (!(h.compareTo(BigInteger.ONE) == 1 && h.compareTo(p) == -1 && h.mod(p).modPow(q, p).compareTo(BigInteger.ONE) == 0));
        return h;
    }

    private BigInteger GeneratePrivateKey(BigInteger q) {
        Random random = new Random();

        // Wybiera losową liczbę od 1 do q-1
        return new BigInteger(160, random).mod(q.subtract(BigInteger.ONE).add(BigInteger.ONE));
    }

    private BigInteger GeneratePublicKey(BigInteger privateKey, BigInteger h, BigInteger p) {
        return h.mod(p).modPow(privateKey, p);
    }

    private BigInteger GenerateR(BigInteger q) {
        Random random = new Random();
        return new BigInteger(160, random).mod(q.subtract(BigInteger.ONE)).add(BigInteger.ONE);
    }

    public BigInteger getP() {
        return p;
    }

    public BigInteger getQ() {
        return q;
    }

    public BigInteger getH() {
        return h;
    }

    public BigInteger getPublicKey() {
        return publicKey;
    }
}
