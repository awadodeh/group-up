package app.group.up.service.mapper;

import app.group.up.domain.*;
import app.group.up.service.dto.CircleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Circle and its DTO CircleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CircleMapper extends EntityMapper <CircleDTO, Circle> {

    @Mapping(target = "enrollments", ignore = true)
    Circle toEntity(CircleDTO circleDTO);
    default Circle fromId(Long id) {
        if (id == null) {
            return null;
        }
        Circle circle = new Circle();
        circle.setId(id);
        return circle;
    }
}
