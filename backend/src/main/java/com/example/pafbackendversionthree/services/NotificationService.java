package com.example.pafbackendversionthree.services;


import com.example.pafbackendversionthree.models.Like;
import com.example.pafbackendversionthree.models.Notification;
import com.example.pafbackendversionthree.repositories.AppUserRepository;
import com.example.pafbackendversionthree.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final AppUserRepository appUserRepository;

    @Autowired
    public NotificationService(
            NotificationRepository notificationRepository,
            AppUserRepository appUserRepository) {
        this.notificationRepository = notificationRepository;
        this.appUserRepository = appUserRepository;
    }

    // Create notification for a new like
    public void createLikeNotification(Like like) {
        System.out.println("Create Notification called");
        // Skip if user is liking their own post
        if (like.getUser().getId().equals(like.getPost().getPostedBy().getId())) {
            return;
        }

        // Create new notification
        Notification notification = new Notification();
        notification.setRecipient(like.getPost().getPostedBy());
        notification.setSender(like.getUser());
        notification.setType(Notification.NotificationType.LIKE);
        notification.setTargetType(Notification.TargetType.POST);
        notification.setTargetId(like.getPost().getId());
        notification.setTargetObject(like.getPost());
        notification.setMessage(like.getUser().getUsername() + " liked your post");

        notificationRepository.save(notification);
        System.out.println("New notifications created");
    }


}