package com.mk.ots.security.web;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.codec.Utf8;

public class Md5PasswordEncoder2UpperCase extends Md5PasswordEncoder {

	@Override
	public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
		String pass1 = "" + encPass;
		String pass2 = encodePassword(rawPass, salt);

		return equalsPwd(pass1.toUpperCase(), pass2.toUpperCase());
	}
	
	/**
     * Constant time comparison to prevent against timing attacks.
     * @param expected
     * @param actual
     * @return
     */
    private boolean equalsPwd(String expected, String actual) {
        byte[] expectedBytes = bytesUtf8(expected);
        byte[] actualBytes = bytesUtf8(actual);
        int expectedLength = expectedBytes == null ? -1 : expectedBytes.length;
        int actualLength = actualBytes == null ? -1 : actualBytes.length;
        if (expectedLength != actualLength) {
            return false;
        }

        int result = 0;
        for (int i = 0; i < expectedLength; i++) {
            result |= expectedBytes[i] ^ actualBytes[i];
        }
        return result == 0;
    }

    private static byte[] bytesUtf8(String s) {
        if(s == null) {
            return null;
        }

        return Utf8.encode(s);
    }
}
