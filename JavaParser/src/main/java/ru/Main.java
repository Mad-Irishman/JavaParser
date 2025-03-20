package ru;

import ru.exceptions.ImageProcessingException;
import ru.repository.ImageRepository;
import ru.service.ImageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите URL страницы: ");
        String url = scanner.nextLine().trim();

        System.out.print("Введите путь для сохранения изображений: ");
        String downloadDir = scanner.nextLine().trim();

        try {
            Files.createDirectories(Paths.get(downloadDir));
            ImageService imageService = new ImageService(new ImageRepository());

            List<String> imageUrls = imageService.fetchImageUrls(url);
            if (imageUrls.isEmpty()) {
                System.err.println("На странице нет изображений для загрузки.");
                return;
            }

            imageService.downloadImages(imageUrls, downloadDir);
            System.out.println("Загрузка завершена.");

        } catch (ImageProcessingException e) {
            System.err.println("Ошибка обработки изображений: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Ошибка при создании директории: " + e.getMessage());
        }
    }
}
