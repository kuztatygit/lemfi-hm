package java.lemfi.dao.payments;


import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.lemfi.model.TransactionType;


@Repository
@RequiredArgsConstructor
public class PaymentDao {
    private JdbcTemplate jdbcTemplate;

    public PaymentDto selectPayment(Long userId) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM payments WHERE userId = ?",
                (rs, rowNum) -> {
                    PaymentDto paymentDto = new PaymentDto();
                    paymentDto.setId(rs.getLong("id"));
                    paymentDto.setType(TransactionType.valueOf(rs.getString("type")));
                    paymentDto.setAmount(rs.getBigDecimal("amount"));
                    paymentDto.setUserId(rs.getLong("user_id"));
                    paymentDto.setRawResponse(rs.getString("raw_response"));
                    return paymentDto;
                },
                userId
        );
    }

    public void deletePaymentById(Long userId) {
        jdbcTemplate.update(
                "DELETE FROM payments WHERE userId = ?",
                userId
        );
    }
}
