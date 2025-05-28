package model;

import java.sql.*;

public class PatientDAO
{
    public void ajouterPatient(Patient patient){
        String query = "INSERT INTO Patient (nom, prenom, cnie, mot_de_passe) VALUES (?,?,?,?)";

        try(Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setString(1, patient.getNom());
            pstmt.setString(2, patient.getPrenom());
            pstmt.setString(3, patient.getCnie());
            pstmt.setString(4, patient.getMotDePasse());

            int rs = pstmt.executeUpdate();
            if(rs > 0){
                System.out.println("patient ajouter avec succee");
            }
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

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, cnie);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                patient = new Patient(
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("cnie"),
                    rs.getString("mot_de_passe")
                );
                patient.setId(rs.getInt("id"));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return patient;
    }
}