package ru.job4j.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class Wget implements Runnable {
    private final String url;
    private final String fileName;
    private final int speed;

    public Wget(String url, String fileName, int speed) {
        this.url = url;
        this.speed = speed;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        var file = new File(fileName);
        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(file)) {
            var dataBuffer = new byte[512];
            int totalBytes = 0;
            var start = System.currentTimeMillis();
            int bytesRead;
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1
                    && !Thread.currentThread().isInterrupted()) {
                output.write(dataBuffer, 0, bytesRead);
                totalBytes += bytesRead;
                if (totalBytes >= speed) {
                    var duration = System.currentTimeMillis() - start;
                    if (duration < 1000) {
                        var sleep = 1000 - duration;
                        System.out.printf("Sleeping for %d ms%n", sleep);
                        Thread.sleep(sleep);
                    }
                    totalBytes = 0;
                    start = System.currentTimeMillis();
                }
            }
            System.out.println(Files.size(file.toPath()) + " bytes");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 3) {
            throw new IllegalArgumentException("Illegal args. Usage: <url> <file> <speed>");
        }
        String url = args[0];
        String fileName = args[1];
        int speed = Integer.parseInt(args[2]);
        Thread wget = new Thread(new Wget(url, fileName, speed));
        wget.start();
        wget.join();
    }
}
