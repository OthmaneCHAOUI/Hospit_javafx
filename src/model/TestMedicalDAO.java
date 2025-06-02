package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestMedicalDAO {
    public static List<TestMedical> getTests(String cnie, int idDoctor) {
        return new TestMedicalDAO().getTestsByPatientAndDoctor(cnie, idDoctor);
    }
    
    public List<TestMedical> getTestsByPatientAndDoctor(String cnie, int idDoctor) {
        List<TestMedical> tests = new ArrayList<>();
        String sql = "SELECT * FROM Test_Medical WHERE cnie_patient = ? AND id_doctor = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cnie);
            stmt.setInt(2, idDoctor);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TestMedical t = new TestMedical(
                    rs.getInt("id"),
                    rs.getInt("id_doctor"),
                    rs.getString("cnie_patient"),
                    rs.getString("type_test"),
                    rs.getString("resultat"),
                    rs.getDate("date_test").toLocalDate(),
                    rs.getString("type_doctor"),
                    rs.getString("statut")
                );
                tests.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tests;
    }
    
     public static void delete(int id) {
    String sql = "DELETE FROM test_medical WHERE id = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, id);
        int rowsAffected = pstmt.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Le TestMedicale a été supprimé avec succès pour ce patient.");
        } else {
            System.out.println("il y'a erreur ressayer");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
     public static void editerTest(TestMedical testMedical) throws SQLException {
        String sql = "UPDATE test_medical SET type_test=?, date_test=?, resultat=?,type_doctor=?,statut=?  WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, testMedical.getTypeTest());
            pstmt.setDate(2, java.sql.Date.valueOf(testMedical.getDateTest()));
            pstmt.setString(3, testMedical.getResultat());
            pstmt.setString(4, testMedical.getTypeDoctor());
            pstmt.setString(5, testMedical.getStatut());
            pstmt.setInt(6, testMedical.getId());
            
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
    public static void inserer(TestMedical testMedical, String cniePatient, int idDoctor)  throws SQLException{
    String sql = "INSERT INTO test_medical (type_test, resultat, date_test, type_doctor, statut, cnie_patient, id_doctor) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, testMedical.getTypeTest());
        pstmt.setString(2, testMedical.getResultat());
        pstmt.setDate(3, java.sql.Date.valueOf(testMedical.getDateTest()));
        pstmt.setString(4, testMedical.getTypeDoctor());
        pstmt.setString(5, testMedical.getStatut());
        pstmt.setString(6, cniePatient);
        pstmt.setInt(7, idDoctor);

        int rowsInserted = pstmt.executeUpdate();

        if (rowsInserted > 0) {
            System.out.println("Test médical ajouté avec succès !");
        } else {
            System.out.println("Échec de l'ajout du test médical.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}
