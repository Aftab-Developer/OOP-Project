package com.oop_project_group.library_management_pro2.Services;

import com.oop_project_group.library_management_pro2.entities.Author;
import com.oop_project_group.library_management_pro2.repositries.AuthorRepositry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service


@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepositry authorRepositry ;

    public Author createAuthor(
                               String author_name ,
                               String author_des ,
                               String author_rating
    ) {
       Author author = Author.builder()
                .author_name(author_name)
                .author_description(author_des)
                .rating(author_rating)
                .books(new ArrayList<>())
                 .build();
      return  authorRepositry.save(author) ;


    }

    public Author updateAuthor(
            String author_name ,
            String author_des ,
            String author_rating ,
            Long author_id
    ) {

        Optional<Author> author = authorRepositry.findById(author_id);
        if( author.isPresent()) {
            Author findedAuthor = author.get();
            findedAuthor.
                    setAuthor_description(author_des)  ;
            findedAuthor.
                    setAuthor_name(author_name);
            findedAuthor.
                    setRating(author_rating);
            authorRepositry.save(findedAuthor) ;
            return findedAuthor ;
        } else {
            return null ;
        }





    }


    public Author getAuthor(Long id) {
        Optional<Author> foundAuthor = authorRepositry.findById(id);
        return foundAuthor.orElse(null);
    }
    public Author getAuthor(String name) {
        Optional<Author> foundAuthor = authorRepositry.findByAuthorName(name);
        return foundAuthor.orElse(null);
    }

    public Author deleteAuthor(Long author_id) {

        Optional<Author> author = authorRepositry.findById(author_id);

        if (author.isPresent()) {

            Author findedAuthor = author.get();

            authorRepositry.delete(findedAuthor);

            return findedAuthor;

        } else {
            return null;
        }
    }



}
