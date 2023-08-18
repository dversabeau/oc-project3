package com.openclassrooms.chatop.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

@Service
public class PictureService {

    public String uploadImage(MultipartFile file) throws IOException {

        String path = "static";
        Path uploadPath = Paths.get(path);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
        String fileName = StringUtils.cleanPath(originalFilename);

        Path filePath = Paths.get(path + "/" + fileName);
        boolean fileExists = Files.exists(filePath);
        if(fileExists) {
            String[] fileArr = fileName.split("\\.");
            String fileFormat = fileArr[fileArr.length-1];
            fileArr = Arrays.copyOf(fileArr, fileArr.length-1);
            int n = 1;
            while (fileExists) {
                String index = "(" + n + ")";
                String newPictureFile = String.join(".", fileArr) + index;
                fileName = newPictureFile + "." + fileFormat;
                filePath = Paths.get("static\\" + fileName);
                fileExists = Files.exists(filePath);
                n++;
            }
        }

        File pictureFile = new File(path + "/" + fileName);
        file.transferTo(pictureFile.toPath());

        return "http://localhost:3001/"+fileName;
    }

}
