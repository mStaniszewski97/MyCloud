package com.stanikov.demo.api;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.stanikov.demo.db.FileRepository;
import com.stanikov.demo.db.UserRepository;
import com.stanikov.demo.model.MyFile;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class FileController {
    private UserRepository userRepository;
    private FileRepository fileRepository;
    private UserController userController;
    private AmazonS3 s3client;
    private final String bucketName = "mstaniszewskimycloud";

    public FileController(UserRepository userRepository, FileRepository fileRepository, UserController userController, Environment environment) {
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
        this.userController = userController;
        AWSCredentials credentials = new BasicAWSCredentials(environment.getProperty("S3AccessKeyID"), environment.getProperty("S3SecretAccessKey"));

        s3client = new AmazonS3Client(credentials);
        Region euWest = Region.getRegion(Regions.EU_WEST_1);
        s3client.setRegion(euWest);
    }

    @PostMapping(value = "/storeFile")
    @ResponseStatus(HttpStatus.OK)
    public void handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        s3client.putObject(new PutObjectRequest(bucketName, file.getOriginalFilename(), convert(file)));
        MyFile myFile = new MyFile(file.getOriginalFilename(), String.valueOf(file.getSize()) + " B", "empty", userController.getLoggedUser());
        fileRepository.save(myFile);
    }

    @RequestMapping(value = "/files", method = RequestMethod.GET)
    @ResponseBody
    public List<S3ObjectSummary> getFiles() {
        List<S3ObjectSummary> objectSummaries = s3client.listObjects(bucketName).getObjectSummaries();
        for (S3ObjectSummary objectSummary : objectSummaries) {
            System.out.println(objectSummary.getKey());
        }
        if (userController.getLoggedUser().getUsername().equals("admin")) {
            return objectSummaries;
        }
        List<S3ObjectSummary> userFiles = new ArrayList<>();
        List<MyFile> allFilesByUser = fileRepository.findAllByUser(userController.getLoggedUser());
        for (S3ObjectSummary objectSummary : objectSummaries) {
            for (MyFile myFile : allFilesByUser) {
                if (myFile.getFileName().equals(objectSummary.getKey())) {
                    userFiles.add(objectSummary);
                }
            }
        }
        return userFiles;
    }

    private static File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    @GetMapping("/downloadFile/{fileKey}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileKey) throws IOException {

        S3Object s3object = s3client.getObject(bucketName, fileKey);
        S3ObjectInputStream fileFromS3 = s3object.getObjectContent();

        ByteArrayOutputStream downloadInputStream = new ByteArrayOutputStream();
        int len;
        byte[] buffer = new byte[4096];
        while ((len = fileFromS3.read(buffer, 0, buffer.length)) != -1) {
            downloadInputStream.write(buffer, 0, len);
        }

        return ResponseEntity.ok()
                .contentType(contentType(fileKey))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileKey + "\"")
                .body(downloadInputStream.toByteArray());
    }

    private MediaType contentType(String keyname) {
        String[] arr = keyname.split("\\.");
        String type = arr[arr.length - 1];
        switch (type) {
            case "txt":
                return MediaType.TEXT_PLAIN;
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
                return MediaType.IMAGE_JPEG;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    public ResponseEntity<Object> deleteFile(@RequestParam("fileKey") String fileKey) {
        s3client.deleteObject(bucketName, fileKey);
        MyFile file = fileRepository.findFileByFileNameAndUser(fileKey, userController.getLoggedUser());
        fileRepository.delete(file);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
