package app.group.up.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Circle entity.
 */
public class CircleDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3)
    private String circleName;

    @NotNull
    private Long circleWorth;

    @NotNull
    private ZonedDateTime startDate;

    @NotNull
    private ZonedDateTime endDate;

    @NotNull
    private Integer numberOfMembers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public Long getCircleWorth() {
        return circleWorth;
    }

    public void setCircleWorth(Long circleWorth) {
        this.circleWorth = circleWorth;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getNumberOfMembers() {
        return numberOfMembers;
    }

    public void setNumberOfMembers(Integer numberOfMembers) {
        this.numberOfMembers = numberOfMembers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CircleDTO circleDTO = (CircleDTO) o;
        if(circleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), circleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CircleDTO{" +
            "id=" + getId() +
            ", circleName='" + getCircleName() + "'" +
            ", circleWorth='" + getCircleWorth() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", numberOfMembers='" + getNumberOfMembers() + "'" +
            "}";
    }
}
