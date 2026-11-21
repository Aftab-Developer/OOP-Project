package com.oop_project_group.library_management_pro2.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="book_tbl")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long book_id ;
    @Column(unique = true)
    @Nonnull
    private String  book_title ;
    @Nonnull
    @Column(length = 75 )
    private String  book_des ;
    @Nonnull
    private LocalDateTime reached_at  ;
    @Nonnull
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author ;
    @Column(nullable = false)
    private Boolean isAvailable ;

}
