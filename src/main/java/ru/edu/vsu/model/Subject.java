package ru.edu.vsu.model;

public class Subject {
    private int id;
    private int attestationId;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAttestationId() {
        return attestationId;
    }

    public void setAttestationId(int attestationId) {
        this.attestationId = attestationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
