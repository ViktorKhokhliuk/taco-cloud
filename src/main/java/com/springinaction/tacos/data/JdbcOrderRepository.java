package com.springinaction.tacos.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springinaction.tacos.TacoOrder;
import com.springinaction.tacos.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcOrderRepository { //implements OrderRepository {
    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper objectMapper;

    @Autowired
    public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {
        this.orderInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Taco_Order")
                .usingGeneratedKeyColumns("id");
        this.orderTacoInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Taco_Order_Tacos");
        this.objectMapper = new ObjectMapper();
    }

    //@Override
    public TacoOrder save(TacoOrder tacoOrder) {
        tacoOrder.setPlacedAt(new Date());
        long orderId = saveOrderDetails(tacoOrder);
        tacoOrder.setId(orderId);
        tacoOrder.getTacos().forEach(taco -> saveTacoOrder(taco, orderId));
        return tacoOrder;
    }

    private long saveOrderDetails(TacoOrder tacoOrder) {
        @SuppressWarnings("unchecked")
        Map<String, Object> values = objectMapper.convertValue(tacoOrder, Map.class);
        values.put("placedAt", tacoOrder.getPlacedAt());

        return orderInserter.executeAndReturnKey(values).longValue();
    }

    private void saveTacoOrder(Taco taco, long orderId) {
        Map<String, Object> values = new HashMap<>();
        values.put("tacoOrder", orderId);
        values.put("taco", taco.getId());
        orderTacoInserter.execute(values);
    }
}
