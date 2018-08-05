package com.blueteam.base.util.ueditor.upload;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.blueteam.base.util.ueditor.oss.ObjectService;
import com.blueteam.base.util.ueditor.oss.properties.OSSClientProperties;
import com.blueteam.base.util.ueditor.utils.SystemUtil;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 同步上传文件到阿里云OSS<br>
 *
 * @author XieXianbin me@xiexianbin.cn
 */
public class SynUploader extends Thread {

    private static Logger logger = Logger.getLogger(SynUploader.class);

    public boolean upload(JSONObject stateJson, OSSClient client,
                          HttpServletRequest request) {
//		Bucket bucket = BucketService.create(client,
//				OSSClientProperties.bucketName);
        // get the key, which the upload file path
        String key = stateJson.getString("url").replaceFirst("/", "");
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(
                    SystemUtil.getProjectRootPath() + key));
            PutObjectResult result = ObjectService.putObject(client,
                    OSSClientProperties.bucketName, key, fileInputStream);
            logger.debug("upload file to aliyun OSS object server success. ETag: "
                    + result.getETag());
            return true;
        } catch (FileNotFoundException e) {
            logger.error("upload file to aliyun OSS object server occur FileNotFoundException.");
        } catch (NumberFormatException e) {
            logger.error("upload file to aliyun OSS object server occur NumberFormatException.");
        } catch (IOException e) {
            logger.error("upload file to aliyun OSS object server occur IOException.");
        }
        return false;
    }

}
