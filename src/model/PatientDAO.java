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

    public static Patient checkPatientByCniePassword(String cnie, String password) {
        String query = "SELECT * FROM Patient WHERE cnie = ? AND mot_de_passe = ?";
        Patient patient = null;

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, cnie);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                patient = new Patient();
                patient.setId(rs.getInt("id"));
                patient.setNom(rs.getString("nom"));
                patient.setPrenom(rs.getString("prenom"));
                patient.setCnie(rs.getString("cnie"));
                patient.setMotDePasse(rs.getString("mot_de_passe"));
                patient.setDateNaissance(rs.getDate("date_naissance") != null ? rs.getDate("date_naissance").toLocalDate() : null);
                patient.setVille(rs.getString("ville"));
                patient.setEmail(rs.getString("email"));
                patient.setTelephone(rs.getString("telephone"));
                patient.setAdresse(rs.getString("adresse"));
                patient.setSexe(rs.getString("sexe"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patient;
    }

    public void updatePatient(Patient patient) {
        String query = "UPDATE Patient SET nom=?, prenom=?, ville=?, email=?, adresse=?, telephone=?, date_naissance=?, mot_de_passe=? WHERE cnie=?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, patient.getNom());
            pstmt.setString(2, patient.getPrenom());
            pstmt.setString(3, patient.getVille());
            pstmt.setString(4, patient.getEmail());
            pstmt.setString(5, patient.getAdresse());
            pstmt.setString(6, patient.getTelephone());
            if (patient.getDateNaissance() != null)
                pstmt.setDate(7, Date.valueOf(patient.getDateNaissance()));
            else
                pstmt.setNull(7, java.sql.Types.DATE);
            pstmt.setString(8, patient.getMotDePasse());
            pstmt.setString(9, patient.getCnie());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}