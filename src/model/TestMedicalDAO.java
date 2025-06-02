package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestMedicalDAO {
    public List<TestMedical> getTests(String cniePatient, int idDoctor) {
        List<TestMedical> tests = new ArrayList<>();
        String sql = "SELECT type_test,resultat,date_test,type_doctor,statut FROM Test_Medical WHERE cnie_patient = ? AND id_doctor = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cniePatient);
            stmt.setInt(2, idDoctor);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TestMedical testMedical = new TestMedical(
                    rs.getString("type_test"),
                    rs.getString("resultat"),
                    rs.getDate("date_test").toLocalDate(),
                    rs.getString("type_doctor"),
                    rs.getString("statut")
                );
                tests.add(testMedical);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tests;
    }
}
