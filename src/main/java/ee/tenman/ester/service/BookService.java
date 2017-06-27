package ee.tenman.ester.service;

import ee.tenman.ester.domain.Book;
import ee.tenman.ester.repository.BookRepository;
import ee.tenman.ester.repository.UserRepository;
import ee.tenman.ester.service.dto.BookDTO;
import ee.tenman.ester.service.mapper.BookMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import static ee.tenman.ester.security.SecurityUtils.getCurrentUserLogin;


/**
 * Service Implementation for managing Book.
 */
@Service
@Transactional
public class BookService {

    private final Logger log = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    @Inject
    private UserRepository userRepository;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    /**
     * Save a book.
     *
     * @param bookDTO the entity to save
     * @return the persisted entity
     */
    public BookDTO save(BookDTO bookDTO) {
        log.debug("Request to save Book : {}", bookDTO);
        Book book = bookMapper.toEntity(bookDTO);
        book.setUser(userRepository.findOneByLogin(getCurrentUserLogin()).get());
        book = bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    /**
     *  Get all the books.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BookDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Books");
        return bookRepository.findAllByUserIsCurrentUser(pageable).map(bookMapper::toDto);
    }

    /**
     *  Get one book by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public BookDTO findOne(Long id) {
        log.debug("Request to get Book : {}", id);
        Book book = bookRepository.findOneWithEagerRelationships(id);
        return bookMapper.toDto(book);
    }

    /**
     *  Delete the  book by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Book : {}", id);
        bookRepository.delete(id);
    }
}
