package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RendezVousDAO {
    public List<RendezVous> getRendezVousByPatientAndDoctor(String cnie, int idDoctor) {
        List<RendezVous> rdvs = new ArrayList<>();
        String sql = "SELECT * FROM Rendez_vous WHERE cnie_patient = ? AND id_doctor = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cnie);
            stmt.setInt(2, idDoctor);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RendezVous r = new RendezVous(
                    rs.getInt("id"),
                    rs.getInt("id_doctor"),
                    rs.getString("cnie_patient"),
                    rs.getDate("date_rendez_vous").toLocalDate(),
                    rs.getTime("heure").toLocalTime(),
                    rs.getString("raison"),
                    rs.getString("statut")
                );
                rdvs.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rdvs;
    }
}
