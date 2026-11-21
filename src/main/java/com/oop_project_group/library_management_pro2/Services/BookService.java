
package com.oop_project_group.library_management_pro2.Services;

import com.oop_project_group.library_management_pro2.entities.Book;
import com.oop_project_group.library_management_pro2.entities.Author;
import com.oop_project_group.library_management_pro2.repositries.BookRepositry;
import com.oop_project_group.library_management_pro2.repositries.AuthorRepositry;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepositry bookRepositry;
    private final AuthorRepositry authorRepositry;

    // CREATE BOOK
    public Book createBook(
            String book_title,
            String book_description,
            Long author_id
    ) {

        Author author = authorRepositry.findById(author_id).orElse(null);

        if (author == null) {
            return null;
        }

        Book book = Book.builder()
                .book_title(book_title)
                .book_des(book_description)
                .author(author)
                .build();

        if (author.getBooks() == null) {
            author.setBooks(new ArrayList<>());
        }

        author.getBooks().add(book);

        authorRepositry.save(author);

        return book;
    }

    // UPDATE BOOK
    public Book updateBook(
            String book_title,
            String book_description,
            Long author_id,
            Long book_id
    ) {

        Book findedBook = bookRepositry.findById(book_id).orElse(null);

        if (findedBook == null) {
            return null;
        }

        Author newAuthor = authorRepositry.findById(author_id).orElse(null);

        if (newAuthor == null) {
            return null;
        }

        Author oldAuthor = findedBook.getAuthor();

        findedBook.setBook_title(book_title);
        findedBook.setBook_des(book_description);

        // Remove from old Author if author is changing
        if (!oldAuthor.getId().equals(author_id)) {

            oldAuthor.getBooks().remove(findedBook);

        }

        // Assign new Author
        findedBook.setAuthor(newAuthor);

        if (newAuthor.getBooks() == null) {
            newAuthor.setBooks(new ArrayList<>());
        }

        if (!newAuthor.getBooks().contains(findedBook)) {
            newAuthor.getBooks().add(findedBook);
        }

        // Save using Author cascade
        if (!oldAuthor.getId().equals(author_id)) {
            authorRepositry.save(oldAuthor);
        }

        authorRepositry.save(newAuthor);

        return findedBook;
    }

    // GET BOOK BY ID
    public Book getBook(Long id) {
        return bookRepositry.findById(id).orElse(null);
    }

    // GET ALL BOOKS
    public List<Book> getAllBooks() {
        return bookRepositry.findAll();
    }

    // DELETE BOOK
    public Book deleteBook(Long id) {

        Book findedBook = bookRepositry.findById(id).orElse(null);

        if (findedBook == null) {
            return null;
        }

        Author author = findedBook.getAuthor();

        if (author.getBooks() != null) {
            author.getBooks().remove(findedBook);
            authorRepositry.save(author);
        }

        bookRepositry.delete(findedBook);

        return findedBook;
    }
}
