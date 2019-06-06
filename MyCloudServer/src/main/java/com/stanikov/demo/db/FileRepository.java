package com.stanikov.demo.db;

import com.stanikov.demo.model.MyFile;
import com.stanikov.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<MyFile, Integer> {
    List<MyFile> findAll();
    MyFile findFileById(int id);
    MyFile findFileByFileNameAndUser(String fileName, User user);
    List<MyFile> findAllByUser(User user);
    void removeByFileName(String fileName);

    @Override
    void delete(MyFile myFile);
}
