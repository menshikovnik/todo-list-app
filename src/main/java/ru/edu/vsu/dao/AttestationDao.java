package ru.edu.vsu.dao;

import ru.edu.vsu.model.Attestation;
import ru.edu.vsu.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttestationDao {

    public List<Attestation> findAll() throws SQLException {
        String sql = "SELECT id, name, start_date, end_date FROM attestations ORDER BY start_date";
        List<Attestation> list = new ArrayList<>();
        try (Connection c = DbUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Attestation a = new Attestation();
                a.setId(rs.getInt("id"));
                a.setName(rs.getString("name"));
                a.setStartDate(rs.getDate("start_date"));
                a.setEndDate(rs.getDate("end_date"));
                list.add(a);
            }
        }
        return list;
    }
}
