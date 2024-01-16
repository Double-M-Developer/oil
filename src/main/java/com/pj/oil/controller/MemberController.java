package com.pj.oil.controller;

import com.pj.oil.dto.LoginRequestDto;
import com.pj.oil.dto.MemberUpdateFormDto;
import com.pj.oil.entity.Member;
import com.pj.oil.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/v1/member")
    public ResponseEntity<List<Member>> findAllMembers() {
        return ResponseEntity.ok(memberService.findAllMembers());
    }

    @GetMapping(value = "/v1/member/{id}")
    public ResponseEntity<EntityModel<Member>> findById(@PathVariable Long id) {
        Optional<Member> findMembers = memberService.findById(id);
        if (findMembers.isEmpty()) { // 예외처리 구문
//            throw new UserNotFoundException("id:" + id);
        }
        // 조회한 member 뿐만 아니라 모든 member 를 조회할 수 있는 link 를 entityModel 에 담기
        EntityModel<Member> entityModel = EntityModel.of(findMembers.get());
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).findAllMembers());
        entityModel.add(link.withRel("all-members"));

        return ResponseEntity.ok(entityModel);
    }

    @PostMapping(value = "/v1/member")
    public ResponseEntity<EntityModel<Member>> signup(@RequestBody Member member) {
        Long savedMemberId = memberService.signup(member);

        Optional<Member> savedMember = memberService.findById(savedMemberId);
        if (savedMember.isEmpty()) { // 예외처리 구문
//            throw new UserNotFoundException("id:" + savedMemberId);
        }

        // 생성한 member 를 조회할 수 있는 uri 를 Location 헤더에 담기
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedMemberId)
                .toUri();

        // 생성한 member 뿐만 아니라 모든 member 를 조회할 수 있는 link 를 entityModel 에 담기
        EntityModel<Member> entityModel = EntityModel.of(savedMember.get());
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).findAllMembers());
        entityModel.add(link.withRel("all-members"));

        return ResponseEntity.created(location).body(entityModel);
    }

    @PostMapping(value = "/v1/login")
    public ResponseEntity<EntityModel<Member>> login(@RequestBody LoginRequestDto loginRequestDto) {
        Long loginMemberId = memberService.login(loginRequestDto);

        Optional<Member> loginMember = memberService.findById(loginMemberId);
        if (loginMember.isEmpty()) { // 예외처리 구문
//            throw new UserNotFoundException("id:" + loginMemberId);
        }

        // 생성한 member 를 조회할 수 있는 uri 를 Location 헤더에 담기
        // login 경로로 시작했기 때문에 path 를 새롭게 설정함
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/v1/member/{id}")
                .buildAndExpand(loginMemberId)
                .toUri();

        // 생성한 member 뿐만 아니라 모든 member 를 조회할 수 있는 link 를 entityModel 에 담기
        EntityModel<Member> entityModel = EntityModel.of(loginMember.get());
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).findAllMembers());
        entityModel.add(link.withRel("all-members"));

        return ResponseEntity.created(location).body(entityModel);
    }

    @PatchMapping(value = "/v1/member/{id}")
    public ResponseEntity<EntityModel<Member>> updateNicknameAndPassword(@PathVariable String userId, @RequestBody MemberUpdateFormDto memberUpdateFormDto) {
        Long updateMemberId = memberService.updateMember(userId, memberUpdateFormDto);

        Optional<Member> loginMember = memberService.findById(updateMemberId);
        if (loginMember.isEmpty()) { // 예외처리 구문
//            throw new UserNotFoundException("id:" + loginMemberId);
        }

        // 수정한 member 를 조회할 수 있는 uri 를 Location 헤더에 담기
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("")
                .buildAndExpand(updateMemberId)
                .toUri();

        // 생성한 member 뿐만 아니라 모든 member 를 조회할 수 있는 link 를 entityModel 에 담기
        EntityModel<Member> entityModel = EntityModel.of(loginMember.get());
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).findAllMembers());
        entityModel.add(link.withRel("all-members"));

        return ResponseEntity.created(location).body(entityModel);
    }

}
