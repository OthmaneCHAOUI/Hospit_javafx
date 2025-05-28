package model;

import java.sql.*;

public class PatientDAO {
    public static int addPatient(Patient patient) {
        String sql = "INSERT INTO patient(nom, prenom, cnie, mot_de_passe) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, patient.getNom());
            stmt.setString(2, patient.getPrenom());
            stmt.setString(3, patient.getCnie());
            stmt.setString(4, patient.getMotDePasse());

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Patient ajouté avec succès !");
                return 1;
            } else {
                System.out.println("Échec de l'ajout du Patient.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Erreur : Valeur unique déjà utilisée (peut-être CNIE).");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Patient findByCnie(String cnie) {
        String sql = "SELECT * FROM patient WHERE cnie = ?";
        Patient patient = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cnie);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                patient = new Patient(
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("cnie"),
                    rs.getString("mot_de_passe")
                );
                patient.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patient;
    }

    public static Patient checkPatientByCniePassword(String cnie, String password) {
        String sql = "SELECT * FROM patient WHERE cnie = ? AND mot_de_passe = ?";
        Patient patient = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cnie);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                patient = new Patient(
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("cnie"),
                    rs.getString("mot_de_passe")
                );
                patient.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patient;
    }

    
   /* public static List<Doctor> getAllDoctors() throws SQLException{
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctor";
        
        try(Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
           
            while (rs.next()){
                 Doctor doctor = new Doctor(rs.getString("cnie"),rs.getString("mot_de_passe"));
                 doctors.add(doctor);
                // doctor.setId(rs.getInt("id")); // Si tu as un champ ID
                 //doctor.setNom(rs.getString("nom"));
                 //doctor.setPrenom(rs.getString("prenom"));
                 //doctor.setCnie(rs.getString("cnie"));
                 //doctor.setSpecialite(rs.getString("specialite"));
                // doctor.setNomCabinet(rs.getString("nom_cabinet"));
                // doctor.setTelephone(rs.getString("telephone"));
                 //doctor.setAdresseCabinet(rs.getString("adresse_cabinet"));
                // doctor.setMotDePasse(rs.getString("mot_de_passe"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return doctors ;
    }*/
}