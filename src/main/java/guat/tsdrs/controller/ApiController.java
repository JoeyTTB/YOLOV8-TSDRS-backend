package guat.tsdrs.controller;

import guat.tsdrs.pojo.Result;
import guat.tsdrs.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Slf4j
public class ApiController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ApiService apiService;

    @PostMapping("/detect_image")
    public Result detectImage(@RequestBody MultipartFile file) {
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        log.info("uuid: " + uuid);
        String url = "http://localhost:5000/detect_image/" + uuid;
        HttpEntity<MultiValueMap<String, Object>> httpEntity = apiService.detect_img(file, uuid);
        return restTemplate.postForObject(url, httpEntity, Result.class);
    }

    @PostMapping("/detect_video")
    public Result detectVideo(@RequestBody MultipartFile file) {
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        String url = "http://localhost:5000/detect_video/" + uuid;
        HttpEntity<MultiValueMap<String, Object>> httpEntity = apiService.detect_video(file, uuid);
        return restTemplate.postForObject(url, httpEntity, Result.class);
    }
}
