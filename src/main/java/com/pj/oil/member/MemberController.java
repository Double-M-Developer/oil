package com.pj.oil.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MemberService memberService;

    @GetMapping("/change-password")
    public String changePasswordPage(Model model) {
        LOGGER.info("[change-password]GET \"/member/change-password\"");
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        model.addAttribute("changePasswordRequest", changePasswordRequest);
        return "changePassword";
    }

    @PostMapping("/change-password")
    public String changePassword(@Valid @ModelAttribute ChangePasswordRequest changePasswordRequest, Principal connectedMember, BindingResult bindingResult) {
        LOGGER.info("[changePassword]POST \"/member/change-password\", parameters={}", changePasswordRequest.toString());
        // 현재 로그인한 사용자가 없는 경우 처리
        if (connectedMember == null) {
            LOGGER.warn("[changePassword] No authenticated user found");
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시 에러 메시지와 함께 회원가입 폼으로 리다이렉트
            LOGGER.info("[changePassword] validate fail, redirect changePassword page");
            return "changePassword";
        }
        try {
            memberService.changePassword(changePasswordRequest, connectedMember);
        } catch (IllegalStateException e) {
            LOGGER.warn("[changePassword] warn: {}", e.getMessage());
            bindingResult.reject("changeFailed", e.getMessage());
            return "changePassword";
        }

        LOGGER.info("[changePassword] success, redirect home page");
        return "redirect:/"; // 성공 시 홈 페이지로 리다이렉트
    }
}
