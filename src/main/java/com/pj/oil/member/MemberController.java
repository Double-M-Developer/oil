package com.pj.oil.member;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MemberService memberService;

    @PatchMapping
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request, Principal connectedMember) {
        LOGGER.info("[changePassword]PATCH \"/member\", parameters={}", request.toString());
        memberService.changePassword(request, connectedMember);
        return ResponseEntity.ok().build();
    }
}
