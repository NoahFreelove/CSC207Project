package com.project.engine.IO;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Unlike image loader, text loader does not buffer the text in memory for
 * fast access next time. This is because text files are usually small and
 * not read frequently. They may be loaded for levels, but that's about it.
 */
public class FileIO {

    public static String[] ReadTextLines(String relativePath) {
        return ReadText(relativePath).split("\n");
    }

    public static String ReadText(String relativePath) {
        return ReadText(relativePath, "");
    }

    public static String ReadText(String relativePath, String defaultText) {
        try(InputStream stream = FileIO.class.getClassLoader().getResourceAsStream(relativePath)) {
            if (stream == null) {
                System.err.println("IO Exception: Resource not found: " + relativePath + "\nResolution: Using default text");
                return defaultText;
            }
            return new String(stream.readAllBytes());
        } catch (IOException e) {
            System.err.println("IO Exception (" + e.getClass().getSimpleName() + "): Failed to load text: " + relativePath + "\nResolution: Using default text");
            return defaultText;
        }
    }

    public static boolean WriteText(String relativePath, String text) {
        try {
            Path path = Paths.get(FileIO.class.getResource("/").getPath());
            path = Paths.get(path.toString(), relativePath);
            Files.createDirectories(path.getParent());
            Files.writeString(path, text);
            return true;
        } catch (Exception e) {
            System.err.println("Exception (" + e.getClass().getSimpleName() + "): Failed to write text: " + relativePath + "\n" + e.getMessage());
            return false;
        }
    }

    public static boolean WriteTextAbs(String absolutePath, String text) {
        try {
            Path path = Paths.get(absolutePath);
            Files.createDirectories(path.getParent());
            Files.writeString(path, text);
            return true;
        } catch (Exception e) {
            System.err.println("Exception (" + e.getClass().getSimpleName() + "): Failed to write text (absolute): " + absolutePath + "\n" + e.getMessage());
            return false;
        }
    }

    public static String ReadTextAbs(String path) {
        try {
            return Files.readString(Paths.get(path));
        } catch (IOException e) {
            System.err.println("IO Exception (" + e.getClass().getSimpleName() + "): Failed to load text (absolute): " + path + "\nResolution: Using default text");
            return "";
        }
    }

    public static String GetAbsPathOfResource(String relResourcePath) {
        try {
            return Paths.get(FileIO.class.getResource(relResourcePath).toURI()).toString();
        } catch (Exception e) {
            System.err.println("Exception (" + e.getClass().getSimpleName() + "): Failed to get absolute path of resource: " + relResourcePath + "\n" + e.getMessage());
            return "";
        }
    }
}
