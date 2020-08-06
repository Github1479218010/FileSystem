package com.tools;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

@Service
public class Channel {

    //最大文件大小300KB
    private long maxSize = 1024 * 300;

    public String ChannelInputStream(MultipartFile file, HttpServletRequest request) {
        //检查是否有文件，防止恶意发包
        if (file != null){
            //检查文件大小
            if (file.getSize() > maxSize){
                return "文件大于300KB";
            }
        }
        //主机
        String host = "47.97.63.69";
        //当前服务器端口
        int port = 8080;
        //获取主目录
        File path = new File(this.getClass().getResource("/").getPath() + "static/img/");
        //检查主目录是否存在，如果不存在则创建
        if (!path.exists()){
            path.mkdir();
        }
        //获取原文件名
        String fileName = file.getOriginalFilename();
        //获取后缀名
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //生成时间戳为输出文件的文件名
        long newName = System.currentTimeMillis();
        //文件大小
        long fileSize= file.getSize();
        //客户端IP地址
        String ip = request.getRemoteAddr();
        //传输文件花费的时间
        long time = 0;
        //设置时间格式精确到秒
        DateFormat format =new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
        //输入输出流
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        //输出文件目的地
        File outFile = new File(path.getPath() + "/" + newName + suffix);
        //接收读取数据的数组
        byte[] bytes = null;
        try {
            //获取文件输入流
            inputStream = (FileInputStream) file.getInputStream();
            //因为文件在300KB内，使用available()是为了保证一次性读完
            bytes = new byte[inputStream.available()];
            if (!outFile.exists()){
                outputStream = new FileOutputStream(outFile);
                long start = System.currentTimeMillis();
                while (inputStream.read(bytes) != -1){
                    outputStream.write(bytes);
                }
                //计算时间差
                time = System.currentTimeMillis() - start;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //判断是否是图片文件
        if (!isImage(outFile)){
            //不是图片文件，则删除已上传的文件
            if (outFile.delete()){
                //删除成功的日志
                System.out.println("用户：" + ip + " 时间：" + format.format(new Date()) + "文件无效，已删除");
            }else{
                //无法删除的日志
                System.out.println("用户：" + ip + " 时间：" + format.format(new Date()) + "文件无效，无法删除");
            }
            return "请上传有效文件";
        }
        //文件上传成功日志输出
        System.out.println("用户：" + ip + " 时间：" + format.format(new Date()) + " 文件名：" + newName + suffix + " 大小：" + fileSize / 1024 + "KB 耗时：" + time + "ms");
        //返回拼接的外链
        return "http://" + host + ":" + port + "/img/" + newName + suffix;
    }

    //检查图片是否有效
    public boolean isImage(File file){
        //声明image流
        ImageInputStream inputStream = null;
        try {
            //创建文件流
            inputStream = ImageIO.createImageInputStream(file);
            //尝试解码
            Iterator<ImageReader> iter = ImageIO.getImageReaders(inputStream);
            //如果迭代具有更多元素，则返回 true，没有更多元素，则返回false
            if (iter.hasNext()) {
                return true;    //不是图片：true  是图片：false
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
