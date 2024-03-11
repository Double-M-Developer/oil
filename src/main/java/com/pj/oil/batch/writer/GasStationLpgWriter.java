package com.pj.oil.batch.writer;

import com.pj.oil.gasStation.dto.GasStationLpgDto;
import com.pj.oil.util.DateUtil;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class GasStationLpgWriter implements ItemWriter<GasStationLpgDto> {
    private final JdbcTemplate jdbcTemplate;
    public GasStationLpgWriter(
            @Qualifier("gasStationJdbcTemplate") JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends GasStationLpgDto> chunk) throws Exception {
        List<? extends GasStationLpgDto> items = chunk.getItems(); // Chunk에서 아이템 리스트를 가져옴
        String sql = "INSERT INTO GasStationLpg (uni_id, area, os_name, poll_div_name, new_address, update_date) VALUES (?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE update_date = VALUES(update_date)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                GasStationLpgDto item = items.get(i);
                ps.setString(1, item.getUniId());
                ps.setString(2, item.getArea());
                ps.setString(3, item.getOsName());
                ps.setString(4, item.getPollDivName());
                ps.setString(5, item.getNewAddress());
                ps.setString(6, DateUtil.getTodayDateString());
            }

            @Override
            public int getBatchSize() {
                return items.size();
            }
        });
    }
}
