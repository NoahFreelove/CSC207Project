package com.project.external_interfaces;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A utility class for handling file input and output, compatible with both
 * JAR and normal filesystem environments.
 */
public class FileIO {

    /**
     * Reads text lines from a resource file relative to the classpath.
     *
     * @param relativePath the path to the resource file
     * @return an array of lines from the file
     */
    public static String[] ReadTextLines(String relativePath) {
        return ReadText(relativePath).split("\n");
    }

    /**
     * Reads the entire text content of a resource file.
     *
     * @param relativePath the path to the resource file
     * @return the text content of the file, or an empty string if not found
     */
    public static String ReadText(String relativePath) {
        return ReadText(relativePath, "");
    }

    /**
     * Reads the entire text content of a resource file with a default fallback.
     *
     * @param relativePath the path to the resource file
     * @param defaultText  the fallback text if the resource is not found
     * @return the text content of the file, or the default text
     */
    public static String ReadText(String relativePath, String defaultText) {
        try (InputStream stream = FileIO.class.getResourceAsStream("/" + relativePath)) {
            if (stream == null) {
                System.err.println("Resource not found: " + relativePath + ". Using default text.");
                return defaultText;
            }
            return new String(getBytesFromStream(stream));
        } catch (IOException e) {
            System.err.println("Failed to load text: " + relativePath + ". Using default text. Error: " + e.getMessage());
            return defaultText;
        }
    }

    /**
     * Gets the bytes of a resource file relative to the classpath.
     *
     * @param relativePath the path to the resource file
     * @return a byte array of the file content
     */
    public static byte[] ReadBytes(String relativePath) {
        try (InputStream stream = FileIO.class.getResourceAsStream("/" + relativePath)) {
            if (stream == null) {
                System.err.println("Resource not found: " + relativePath);
                return new byte[0];
            }
            return getBytesFromStream(stream);
        } catch (IOException e) {
            System.err.println("Failed to read bytes from resource: " + relativePath + ". Error: " + e.getMessage());
            return new byte[0];
        }
    }

    /**
     * Writes text to a relative path in the filesystem.
     *
     * @param relativePath the relative path to the file
     * @param text         the text to write
     * @return true if successful, false otherwise
     */
    public static boolean WriteText(String relativePath, String text) {
        try {
            Path path = Paths.get(System.getProperty("user.dir")).resolve(relativePath);
            Files.createDirectories(path.getParent());
            Files.writeString(path, text);
            return true;
        } catch (Exception e) {
            System.err.println("Failed to write text: " + relativePath + ". Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Reads text from an absolute path in the filesystem.
     *
     * @param absolutePath the absolute path to the file
     * @return the text content of the file, or an empty string if not found
     */
    public static String ReadTextAbs(String absolutePath) {
        try {
            return Files.readString(Paths.get(absolutePath));
        } catch (IOException e) {
            System.err.println("Failed to load text (absolute): " + absolutePath + ". Returning empty text. Error: " + e.getMessage());
            return "";
        }
    }

    /**
     * Writes text to an absolute path in the filesystem.
     *
     * @param absolutePath the absolute path to the file
     * @param text         the text to write
     * @return true if successful, false otherwise
     */
    public static boolean WriteTextAbs(String absolutePath, String text) {
        try {
            Path path = Paths.get(absolutePath);
            Files.createDirectories(path.getParent());
            Files.writeString(path, text);
            return true;
        } catch (Exception e) {
            System.err.println("Failed to write text (absolute): " + absolutePath + ". Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Reads bytes from an input stream.
     *
     * @param stream the input stream to read
     * @return a byte array of the stream content
     */
    private static byte[] getBytesFromStream(InputStream stream) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[8192]; // Read in chunks
            int length;
            while ((length = stream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            return new byte[0];
        }
    }
}
