package com.devco.spaceandbeyond.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public final class TestResources {

    private TestResources() {
    }

    public static Path fileOnClasspath(String resourcePath) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(resourcePath);
        if (url == null) {
            throw new IllegalArgumentException("Resource not found on classpath: " + resourcePath);
        }
        try {
            if ("file".equals(url.getProtocol())) {
                return Paths.get(url.toURI());
            }
            String fileName = Paths.get(resourcePath).getFileName().toString();
            Path temp = Files.createTempFile("space-and-beyond-", "-" + fileName);
            temp.toFile().deleteOnExit();
            try (InputStream in = url.openStream()) {
                Files.copy(in, temp, StandardCopyOption.REPLACE_EXISTING);
            }
            return temp;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Bad resource URI: " + resourcePath, e);
        }
    }
}
