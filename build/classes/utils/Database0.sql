-- Base de données
CREATE DATABASE IF NOT EXISTS cabinet_medical;
USE cabinet_medical;

-- =========================
-- 👨‍⚕️ Table des docteurs
-- =========================
CREATE TABLE Doctor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(30) NOT NULL,
    prenom VARCHAR(30) NOT NULL,
    cnie VARCHAR(15) NOT NULL UNIQUE,
    specialite VARCHAR(50) NOT NULL,
    nom_cabinet VARCHAR(50) NOT NULL,
    telephone VARCHAR(15),
    adresse_cabinet VARCHAR(255),
    mot_de_passe VARCHAR(255) NOT NULL -- haché ou non selon ton app
) ENGINE=InnoDB;

-- ==============================
-- 🧍 Table des patients avec compte
-- ==============================
CREATE TABLE Patient (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(30) NOT NULL,
    prenom VARCHAR(30) NOT NULL,
    cnie VARCHAR(15) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(255) NOT NULL,
    date_naissance DATE,
    ville VARCHAR(30),
    telephone VARCHAR(15),
    adresse VARCHAR(255),
    sexe ENUM('F', 'M'),
    compte_cree BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- =========================
-- 📁 Table Doctor_Patient (dossier interne du docteur)
-- =========================
CREATE TABLE Doctor_Patient (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_doctor INT NOT NULL,
    nom VARCHAR(30) NOT NULL,
    prenom VARCHAR(30) NOT NULL,
    cnie VARCHAR(15) NOT NULL,
    date_naissance DATE,
    ville VARCHAR(30),
    telephone VARCHAR(15),
    adresse VARCHAR(255),
    sexe ENUM('F', 'M'),
    remarque TEXT,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    UNIQUE (id_doctor, cnie),
    FOREIGN KEY (id_doctor) REFERENCES Doctor(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- =========================
-- 📅 Table des rendez-vous
-- =========================
CREATE TABLE Rendez_vous (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_doctor INT NOT NULL,
    cnie_patient VARCHAR(15) NOT NULL,
    date_rendez_vous DATE NOT NULL,
    heure TIME,
    raison VARCHAR(255),
    statut ENUM('à venir', 'présent', 'absent') DEFAULT 'à venir',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (id_doctor) REFERENCES Doctor(id) ON DELETE CASCADE,
    -- On vérifie le cnie_patient dans l'application
    UNIQUE (date_rendez_vous, cnie_patient, heure)
) ENGINE=InnoDB;

-- =========================
-- 💊 Table des médicaments
-- =========================
CREATE TABLE Medicament (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_doctor INT NOT NULL,
    cnie_patient VARCHAR(15) NOT NULL,
    nom VARCHAR(100) NOT NULL,
    date_debut DATE NOT NULL,
    periode INT,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (id_doctor) REFERENCES Doctor(id) ON DELETE CASCADE,
    -- Vérifie le cnie_patient dans l’application
    UNIQUE (cnie_patient, nom, date_debut, id_doctor)
) ENGINE=InnoDB;

-- =========================
-- 🧪 Table des tests médicaux
-- =========================
CREATE TABLE Test_Medical (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_doctor INT NOT NULL,
    cnie_patient VARCHAR(15) NOT NULL,
    type_test VARCHAR(100) NOT NULL,
    resultat TEXT,
    date_test DATE NOT NULL,
    type_doctor ENUM('interne', 'externe') NOT NULL,
    statut ENUM('en attente', 'effectué') DEFAULT 'en attente',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (id_doctor) REFERENCES Doctor(id) ON DELETE CASCADE
    -- cnie_patient à vérifier dans l’application
) ENGINE=InnoDB;
