package com.pj.oil.batch.writer;

import com.pj.oil.gasStation.entity.maria.PriceOil;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class PriceOilWriter implements ItemWriter<PriceOil> {
    private final JdbcTemplate jdbcTemplate;

    public PriceOilWriter(
            @Qualifier("gasStationJdbcTemplate") JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends PriceOil> chunk) throws Exception {
        List<? extends PriceOil> items = chunk.getItems(); // Chunk에서 아이템 리스트를 가져옴
        String sql = "INSERT INTO PriceOil (uni_id, pre_gasoline, gasoline, diesel) VALUES(?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                PriceOil item = items.get(i);
                ps.setString(1, item.getUniId());
                ps.setInt(2, item.getPreGasoline());
                ps.setInt(3, item.getGasoline());
                ps.setInt(4, item.getDiesel());
            }

            @Override
            public int getBatchSize() {
                return items.size();
            }
        });
    }
}