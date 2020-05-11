package com.example.EcommerceApp.entities;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String fileName;
    private String path;
    private Boolean status;
    private Long userId;
    private Date createdOn;
    private String fileType;

    @Lob
    private byte[] data;

    public Image() {

    }

    public Image(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", path='" + path + '\'' +
                ", status=" + status +
                ", userId=" + userId +
                ", createdOn=" + createdOn +
                ", fileType='" + fileType + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}