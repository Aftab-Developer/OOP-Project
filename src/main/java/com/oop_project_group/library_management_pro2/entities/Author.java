package com.oop_project_group.library_management_pro2.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Entity
@Table(name="author_tbl")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Column(length = 20 , nullable = false , unique = true)
    private String author_name   ;
    @Column(nullable = false , length = 78)
    private String author_description   ;
    private String rating  ;
    @OneToMany(mappedBy = "author" , cascade = CascadeType.ALL)
    private ArrayList<Book> books  = new ArrayList<>();

}
