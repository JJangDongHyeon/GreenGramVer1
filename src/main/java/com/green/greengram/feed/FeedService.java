package com.green.greengram.feed;

import com.green.greengram.common.CustomFileUtils;
import com.green.greengram.feed.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedMapper mapper;
    private final CustomFileUtils customFileUtils;//DI를 받는다.(빈등록)
    @Transactional//사진 업로드 중에 실패하면 글 작성 한것도 취소하기 위해 사용
    public FeedPostRes postFeed(List<MultipartFile> pics , FeedPostReq p){//pk값(feedId 가 long 이라)
        int result = mapper.postFeed(p);
        log.info(Integer.toString(result));
        if(pics == null )
        {
            return FeedPostRes.builder()
                .feedId(p.getFeedId())
                .build();
        }
        FeedPicPostDto picDto = FeedPicPostDto.builder().feedId(p.getFeedId()).build();
        try{
                String path = String.format("feed/%d" , p.getFeedId());
                customFileUtils.makeFolders(path);
                for(MultipartFile pic : pics){
                    String saveFileName = customFileUtils.makeRandomFileName(pic);
                    picDto.getFileNames().add(saveFileName);//랜덤한 파일 이름을 fileNames에 집어넣고있음.
                    String target = String.format("%s/%s", path, saveFileName);
                    customFileUtils.transferTo(pic, target);
                    //long값 하나랑 스트링 여ㅑ러개 있는 친구를 만드는 작업임.
                }
                int affectedRowsPics = mapper.postFeedPics(picDto);
            } catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException("Feed 등록 오류");
            }
        return FeedPostRes.builder()
                .feedId(p.getFeedId())
                .pics(picDto.getFileNames())
                .build();
    }

    public List<FeedGetRes> getFeed(FeedGetReq p){
        List<FeedGetRes> list = mapper.getFeed(p);

        for(FeedGetRes res : list){
            List<String> pics = mapper.getFeedPicsByFeedId(res.getFeedId());
            res.setPics(pics);
        }
        return list;
    }
}
