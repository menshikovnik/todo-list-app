package ru.edu.vsu.service;

import ru.edu.vsu.dao.AttestationDao;
import ru.edu.vsu.model.Attestation;

import java.sql.SQLException;
import java.util.List;

public class AttestationService {
    private final AttestationDao attestationDao;

    public AttestationService(AttestationDao attestationDao) {
        this.attestationDao = attestationDao;
    }

    public List<Attestation> getAll() throws SQLException {
        return attestationDao.findAll();
    }
}
