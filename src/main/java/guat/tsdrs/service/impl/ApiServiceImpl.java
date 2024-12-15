package guat.tsdrs.service.impl;

import guat.tsdrs.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@Slf4j
public class ApiServiceImpl implements ApiService {

    @Override
    public HttpEntity<MultiValueMap<String, Object>> detect_img(MultipartFile file, String uuid) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        String paramName = "img" + uuid + ".jpg";
        ByteArrayResource resource = null;
        try {
            resource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
                @Override
                public long contentLength() {
                    return file.getSize();
                }
            };
        } catch (IOException e) {
            log.error("流转换失败");
            throw new RuntimeException(e);
        }
        body.add(paramName, resource);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        return new HttpEntity<>(body, headers);
    }

    @Override
    public HttpEntity<MultiValueMap<String, Object>> detect_video(MultipartFile file, String uuid) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        String paramName = "video" + uuid + ".mp4";
        ByteArrayResource resource = null;
        try {
            resource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
                @Override
                public long contentLength() {
                    return file.getSize();
                }
            };
        } catch (IOException e) {
            log.error("流转换失败");
            throw new RuntimeException(e);
        }
        body.add(paramName, resource);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        return new HttpEntity<>(body, headers);
    }
}
