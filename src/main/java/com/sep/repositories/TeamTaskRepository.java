package com.sep.repositories;

import com.sep.domain.TeamTask;
import com.sep.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TeamTaskRepository extends JpaRepository<TeamTask, Long> {

//    @Query( "select o from Role o where name in :names" )
    Set<TeamTask> findByAssignedTo(User user);
}