package com.pj.oil.board;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void list(){

    }

    public void write(){

    }

    public void delete(){

    }
}
