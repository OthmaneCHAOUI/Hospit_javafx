package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentDAO
{
    public List<Medicament> getMedicaments(String cniePatient, int idDoctor) {
        List<Medicament> medicaments = new ArrayList<>();
        String sql = "SELECT * FROM Medicament WHERE cnie_patient = ? AND id_doctor = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, cniePatient);
            pstmt.setInt(2, idDoctor);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Medicament m = new Medicament(
                    rs.getString("nom"),
                    rs.getDate("date_debut").toLocalDate(),
                    rs.getInt("periode"),
                    rs.getString("description")
                );
                medicaments.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicaments;
    }
}