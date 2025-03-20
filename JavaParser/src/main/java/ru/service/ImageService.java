package ru.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import ru.exceptions.ImageProcessingException;
import ru.repository.ImageRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<String> fetchImageUrls(String websiteUrl) throws ImageProcessingException {
        try {
            List<String> imageUrls = new ArrayList<>();
            Document doc = Jsoup.connect(websiteUrl).get();
            for (Element imgElement : doc.select("img")) {
                String imgUrl = imgElement.absUrl("src");
                if (!imgUrl.isEmpty()) {
                    imageUrls.add(imgUrl);
                }
            }
            return imageUrls;
        } catch (IOException e) {
            throw new ImageProcessingException("Ошибка при получении списка изображений с " + websiteUrl, e);
        }
    }

    public void downloadImages(List<String> imageUrls, String downloadDir) throws ImageProcessingException {
        for (String imageUrl : imageUrls) {
            String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            imageRepository.downloadImage(imageUrl, downloadDir + "/" + fileName);
            System.out.println("Скачано: " + fileName);
        }
    }
}
