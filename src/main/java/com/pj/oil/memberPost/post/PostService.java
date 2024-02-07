package com.pj.oil.memberPost.post;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false, transactionManager = "memberPostTransactionManager")
public class PostService {

    private final PostRepository postRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void list(){

    }

    public void write(Post post){
        postRepository.save(post);
    }

    public void delete(Long postId){
        postRepository.deleteById(postId);
    }
}
