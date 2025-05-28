CREATE DATABASE cabinet_medical;

USE cabinet_medical;

CREATE TABLE Patient (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(30) NOT NULL,
    prenom VARCHAR(30) NOT NULL,
    cnie VARCHAR(15) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(15) NOT NULL,
    date_naissance DATE
) ENGINE=InnoDB ;


CREATE TABLE Doctor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(30) NOT NULL,
    prenom VARCHAR(30) NOT NULL,
    cnie VARCHAR(15) NOT NULL UNIQUE,
    specialite VARCHAR(50) NOT NULL,
    nom_cabinet VARCHAR(50) NOT NULL,
    telephone VARCHAR(15),
    adresse_cabinet VARCHAR(255),
    mot_de_passe VARCHAR(15) NOT NULL
    -- email VARCHAR(30)
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


-- ======================
-- 👨‍⚕️ DOCTEURS
-- ======================
INSERT INTO Doctor (nom, prenom, cnie, specialite, nom_cabinet, telephone, adresse_cabinet, mot_de_passe)
VALUES 
('Dupont', 'Jean', 'CNI123A', 'Cardiologue', 'Cabinet Santé Cœur', '0612345678', '12 rue des Lilas, Paris', 'pass1234'),
('Martin', 'Sophie', 'CNI456B', 'Dermatologue', 'Dermacenter', '0623456789', '89 avenue Lumière, Lyon', 'pass4567'),
('Nguyen', 'Paul', 'CNI789C', 'Généraliste', 'Clinique Horizon', '0634567890', '45 boulevard Santé, Marseille', 'pass7890');

-- ======================
-- 🧍‍♂️ PATIENTS (5 par docteur)
-- ======================
-- Docteur 1 : Dupont
INSERT INTO Doctor_Patient (id_doctor, nom, prenom, date_naissance, cnie, ville, telephone, adresse, sexe)
VALUES
(1, 'Lemoine', 'Alice', '1990-04-12', 'PA001', 'Paris', '0700000001', '10 rue Bleue', 'F'),
(1, 'Durand', 'Michel', '1985-06-30', 'PA002', 'Paris', '0700000002', '22 rue Rouge', 'M'),
(1, 'Roux', 'Claire', '1993-09-15', 'PA003', 'Paris', '0700000003', '33 rue Verte', 'F'),
(1, 'Morel', 'David', '1980-01-22', 'PA004', 'Paris', '0700000004', '44 rue Jaune', 'M'),
(1, 'Petit', 'Emma', '2000-11-05', 'PA005', 'Paris', '0700000005', '55 rue Noire', 'F');

-- Docteur 2 : Martin
INSERT INTO Doctor_Patient (id_doctor, nom, prenom, date_naissance, cnie, ville, telephone, adresse, sexe)
VALUES
(2, 'Girard', 'Luc', '1987-07-10', 'PB001', 'Lyon', '0700000011', '1 rue A', 'M'),
(2, 'Baron', 'Julie', '1991-03-25', 'PB002', 'Lyon', '0700000012', '2 rue B', 'F'),
(2, 'Garnier', 'Louis', '1975-12-08', 'PB003', 'Lyon', '0700000013', '3 rue C', 'M'),
(2, 'Noël', 'Sarah', '1996-02-18', 'PB004', 'Lyon', '0700000014', '4 rue D', 'F'),
(2, 'Lemoine', 'Kevin', '2002-09-01', 'PB005', 'Lyon', '0700000015', '5 rue E', 'M');

-- Docteur 3 : Nguyen
INSERT INTO Doctor_Patient (id_doctor, nom, prenom, date_naissance, cnie, ville, telephone, adresse, sexe)
VALUES
(3, 'Chevalier', 'Anne', '1983-05-02', 'PC001', 'Marseille', '0700000021', '6 rue Alpha', 'F'),
(3, 'Perrot', 'Lucas', '1990-10-13', 'PC002', 'Marseille', '0700000022', '7 rue Beta', 'M'),
(3, 'Lefevre', 'Nina', '1995-04-20', 'PC003', 'Marseille', '0700000023', '8 rue Gamma', 'F'),
(3, 'Renard', 'Thomas', '1988-12-31', 'PC004', 'Marseille', '0700000024', '9 rue Delta', 'M'),
(3, 'Benoit', 'Laura', '2001-07-07', 'PC005', 'Marseille', '0700000025', '10 rue Epsilon', 'F');

-- ======================
-- 💊 MÉDICAMENTS (2 par patient)
-- ======================
INSERT INTO Medicament (id_doctor, cnie_patient, nom, date_debut, periode, description)
VALUES
-- Dupont
(1, 'PA001', 'Doliprane', '2024-05-01', 7, 'Forte fièvre'),
(1, 'PA001', 'Ibuprofène', '2024-05-08', 5, 'Douleurs musculaires'),
(1, 'PA002', 'Amlodipine', '2024-05-02', 30, 'Tension artérielle'),
-- Martin
(2, 'PB001', 'Zyrtec', '2024-05-01', 10, 'Allergies'),
(2, 'PB002', 'Crème corticoïde', '2024-05-02', 14, 'Eczéma'),
(2, 'PB003', 'Antifongique', '2024-05-03', 10, 'Mycose'),
-- Nguyen
(3, 'PC001', 'Vitamine D', '2024-05-01', 30, 'Renforcement osseux'),
(3, 'PC002', 'Paracétamol', '2024-05-02', 5, 'Maux de tête'),
(3, 'PC003', 'Oméprazole', '2024-05-03', 14, 'Reflux gastrique');

