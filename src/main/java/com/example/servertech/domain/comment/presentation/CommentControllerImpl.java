package com.example.servertech.domain.comment.presentation;

import com.example.servertech.domain.comment.application.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentControllerImpl implements CommentController {
	private final CommentService commentService;
}
