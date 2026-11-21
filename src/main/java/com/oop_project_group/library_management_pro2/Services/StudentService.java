
package com.oop_project_group.library_management_pro2.Services;

import com.oop_project_group.library_management_pro2.entities.Student;
import com.oop_project_group.library_management_pro2.repositries.StudentRepositry;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepositry studentRepositry;

    // CREATE STUDENT
    public Student createStudent(
            String cms_id,
            String std_name,
            String std_email,
            String std_dep,
            String std_number
    ) {

        Student student = new Student();

        student.setCmsId(cms_id);
        student.setStd_name(std_name);
        student.setStd_email(std_email);
        student.setStd_dep(std_dep);
        student.setStd_number(std_number);

        return studentRepositry.save(student);
    }

    // UPDATE STUDENT
    public Student updateStudent(
            String std_name,
            String std_email,
            String std_dep,
            String std_number,
            String cms_id
    ) {

        Optional<Student> student = studentRepositry.findByCmsId(cms_id);

        if (student.isPresent()) {

            Student findedStudent = student.get();

            findedStudent.setStd_name(std_name);
            findedStudent.setStd_email(std_email);
            findedStudent.setStd_dep(std_dep);
            findedStudent.setStd_number(std_number);

            studentRepositry.save(findedStudent);

            return findedStudent;

        } else {
            return null;
        }
    }

    // GET STUDENT BY CMS ID
    public Student getStudent(String cms_id) {

        Optional<Student> foundStudent =
                studentRepositry.findByCmsId((cms_id));

        return foundStudent.orElse(null);
    }

    // GET ALL STUDENTS
    public List<Student> getAllStudents() {

        return studentRepositry.findAll();
    }

    // DELETE STUDENT
    public Student deleteStudent(String cms_id) {

        Optional<Student> student =
                studentRepositry.findByCmsId(cms_id);

        if (student.isPresent()) {

            Student findedStudent = student.get();

            studentRepositry.delete(findedStudent);

            return findedStudent;

        } else {
            return null;
        }
    }
}
