package model;

import java.sql.*;

public class PatientDAO
{
    public void ajouterPatient(Patient patient){
        String query = "INSERT INTO Patient (nom,prenom,cnie,mot_de_passe) VALUES (?,?,?,?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setString(1, patient.getNom());
            pstmt.setString(2, patient.getPrenom());
            pstmt.setString(3, patient.getCnie());
            pstmt.setString(4, patient.getMotDePasse());
            pstmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public Patient trouverParCnie(String cnie) throws SQLException{
        String query = "SELECT * FROM Patient WHERE cnie = ?";
        Patient patient = null;
        try(Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)){
            
            pstmt.setString(1, cnie);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return new Patient(rs.getString("nom"), rs.getString("prenom"), rs.getString("cnie"), rs.getString("mot_de_passe"));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return patient;
    }
    public static Patient checkPatientByCniePassword(String cnie,String password) throws SQLException{
        String query = "SELECT * FROM Patient WHERE cnie = ? AND mot_de_passe = ? ";
        Patient patient = null;
        
        try(Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)){
            
            pstmt.setString(1, cnie);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next()){
               patient = new Patient();
               patient.setId(rs.getInt("id")); // Si tu as un champ ID
               patient.setNom(rs.getString("nom"));
               patient.setPrenom(rs.getString("prenom"));
               patient.setCnie(rs.getString("cnie"));
               patient.setMotDePasse(rs.getString("mot_de_passe"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return patient ;
    }
   
   /* public static List<Doctor> getAllDoctors() throws SQLException{
        List<Doctor> doctors = new ArrayList<>();
        String query = "SELECT * FROM doctor";
        
        try(Connection conn = DBConnection.getConnection();
            Statement pstmt = conn.createStatement();
            ResultSet rs = pstmt.executeQuery(query)){
           
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