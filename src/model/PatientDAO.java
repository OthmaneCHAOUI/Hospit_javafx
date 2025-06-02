package model;

import java.sql.*;

public class PatientDAO
{
    public static int inserer(Patient patient) throws SQLException{
        String sql = "INSERT INTO Patient (nom, prenom, cnie, mot_de_passe) VALUES (?,?,?,?)";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setString(1, patient.getNom());
            pstmt.setString(2, patient.getPrenom());
            pstmt.setString(3, patient.getCnie());
            pstmt.setString(4, patient.getMotDePasse());

            int rowsInserted = pstmt.executeUpdate();
            if(rowsInserted > 0){
                System.out.println("patient ajouté avec succès !");
                return 1 ;
            } else {
                System.out.println("Échec de l'ajout du Patient.");
            }
        }catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Erreur : Valeur unique déjà utilisée.");
            return -1 ;
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public static Patient check(String cniePatient, String passwordPatient) {
        String sql = "SELECT * FROM Patient WHERE cnie = ? AND mot_de_passe = ?";
        Patient patient = null;

        try (Connection conn = DBConnection.getConnection();
              PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cniePatient);
            stmt.setString(2, passwordPatient);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                patient = new Patient();
                patient.setNom(rs.getString("nom"));
                patient.setPrenom(rs.getString("prenom"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patient;
    }

    public static void update(Patient patient) {
        String sql = "UPDATE Patient SET nom=?, prenom=?, mot_de_passe=? WHERE cnie=?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, patient.getNom());
            pstmt.setString(2, patient.getPrenom());
            pstmt.setString(3, patient.getMotDePasse());
            pstmt.setString(4, patient.getCnie());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}