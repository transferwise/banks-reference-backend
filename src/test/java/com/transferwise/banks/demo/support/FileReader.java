package com.transferwise.banks.demo.support;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReader {

    public static String read(final String filename) throws IOException {
        final var file = Paths.get("src", "test", "resources", filename);
        return Files.readString(file);
    }
}
