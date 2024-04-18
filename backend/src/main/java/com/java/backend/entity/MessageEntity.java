package com.java.backend.entity;

import com.java.backend.enums.MessageStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
@Entity
@Table(name = "messages")
@Getter
@Setter
public class MessageEntity extends AbstractEntity {
    @Column(nullable = false)
    private String messageContent;
    @Column(nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private UserEntity sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    private UserEntity receiver;
}
