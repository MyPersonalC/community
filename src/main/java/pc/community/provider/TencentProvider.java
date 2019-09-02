package pc.community.provider;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.region.Region;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pc.community.exception.CustomizeErrorCode;
import pc.community.exception.CustomizeException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
public class TencentProvider {
    @Value("${tencent.secretId}")
    private String secretId;
    @Value("${tencent.secretKey}")
    private String secretKey;
    @Value("${tencent.bucketName}")
    private String bucketName;
    @Value("tencent.region")
    private String regionString;

    public String upload(MultipartFile file) throws IOException {
        String generatedFileName = "";
        FileInputStream fileStream = (FileInputStream) file.getInputStream();
        //文件类型
//        String mineType = file.getContentType();
        String fileName = file.getOriginalFilename();
        String[] filePaths = fileName.split("\\.");
        if (filePaths.length > 1) {
            generatedFileName = UUID.randomUUID().toString() + "." + filePaths[filePaths.length - 1];
        } else {
            throw new CustomizeException(CustomizeErrorCode.UPLOAD_FILE_FAIL);
        }
        // 1 初始化用户身份信息（secretId, secretKey）
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的区域 clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(regionString);
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);

        try {

            BufferedInputStream in = new BufferedInputStream(fileStream);
//            设置超时
            Date expirationTime = new Date(System.currentTimeMillis() + 30 * 60 * 1000);
            //设置数据类型
//            request.setContentType(mineType);
            URL url = cosClient.generatePresignedUrl(bucketName, generatedFileName, expirationTime, HttpMethodName.PUT);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");
            BufferedOutputStream out = new BufferedOutputStream(connection.getOutputStream());
            // 写入要上传的数据
            byte[] flush = new byte[1024];
            int i = in.read(flush);
            while (i != -1) {
                out.write(flush, 0, i);
                out.flush();
                i = in.read(flush);
            }
            int responseCode = connection.getResponseCode();
            out.close();
            if (responseCode == 200) {
                return generatedFileName;
            } else {
                throw new CustomizeException(CustomizeErrorCode.UPLOAD_FILE_FAIL);
            }
        } catch (CosClientException | IOException e) {
            e.printStackTrace();
            throw new CustomizeException(CustomizeErrorCode.UPLOAD_FILE_FAIL);
        } finally {
            cosClient.shutdown();
        }
    }

    // 获取下载的预签名连接
    public String download(String filename) {

        // 1 初始化用户身份信息（secretId, secretKey）
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的区域 clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(regionString);
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);

        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, filename, HttpMethodName.GET);
        // 设置签名过期时间(可选), 若未进行设置则默认使用ClientConfig中的签名过期时间(1小时)
        // 这里设置签名在半个小时后过期
        Date expirationDate = new Date(System.currentTimeMillis() + 10 * 356 * 24 * 60 * 60 * 1000L);
        req.setExpiration(expirationDate);

        URL url = cosClient.generatePresignedUrl(req);
        cosClient.shutdown();
        return url.toString();
    }

}
