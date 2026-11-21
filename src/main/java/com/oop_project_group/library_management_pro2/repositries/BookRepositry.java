package com.oop_project_group.library_management_pro2.repositries;

import com.oop_project_group.library_management_pro2.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepositry extends JpaRepository<Book, Long> {
}
