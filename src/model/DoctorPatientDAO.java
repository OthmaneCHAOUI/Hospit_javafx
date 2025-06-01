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
