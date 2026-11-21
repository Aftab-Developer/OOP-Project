package com.oop_project_group.library_management_pro2;

import com.oop_project_group.library_management_pro2.Services.*;
import com.oop_project_group.library_management_pro2.entities.Author;
import com.oop_project_group.library_management_pro2.entities.Book;
import com.oop_project_group.library_management_pro2.entities.Student;
import com.oop_project_group.library_management_pro2.entities.BookAssignments;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
@RequiredArgsConstructor
public class LibraryManagementPro2Application implements CommandLineRunner {

	private final AuthorService authorService;
	private final BookService bookService;
	private final StudentService studentService;
	private final BookAssignmentService assignmentService;

	private final Scanner sc = new Scanner(System.in);

	// ==========================================
	//              PENALTY RATE
	// ==========================================

	private enum PenaltyRate {
		PER_DAY(50.0);

		private final double amount;

		PenaltyRate(double amount) {
			this.amount = amount;
		}

		public double getAmount() {
			return amount;
		}
	}

	// ==========================================
	//           PENALTY CALCULATION
	// ==========================================

	private long calculateLateDays(BookAssignments assignment) {

		if (assignment == null || assignment.getToDate() == null) {
			return 0;
		}

		// Completed assignment ki actual return date store nahi hoti.
		// Is liye penalty sirf pending assignments par calculate hogi.
		if (Boolean.TRUE.equals(assignment.getIsCompleted())) {
			return 0;
		}

		LocalDate dueDate = assignment.getToDate().toLocalDate();
		LocalDate today = LocalDate.now();

		long days = ChronoUnit.DAYS.between(dueDate, today);
		return Math.max(0, days);
	}

	private double calculatePenalty(BookAssignments assignment) {
		return calculateLateDays(assignment) * PenaltyRate.PER_DAY.getAmount();
	}

