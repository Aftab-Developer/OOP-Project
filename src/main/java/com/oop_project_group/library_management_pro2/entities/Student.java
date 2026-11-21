package com.oop_project_group.library_management_pro2.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "student_tbl")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {
    @Id
    private String cmsId ;
    @Column(nullable = false)
    private String std_name ;
    @Column(nullable = false , unique = true)
    private String std_email ;
    @Column(nullable = false)
    private String std_dep ;
    @Column(length = 11)
    @Pattern(
            regexp = "^03[0-9]{9}$",
            message = "Invalid Pakistani phone number. Example: 03174731990"
    )
    private String std_number  ;
}
