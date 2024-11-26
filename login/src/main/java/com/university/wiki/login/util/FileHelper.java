package com.university.wiki.login.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHelper {
    /**
     * Retrieves a resource as a File.
     *
     * @param resourcePath the path to the resource (relative to the resources folder, e.g., "/keys/private.pem").
     * @return a File object pointing to the resource, copied to a temporary file.
     * @throws IOException if the resource cannot be read or copied.
     */
    public static File getResourceAsFile(String resourcePath) throws IOException {
        // Ensure the resource path starts with a "/"
        if (!resourcePath.startsWith("/")) {
            resourcePath = "/" + resourcePath;
        }

        // Load the resource
        var resourceURL = FileHelper.class.getResource(resourcePath);
        if (resourceURL == null) {
            throw new IOException("Resource not found: " + resourcePath);
        }

        // Copy resource to a temporary file
        Path tempFile = Files.createTempFile("resource-", "-" + Path.of(resourcePath).getFileName());
        Files.copy(resourceURL.openStream(), tempFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        return tempFile.toFile();
    }
}
