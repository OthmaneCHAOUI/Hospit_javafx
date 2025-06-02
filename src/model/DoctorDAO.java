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
            System.out.println("Erreur : Valeur unique déjà utilisée (peut-être CNIE ou téléphone).");
            return -1 ;
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static Doctor check(String cnie,String password) throws SQLException{
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

    public static List<Doctor> getDoctors(String cnie_p) throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT D.id,D.nom,D.prenom,D.specialite,D.nom_cabinet,D.telephone,D.adresse_cabinet FROM Doctor D , Doctor_Patient DP  WHERE D.id = DP.id_doctor AND D.cnie = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cnie_p);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Doctor doctor = new Doctor(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("specialite"),
                    rs.getString("nom_cabinet"),
                    rs.getString("telephone"),
                    rs.getString("adresse_cabinet")
                );
                doctors.add(doctor);
            }
        }
        return doctors;
    }
}
