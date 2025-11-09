package br.pucpr.projetobackend.service;

import br.pucpr.projetobackend.model.Author;
import br.pucpr.projetobackend.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author();
        author.setId(1);
        author.setName("Christopher Nolan");
        author.setBio("British-American filmmaker");
    }

    @Test
    void testSaveAuthor_Success() {
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        Author result = authorService.save(author);

        assertNotNull(result);
        assertEquals("Christopher Nolan", result.getName());
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void testGetAllAuthors_Success() {
        List<Author> authors = Arrays.asList(author);
        when(authorRepository.findAll()).thenReturn(authors);

        List<Author> result = authorService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Christopher Nolan", result.get(0).getName());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void testGetAuthorById_Success() {
        when(authorRepository.findById(1)).thenReturn(Optional.of(author));

        Author result = authorService.getId(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Christopher Nolan", result.getName());
        verify(authorRepository, times(1)).findById(1);
    }

    @Test
    void testGetAuthorById_NotFound() {
        when(authorRepository.findById(999)).thenReturn(Optional.empty());

        Author result = authorService.getId(999);

        assertNull(result);
        verify(authorRepository, times(1)).findById(999);
    }

    @Test
    void testUpdateAuthor_Success() {
        Author updatedAuthor = new Author();
        updatedAuthor.setId(1);
        updatedAuthor.setName("Christopher Nolan Updated");
        updatedAuthor.setBio("Updated bio");

        when(authorRepository.findById(1)).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        Author result = authorService.update(updatedAuthor);

        assertNotNull(result);
        verify(authorRepository, times(1)).findById(1);
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    void testUpdateAuthor_NotFound() {
        Author updatedAuthor = new Author();
        updatedAuthor.setId(999);

        when(authorRepository.findById(999)).thenReturn(Optional.empty());

        Author result = authorService.update(updatedAuthor);

        assertNull(result);
        verify(authorRepository, times(1)).findById(999);
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    void testDeleteAuthor_Success() {
        doNothing().when(authorRepository).deleteById(1);

        authorService.delete(1);

        verify(authorRepository, times(1)).deleteById(1);
    }
}

