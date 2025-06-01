package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestMedicalDAO {
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
}
