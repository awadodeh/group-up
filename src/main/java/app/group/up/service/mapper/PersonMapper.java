package app.group.up.service.mapper;

import app.group.up.domain.*;
import app.group.up.service.dto.PersonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Person and its DTO PersonDTO.
 */
@Mapper(componentModel = "spring", uses = {AddressMapper.class, })
public interface PersonMapper extends EntityMapper <PersonDTO, Person> {

    @Mapping(source = "address.id", target = "addressId")
    PersonDTO toDto(Person person);

    @Mapping(source = "addressId", target = "address")
    @Mapping(target = "payments", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    Person toEntity(PersonDTO personDTO);
    default Person fromId(Long id) {
        if (id == null) {
            return null;
        }
        Person person = new Person();
        person.setId(id);
        return person;
    }
}
