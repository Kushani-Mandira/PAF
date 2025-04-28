package com.example.pafbackendversionthree.services;

import com.example.pafbackendversionthree.dtos.LikeDTO;
import com.example.pafbackendversionthree.exceptions.ResourceNotFoundException;
import com.example.pafbackendversionthree.models.AppUser;
import com.example.pafbackendversionthree.models.Like;
import com.example.pafbackendversionthree.models.UserPost;
import com.example.pafbackendversionthree.repositories.AppUserRepository;
import com.example.pafbackendversionthree.repositories.LikeRepository;
import com.example.pafbackendversionthree.repositories.UserPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserPostRepository userPostRepository;
    private final AppUserRepository appUserRepository;

    private final NotificationService notificationService;

    @Autowired
    public LikeService(
            LikeRepository likeRepository,
            UserPostRepository userPostRepository,
            AppUserRepository appUserRepository,NotificationService notificationService) {
        this.likeRepository = likeRepository;
        this.userPostRepository = userPostRepository;
        this.appUserRepository = appUserRepository;
        this.notificationService= notificationService;
    }

    public LikeDTO.LikeResponseDTO toggleLike(String userId, LikeDTO.LikeRequestDTO likeRequestDTO) {
        // Get the user
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // Get the post
        UserPost post = userPostRepository.findById(likeRequestDTO.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + likeRequestDTO.getPostId()));

        // Check if like already exists
        Optional<Like> existingLike = likeRepository.findByUserIdAndPostId(userId, likeRequestDTO.getPostId());

        if (existingLike.isPresent()) {
            // If like exists, remove it (unlike)
            likeRepository.delete(existingLike.get());
            return null; // Returning null indicates post was unliked
        } else {
            try {
                // If like doesn't exist, create it
                Like like = new Like();
                like.setUser(user);
                like.setPost(post);
                notificationService.createLikeNotification(like);
                // Save like
                Like savedLike = likeRepository.save(like);


                // Return response DTO
                return LikeDTO.LikeResponseDTO.fromLike(savedLike);
            } catch (Exception e) {
                // Check if this is a duplicate key error
                if (e.getMessage() != null && e.getMessage().contains("duplicate key error")) {

                    Optional<Like> concurrentLike = likeRepository.findByUserIdAndPostId(userId, likeRequestDTO.getPostId());
                    if (concurrentLike.isPresent()) {
                        likeRepository.delete(concurrentLike.get());
                    }
                    return null;
                }
                // For other errors, rethrow
                throw e;
            }
        }
    }

    }