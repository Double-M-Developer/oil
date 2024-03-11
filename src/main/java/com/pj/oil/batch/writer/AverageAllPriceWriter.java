package com.pj.oil.batch.writer;

import com.pj.oil.gasStation.dto.AverageAllPriceDto;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class AverageAllPriceWriter implements ItemWriter<AverageAllPriceDto> {

    private final JdbcTemplate jdbcTemplate;

    public AverageAllPriceWriter(
            @Qualifier("gasStationJdbcTemplate") JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends AverageAllPriceDto> chunk) throws Exception {
        List<? extends AverageAllPriceDto> items = chunk.getItems(); // Chunk에서 아이템 리스트를 가져옴
        String sql = "INSERT INTO AverageAllPrice (trade_date, product_code, price_average, price_change) VALUES (?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                AverageAllPriceDto item = items.get(i);
                ps.setString(1, item.getTradeDate());
                ps.setString(2, item.getProductCode());
                ps.setDouble(3, item.getPriceAverage());
                ps.setDouble(4, item.getPriceChange());
            }

            @Override
            public int getBatchSize() {
                return items.size();
            }
        });
    }
}
