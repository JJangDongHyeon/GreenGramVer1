package com.green.greengram.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class CustomFileUtilsTest {

    @Autowired//di를 해달라
    CustomFileUtils utils;

    @Test
    void makeFolders() {
        String result = utils.makeFolders("ddd2");
        System.out.println("result: " + result);
    }
    @Test
    void makeRandomFileName() {
        String result = utils.makeRandomFileName();
        System.out.println("makeRandomFileName: " + result);
    }
    @Test
    void getExt(){
        String ext = utils.getExt("askmv.ddd.jpg");
        System.out.println("ext: " + ext);
    }
    @Test
    void make(){
       String test = utils.makeRandomFileName("askmv.ddd.jpg");
        System.out.println("test:" + test);
    }


}