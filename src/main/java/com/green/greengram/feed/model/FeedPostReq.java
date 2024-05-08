package com.green.greengram.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class FeedPostReq {
    @JsonIgnore
    private long feedId;
    private long userId;
    private String contents;
    private String location;

//    @JsonIgnore
//    private List<MultipartFile> pics;
}
