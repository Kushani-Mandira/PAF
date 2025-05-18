package com.example.pafbackendversionthree.controllers;



import com.example.pafbackendversionthree.dtos.LikeDTO;
import com.example.pafbackendversionthree.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/toggle")
    public ResponseEntity<?> toggleLike(
            @RequestHeader("User-ID") String userId,
            @RequestBody LikeDTO.LikeRequestDTO likeRequestDTO) {

        LikeDTO.LikeResponseDTO result = likeService.toggleLike(userId, likeRequestDTO);

        if (result == null) {
            // If null is returned, post was unliked
            return ResponseEntity.noContent().build();
        } else {
            // If DTO is returned, post was liked
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
    }


}