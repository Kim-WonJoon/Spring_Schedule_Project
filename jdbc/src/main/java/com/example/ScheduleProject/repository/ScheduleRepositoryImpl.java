package com.example.ScheduleProject.repository;

import com.example.ScheduleProject.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 일정 저장
    @Override
    public Schedule save(Schedule schedule) {
        String sql = "INSERT INTO schedule (content, name, password, write_time, update_time) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, schedule.getContent(), schedule.getName(), schedule.getPassword(),
                schedule.getWriteTime(), schedule.getUpdateTime());

        return schedule;
    }

    // ID로 일정 조회
    @Override
    public Optional<Schedule> findById(Long id) {
        String sql = "SELECT * FROM schedule WHERE id = ?";
        List<Schedule> result = jdbcTemplate.query(sql, scheduleRowMapper(), id);
        return result.stream().findFirst();
    }

    // 전체 일정 조회 (조건부 검색 포함)
    @Override
    public List<Schedule> findAll(String updateTime, String name) {
        String sql = "SELECT * FROM schedule WHERE 1=1";

        if (updateTime != null) {
            sql += " AND DATE(update_time) = ?";
        }
        if (name != null) {
            sql += " AND name = ?";
        }

        sql += " ORDER BY update_time DESC";

        return jdbcTemplate.query(sql, scheduleRowMapper(), updateTime, name);
    }

    // 일정 수정
    @Override
    public void update(Long id, String content, String name, String password) {
        String sql = "UPDATE schedule SET content = ?, name = ?, update_time = ? WHERE id = ? AND password = ?";
        int updatedRows = jdbcTemplate.update(sql, content, name, LocalDateTime.now(), id, password);

        if (updatedRows == 0) {
            throw new RuntimeException("비밀번호가 일치하지 않거나 존재하지 않는 일정입니다.");
        }
    }

    // 일정 삭제
    @Override
    public void delete(Long id, String password) {
        String sql = "DELETE FROM schedule WHERE id = ? AND password = ?";
        int deletedRows = jdbcTemplate.update(sql, id, password);

        if (deletedRows == 0) {
            throw new RuntimeException("비밀번호가 일치하지 않거나 존재하지 않는 일정입니다.");
        }
    }

    private RowMapper<Schedule> scheduleRowMapper() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("id"),
                        rs.getString("content"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getTimestamp("write_time").toLocalDateTime(),
                        rs.getTimestamp("update_time").toLocalDateTime()
                );
            }
        };
    }
}
