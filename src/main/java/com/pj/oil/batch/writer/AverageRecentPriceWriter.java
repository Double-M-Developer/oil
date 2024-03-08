package com.pj.oil.batch.writer;

import com.pj.oil.gasStation.entity.AverageRecentPrice;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class AverageRecentPriceWriter implements ItemWriter<AverageRecentPrice> {

    private final JdbcTemplate jdbcTemplate;

    public AverageRecentPriceWriter(
            @Qualifier("gasStationJdbcTemplate") JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends AverageRecentPrice> chunk) throws Exception {
        List<? extends AverageRecentPrice> items = chunk.getItems(); // Chunk에서 아이템 리스트를 가져옴
        String sql = "INSERT INTO AverageRecentPrice (date, product_code, price_average) VALUES (?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                AverageRecentPrice item = items.get(i);
                ps.setString(1, item.getDate());
                ps.setString(2, item.getProductCode());
                ps.setDouble(3, item.getPriceAverage());
            }

            @Override
            public int getBatchSize() {
                return items.size();
            }
        });
    }
}
