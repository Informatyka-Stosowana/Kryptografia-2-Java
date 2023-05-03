//DSA
//Copyright(C) 2023
//Dominik Kwiecień 242448
//Hubert Pietras 242494

//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.

//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.

//You should have received a copy of the GNU General Public License
//along with this program.  If not, see <http://www.gnu.org/licenses/>.

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
