package com.example.servertech.domain.post.presentation;

import com.example.servertech.domain.post.application.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostControllerImpl implements PostController {
	private final PostService postService;
}
