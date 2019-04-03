package com.stanikov.demo.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Entity
public class File {

    @Id
    private UUID id;
    @NotNull
    @NotEmpty
    private String fileName;
    @NotNull
    @NotEmpty
    private String size;
    private String description;
    private String addDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public File(){}

    public File(String fileName, String size, User user) {
        this.id = UUID.randomUUID();
        this.fileName = fileName;
        this.size = size;
        this.description = "Empty";
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        this.addDate = dateFormat.format(date);
        this.user = user;
    }

    public File(String fileName, String size, String description, User user) {
        this(fileName, size, user);
        this.description = description;
    }

    public File(String id, String fileName, String size, String description, User user) {
        this(fileName, size, description, user);
        if(id != null){
            this.id = UUID.fromString(id);
        }
    }

    public UUID getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getSize() {
        return size;
    }

    public String getDescription() {
        return description;
    }

    public String getAddDate() {
        return addDate;
    }

    public User getUser() {
        return user;
    }
}
