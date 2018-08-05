package com.blueteam.base.util.ueditor.upload;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.blueteam.base.util.ueditor.oss.BucketService;
import com.blueteam.base.util.ueditor.oss.OSSClientFactory;
import com.blueteam.base.util.ueditor.oss.properties.OSSClientProperties;
import com.blueteam.base.util.ueditor.utils.SystemUtil;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Map;

/**
 * 同步上传文件到阿里云OSS<br>
 *
 * @author XieXianbin me@xiexianbin.cn
 */
public class Uploader {
    private static Logger logger = Logger.getLogger(Uploader.class);

    private HttpServletRequest request = null;
    private Map<String, Object> conf = null;

    public Uploader(HttpServletRequest request, Map<String, Object> conf) {
        this.request = request;
        this.conf = conf;
    }

    public final com.blueteam.base.util.ueditor.define.State doExec() {
        String filedName = (String) this.conf.get("fieldName");
        com.blueteam.base.util.ueditor.define.State state = null;

        if ("true".equals(this.conf.get("isBase64"))) {
            state = Base64Uploader.save(this.request.getParameter(filedName),
                    this.conf);
        } else {
            state = BinaryUploader.save(this.request, this.conf);
            JSONObject stateJson = new JSONObject(state.toJSONString());
            // 判别云同步方式
            if (OSSClientProperties.useStatus) {

                String bucketName = OSSClientProperties.bucketName;
                OSSClient client = OSSClientFactory.createOSSClient();
                // auto create Bucket to default zone
                if (OSSClientProperties.autoCreateBucket) {
                    Bucket bucket = BucketService.create(client, bucketName);
                    logger.debug("Bucket 's " + bucket.getName() + " Created.");
                }

                // upload type
                if (OSSClientProperties.useAsynUploader) {
                    AsynUploaderThreader asynThreader = new AsynUploaderThreader();
                    asynThreader.init(stateJson, client, this.request);
                    Thread uploadThreader = new Thread(asynThreader);
                    uploadThreader.start();
                } else {
                    SynUploader synUploader = new SynUploader();
                    synUploader.upload(stateJson, client, this.request);
                }

                // storage type
                if (false == OSSClientProperties.useLocalStorager) {
                    String uploadFilePath = (String) this.conf.get("rootPath") + (String) stateJson.get("url");
                    File uploadFile = new File(uploadFilePath);
                    if (uploadFile.isFile() && uploadFile.exists()) {
                        uploadFile.delete();
                    }
                }

                state.putInfo(
                        "url",
                        OSSClientProperties.ossEndPoint
                                + stateJson.getString("url"));
            } else {
                state.putInfo("url", "/" + SystemUtil.getProjectName()
                        + stateJson.getString("url"));
            }
        }
        /*
         * { "state": "SUCCESS", "title": "1415236747300087471.jpg", "original":
		 * "a.jpg", "type": ".jpg", "url":
		 * "/upload/image/20141106/1415236747300087471.jpg", "size": "18827" }
		 */
        logger.debug(state.toJSONString());
        return state;
    }
}
