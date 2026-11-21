package com.oop_project_group.library_management_pro2.repositries;

import com.oop_project_group.library_management_pro2.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepositry extends JpaRepository<Student , Long> {
    Optional<Student> findByCmsId(String cmsId);
}
