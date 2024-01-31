package com.pj.oil.board;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/list")
public class BoardController {

    private final Logger Logger = LoggerFactory.getLogger(this.getClass());

    private final BoardService boardService;
}
