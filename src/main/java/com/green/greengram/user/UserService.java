package com.green.greengram.user;

import com.green.greengram.common.CustomFileUtils;
import com.green.greengram.user.model.SignInPostReq;
import com.green.greengram.user.model.SignInRes;
import com.green.greengram.user.model.SignUpPostReq;
import com.green.greengram.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final CustomFileUtils customFileUtils;

    @Transactional
    public int postSignUp(MultipartFile pic, SignUpPostReq p) {
        //프로필 이미지 파일 처리

            String saveFileName = customFileUtils.makeRandomFileName(pic);
            p.setPic(saveFileName); //null이면 null로 이름 들어감
            String hashedPassword = BCrypt.hashpw(p.getUpw(), BCrypt.gensalt());
            p.setUpw(hashedPassword);

            int result = mapper.postUser(p);
            if (pic == null) {
                return result;
            }
            try {
                String path = String.format("user/%d", p.getUserId());
                customFileUtils.makeFolders(path);
                String target = String.format("%s/%s", path, saveFileName);
                customFileUtils.transferTo(pic, target);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("파일 오류"); //이걸 안하면 오류 터져도 커밋을 해버려서 db에 값이 들어가긴함
            }
            return result;

    }

    public SignInRes postSignIn(SignInPostReq p) {
        User userEntity = mapper.getUserById(p.getUid());
            if(userEntity == null){
                throw new RuntimeException("아이디를 확인해주세요.");
            } else if (!BCrypt.checkpw(p.getUpw() , userEntity.getUpw())) {
                throw new RuntimeException("비밀번호를 확인해주세요.");
            }

            return SignInRes.builder()
                    .userId(userEntity.getUserId())
                    .nm(userEntity.getNm())
                    .pic(userEntity.getPic())
                    .build();
    }
}
