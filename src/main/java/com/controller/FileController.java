package com.controller;

import com.siyecao.utils.CommonsResult;
import com.utils.Channel;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/file")
public class FileController {

    @Resource
    private Channel channel;

    @PostMapping("/upload")
    public CommonsResult Upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        return CommonsResult.Success(channel.ChannelInputStream(file,request));
    }
}
