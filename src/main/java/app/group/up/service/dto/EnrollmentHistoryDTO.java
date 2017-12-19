package app.group.up.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import app.group.up.domain.enumeration.Language;

/**
 * A DTO for the EnrollmentHistory entity.
 */
public class EnrollmentHistoryDTO implements Serializable {

    private Long id;

    private Language language;

    private Long circleId;

    private Long personId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
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

        EnrollmentHistoryDTO enrollmentHistoryDTO = (EnrollmentHistoryDTO) o;
        if(enrollmentHistoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), enrollmentHistoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EnrollmentHistoryDTO{" +
            "id=" + getId() +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
