package ru.edu.vsu.dao;

import ru.edu.vsu.model.Subject;
import ru.edu.vsu.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDao {

    public List<Subject> findByAttestation(int attestationId) throws SQLException {
        String sql = "SELECT id, attestation_id, name FROM subjects WHERE attestation_id = ? ORDER BY name";
        List<Subject> list = new ArrayList<>();
        try (Connection c = DbUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, attestationId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Subject s = new Subject();
                    s.setId(rs.getInt("id"));
                    s.setAttestationId(rs.getInt("attestation_id"));
                    s.setName(rs.getString("name"));
                    list.add(s);
                }
            }
        }
        return list;
    }
}
