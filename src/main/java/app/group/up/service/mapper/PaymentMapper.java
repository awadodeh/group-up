package app.group.up.service.mapper;

import app.group.up.domain.*;
import app.group.up.service.dto.PaymentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Payment and its DTO PaymentDTO.
 */
@Mapper(componentModel = "spring", uses = {PersonMapper.class, })
public interface PaymentMapper extends EntityMapper <PaymentDTO, Payment> {

    @Mapping(source = "person.id", target = "personId")
    PaymentDTO toDto(Payment payment); 

    @Mapping(source = "personId", target = "person")
    Payment toEntity(PaymentDTO paymentDTO); 
    default Payment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Payment payment = new Payment();
        payment.setId(id);
        return payment;
    }
}