-- ======================
-- 🧪 TESTS MÉDICAUX
-- ======================
INSERT INTO test_medical (id_doctor, cnie_patient, type_test, resultat, date_test, type_doctor, statut)
VALUES
-- Dupont
(1, 'PA001', 'ECG', 'Normale', '2024-04-10', 'interne', 'effectué'),
(1, 'PA002', 'Échographie cardiaque', 'Légère hypertrophie', '2024-04-12', 'interne', 'effectué'),
-- Martin
(2, 'PB001', 'Biopsie de peau', 'Bénigne', '2024-04-05', 'externe', 'effectué'),
(2, 'PB002', 'Allergotest', 'Réaction positive à pollen', '2024-04-07', 'interne', 'effectué'),
-- Nguyen
(3, 'PC001', 'Prise de sang', 'Niveau fer bas', '2024-04-03', 'interne', 'effectué'),
(3, 'PC002', 'Scanner', 'Rien à signaler', '2024-04-06', 'externe', 'effectué');

-- ======================
-- 🧪 PATIENTS
-- ======================
-- Patients du docteur Dupont
INSERT INTO Patient (nom, prenom, cnie, mot_de_passe, date_naissance) VALUES
('Lemoine', 'Alice', 'PA001', 'motdepasse123', '1990-04-12'),
('Durand', 'Michel', 'PA002', 'motdepasse123', '1985-06-30'),
('Roux', 'Claire', 'PA003', 'motdepasse123', '1993-09-15'),
('Morel', 'David', 'PA004', 'motdepasse123', '1980-01-22'),
('Petit', 'Emma', 'PA005', 'motdepasse123', '2000-11-05');

-- Patients du docteur Martin
INSERT INTO Patient (nom, prenom, cnie, mot_de_passe, date_naissance) VALUES
('Girard', 'Luc', 'PB001', 'motdepasse456', '1987-07-10'),
('Baron', 'Julie', 'PB002', 'motdepasse456', '1991-03-25'),
('Garnier', 'Louis', 'PB003', 'motdepasse456', '1975-12-08'),
('Noël', 'Sarah', 'PB004', 'motdepasse456', '1996-02-18'),
('Lemoine', 'Kevin', 'PB005', 'motdepasse456', '2002-09-01');

-- Patients du docteur Nguyen
INSERT INTO Patient (nom, prenom, cnie, mot_de_passe, date_naissance) VALUES
('Chevalier', 'Anne', 'PC001', 'motdepasse789', '1983-05-02'),
('Perrot', 'Lucas', 'PC002', 'motdepasse789', '1990-10-13'),
('Lefevre', 'Nina', 'PC003', 'motdepasse789', '1995-04-20'),
('Renard', 'Thomas', 'PC004', 'motdepasse789', '1988-12-31'),
('Benoit', 'Laura', 'PC005', 'motdepasse789', '2001-07-07');

-- ======================
-- 🧪 RENDEZ VOUS
-- ======================

-- Rendez-vous pour les patients du docteur Dupont (id = 1)
INSERT INTO Rendez_vous (id_doctor, cnie_patient, date_rendez_vous, heure, raison, statut) VALUES
(1, 'PA001', '2024-06-01', '09:00:00', 'Suivi tension', 'à venir'),
(1, 'PA002', '2024-06-01', '10:00:00', 'Résultat ECG', 'à venir'),
(1, 'PA003', '2024-06-02', '11:00:00', 'Consultation générale', 'à venir');

-- Rendez-vous pour les patients du docteur Martin (id = 2)
INSERT INTO Rendez_vous (id_doctor, cnie_patient, date_rendez_vous, heure, raison, statut) VALUES
(2, 'PB001', '2024-06-03', '09:30:00', 'Suivi allergie', 'présent'),
(2, 'PB002', '2024-06-03', '10:30:00', 'Dermatite chronique', 'absent'),
(2, 'PB003', '2024-06-04', '11:30:00', 'Eczéma', 'à venir');

-- Rendez-vous pour les patients du docteur Nguyen (id = 3)
INSERT INTO Rendez_vous (id_doctor, cnie_patient, date_rendez_vous, heure, raison, statut) VALUES
(3, 'PC001', '2024-06-05', '08:30:00', 'Résultats prise de sang', 'à venir'),
(3, 'PC002', '2024-06-05', '09:30:00', 'Scanner diagnostic', 'présent'),
(3, 'PC003', '2024-06-06', '10:30:00', 'Douleurs gastriques', 'à venir');
