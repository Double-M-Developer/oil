package com.pj.oil.memberPost.post;

import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "posts")
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String email;

    private String title;

    private String rate;

}
