package com.jumbox.demo.controller.controller;

import com.jumbox.demo.common.utils.CommonResult;
import com.jumbox.demo.mbg.pojo.Article;
import com.jumbox.demo.service.ArticleService;
import com.jumbox.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/shudong")
public class shudongController {
    @Resource
    private UserService userService;
    @Resource
    private ArticleService articleService;

    @GetMapping(path = "/getAdminList")
    public CommonResult getAdminList() {
        return CommonResult.success(userService.getAdminList());
    }

    @PostMapping(path = "/login")
    public CommonResult login(@RequestParam String id, @RequestParam String username) {
        return CommonResult.success(userService.login(id, username));
    }

//    @GetMapping(path = "/adminRegister")
//    public CommonResult adminRegister(@RequestParam("username") String username) {
//        return CommonResult.success(userService.adminRegister(username));
//    }

    @PostMapping(path = "/createArticle")
    public CommonResult createArticle(@RequestBody Article article) {
        return CommonResult.success(articleService.create(article));
    }

    @PostMapping(path = "/addComment")
    public CommonResult addComment(@RequestParam("id") String id, @RequestParam("comment") String comment) {
        return CommonResult.success(articleService.addComment(id, comment));
    }

    @GetMapping(path = "/getArticleList")
    public CommonResult getArticleList(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return CommonResult.success(articleService.getList(page, pageSize));
    }
}
