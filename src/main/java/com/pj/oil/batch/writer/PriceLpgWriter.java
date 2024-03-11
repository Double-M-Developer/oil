package com.pj.oil.batch.writer;

import com.pj.oil.gasStation.dto.PriceLpgDto;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class PriceLpgWriter implements ItemWriter<PriceLpgDto> {
    private final JdbcTemplate jdbcTemplate;

    public PriceLpgWriter(
            @Qualifier("gasStationJdbcTemplate") JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void write(Chunk<? extends PriceLpgDto> chunk) throws Exception {
        List<? extends PriceLpgDto> items = chunk.getItems(); // Chunk에서 아이템 리스트를 가져옴
        String sql = "INSERT INTO PriceLpg (uni_id, lpg) VALUES(?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                PriceLpgDto item = items.get(i);
                ps.setString(1, item.getUniId());
                ps.setInt(2, item.getLpg());
            }

            @Override
            public int getBatchSize() {
                return items.size();
            }
        });
    }
}
