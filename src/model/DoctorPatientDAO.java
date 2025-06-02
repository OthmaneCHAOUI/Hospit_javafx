package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorPatientDAO {
    public static List<Doctor> getDoctorsByPatientCnie(String cnie) throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String sql = """
            SELECT d.* FROM Doctor d
            JOIN Doctor_Patient dp ON d.id = dp.id_doctor
            WHERE dp.cnie = ?
        """;
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cnie);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Doctor doc = new Doctor();
                doc.setId(rs.getInt("id"));
                doc.setNom(rs.getString("nom"));
                doc.setPrenom(rs.getString("prenom"));
                doc.setCnie(rs.getString("cnie"));
                doc.setSpecialite(rs.getString("specialite"));
                doc.setNomCabinet(rs.getString("nom_cabinet"));
                doc.setTelephone(rs.getString("telephone"));
                doc.setAdresseCabinet(rs.getString("adresse_cabinet"));
                doc.setMotDePasse(rs.getString("mot_de_passe"));
                doctors.add(doc);
            }
        }
        return doctors;
    }
}

    public static void inserer(DoctorPatient patient, int idDoctor) {
    String sql = "INSERT INTO Doctor_Patient (nom, prenom, date_naissance, cnie, ville, telephone, adresse, sexe, id_doctor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, patient.getNom());
        pstmt.setString(2, patient.getPrenom());
        pstmt.setDate(3, java.sql.Date.valueOf(patient.getDateNaissance()));
        pstmt.setString(4, patient.getCnie());
        pstmt.setString(5, patient.getVille());
        pstmt.setString(6, patient.getTelephone());
        pstmt.setString(7, patient.getAdresse());
        pstmt.setString(8, patient.getSexe());
        pstmt.setInt(9, idDoctor);

        pstmt.executeUpdate();

        System.out.println("Patient ajouté avec succès !");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    public static List<DoctorPatient> getPatients(int idDoctor) throws SQLException {
        List<DoctorPatient> doctorPatients = new ArrayList<>();
        String sql = "SELECT nom,prenom,date_naissance,cnie,ville,telephone,adresse,sexe FROM Doctor_Patient WHERE id_doctor = ?";
        try (Connection conn = DBConnection.getConnection();
              PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idDoctor);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DoctorPatient doctorPatient = new DoctorPatient(
                     rs.getString("nom"),
                     rs.getString("prenom"),
                     rs.getDate("date_naissance").toLocalDate(),
                     rs.getString("cnie"),
                     rs.getString("ville"),
                     rs.getString("telephone"),
                     rs.getString("adresse"),
                     rs.getString("sexe")
                );
                doctorPatients.add(doctorPatient);
            }
        }
        return doctorPatients;
    }
    
    public static void update(DoctorPatient newdoctorPatient,String cniePatient,int idDoctor) {
        String sql = "UPDATE Doctor_Patient SET nom=?, prenom=?, date_naissance=?,ville=?,telephone=?,adresse=?,sexe=? WHERE cnie=? AND id_doctor=?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newdoctorPatient.getNom());
            pstmt.setString(2, newdoctorPatient.getPrenom());
            pstmt.setDate(3, java.sql.Date.valueOf(newdoctorPatient.getDateNaissance()));
            pstmt.setString(4, newdoctorPatient.getVille());
            pstmt.setString(5, newdoctorPatient.getTelephone());
            pstmt.setString(6, newdoctorPatient.getAdresse());
            pstmt.setString(7, newdoctorPatient.getSexe());
            pstmt.setString(8, cniePatient);
            pstmt.setInt(9, idDoctor);
            
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Le patient a été changé avec succès pour ce médecin.");
            } else {
                System.out.println("Aucun patient trouvé avec cette CNIE et cet ID médecin.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    public static void delete(String cniePatient, int idDoctor) {
    String sql = "DELETE FROM Doctor_Patient WHERE cnie = ? AND id_doctor = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, cniePatient);
        pstmt.setInt(2, idDoctor);
        int rowsAffected = pstmt.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Le patient a été supprimé avec succès pour ce médecin.");
        } else {
            System.out.println("Aucun patient trouvé avec cette CNIE et cet ID médecin.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}
