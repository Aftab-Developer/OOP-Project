package com.oop_project_group.library_management_pro2.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "book_assignmnet_tbl")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookAssignments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assignmentId;
    @OneToOne
    private Book book  ;
    @OneToOne
    private Student std ;
    private Date fromDate ;
    private Boolean isCompleted ;
    private Date toDate ;
}
