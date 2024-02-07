package com.pj.oil.memberPost.post;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final Logger Logger = LoggerFactory.getLogger(this.getClass());

    private final PostService postService;

    @PostMapping("/save")
    public void savePost(@RequestBody Post post){
        postService.write(post);
    }

    @GetMapping("/delete/{postId}")
    public void deletePost(@PathVariable Long postId){
        postService.delete(postId);
    }

}
