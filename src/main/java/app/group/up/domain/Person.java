package app.group.up.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 3)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @OneToOne
    @JoinColumn(unique = true)
    private Address address;

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Payment> payments = new HashSet<>();

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Enrollments> enrollments = new HashSet<>();

    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.REFRESH })
    @JoinTable(
        name = "member_enrollment",
        joinColumns = { @JoinColumn(name = "persons_id", referencedColumnName="id") },
        inverseJoinColumns = { @JoinColumn(name = "circles_id", referencedColumnName="id") }
    )
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Circle> circles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Person firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Person lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Person email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Person phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public Person address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public Person payments(Set<Payment> payments) {
        this.payments = payments;
        return this;
    }

    public Person addPayment(Payment payment) {
        this.payments.add(payment);
        payment.setPerson(this);
        return this;
    }

    public Person removePayment(Payment payment) {
        this.payments.remove(payment);
        payment.setPerson(null);
        return this;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public Person reviews(Set<Review> reviews) {
        this.reviews = reviews;
        return this;
    }

    public Person addReview(Review review) {
        this.reviews.add(review);
        review.setPerson(this);
        return this;
    }

    public Person removeReview(Review review) {
        this.reviews.remove(review);
        review.setPerson(null);
        return this;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<Enrollments> getEnrollments() {
        return enrollments;
    }

    public Person enrollments(Set<Enrollments> enrollments) {
        this.enrollments = enrollments;
        return this;
    }

    public Person addEnrollments(Enrollments enrollments) {
        this.enrollments.add(enrollments);
        enrollments.setPerson(this);
        return this;
    }

    public Person removeEnrollments(Enrollments enrollments) {
        this.enrollments.remove(enrollments);
        enrollments.setPerson(null);
        return this;
    }

    public void setEnrollments(Set<Enrollments> enrollments) {
        this.enrollments = enrollments;
    }

    public Set<Circle> getCircles() {
        return circles;
    }

    public void setCircles(Set<Circle> circles) {
        this.circles = circles;
    }

    public int getNumberOfEnrollments() {
        return this.circles.size();
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        if (person.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), person.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
