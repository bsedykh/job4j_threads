package ru.job4j.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    private String content(Predicate<Character> filter) throws IOException {
        StringBuilder output = new StringBuilder();
        int data;
        try (var input = new BufferedInputStream(new FileInputStream(file))) {
            while ((data = input.read()) > 0) {
                char c = (char) data;
                if (filter.test(c)) {
                    output.append(c);
                }
            }
        }
        return output.toString();
    }

    public String getContent() throws IOException {
        return content(c -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return content(c -> c < 0x80);
    }
}
