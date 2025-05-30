-- DROP IF NEEDED
DROP DATABASE IF EXISTS cabinet_medical2;
CREATE DATABASE cabinet_medical2;
USE cabinet_medical2;

-- ============================
-- 🧑‍⚕️ DOCTOR TABLE
-- ============================
CREATE TABLE Doctor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(30) NOT NULL,
    prenom VARCHAR(30) NOT NULL,
    cnie VARCHAR(15) NOT NULL UNIQUE,
    email VARCHAR(50) NOT NULL UNIQUE,
    mot_de_passe_hash VARCHAR(255) NOT NULL,
    specialite VARCHAR(50) NOT NULL,
    nom_cabinet VARCHAR(50) NOT NULL,
    telephone VARCHAR(15),
    adresse_cabinet VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ============================
-- 🧍‍♂️ PATIENT TABLE
-- ============================
CREATE TABLE Patient (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(30) NOT NULL,
    prenom VARCHAR(30) NOT NULL,
    cnie VARCHAR(15) NOT NULL UNIQUE,
    email VARCHAR(50) UNIQUE,
    mot_de_passe_hash VARCHAR(255) NOT NULL,
    date_naissance DATE,
    ville VARCHAR(30),
    telephone VARCHAR(15),
    adresse VARCHAR(255),
    sexe ENUM('F', 'M') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ============================
-- 🔗 DOCTOR-PATIENT RELATIONSHIP
-- ============================
CREATE TABLE Doctor_Patient (
    id_doctor INT NOT NULL,
    id_patient INT NOT NULL,
    PRIMARY KEY (id_doctor, id_patient),
    FOREIGN KEY (id_doctor) REFERENCES Doctor(id) ON DELETE CASCADE,
    FOREIGN KEY (id_patient) REFERENCES Patient(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ============================
-- 📅 RENDEZ-VOUS
-- ============================
CREATE TABLE Rendez_vous (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_doctor INT NOT NULL,
    id_patient INT NOT NULL,
    date_rendez_vous DATE NOT NULL,
    heure TIME,
    raison VARCHAR(255),
    statut ENUM('à venir', 'présent', 'absent') DEFAULT 'à venir',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT unique_rdv UNIQUE (date_rendez_vous, id_patient, heure),
    FOREIGN KEY (id_doctor) REFERENCES Doctor(id) ON DELETE CASCADE,
    FOREIGN KEY (id_patient) REFERENCES Patient(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ============================
-- 💊 MÉDICAMENTS
-- ============================
CREATE TABLE Medicament (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_doctor INT NOT NULL,
    id_patient INT NOT NULL,
    nom VARCHAR(100) NOT NULL,
    date_debut DATE NOT NULL,
    periode INT,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT unique_prescription UNIQUE (id_patient, nom, date_debut, id_doctor),
    FOREIGN KEY (id_doctor) REFERENCES Doctor(id) ON DELETE CASCADE,
    FOREIGN KEY (id_patient) REFERENCES Patient(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ============================
-- 🧪 TESTS MÉDICAUX
-- ============================
CREATE TABLE Test_Medical (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_doctor INT NOT NULL,
    id_patient INT NOT NULL,
    type_test VARCHAR(100) NOT NULL,
    resultat TEXT,
    date_test DATE NOT NULL,
    type_doctor ENUM('interne', 'externe') NOT NULL,
    statut ENUM('en attente', 'effectué') DEFAULT 'en attente',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_doctor) REFERENCES Doctor(id) ON DELETE CASCADE,
    FOREIGN KEY (id_patient) REFERENCES Patient(id) ON DELETE CASCADE
) ENGINE=InnoDB;

INSERT INTO Doctor (nom, prenom, cnie, email, mot_de_passe_hash, specialite, nom_cabinet, telephone, adresse_cabinet)
VALUES
('Benjelloun', 'Youssef', 'CNI10001', 'youssef.benjelloun@med.ma', 'hashed_pass1', 'Cardiologue', 'Clinique El Amal', '0600000001', '10 Rue Oujda, Fès'),
('El Alaoui', 'Khadija', 'CNI10002', 'khadija.alaoui@med.ma', 'hashed_pass2', 'Dermatologue', 'Cabinet DermoLux', '0600000002', '22 Rue Massira, Rabat'),
('Berrada', 'Amine', 'CNI10003', 'amine.berrada@med.ma', 'hashed_pass3', 'Pédiatre', 'Centre Enfant Saine', '0600000003', '45 Avenue Zerktouni, Casablanca');

INSERT INTO Patient (nom, prenom, cnie, email, mot_de_passe_hash, date_naissance, ville, telephone, adresse, sexe)
VALUES
('El Idrissi', 'Imane', 'P001', 'imane.idrissi@gmail.com', 'p1hash', '1990-03-10', 'Fès', '0610000001', '1 Rue Fès', 'F'),
('Ouahbi', 'Nabil', 'P002', 'nabil.ouahbi@gmail.com', 'p2hash', '1987-06-22', 'Fès', '0610000002', '2 Rue Fès', 'M'),
('Alaoui', 'Salma', 'P003', 'salma.alaoui@gmail.com', 'p3hash', '1995-09-05', 'Rabat', '0620000003', '3 Rue Rabat', 'F'),
('Mernissi', 'Ayoub', 'P004', 'ayoub.mernissi@gmail.com', 'p4hash', '1984-01-19', 'Rabat', '0620000004', '4 Rue Rabat', 'M'),
('Tazi', 'Yasmine', 'P005', 'yasmine.tazi@gmail.com', 'p5hash', '1992-04-25', 'Casablanca', '0630000005', '5 Rue Casa', 'F'),
('Fassi', 'Reda', 'P006', 'reda.fassi@gmail.com', 'p6hash', '1989-12-01', 'Casablanca', '0630000006', '6 Rue Casa', 'M'),
('Zouhair', 'Lina', 'P007', 'lina.zouhair@gmail.com', 'p7hash', '1994-11-11', 'Fès', '0610000007', '7 Rue Fès', 'F'),
('El Hachimi', 'Mehdi', 'P008', 'mehdi.hachimi@gmail.com', 'p8hash', '1981-08-08', 'Rabat', '0620000008', '8 Rue Rabat', 'M'),
('Bennis', 'Sara', 'P009', 'sara.bennis@gmail.com', 'p9hash', '1996-05-15', 'Casablanca', '0630000009', '9 Rue Casa', 'F');

-- Dr. Benjelloun (Cardio)
INSERT INTO Doctor_Patient (id_doctor, id_patient) VALUES
(1, 1), (1, 2), (1, 7);

-- Dr. El Alaoui (Dermato)
INSERT INTO Doctor_Patient (id_doctor, id_patient) VALUES
(2, 3), (2, 4), (2, 8);

-- Dr. Berrada (Pédiatre)
INSERT INTO Doctor_Patient (id_doctor, id_patient) VALUES
(3, 5), (3, 6), (3, 9);

INSERT INTO Rendez_vous (id_doctor, id_patient, date_rendez_vous, heure, raison, statut) VALUES
(1, 1, '2025-06-01', '09:00:00', 'Suivi tension', 'à venir'),
(1, 2, '2025-06-01', '10:00:00', 'Consultation douleur thoracique', 'à venir'),
(2, 3, '2025-06-02', '11:00:00', 'Problème de peau', 'à venir'),
(2, 4, '2025-06-02', '12:00:00', 'Contrôle post-traitement', 'à venir'),
(3, 5, '2025-06-03', '13:00:00', 'Vaccination', 'à venir'),
(3, 6, '2025-06-03', '14:00:00', 'Bilan santé', 'à venir');

INSERT INTO Medicament (id_doctor, id_patient, nom, date_debut, periode, description) VALUES
(1, 1, 'Amlor', '2025-06-01', 30, 'Hypertension artérielle'),
(1, 2, 'Doliprane', '2025-06-01', 5, 'Antidouleur'),
(2, 3, 'Dermovate', '2025-06-02', 14, 'Eczéma'),
(2, 4, 'Zyrtec', '2025-06-02', 10, 'Allergie saisonnière'),
(3, 5, 'Vitamine D', '2025-06-03', 30, 'Carence'),
(3, 6, 'Ibuprofène', '2025-06-03', 7, 'Fièvre');

INSERT INTO Test_Medical (id_doctor, id_patient, type_test, resultat, date_test, type_doctor, statut) VALUES
(1, 1, 'ECG', 'Normal', '2025-05-28', 'interne', 'effectué'),
(1, 2, 'Prise de sang', 'Anémie légère', '2025-05-28', 'interne', 'effectué'),
(2, 3, 'Biopsie peau', 'Négatif', '2025-05-29', 'externe', 'effectué'),
(2, 4, 'Allergotest', 'Poussière détectée', '2025-05-29', 'interne', 'effectué'),
(3, 5, 'Test auditif', 'Légère perte', '2025-05-30', 'interne', 'effectué'),
(3, 6, 'Scanner', 'Normal', '2025-05-30', 'externe', 'effectué');
