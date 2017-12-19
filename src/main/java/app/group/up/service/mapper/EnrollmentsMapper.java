package app.group.up.service.mapper;

import app.group.up.domain.*;
import app.group.up.service.dto.EnrollmentsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Enrollments and its DTO EnrollmentsDTO.
 */
@Mapper(componentModel = "spring", uses = {CircleMapper.class, PersonMapper.class, })
public interface EnrollmentsMapper extends EntityMapper <EnrollmentsDTO, Enrollments> {

    @Mapping(source = "circle.id", target = "circleId")

    @Mapping(source = "person.id", target = "personId")
    EnrollmentsDTO toDto(Enrollments enrollments); 

    @Mapping(source = "circleId", target = "circle")

    @Mapping(source = "personId", target = "person")
    Enrollments toEntity(EnrollmentsDTO enrollmentsDTO); 
    default Enrollments fromId(Long id) {
        if (id == null) {
            return null;
        }
        Enrollments enrollments = new Enrollments();
        enrollments.setId(id);
        return enrollments;
    }
}
