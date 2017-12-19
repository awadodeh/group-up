package app.group.up.service.mapper;

import app.group.up.domain.*;
import app.group.up.service.dto.ReviewDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Review and its DTO ReviewDTO.
 */
@Mapper(componentModel = "spring", uses = {PersonMapper.class, })
public interface ReviewMapper extends EntityMapper <ReviewDTO, Review> {

    @Mapping(source = "person.id", target = "personId")
    ReviewDTO toDto(Review review); 

    @Mapping(source = "personId", target = "person")
    Review toEntity(ReviewDTO reviewDTO); 
    default Review fromId(Long id) {
        if (id == null) {
            return null;
        }
        Review review = new Review();
        review.setId(id);
        return review;
    }
}
