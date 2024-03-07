package com.codehustle.chatterpark.repository;

import com.codehustle.chatterpark.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessagesRepository extends JpaRepository<Messages,Long> {

    List<Messages> findMessagesByMessageTimeAfter(LocalDateTime time);
}
