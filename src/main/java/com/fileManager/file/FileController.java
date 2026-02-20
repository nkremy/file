package com.fileManager.file;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(path = "/API/v1")
@CrossOrigin
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload/")
    public ResponseEntity<?> add(@RequestParam("file") MultipartFile file) {

        try {
            fileStorageService.save(file);
            return new ResponseEntity<>(fileStorageService.load(file.getOriginalFilename()), HttpStatus.CREATED);
        } catch (Exception e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/{name}")
    public ResponseEntity<?> load(@PathVariable(name = "name") String param) {
        try {
            return new ResponseEntity<>(fileStorageService.load(param), HttpStatus.CREATED);
        } catch (Exception e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/name")
    public ResponseEntity<?> delete(@PathVariable String param) {
        try {
            fileStorageService.delete(param);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{last}/{new}")
    public ResponseEntity<?> rename(@PathVariable(name = "last") String oldName,
            @PathVariable(name = "new") String newName) {
        try {
            fileStorageService.rename(oldName, newName);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
