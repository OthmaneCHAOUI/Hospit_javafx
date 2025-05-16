-- CREATE DATABASE cabinet_db;

USE cabinet_db;

CREATE TABLE Patient (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(30) NOT NULL,
    prenom VARCHAR(30) NOT NULL,
    cnie VARCHAR(15) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(15) NOT NULL,
    date_naissance DATE NOT NULL
) ENGINE=InnoDB ;


CREATE TABLE Doctor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(30) NOT NULL,
    prenom VARCHAR(30) NOT NULL,
    cnie VARCHAR(15) NOT NULL UNIQUE,
    specialite VARCHAR(50) NOT NULL,
    nom_cabinet VARCHAR(50) NOT NULL,
    telephone VARCHAR(15),
    adresse_cabinet VARCHAR(255) NOT NULL,
    -- numero_inscription VARCHAR(20) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(15) NOT NULL
) ENGINE=InnoDB ;

CREATE TABLE Doctor_Patient (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_doctor INT NOT NULL,
    nom VARCHAR(30) NOT NULL,
    prenom VARCHAR(30) NOT NULL,
    date_naissance DATE NOT NULL,
    cnie VARCHAR(15) NOT NULL,
    ville VARCHAR(30),
    telephone VARCHAR(15),
    adresse VARCHAR(255),
    sexe ENUM('F', 'M') NOT NULL,
    UNIQUE (id_doctor, cnie),
    FOREIGN KEY (id_doctor) REFERENCES Doctor(id) ON DELETE CASCADE
) ENGINE=InnoDB ;



CREATE TABLE Rendez_vous (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_doctor INT NOT NULL,
    cnie_patient VARCHAR(15) NOT NULL,
    date_rendez_vous DATE NOT NULL,
    heure TIME NOT NULL,
    raison VARCHAR(255),
    statut ENUM('à venir', 'présent', 'absent') DEFAULT 'à venir',
    FOREIGN KEY (id_doctor) REFERENCES Doctor(id) ON DELETE CASCADE,
    CONSTRAINT unique_date UNIQUE (date_rendez_vous,cnie_patient,heure)
) ENGINE=InnoDB ;



CREATE TABLE Medicament (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_doctor INT NOT NULL,
    cnie_patient VARCHAR(15) NOT NULL,
    nom VARCHAR(100) NOT NULL,
    date_debut DATE NOT NULL,
    periode INT, 
    description TEXT,
    FOREIGN KEY (id_doctor) REFERENCES Doctor(id) ON DELETE CASCADE,
    CONSTRAINT unique_prescription UNIQUE (cnie_patient, nom, date_debut,id_doctor)
) ENGINE=InnoDB ;



CREATE TABLE test_medical (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_doctor INT NOT NULL,
    cnie_patient VARCHAR(15) NOT NULL,
    type_test VARCHAR(100) NOT NULL,
    resultat TEXT,                                            
    date_test DATE NOT NULL,                      
    type_doctor ENUM('interne', 'externe') NOT NULL,          
    statut ENUM('en attente', 'effectué') DEFAULT 'en attente',
    FOREIGN KEY (id_doctor) REFERENCES Doctor(id) ON DELETE CASCADE
) ENGINE=InnoDB ;
