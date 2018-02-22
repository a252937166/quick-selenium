package com.ouyanglol.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;

/**
 * Created by Ouyang on 2017/7/6.
 */
@Component
public class QiniuUtil {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${qiniu_ak}")
    private String AK;

    @Value("${qiniu_sk}")
    private String SK;

    @Value("${qiniu_bucket}")
    private String BUCKET;

    @Value("${qiniu_cdn}")
    private String CDN;

    private UploadManager uploadManager = new UploadManager(new Configuration(Zone.zone2()));


    public String uploadImg(String fileName,byte[] fileBytes) {
        Auth auth = Auth.create(AK, SK);
        String upToken = auth.uploadToken(BUCKET);
        DefaultPutRet putRet = null;
        try {
            Response response = uploadManager.put(fileBytes, fileName, upToken);
            //解析上传成功的结果
            putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            logger.info(fileName+"的putRet.key->{}",putRet.key);
            logger.info(fileName+"的putRet.hash->{}",putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            logger.error(r.toString());
            try {
                logger.error(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return putRet!=null?putRet.key:null;
    }

    public String uploadImg(String fileName,String imgUrl) {
        return uploadImg(fileName,imgUrl,null);
    }

    public String uploadImg(String fileName,String imgUrl,String referer) {
        byte[] fileBytes = Http.getImageBytes(imgUrl,referer);
        return uploadImg(fileName,fileBytes);
    }

    public String getPrivateImage(String fileName) throws UnsupportedEncodingException {
        if (new Random().nextBoolean()) {
            fileName = fileName+"-info";
        } else {
            fileName = fileName+"-blog";
        }
        String encodedFileName = URLEncoder.encode(fileName, "utf-8");
        String publicUrl = String.format("%s/%s", CDN, encodedFileName);
        Auth auth = Auth.create(AK, SK);
        long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
        String privateUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
        return privateUrl;
    }


    public String getImgUrl(String key) {
        return CDN+"/"+key;
    }
}
