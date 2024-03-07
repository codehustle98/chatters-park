package com.codehustle.chatterpark.repository;

import com.codehustle.chatterpark.entity.User;
import com.codehustle.chatterpark.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsernameOrUserEmail(String username,String userEmail);

    @Query(value = "from User u where u.isOnline = true")
    List<User> findOnlineUsers();

    @Modifying
    @Query(value = "update User set isOnline =:isOnline where userId =:userId")
    void  updateUserLoginStatus(@Param("isOnline") boolean isOnline,@Param("userId")Long userId);
}
