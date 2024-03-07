package com.codehustle.chatterpark.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
public class Messages implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "message_id")
    private Long messageId;

    @Column(name = "sender_id")
    private Long userId;

    @Column(name = "sender_name")
    private String username;

    @Column(name = "content")
    private String message;

    @Column(name = "message_time")
    private LocalDateTime messageTime;
}
