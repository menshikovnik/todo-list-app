package ru.edu.vsu.dao;

import ru.edu.vsu.model.Task;
import ru.edu.vsu.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDao {

    public List<Task> findByUser(int userId) throws SQLException {
        String sql = "SELECT id, user_id, subject_id, description, due_date, is_done, created_at " +
                "FROM tasks WHERE user_id = ? ORDER BY created_at";
        List<Task> list = new ArrayList<>();
        try (Connection c = DbUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Task t = new Task();
                    t.setId(rs.getInt("id"));
                    t.setUserId(rs.getInt("user_id"));
                    t.setSubjectId(rs.getInt("subject_id"));
                    t.setDescription(rs.getString("description"));
                    t.setDueDate(rs.getDate("due_date"));
                    t.setDone(rs.getBoolean("is_done"));
                    t.setCreatedAt(rs.getTimestamp("created_at"));
                    list.add(t);
                }
            }
        }
        return list;
    }

    public List<Task> findBySubject(int subjectId) throws SQLException {
        String sql = "SELECT id, user_id, subject_id, description, due_date, is_done, created_at " +
                "FROM tasks WHERE subject_id = ? ORDER BY created_at";
        List<Task> list = new ArrayList<>();
        try (Connection c = DbUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, subjectId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Task t = new Task();
                    t.setId(rs.getInt("id"));
                    t.setUserId(rs.getInt("user_id"));
                    t.setSubjectId(rs.getInt("subject_id"));
                    t.setDescription(rs.getString("description"));
                    t.setDueDate(rs.getDate("due_date"));
                    t.setDone(rs.getBoolean("is_done"));
                    t.setCreatedAt(rs.getTimestamp("created_at"));
                    list.add(t);
                }
            }
        }
        return list;
    }

    public void add(Task task) throws SQLException {
        String sql = "INSERT INTO tasks(user_id, subject_id, description, due_date) VALUES(?, ?, ?, ?)";
        try (Connection c = DbUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, task.getUserId());
            ps.setInt(2, task.getSubjectId());
            ps.setString(3, task.getDescription());
            ps.setDate(4, task.getDueDate());
            ps.executeUpdate();
        }
    }

    public void toggle(int taskId) throws SQLException {
        String sql = "UPDATE tasks SET is_done = NOT is_done WHERE id = ?";
        try (Connection c = DbUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, taskId);
            ps.executeUpdate();
        }
    }

    public void delete(int taskId) throws SQLException {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (Connection c = DbUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, taskId);
            ps.executeUpdate();
        }
    }

    public List<Task> findByAttestation(int attestationId) throws SQLException {
        String sql = """
                SELECT t.id, t.user_id, t.subject_id, s.name     AS subject_name,
                       t.description, t.due_date, t.is_done, t.created_at
                FROM   tasks t
                JOIN   subjects s ON t.subject_id = s.id
                WHERE  s.attestation_id = ?
                ORDER  BY t.created_at
                """;

        List<Task> list = new ArrayList<>();
        try (Connection c = DbUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, attestationId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Task t = new Task();
                    t.setId(rs.getInt("id"));
                    t.setUserId(rs.getInt("user_id"));
                    t.setSubjectId(rs.getInt("subject_id"));
                    t.setSubjectName(rs.getString("subject_name"));   // ← добавьте поле в модель, если его нет
                    t.setDescription(rs.getString("description"));
                    t.setDueDate(rs.getDate("due_date"));
                    t.setDone(rs.getBoolean("is_done"));
                    t.setCreatedAt(rs.getTimestamp("created_at"));
                    list.add(t);
                }
            }
        }
        return list;
    }
}
