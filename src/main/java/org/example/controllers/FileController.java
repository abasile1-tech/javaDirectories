package org.example.controllers;

import org.example.models.File;
import org.example.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class FileController {
    @Autowired
    FileRepository fileRepository;
    
    @GetMapping(value = "/files")
    public ResponseEntity<List<File>> getAllFiles() {
        return new ResponseEntity<List<File>>(fileRepository.findAll(), HttpStatus.OK);
    }
    
    @GetMapping(value = "/files/{id}")
    public ResponseEntity<Optional<File>> getFile(@PathVariable Long id) {
        return new ResponseEntity<Optional<File>>(fileRepository.findById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/files")
    public ResponseEntity<File> createFile(@RequestBody File file) {
        return new ResponseEntity<>(fileRepository.save(file), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/files/{id}")
    public void deleteFile(@PathVariable Long id) {
        fileRepository.deleteById(id);
    }

    @PutMapping(value = "/files/{id}")
    public File replaceFile(@PathVariable Long id, @RequestBody File newFile) {
        return fileRepository.findById(id).map((file) -> {
            file.setName(newFile.getName());
            file.setExtension(newFile.getExtension());
            file.setSize(newFile.getSize());
            file.setFolder((newFile.getFolder()));
            return fileRepository.save(file);
        }).orElseGet(() -> {
            newFile.setId(id);
            return fileRepository.save(newFile);
        });
    }
}
