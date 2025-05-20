package ru.edu.vsu.service;

import ru.edu.vsu.dao.SubjectDao;
import ru.edu.vsu.model.Subject;

import java.sql.SQLException;
import java.util.List;

public class SubjectService {
    private final SubjectDao subjectDao;

    public SubjectService(SubjectDao subjectDao) {
        this.subjectDao = subjectDao;
    }

    public List<Subject> getByAttestation(int attestationId) throws SQLException {
        return subjectDao.findByAttestation(attestationId);
    }
}
