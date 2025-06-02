package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentDAO
{
    public static List<Medicament> getMedicaments(String cniePatient, int idDoctor) {
        return new MedicamentDAO().getMedicamentsByPatientAndDoctor(cniePatient, idDoctor);
    }
    
    public List<Medicament> getMedicamentsByPatientAndDoctor(String cniePatient, int idDoctor) {
        List<Medicament> medicaments = new ArrayList<>();
        String query = "SELECT * FROM Medicament WHERE cnie_patient = ? AND id_doctor = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, cniePatient);
            pstmt.setInt(2, idDoctor);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Medicament m = new Medicament(
                    rs.getInt("id"),
                    rs.getInt("id_doctor"),
                    rs.getString("cnie_patient"),
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
    
     public static void delete(int id) {
    String sql = "DELETE FROM Medicament WHERE id = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, id);
        int rowsAffected = pstmt.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Le Medicament a été supprimé avec succès pour ce patient.");
        } else {
            System.out.println("il y'a erreur ressayer");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
     public static void editerMedicament(Medicament medicament) throws SQLException{
        String sql = "UPDATE Medicament SET nom=?, dateDebut=?, description=?,periode=?  WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, medicament.getNom());
            pstmt.setDate(2, java.sql.Date.valueOf(medicament.getDateDebut()));
            pstmt.setString(3, medicament.getDescription());
            pstmt.setInt(4, medicament.getPeriode());
            pstmt.setInt(5, medicament.getId());
            
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
    public static void inserer(Medicament medicament, String cniePatient, int idDoctor) throws SQLException {
    String sql = "INSERT INTO Medicament (nom, date_debut, periode, description, cnie_patient, id_doctor) VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, medicament.getNom());
        pstmt.setDate(2, java.sql.Date.valueOf(medicament.getDateDebut()));
        pstmt.setInt(3, medicament.getPeriode());
        pstmt.setString(4, medicament.getDescription());
        pstmt.setString(5, cniePatient);
        pstmt.setInt(6, idDoctor);

        int rowsInserted = pstmt.executeUpdate();

        if (rowsInserted > 0) {
            System.out.println("Médicament inséré avec succès.");
        } else {
            System.out.println("Échec de l'insertion du médicament.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}