package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RendezVousDAO {
    public List<RendezVous> getRendezVouss(String cniePatient, int idDoctor) {
        List<RendezVous> rendezVouss = new ArrayList<>();
        String sql = "SELECT date_rendez_vous,heure,raison,statut FROM Rendez_vous WHERE cnie_patient = ? AND id_doctor = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cniePatient);
            stmt.setInt(2, idDoctor);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RendezVous rendezVous = new RendezVous(
                    rs.getDate("date_rendez_vous").toLocalDate(),
                    rs.getTime("heure").toLocalTime(),
                    rs.getString("raison"),
                    rs.getString("statut")
                );
                rendezVouss.add(rendezVous);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rendezVouss;
    }
}
