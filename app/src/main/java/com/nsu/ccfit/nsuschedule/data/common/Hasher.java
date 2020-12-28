package com.nsu.ccfit.nsuschedule.data.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {
    private final static int BUFF_SIZE = 1024;
    public byte[] getFileHash(File file) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        FileInputStream fis = new FileInputStream(file);

        byte[] dataBytes = new byte[BUFF_SIZE];
        int nread = 0;
        while ((nread = fis.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        }
        fis.close();
        return md.digest();
    }

    public byte[] getStringHash(String line) {
        return null;
    }
}
