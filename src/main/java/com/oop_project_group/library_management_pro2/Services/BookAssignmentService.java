
package com.oop_project_group.library_management_pro2.Services;

import com.oop_project_group.library_management_pro2.entities.BookAssignments;
import com.oop_project_group.library_management_pro2.entities.Book;
import com.oop_project_group.library_management_pro2.entities.Student;
import com.oop_project_group.library_management_pro2.repositries.BookAssignmentRepositry;
import com.oop_project_group.library_management_pro2.repositries.BookRepositry;
import com.oop_project_group.library_management_pro2.repositries.StudentRepositry;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookAssignmentService {

    private final BookAssignmentRepositry bookAssignmentsRepositry;
    private final BookRepositry bookRepositry;
    private final StudentRepositry studentRepositry;

    // CREATE ASSIGNMENT
    public BookAssignments createAssignment(
            Long book_id,
            String cms_id,
            Date fromDate,
            Date toDate,
            Boolean isCompleted
    ) {

        Book book = bookRepositry.findById(book_id).orElse(null);
        Student student = studentRepositry.findByCmsId(cms_id).orElse(null);

        if (book != null && student != null) {

            BookAssignments assignment = new BookAssignments();

            assignment.setBook(book);
            assignment.setStd(student);
            assignment.setFromDate(fromDate);
            assignment.setToDate(toDate);
            assignment.setIsCompleted(isCompleted);

            return bookAssignmentsRepositry.save(assignment);

        } else {
            return null;
        }
    }

    // UPDATE ASSIGNMENT
    public BookAssignments updateAssignment(
            Long book_id,
            String cms_id,
            Date fromDate,
            Date toDate,
            Boolean isCompleted,
            Long assignmentId
    ) {

        Optional<BookAssignments> assignment =
                bookAssignmentsRepositry.findById(assignmentId);

        if (assignment.isPresent()) {

            Book book = bookRepositry.findById(book_id).orElse(null);
            Student student = studentRepositry.findByCmsId(cms_id).orElse(null);

            if (book == null || student == null) {
                return null;
            }

            BookAssignments findedAssignment = assignment.get();

            findedAssignment.setBook(book);
            findedAssignment.setStd(student);
            findedAssignment.setFromDate(fromDate);
            findedAssignment.setToDate(toDate);
            findedAssignment.setIsCompleted(isCompleted);

            bookAssignmentsRepositry.save(findedAssignment);

            return findedAssignment;

        } else {
            return null;
        }
    }

    // GET ASSIGNMENT BY ID
    public BookAssignments getAssignment(Long assignmentId) {

        Optional<BookAssignments> assignment =
                bookAssignmentsRepositry.findById(assignmentId);

        return assignment.orElse(null);
    }

    // GET ALL ASSIGNMENTS
    public List<BookAssignments> getAllAssignments() {

        return bookAssignmentsRepositry.findAll();
    }

    // DELETE ASSIGNMENT
    public BookAssignments deleteAssignment(Long assignmentId) {

        Optional<BookAssignments> assignment =
                bookAssignmentsRepositry.findById(assignmentId);

        if (assignment.isPresent()) {

            BookAssignments findedAssignment = assignment.get();

            bookAssignmentsRepositry.delete(findedAssignment);

            return findedAssignment;

        } else {
            return null;
        }
    }
}
