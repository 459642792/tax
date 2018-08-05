package com.blueteam.base.util;

import com.blueteam.base.constant.Constants;
import com.blueteam.base.util.weixin.WechatUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class FileTools {

    public static String convert(String str) {
        str = (str == null ? "" : str);
        String tmp;
        StringBuffer sb = new StringBuffer(1000);
        char c;
        int i, j;
        sb.setLength(0);
        for (i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            sb.append("\\u");
            j = (c >>> 8);
            tmp = Integer.toHexString(j);
            if (tmp.length() == 1)
                sb.append("0");
            sb.append(tmp);
            j = (c & 0xFF);
            tmp = Integer.toHexString(j);
            if (tmp.length() == 1)
                sb.append("0");
            sb.append(tmp);
        }
        return (new String(sb));
    }

    public static void doDeleteEmptyDir(String dir) {
        boolean success = (new File(dir)).delete();
        if (success) {
            System.out.println("Successfully deleted empty directory: " + dir);
        } else {
            System.out.println("Failed to delete empty directory: " + dir);
        }
    }

    public static void deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                deleteDir(new File(dir, children[i]));
            }
        }
        dir.delete();
    }

    /**
     * 下载 文件保存到磁盘
     *
     * @param urlString 地址
     * @param filename  保存文件名
     * @param savePath  文件路径
     * @param jsonParam 请求参数
     * @throws Exception
     * @author Eric Lee,2017-03-07
     */
    public void download(String urlString, String filename,
                         String savePath, String jsonParam) throws Exception {
        // 构造URL    
        URL url = new URL(urlString);
        // 打开连接    
        URLConnection con = url.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        if (jsonParam != null) {
            con.getOutputStream().write(jsonParam.getBytes("utf-8"));
        }
        //设置请求超时为5s    
        con.setConnectTimeout(5 * 1000);
        // 输入流    
        InputStream is = con.getInputStream();

        // 1K的数据缓冲    
        byte[] bs = new byte[1024];
        // 读取到的数据长度    
        int len;
        // 输出的文件流    
        File sf = new File(savePath);
        if (!sf.exists()) {
            sf.mkdirs();
        }
        OutputStream os = new FileOutputStream(sf.getPath() + "//" + filename);
        // 开始读取    
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接    
        os.close();
        is.close();
    }

    /**
     * 获取小程序二维码
     *
     * @param vendorId 商家ID
     * @param request
     * @return 返回存放的相对路径，即域名之后的路径
     * @throws Exception
     */
    public String getQrcode(int vendorId, HttpServletRequest request) throws Exception {
        String jsonParam = "{\"path\": \"pages/shopInfo/shopInfo?id=" + vendorId + "\", \"width\": 430}";
        String realPath = request.getServletContext().getRealPath(Constants.VENDOR_QRCODE_SAVE_PATH);
        String fileName = vendorId + ".jpg";
        String resultPath = "/" + Constants.VENDOR_QRCODE_SAVE_PATH + "/" + fileName;
        if (new File(realPath + fileName).exists()) {
            return resultPath;
        }
        download(Constants.VENDOR_QRCODE_ADDR + WechatUtils.getToken(), fileName, realPath, jsonParam);

        return resultPath;
    }


    /**
     * 获取小程序二维码
     *
     * @param vendorId 商家ID
     * @param request
     * @return 返回存放的相对路径，即域名之后的路径
     * @throws Exception
     */
    public String getQrcodeToWeb(int vendorId, HttpServletRequest request) throws Exception {
        String jsonParam = "{\"path\": \"pages/shopInfo/shopInfo?id=" + vendorId + "\", \"width\": 430}";
        String realPath = request.getServletContext().getRealPath("/WEB-INF/" + Constants.VENDOR_QRCODE_SAVE_PATH);
        String fileName = vendorId + ".jpg";
        String resultPath = "/" + Constants.VENDOR_QRCODE_SAVE_PATH + "/" + fileName;
        if (new File(realPath + fileName).exists()) {
            return resultPath;
        }
        download(Constants.VENDOR_QRCODE_ADDR + WechatUtils.getToken(), fileName, realPath, jsonParam);

        return resultPath;
    }
}

