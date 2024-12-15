package guat.tsdrs.service;

import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

public interface ApiService {

    public HttpEntity<MultiValueMap<String, Object>> detect_img(MultipartFile file, String uuid);

    public HttpEntity<MultiValueMap<String, Object>> detect_video(MultipartFile file, String uuid);
}
