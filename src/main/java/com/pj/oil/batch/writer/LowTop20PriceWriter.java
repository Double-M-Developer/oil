package com.pj.oil.batch.writer;

import com.pj.oil.gasStation.entity.maria.LowTop20Price;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class LowTop20PriceWriter implements ItemWriter<LowTop20Price> {
    private final JdbcTemplate jdbcTemplate;

    public LowTop20PriceWriter(
            @Qualifier("gasStationJdbcTemplate") JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends LowTop20Price> chunk) throws Exception {
        List<? extends LowTop20Price> items = chunk.getItems(); // Chunk에서 아이템 리스트를 가져옴
        String sql = "INSERT INTO LowTop20Price (uni_id, price_current, poll_div_code, os_name, van_address, new_address, gis_x_coor, gis_y_coor, product_code, area_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                LowTop20Price item = items.get(i);
                ps.setString(1, item.getUniId());
                ps.setInt(2, item.getPriceCurrent());
                ps.setString(3, item.getPollDivCode());
                ps.setString(4, item.getOsName());
                ps.setString(5, item.getVanAddress());
                ps.setString(6, item.getNewAddress());
                ps.setDouble(7, item.getGisXCoor());
                ps.setDouble(8, item.getGisYCoor());
                ps.setString(9, item.getProductCode());
                ps.setString(10, item.getAreaCode());
            }

            @Override
            public int getBatchSize() {
                return items.size();
            }
        });
    }
}