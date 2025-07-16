package guat.tsdrs.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
public class AliOSSUtils {
    private String endpoint = "";
    private String httpEndPoint = "";
    private String accessKeyId = "";
    private String accessKeySecret = "";
//    EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
    private String bucketName = "";

    public String upload(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();//get binary code stream
        String originalFileName = file.getOriginalFilename();//get name
        //create unique filename to avoid name collision
        String fileName = UUID.randomUUID().toString() +
                originalFileName.substring(originalFileName.lastIndexOf("."));
        //create oss obj
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //send request and store file
        ossClient.putObject(bucketName, fileName, inputStream);
        //create the file request url
        String url = httpEndPoint.split("//")[0] + "//" + bucketName + "."
                + httpEndPoint.split("//")[1] + "//" + fileName;
        ossClient.shutdown();
        return url;
    }

    public AliOSSUtils() throws ClientException {
        System.out.println("Access Key is wrong");
    }
}
