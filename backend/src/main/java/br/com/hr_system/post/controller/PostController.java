package br.com.hr_system.post.controller;

import br.com.hr_system.post.domain.Post;
import br.com.hr_system.post.dto.CreatePostDto;
import br.com.hr_system.post.dto.PostResponseDto;
import br.com.hr_system.post.service.PostService;
import br.com.hr_system.shared.dto.CreatedMessageResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> getNotifications(Pageable pageable){
        return new ResponseEntity<>(postService.getPosts(pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CreatedMessageResponseDTO> createPost(@RequestBody CreatePostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
}
