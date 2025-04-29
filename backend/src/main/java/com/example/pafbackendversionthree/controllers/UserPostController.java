package com.example.pafbackendversionthree.controllers;

import com.example.pafbackendversionthree.dtos.CreateUpdatePostDto;
import com.example.pafbackendversionthree.dtos.UserPostDto;
import com.example.pafbackendversionthree.services.UserPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class UserPostController {

    @Autowired
    private UserPostService userPostService;

    // Create a new post
    @PostMapping
    public ResponseEntity<UserPostDto> createPost(@RequestParam String userId, @RequestBody CreateUpdatePostDto createUpdatePostDto) {
        UserPostDto createdPost = userPostService.createPost(userId, createUpdatePostDto);
        return ResponseEntity.ok(createdPost);
    }

    // Update an existing post
    @PutMapping("/{postId}")
    public ResponseEntity<UserPostDto> updatePost(@PathVariable String postId, @RequestBody CreateUpdatePostDto createUpdatePostDto) {
        UserPostDto updatedPost = userPostService.updatePost(postId, createUpdatePostDto);
        return ResponseEntity.ok(updatedPost);
    }

}