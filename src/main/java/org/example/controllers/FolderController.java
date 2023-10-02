package org.example.controllers;

import org.example.models.Folder;
import org.example.repositories.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class FolderController {
    @Autowired
    FolderRepository folderRepository;

    @GetMapping(value = "/folders")
    public ResponseEntity<List<Folder>> getAllFolders() {
        return new ResponseEntity<List<Folder>>(folderRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/folders/{id}")
    public ResponseEntity<Optional<Folder>> getFolder(@PathVariable Long id) {
        return new ResponseEntity<Optional<Folder>>(folderRepository.findById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/folders")
    public ResponseEntity<Folder> createFolder(@RequestBody Folder folder) {
        return new ResponseEntity<>(folderRepository.save(folder), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/folders/{id}")
    public void deleteFolder(@PathVariable Long id) {
        folderRepository.deleteById(id);
    }

    @PutMapping(value = "/folders/{id}")
    public Folder replaceFolder(@PathVariable Long id, @RequestBody Folder newFolder) {
        return folderRepository.findById(id).map((folder) -> {
            folder.setTitle(newFolder.getTitle());
            folder.setPerson(newFolder.getPerson());
            folder.setFiles(newFolder.getFiles());
            return folderRepository.save(folder);
        }).orElseGet(() -> {
            newFolder.setId(id);
            return folderRepository.save(newFolder);
        });
    }
}
