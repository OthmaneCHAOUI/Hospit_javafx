package model;

// import model.Patient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/cabinet_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private Connection connection;

    public PatientDAO() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        try {
            String query = "SELECT * FROM Patient";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Patient patient = new Patient(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("cnie"),
                    rs.getString("mot_de_passe"),
                    rs.getDate("date_naissance").toLocalDate()
                );
                patients.add(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    public void addPatient(Patient patient) {
        try {
            String query = "INSERT INTO Patient (nom, prenom, cnie, mot_de_passe, date_naissance) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, patient.getNom());
            pstmt.setString(2, patient.getPrenom());
            pstmt.setString(3, patient.getCnie());
            pstmt.setString(4, patient.getMotDePasse());
            pstmt.setDate(5, Date.valueOf(patient.getDateNaissance()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteByCnie(String cnie) {
        try {
            String query = "DELETE FROM Patient WHERE cnie = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, cnie);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Patient findByCnie(String cnie) {
        try {
            String query = "SELECT * FROM Patient WHERE cnie = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, cnie);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Patient(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("cnie"),
                    rs.getString("mot_de_passe"),
                    rs.getDate("date_naissance").toLocalDate()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}