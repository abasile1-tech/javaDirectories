package org.example.components;

import org.example.models.File;
import org.example.models.Folder;
import org.example.models.Person;
import org.example.repositories.FileRepository;
import org.example.repositories.FolderRepository;
import org.example.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component // comment this out once you have seeded the db if you do not want to run the data loader every time
public class DataLoader implements ApplicationRunner {

    @Autowired
    FileRepository fileRepository;

    @Autowired
    FolderRepository folderRepository;

    @Autowired
    PersonRepository personRepository;

    public DataLoader() {

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Person john = new Person("John");
        personRepository.save(john);
        Folder special_folder = new Folder("special folder", john);
        folderRepository.save(special_folder);
        File secret_file = new File("secret file", ".txt", 200, special_folder);
        fileRepository.save(secret_file);
        File hidden_file = new File("hidden file", ".env", 100, special_folder);
        fileRepository.save(hidden_file);
        Folder favorite_folder = new Folder("favorite folder", john);
        folderRepository.save(favorite_folder);
        File ignored_file = new File("git ignore file", ".gitignore", 150, favorite_folder);
        fileRepository.save(ignored_file);
    }
}
