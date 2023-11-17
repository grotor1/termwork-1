package com.grotor.termwork1sem.helpers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
    public static String saveFile(String name, byte[] fileBuffer) {
        String path = "C:/files/" + name;
        File file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (FileOutputStream fod = new FileOutputStream(file)) {
            fod.write(fileBuffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return path;
    }
}
