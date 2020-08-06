package com.controller;

import com.tools.Result;
import com.tools.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/")
public class Controller {

    @Autowired
    private Channel channel;

    @RequestMapping("upload")
    public Result Upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        return Result.Success(channel.ChannelInputStream(file,request));
    }
}