	// ==========================================
	//                MAIN METHOD
	// ==========================================

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementPro2Application.class, args);
	}

	@Override
	public void run(String... args) {

		System.out.println("\n");
		System.out.println("==============================================");
		System.out.println("          LIBRARY MANAGEMENT SYSTEM");
		System.out.println("==============================================");

		mainMenu();
	}

	// ==========================================
	//              MAIN MENU
	// ==========================================

	private void mainMenu() {

		boolean running = true;

		while (running) {

			System.out.println("\n==============================================");
			System.out.println("                 Library Managment System MAIN MENU");
			System.out.println("==============================================");

			System.out.println("  1. Author Management");
			System.out.println("  2. Book Management");
			System.out.println("  3. Student Management");
			System.out.println("  4. Book Assignment Management");
			System.out.println("  0. Exit Application");

			System.out.println("----------------------------------------------");

			int choice = readInt("Enter your choice: ");

			switch (choice) {

				case 1 -> authorMenu();

				case 2 -> bookMenu();

				case 3 -> studentMenu();

				case 4 -> assignmentMenu();

				case 0 -> {
					running = false;
					System.out.println("\nThank you for using Library Management System!");
				}

				default -> System.out.println("Invalid choice! Try again.");
			}
		}

		sc.close();
	}

	// ==========================================
	//              AUTHOR MENU
	// ==========================================

	private void authorMenu() {

		boolean running = true;

		while (running) {

			System.out.println("\n========== AUTHOR MANAGEMENT ==========");
			System.out.println("1. Create Author");
			System.out.println("2. Update Author");
			System.out.println("3. Get Author By ID");
			System.out.println("4. Get Author By Name");
			System.out.println("5. Delete Author");
			System.out.println("0. Back To Main Menu");

			int choice = readInt("Enter choice: ");

			switch (choice) {

				case 1 -> createAuthor();

				case 2 -> updateAuthor();

				case 3 -> getAuthorById();

				case 4 -> getAuthorByName();

				case 5 -> deleteAuthor();

				case 0 -> running = false;

				default -> System.out.println("Invalid choice!");
			}
		}
	}

	private void createAuthor() {

		System.out.println("\n========== CREATE AUTHOR ==========");

		String name = readString("Enter Author Name: ");
		String description = readString("Enter Author Description: ");
		String rating = readString("Enter Author Rating: ");

		Author author = authorService.createAuthor(name, description, rating);

		if (author != null) {
			System.out.println("Author Created Successfully!");
			showAuthor(author);
		} else {
			System.out.println("Unable to create Author.");
		}
	}

	private void updateAuthor() {

		System.out.println("\n========== UPDATE AUTHOR ==========");

		Long id = readLong("Enter Author ID: ");

		String name = readString("Enter New Name: ");
		String description = readString("Enter New Description: ");
		String rating = readString("Enter New Rating: ");

		Author author = authorService.updateAuthor(
				name, description, rating, id
		);

		if (author != null) {
			System.out.println("Author Updated Successfully!");
			showAuthor(author);
		} else {
			System.out.println("Author Not Found!");
		}
	}

	private void getAuthorById() {

		Long id = readLong("Enter Author ID: ");

		Author author = authorService.getAuthor(id);

		if (author != null) {
			showAuthor(author);
		} else {
			System.out.println("Author Not Found!");
		}
	}

	private void getAuthorByName() {

		String name = readString("Enter Author Name: ");

		Author author = authorService.getAuthor(name);

		if (author != null) {
			showAuthor(author);
		} else {
			System.out.println("Author Not Found!");
		}
	}

	private void deleteAuthor() {

		Long id = readLong("Enter Author ID: ");

		Author author = authorService.deleteAuthor(id);

		if (author != null) {
			System.out.println("Author Deleted Successfully!");
			showAuthor(author);
		} else {
			System.out.println("Author Not Found!");
		}
	}

	// ==========================================
	//               BOOK MENU
	// ==========================================

	private void bookMenu() {

		boolean running = true;

		while (running) {

			System.out.println("\n========== BOOK MANAGEMENT ==========");
			System.out.println("1. Create Book");
			System.out.println("2. Update Book");
			System.out.println("3. Get Book By ID");
			System.out.println("4. Get All Books");
			System.out.println("5. Delete Book");
			System.out.println("0. Back To Main Menu");

			int choice = readInt("Enter choice: ");

			switch (choice) {

				case 1 -> createBook();

				case 2 -> updateBook();

				case 3 -> getBook();

				case 4 -> getAllBooks();

				case 5 -> deleteBook();

				case 0 -> running = false;

				default -> System.out.println("Invalid choice!");
			}
		}
	}

	private void createBook() {

		System.out.println("\n========== CREATE BOOK ==========");

		String title = readString("Enter Book Title: ");
		String description = readString("Enter Book Description: ");
		Long authorId = readLong("Enter Author ID: ");

		Book book = bookService.createBook(title, description, authorId);

		if (book != null) {
			System.out.println("Book Created Successfully!");
			showBook(book);
		} else {
			System.out.println("Author Not Found! Book Not Created.");
		}
	}

	private void updateBook() {

		System.out.println("\n========== UPDATE BOOK ==========");

		Long bookId = readLong("Enter Book ID: ");
		String title = readString("Enter New Title: ");
		String description = readString("Enter New Description: ");
		Long authorId = readLong("Enter New Author ID: ");

		Book book = bookService.updateBook(
				title, description, authorId, bookId
		);

		if (book != null) {
			System.out.println("Book Updated Successfully!");
			showBook(book);
		} else {
			System.out.println("Book or Author Not Found!");
		}
	}

	private void getBook() {

		Long id = readLong("Enter Book ID: ");

		Book book = bookService.getBook(id);

		if (book != null) {
			showBook(book);
		} else {
			System.out.println("Book Not Found!");
		}
	}

	private void getAllBooks() {

		List<Book> books = bookService.getAllBooks();

		if (books == null || books.isEmpty()) {
			System.out.println("No Books Found!");
			return;
		}

		System.out.println("\n========== ALL BOOKS ==========");

		for (Book book : books) {
			showBook(book);
		}
	}

	private void deleteBook() {

		Long id = readLong("Enter Book ID: ");

		Book book = bookService.deleteBook(id);

		if (book != null) {
			System.out.println("Book Deleted Successfully!");
			showBook(book);
		} else {
			System.out.println("Book Not Found!");
		}
	}

	// ==========================================
	//              STUDENT MENU
	// ==========================================

	private void studentMenu() {

		boolean running = true;

		while (running) {

			System.out.println("\n========== STUDENT MANAGEMENT ==========");
			System.out.println("1. Create Student");
			System.out.println("2. Update Student");
			System.out.println("3. Get Student By CMS ID");
			System.out.println("4. Get All Students");
			System.out.println("5. Delete Student");
			System.out.println("0. Back To Main Menu");

			int choice = readInt("Enter choice: ");

			switch (choice) {

				case 1 -> createStudent();

				case 2 -> updateStudent();

				case 3 -> getStudent();

				case 4 -> getAllStudents();

				case 5 -> deleteStudent();

				case 0 -> running = false;

				default -> System.out.println("Invalid choice!");
			}
		}
	}

	private void createStudent() {

		System.out.println("\n========== CREATE STUDENT ==========");

		String cms = readString("Enter CMS ID: ");
		String name = readString("Enter Student Name: ");
		String email = readString("Enter Email: ");
		String department = readString("Enter Department: ");
		String number = readString("Enter Phone Number: ");

		Student student = studentService.createStudent(
				cms, name, email, department, number
		);

		if (student != null) {
			System.out.println("Student Created Successfully!");
			showStudent(student);
		} else {
			System.out.println("Unable to Create Student.");
		}
	}

	private void updateStudent() {

		System.out.println("\n========== UPDATE STUDENT ==========");

		String cms = readString("Enter CMS ID: ");
		String name = readString("Enter New Name: ");
		String email = readString("Enter New Email: ");
		String department = readString("Enter New Department: ");
		String number = readString("Enter New Phone Number: ");

		Student student = studentService.updateStudent(
				name, email, department, number, cms
		);

		if (student != null) {
			System.out.println("Student Updated Successfully!");
			showStudent(student);
		} else {
			System.out.println("Student Not Found!");
		}
	}

	private void getStudent() {

		String cms = readString("Enter CMS ID: ");

		Student student = studentService.getStudent(cms);

		if (student != null) {
			showStudent(student);
		} else {
			System.out.println("Student Not Found!");
		}
	}

	private void getAllStudents() {

		List<Student> students = studentService.getAllStudents();

		if (students == null || students.isEmpty()) {
			System.out.println("No Students Found!");
			return;
		}

		System.out.println("\n========== ALL STUDENTS ==========");

		for (Student student : students) {
			showStudent(student);
		}
	}

	private void deleteStudent() {

		String cms = readString("Enter CMS ID: ");

		Student student = studentService.deleteStudent(cms);

		if (student != null) {
			System.out.println("Student Deleted Successfully!");
			showStudent(student);
		} else {
			System.out.println("Student Not Found!");
		}
	}

	// ==========================================
	//           ASSIGNMENT MENU
	// ==========================================

	private void assignmentMenu() {

		boolean running = true;

		while (running) {

			System.out.println("\n======= BOOK ASSIGNMENT MANAGEMENT =======");
			System.out.println("1. Assign Book To Student");
			System.out.println("2. Update Assignment");
			System.out.println("3. Get Assignment By ID");
			System.out.println("4. Get All Assignments");
			System.out.println("5. Delete Assignment");
			System.out.println("0. Back To Main Menu");

			int choice = readInt("Enter choice: ");

			switch (choice) {

				case 1 -> createAssignment();

				case 2 -> updateAssignment();

				case 3 -> getAssignment();

				case 4 -> getAllAssignments();

				case 5 -> deleteAssignment();

				case 0 -> running = false;

				default -> System.out.println("Invalid choice!");
			}
		}
	}

	private void createAssignment() {

		System.out.println("\n========== CREATE ASSIGNMENT ==========");

		Long bookId = readLong("Enter Book ID: ");
		String cms = readString("Enter Student CMS ID: ");

		Date fromDate = readDate("Enter From Date (yyyy-MM-dd): ");
		Date toDate = readDate("Enter To Date (yyyy-MM-dd): ");

		Boolean completed = readBoolean("Is Assignment Completed?");

		BookAssignments assignment = assignmentService.createAssignment(
				bookId, cms, fromDate, toDate, completed
		);

		if (assignment != null) {
			System.out.println("Book Assigned Successfully!");
			showAssignment(assignment);
		} else {
			System.out.println("Book or Student Not Found!");
		}
	}

	private void updateAssignment() {

		System.out.println("\n========== UPDATE ASSIGNMENT ==========");

		Long assignmentId = readLong("Enter Assignment ID: ");
		Long bookId = readLong("Enter New Book ID: ");
		String cms = readString("Enter New Student CMS ID: ");

		Date fromDate = readDate("Enter New From Date (yyyy-MM-dd): ");
		Date toDate = readDate("Enter New To Date (yyyy-MM-dd): ");

		Boolean completed = readBoolean("Is Assignment Completed?");

		BookAssignments assignment = assignmentService.updateAssignment(
				bookId, cms, fromDate, toDate, completed, assignmentId
		);

		if (assignment != null) {
			System.out.println("Assignment Updated Successfully!");
			showAssignment(assignment);
		} else {
			System.out.println("Assignment, Book or Student Not Found!");
		}
	}

	private void getAssignment() {

		Long id = readLong("Enter Assignment ID: ");

		BookAssignments assignment = assignmentService.getAssignment(id);

		if (assignment != null) {
			showAssignment(assignment);
		} else {
			System.out.println("Assignment Not Found!");
		}
	}

	private void getAllAssignments() {

		List<BookAssignments> assignments =
				assignmentService.getAllAssignments();

		if (assignments == null || assignments.isEmpty()) {
			System.out.println("No Assignments Found!");
			return;
		}

		System.out.println("\n========== ALL ASSIGNMENTS ==========");

		for (BookAssignments assignment : assignments) {
			showAssignment(assignment);
		}
	}

	private void deleteAssignment() {

		Long id = readLong("Enter Assignment ID: ");

		BookAssignments assignment = assignmentService.deleteAssignment(id);

		if (assignment != null) {
			System.out.println("Assignment Deleted Successfully!");
			showAssignment(assignment);
		} else {
			System.out.println("Assignment Not Found!");
		}
	}

	// ==========================================
	//             DISPLAY METHODS
	// ==========================================

	private void showAuthor(Author author) {

		System.out.println("\n========== AUTHOR DETAILS ==========");
		System.out.println("Author ID      : " + author.getId());
		System.out.println("Author Name    : " + author.getAuthor_name());
		System.out.println("Description    : " + author.getAuthor_description());
		System.out.println("Rating         : " + author.getRating());
		System.out.println("====================================");
	}

	private void showBook(Book book) {

		System.out.println("\n========== BOOK DETAILS ==========");
		System.out.println("Book Title     : " + book.getBook_title());
		System.out.println("Description    : " + book.getBook_des());

		if (book.getAuthor() != null) {
			System.out.println("Author Name    : " + book.getAuthor().getAuthor_name());
		}

		System.out.println("==================================");
	}

	private void showStudent(Student student) {

		System.out.println("\n========== STUDENT DETAILS ==========");
		System.out.println("CMS ID         : " + student.getCmsId());
		System.out.println("Name           : " + student.getStd_name());
		System.out.println("Email          : " + student.getStd_email());
		System.out.println("Department     : " + student.getStd_dep());
		System.out.println("Phone          : " + student.getStd_number());
		System.out.println("=====================================");
	}

	private void showAssignment(BookAssignments assignment) {

		System.out.println("\n========== ASSIGNMENT DETAILS ==========");
		System.out.println("Assignment ID  : " + assignment.getAssignmentId());

		if (assignment.getBook() != null) {
			System.out.println("Book Title     : " + assignment.getBook().getBook_title());
		}

		if (assignment.getStd() != null) {
			System.out.println("Student CMS ID : " + assignment.getStd().getCmsId());
		}

		System.out.println("From Date      : " + assignment.getFromDate());
		System.out.println("To Date        : " + assignment.getToDate());
		System.out.println("Completed      : " + assignment.getIsCompleted());

		if (Boolean.TRUE.equals(assignment.getIsCompleted())) {

			System.out.println("Late Days      : N/A");
			System.out.println("Penalty        : N/A (actual return date is not stored)");

		} else {

			long lateDays = calculateLateDays(assignment);
			double penalty = calculatePenalty(assignment);

			System.out.println("Late Days      : " + lateDays);
			System.out.println("Daily Fine     : Rs. " + PenaltyRate.PER_DAY.getAmount());
			System.out.println("Total Penalty  : Rs. " + penalty);

			if (lateDays > 0) {
				System.out.println("Status         : OVERDUE");
			} else {
				System.out.println("Status         : ON TIME");
			}
		}

		System.out.println("========================================");
	}

	// ==========================================
	//             INPUT HELPERS
	// ==========================================

	private String readString(String message) {

		while (true) {

			System.out.print(message);

			String value = sc.nextLine().trim();

			if (!value.isEmpty()) {
				return value;
			}

			System.out.println("Input cannot be empty! Try again.");
		}
	}

	private int readInt(String message) {

		while (true) {

			System.out.print(message);

			try {
				return Integer.parseInt(sc.nextLine().trim());
			} catch (NumberFormatException e) {
				System.out.println("Please enter a valid integer!");
			}
		}
	}

	private Long readLong(String message) {

		while (true) {

			System.out.print(message);

			try {
				return Long.parseLong(sc.nextLine().trim());
			} catch (NumberFormatException e) {
				System.out.println("Please enter a valid Long value!");
			}
		}
	}

	private Date readDate(String message) {

		while (true) {

			System.out.print(message);

			try {
				return Date.valueOf(sc.nextLine().trim());
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid date! Use yyyy-MM-dd format.");
			}
		}
	}

	private Boolean readBoolean(String message) {

		while (true) {

			System.out.println(message);
			System.out.println("1. Yes");
			System.out.println("2. No");

			int choice = readInt("Enter choice: ");

			if (choice == 1) {
				return true;
			} else if (choice == 2) {
				return false;
			}

			System.out.println("Invalid choice! Enter 1 or 2.");
		}
	}
}