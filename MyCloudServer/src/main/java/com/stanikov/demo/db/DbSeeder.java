package com.stanikov.demo.db;

import com.stanikov.demo.model.File;
import com.stanikov.demo.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "noteit.db.recreate", havingValue = "true")
public class DbSeeder implements CommandLineRunner {

//    private NotebookRepository notebookRepository;
//    private NoteRepository noteRepository;
    private UserRepository userRepository;
    private FileRepository fileRepository;

    public DbSeeder(UserRepository userRepository, FileRepository fileRepository){
//        this.notebookRepository = notebookRepository;
//        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
    }

    @Override
    public void run(String... args) throws Exception {

//        this.notebookRepository.deleteAll();
//        this.noteRepository.deleteAll();
        this.userRepository.deleteAll();
        this.fileRepository.deleteAll();

//        Notebook defaultNotebook = new Notebook("Default");
//        this.notebookRepository.save(defaultNotebook);
//
//        Notebook testNotebook = new Notebook("Test");
//        this.notebookRepository.save(testNotebook);
//
//        Note defaultNote = new Note("Hello", "Test to Note it", defaultNotebook);
//        this.noteRepository.save(defaultNote);

        User admin = new User("admin", "admin@gmail.com", "admin");
        this.userRepository.save(admin);

        File file1 = new File("testFile1", "20Mb", "This is test file", admin);
        File file2 = new File("testFile2", "210Mb", "test", admin);
        this.fileRepository.save(file1);
        this.fileRepository.save(file2);

        System.out.println("Finish initialized");
    }
}
