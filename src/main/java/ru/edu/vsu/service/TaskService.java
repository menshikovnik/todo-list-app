package ru.edu.vsu.service;

import ru.edu.vsu.dao.TaskDao;
import ru.edu.vsu.model.Task;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class TaskService {
    private final TaskDao taskDao;

    public TaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public List<Task> findByUser(int userId) throws SQLException {
        return taskDao.findByUser(userId);
    }

    public List<Task> findBySubject(int subjectId) throws SQLException {
        return taskDao.findBySubject(subjectId);
    }

    public void addTask(int userId, int subjectId, String description, Date dueDate) throws SQLException {
        Task task = new Task();
        task.setUserId(userId);
        task.setSubjectId(subjectId);
        task.setDescription(description);
        task.setDueDate(dueDate);
        task.setDone(false);
        taskDao.add(task);
    }

    public void toggleDone(int taskId) throws SQLException {
        taskDao.toggle(taskId);
    }

    public void delete(int taskId) throws SQLException {
        taskDao.delete(taskId);
    }

    public List<Task> findByAttestation(int attId) throws SQLException {
        return taskDao.findByAttestation(attId);
    }
}