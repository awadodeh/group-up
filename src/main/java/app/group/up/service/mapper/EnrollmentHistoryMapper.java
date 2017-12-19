package app.group.up.service.mapper;

import app.group.up.domain.*;
import app.group.up.service.dto.EnrollmentHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EnrollmentHistory and its DTO EnrollmentHistoryDTO.
 */
@Mapper(componentModel = "spring", uses = {CircleMapper.class, PersonMapper.class, })
public interface EnrollmentHistoryMapper extends EntityMapper <EnrollmentHistoryDTO, EnrollmentHistory> {

    @Mapping(source = "circle.id", target = "circleId")

    @Mapping(source = "person.id", target = "personId")
    EnrollmentHistoryDTO toDto(EnrollmentHistory enrollmentHistory); 

    @Mapping(source = "circleId", target = "circle")

    @Mapping(source = "personId", target = "person")
    EnrollmentHistory toEntity(EnrollmentHistoryDTO enrollmentHistoryDTO); 
    default EnrollmentHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        EnrollmentHistory enrollmentHistory = new EnrollmentHistory();
        enrollmentHistory.setId(id);
        return enrollmentHistory;
    }
}
