package ru.repository;

import ru.exceptions.ImageProcessingException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageRepository {
    public void downloadImage(String imageUrl, String fileName) throws ImageProcessingException {
        try (InputStream in = new URL(imageUrl).openStream();
             FileOutputStream out = new FileOutputStream(fileName)) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

        } catch (IOException e) {
            throw new ImageProcessingException("Ошибка при скачивании изображения: " + imageUrl, e);
        }
    }
}
