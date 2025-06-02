package model;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class DoctorDAO {
    
    public static int inserer(Doctor doctor) throws SQLException{
        String sql = "INSERT INTO doctor(nom,prenom,cnie,specialite,nom_cabinet,telephone,adresse_cabinet,mot_de_passe) VALUES (?,?,?,?,?,?,?,?)";
        
        try(Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setString(1, doctor.getNom());
            stmt.setString(2, doctor.getPrenom());
            stmt.setString(3, doctor.getCnie());
            stmt.setString(4, doctor.getSpecialite());
            stmt.setString(5, doctor.getNomCabinet());
            stmt.setString(6, doctor.getTelephone());
            stmt.setString(7, doctor.getAdresseCabinet());
            stmt.setString(8, doctor.getMotDePasse());
            
            int rowsInserted = stmt.executeUpdate();

        if (rowsInserted > 0) {
            System.out.println("Doctor ajouté avec succès !");
            return 1 ;
        } else {
            System.out.println("Échec de l'ajout du Doctor.");
        }
        }catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Erreur : Valeur unique déjà utilisée.");
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Doctor checkDoctorByCniePassword(String cnie,String password) throws SQLException{
        String sql = "SELECT * FROM doctor WHERE cnie = ? AND mot_de_passe = ? ";
        Doctor doctor = null;
        
        try(Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setString(1, cnie);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
               doctor = new Doctor();
               doctor.setId(rs.getInt("id"));
               doctor.setNom(rs.getString("nom"));
               doctor.setPrenom(rs.getString("prenom"));
               doctor.setCnie(rs.getString("cnie"));
               doctor.setSpecialite(rs.getString("specialite"));
               doctor.setNomCabinet(rs.getString("nom_cabinet"));
               doctor.setTelephone(rs.getString("telephone"));
               doctor.setAdresseCabinet(rs.getString("adresse_cabinet"));
               doctor.setMotDePasse(rs.getString("mot_de_passe"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return doctor ;
    }

    public static List<Doctor> getAllDoctors() throws SQLException{
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctor";
        
        try(Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
           
            while (rs.next()){
                Doctor doctor = new Doctor();
                doctor.setId(rs.getInt("id"));
                doctor.setNom(rs.getString("nom"));
                doctor.setPrenom(rs.getString("prenom"));
                doctor.setCnie(rs.getString("cnie"));
                doctor.setSpecialite(rs.getString("specialite"));
                doctor.setNomCabinet(rs.getString("nom_cabinet"));
                doctor.setTelephone(rs.getString("telephone"));
                doctor.setAdresseCabinet(rs.getString("adresse_cabinet"));
                doctor.setMotDePasse(rs.getString("mot_de_passe"));
                doctors.add(doctor);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return doctors ;
    }

    // DoctorDAO.java
    public static List<Doctor> getDoctorsByPatient(String cnie) throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT d.* FROM doctor d " +
                    "JOIN traitement t ON d.id = t.doctor_id " +
                    "WHERE t.patient_cnie = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cnie);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Doctor doc = new Doctor(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("specialite")
                );
                doctors.add(doc);
            }
        }
        return doctors;
    }

    public static int update(Doctor doctor) throws SQLException {
        String sql = "UPDATE doctor SET nom = ?, prenom = ?, cnie = ?, specialite = ?, nom_cabinet = ?, telephone = ?, adresse_cabinet = ?, mot_de_passe = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, doctor.getNom());
            stmt.setString(2, doctor.getPrenom());
            stmt.setString(3, doctor.getCnie());
            stmt.setString(4, doctor.getSpecialite());
            stmt.setString(5, doctor.getNomCabinet());
            stmt.setString(6, doctor.getTelephone());
            stmt.setString(7, doctor.getAdresseCabinet());
            stmt.setString(8, doctor.getMotDePasse());
            stmt.setInt(9, doctor.getId());

            int rowsUpdated = stmt.executeUpdate();
            
            if (rowsUpdated > 0) {
                System.out.println("Doctor mis à jour avec succès !");
                return 1;
            } else {
                System.out.println("Échec de la mise à jour.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
