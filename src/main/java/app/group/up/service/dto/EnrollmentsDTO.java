package app.group.up.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Enrollments entity.
 */
public class EnrollmentsDTO implements Serializable {

    private Long id;

    private String personId;

    private String circleId;

    private Long circleId;

    private Long personId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getCircleId() {
        return circleId;
    }

    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }

    public Long getCircleId() {
        return circleId;
    }

    public void setCircleId(Long circleId) {
        this.circleId = circleId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EnrollmentsDTO enrollmentsDTO = (EnrollmentsDTO) o;
        if(enrollmentsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), enrollmentsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EnrollmentsDTO{" +
            "id=" + getId() +
            ", personId='" + getPersonId() + "'" +
            ", circleId='" + getCircleId() + "'" +
            "}";
    }
}
