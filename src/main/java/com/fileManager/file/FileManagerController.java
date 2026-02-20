package com.fileManager.file;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController

public class FileManagerController {

    @Autowired
    private FileStorageService fileStorageService;
    private static final Logger log = Logger.getLogger(FileManagerController.class.getName());

    @PostMapping("/upload-file")
    public boolean uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // fileStorageService.saveFile(file);
            fileStorageService.saveFile2(file);
            return true;
        } catch (IOException e) {
            log.log(Level.SEVERE, "Exception during uploading", e.getMessage());
            System.out.println(e);
            return false;
        }
    }
}
