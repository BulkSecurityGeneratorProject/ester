package ee.tenman.ester.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Library entity.
 */
public class LibraryDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String address;

    @NotNull
    @Size(min = 7)
    private String contact;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LibraryDTO libraryDTO = (LibraryDTO) o;
        if(libraryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), libraryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LibraryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", contact='" + getContact() + "'" +
            "}";
    }
}
