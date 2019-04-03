package com.stanikov.demo.db;

import com.stanikov.demo.model.File;
import com.stanikov.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Integer> {
    List<File> findAll();
    File findFileById(int id);
    List<File> findAllByUser(User user);
}
