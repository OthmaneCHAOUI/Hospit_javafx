-- CREATE DATABASE cabinet_db;

USE cabinet_db;

CREATE TABLE Patient (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(20) NOT NULL,
    prenom VARCHAR(20) NOT NULL,
    date_naissance DATE NOT NULL,
    cnie VARCHAR(15) NOT NULL UNIQUE,
    telephone VARCHAR(15),
    mot_de_passe VARCHAR(12) NOT NULL
) ENGINE=InnoDB ;


CREATE TABLE Doctor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(20) NOT NULL,
    prenom VARCHAR(20) NOT NULL,
    cnie VARCHAR(15) NOT NULL UNIQUE,
    specialite VARCHAR(30),
    telephone VARCHAR(15),
    adresse_cabinet VARCHAR(255),
    mot_de_passe VARCHAR(12) NOT NULL
) ENGINE=InnoDB ;



CREATE TABLE Rendez_vous (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_patient INT NOT NULL,
    date_rendez_vous DATE NOT NULL,
    heure TIME NOT NULL,
    raison VARCHAR(255),
    statut ENUM('à venir', 'présent', 'absent') DEFAULT 'à venir',
    FOREIGN KEY (id_patient) REFERENCES patient(id) ON DELETE CASCADE
) ENGINE=InnoDB ;



CREATE TABLE Medicament (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_patient INT NOT NULL,
    nom VARCHAR(100) NOT NULL,
    date_debut DATE NOT NULL,
    periode INT, 
    description TEXT,
    FOREIGN KEY (id_patient) REFERENCES Patient(id) ON DELETE CASCADE,
    CONSTRAINT unique_prescription UNIQUE (id_patient, nom, date_debut)
) ENGINE=InnoDB ;



CREATE TABLE test_medical (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_patient INT NOT NULL,
    type_test VARCHAR(100) NOT NULL,
    resultat TEXT,                                            
    date_test DATE NOT NULL,                      
    type_doctor ENUM('interne', 'externe') NOT NULL,          
    statut ENUM('en attente', 'effectué') DEFAULT 'en attente',
    FOREIGN KEY (id_patient) REFERENCES patient(id) ON DELETE CASCADE        
) ENGINE=InnoDB ;
