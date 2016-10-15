package com.sep.repositories;

import com.sep.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);

    @Query( "select o from Role o where name in :names" )
    Set<Role> findByNames(@Param("names") List<String> namesList);
}