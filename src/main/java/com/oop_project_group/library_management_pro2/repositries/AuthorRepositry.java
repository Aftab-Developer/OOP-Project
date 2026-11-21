package com.oop_project_group.library_management_pro2.repositries;

import com.oop_project_group.library_management_pro2.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AuthorRepositry extends JpaRepository<Author , Long> { 
    @Query(
             value = "select * from author_tbl where author_name = :name" ,
            nativeQuery = true
    )
    Optional<Author> findByAuthorName(@Param("name") String name); 
}
