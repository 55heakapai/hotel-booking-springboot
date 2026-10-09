package com.stayease.hotel.service;

import com.stayease.hotel.exception.OurException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/** Stores room photos on local disk and serves them from /uploads/**. */
@Service
public class ImageStorageService {

    private final Path uploadDir;
    private final String publicUrl;

    public ImageStorageService(@Value("${app.upload-dir}") String uploadDir,
                               @Value("${app.public-url}") String publicUrl) {
        this.uploadDir = Paths.get(uploadDir).toAbsolutePath().normalize();
        this.publicUrl = publicUrl.endsWith("/") ? publicUrl.substring(0, publicUrl.length() - 1) : publicUrl;
    }

    public String saveImage(MultipartFile photo) {
        try {
            String contentType = photo.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new OurException("Only image files are allowed");
            }
            Files.createDirectories(uploadDir);
            String original = photo.getOriginalFilename() == null ? "room.jpg" : photo.getOriginalFilename();
            String filename = UUID.randomUUID() + "-" + original.replaceAll("[^a-zA-Z0-9._-]", "_");
            Files.copy(photo.getInputStream(), uploadDir.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            return publicUrl + "/uploads/" + filename;
        } catch (OurException e) {
            throw e;
        } catch (IOException e) {
            throw new OurException("Unable to store image: " + e.getMessage());
        }
    }

    public Path getUploadDir() {
        return uploadDir;
    }
}
