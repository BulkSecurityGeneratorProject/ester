package ee.tenman.ester.repository;

import ee.tenman.ester.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Book entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    @Query("select book from Book book where book.user.login = ?#{principal.username}")
    Page<Book> findAllByUserIsCurrentUser(Pageable pageable);

    @Query("select distinct book from Book book left join fetch book.libraries")
    List<Book> findAllWithEagerRelationships();

    @Query("select book from Book book left join fetch book.libraries where book.id =:id")
    Book findOneWithEagerRelationships(@Param("id") Long id);

}
