package com.example.TaskManager.repository;


import com.example.TaskManager.model.User;
import com.example.TaskManager.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, UUID> {

    @Query("SELECT w FROM Workspace w JOIN FETCH w.user u " +
            "WHERE w.id = :workspaceId AND u.email = :email")
    Optional<Workspace> findSecureById(
            @Param("workspaceId") UUID workspaceId,
            @Param("email") String email
    );

    @Query("SELECT CASE WHEN COUNT(w) > 0 THEN true ELSE false END " +
            "FROM Workspace w JOIN w.user u " +
            "WHERE w.id = :workspaceId AND u.email = :email")
    boolean existsSecure(
            @Param("workspaceId") UUID workspaceId,
            @Param("email") String email
    );
}