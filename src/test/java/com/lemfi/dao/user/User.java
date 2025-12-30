package com.lemfi.dao.user;

import lombok.Data;

import com.lemfi.model.payment.Payment;
import java.math.BigDecimal;
import java.util.List;
@Data
public class User {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String surname;
    private Long personalId;
    private List<Payment> payments;
    private BigDecimal balance;
}

