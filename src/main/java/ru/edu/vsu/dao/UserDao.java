package ru.edu.vsu.dao;

import ru.edu.vsu.model.User;
import ru.edu.vsu.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    public int save(User user) throws SQLException {
        String sql = "INSERT INTO users(email, password_hash) VALUES(?, ?) RETURNING id";
        try (Connection c = DbUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPasswordHash());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            throw new SQLException("User insert failed, no ID obtained.");
        }
    }

    public User findByEmail(String email) throws SQLException {
        String sql = "SELECT id, email, password_hash, created_at, true AS enabled FROM users WHERE email = ?";
        try (Connection c = DbUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setEmail(rs.getString("email"));
                u.setPasswordHash(rs.getString("password_hash"));
                u.setCreatedAt(rs.getTimestamp("created_at"));
                u.setEnabled(rs.getBoolean("enabled"));
                return u;
            }
            return null;
        }
    }
}
