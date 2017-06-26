package ee.tenman.ester.service.mapper;

import ee.tenman.ester.domain.*;
import ee.tenman.ester.service.dto.BookDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Book and its DTO BookDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, LibraryMapper.class, })
public interface BookMapper extends EntityMapper <BookDTO, Book> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    BookDTO toDto(Book book); 

    @Mapping(source = "userId", target = "user")
    Book toEntity(BookDTO bookDTO); 
    default Book fromId(Long id) {
        if (id == null) {
            return null;
        }
        Book book = new Book();
        book.setId(id);
        return book;
    }
}
