package com.example.TaskManager.repository;


import com.example.TaskManager.model.UserTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface TaskRepository extends JpaRepository<UserTask, UUID> {

    @Query("SELECT t FROM UserTask t JOIN FETCH t.workspace w WHERE w.id = :workspaceId")
    List<UserTask> findByWorkspaceId(@Param("workspaceId") UUID workspaceId);

    @Query("SELECT t FROM UserTask t " +
            "JOIN FETCH t.workspace w " +
            "JOIN FETCH w.user u " +
            "WHERE t.id = :taskId AND u.email = :email")
    Optional<UserTask> findSecureById(
            @Param("taskId") UUID taskId,
            @Param("email") String email
    );
}