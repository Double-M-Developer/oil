package com.pj.oil.controller;

import com.pj.oil.dto.LoginRequestDto;
import com.pj.oil.dto.MemberUpdateFormDto;
import com.pj.oil.entity.Member;
import com.pj.oil.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
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

    @Operation(summary = "사용자 전체 조회", description = "db에 있는 사용자 전체 데이터 조회")
    @ApiResponse(content = @Content(schema = @Schema(implementation = Member.class)))
    @GetMapping(value = "/v1/member")
    public ResponseEntity<List<Member>> findAllMembers() {
        return ResponseEntity.ok(memberService.findAllMembers());
    }

    @Operation(summary = "사용자 1명 조회", description = "db에 있는 사용자 1명 데이터 사용자 id로 조회")
    @ApiResponse(content = @Content(schema = @Schema(implementation = Member.class)))
    @GetMapping(value = "/v1/member/{id}")
    public ResponseEntity<EntityModel<Member>> findById(@PathVariable Long id) {
        Optional<Member> findMembers = memberService.findById(id);
        if (findMembers.isEmpty()) { // 예외처리 구문
            ResponseEntity.noContent();
        }
        // 조회한 member 뿐만 아니라 모든 member 를 조회할 수 있는 link 를 entityModel 에 담기
        EntityModel<Member> entityModel = EntityModel.of(findMembers.get());
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).findAllMembers());
        entityModel.add(link.withRel("all-members"));

        return ResponseEntity.ok(entityModel);
    }

    @Operation(summary = "사용자 회원가입", description = "요청받은 사용자 1명 데이터 db에 저장")
    @ApiResponse(content = @Content(schema = @Schema(implementation = Member.class)))
    @PostMapping(value = "/v1/member")
    public ResponseEntity<EntityModel<Member>> signup(@RequestBody Member member) {
        Long savedMemberId = memberService.signup(member);

        Optional<Member> savedMember = memberService.findById(savedMemberId);
        if (savedMember.isEmpty()) { // 예외처리 구문
            ResponseEntity.badRequest();
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

    @Operation(summary = "사용자 로그인", description = "요청받은 사용자 데이터와 db에 있는 사용자 데이터 검증 후 결과 반환")
    @ApiResponse(content = @Content(schema = @Schema(implementation = Member.class)))
    @PostMapping(value = "/v1/login")
    public ResponseEntity<EntityModel<Member>> login(@RequestBody LoginRequestDto loginRequestDto) {
        Long loginMemberId = memberService.login(loginRequestDto);

        Optional<Member> loginMember = memberService.findById(loginMemberId);
        if (loginMember.isEmpty()) { // 예외처리 구문
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 로그인한 member 를 조회할 수 있는 uri 를 Location 헤더에 담기
        // login 경로로 시작했기 때문에 path 를 새롭게 설정함
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/v1/member/{id}")
                .buildAndExpand(loginMemberId)
                .toUri();

        // 로그인한 member 뿐만 아니라 모든 member 를 조회할 수 있는 link 를 entityModel 에 담기
        EntityModel<Member> entityModel = EntityModel.of(loginMember.get());
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).findAllMembers());
        entityModel.add(link.withRel("all-members"));

        return ResponseEntity.ok().location(location).body(entityModel);
    }

    @Operation(summary = "사용자 1명 정보 수정", description = "요청받은 사용자 데이터와 db에 있는 사용자 데이터 검증 후 정보 수정")
    @ApiResponse(content = @Content(schema = @Schema(implementation = Member.class)))
    @PatchMapping(value = "/v1/member/{id}")
    public ResponseEntity<EntityModel<Member>> updateNicknameAndPassword(@PathVariable String userId, @RequestBody MemberUpdateFormDto memberUpdateFormDto) {
        Long updateMemberId = memberService.updateMember(userId, memberUpdateFormDto);

        Optional<Member> loginMember = memberService.findById(updateMemberId);

        // 수정한 member 를 조회할 수 있는 uri 를 Location 헤더에 담기
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("")
                .buildAndExpand(updateMemberId)
                .toUri();


        if (loginMember.isEmpty()) { // 예외처리 구문
            return ResponseEntity.noContent().location(location).build();
        }

        // 수정한 member 뿐만 아니라 모든 member 를 조회할 수 있는 link 를 entityModel 에 담기
        EntityModel<Member> entityModel = EntityModel.of(loginMember.get());
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).findAllMembers());
        entityModel.add(link.withRel("all-members"));

        return ResponseEntity.created(location).body(entityModel);
    }

}
