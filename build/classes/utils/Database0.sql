DROP DATABASE IF EXISTS cabinet_medical;
CREATE DATABASE IF NOT EXISTS cabinet_medical;
USE cabinet_medical;

-- Table des docteurs avec compte
CREATE TABLE Doctor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(30) NOT NULL,
    prenom VARCHAR(30) NOT NULL,
    cnie VARCHAR(15) NOT NULL UNIQUE,
    specialite VARCHAR(50) NOT NULL,
    nom_cabinet VARCHAR(50) NOT NULL,
    telephone VARCHAR(15),
    adresse_cabinet VARCHAR(255),
    mot_de_passe VARCHAR(255) NOT NULL,
    -- email VARCHAR(40),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- pour la date de creation de compte
) ENGINE=InnoDB;

-- Table des patients avec compte
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
    email VARCHAR(40),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Table des patients suivi par docteur (dossier interne du docteur)
CREATE TABLE Doctor_Patient (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_doctor INT NOT NULL,
    -- infos patient
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
    
    -- UNIQUE (id_doctor, cnie),
    FOREIGN KEY (id_doctor) REFERENCES Doctor(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Table des rendez-vous
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
    UNIQUE (date_rendez_vous, cnie_patient, heure)
) ENGINE=InnoDB;

-- Table des médicaments
CREATE TABLE Medicament (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_doctor INT NOT NULL,
    cnie_patient VARCHAR(15) NOT NULL,
    nom VARCHAR(100) NOT NULL,
    date_debut DATE NOT NULL,
    periode INT,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (id_doctor) REFERENCES Doctor(id) ON DELETE CASCADE
    -- UNIQUE (cnie_patient, nom, date_debut, id_doctor)
) ENGINE=InnoDB;

-- Table des tests médicaux
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
) ENGINE=InnoDB;

-- Docteurs
INSERT INTO Doctor (nom, prenom, cnie, specialite, nom_cabinet, telephone, adresse_cabinet, mot_de_passe) VALUES
('El Amrani', 'Youssef', 'AA123456', 'Cardiologue', 'Cabinet Al Amal', '0612345678', 'Rue Hassan II, Casablanca', 'pass123'),
('Berrada', 'Salma', 'BB234567', 'Dermatologue', 'Cabinet Chifa', '0623456789', 'Avenue Mohammed V, Rabat', 'pass456'),
('Ouazzani', 'Hamza', 'CC345678', 'Pédiatre', 'Cabinet Nour', '0634567890', 'Boulevard Zerktouni, Fès', 'pass789');

-- Patients
INSERT INTO Patient (nom, prenom, cnie, mot_de_passe, date_naissance, ville, telephone, adresse, sexe, email) VALUES
('Bennani', 'Fatima', 'PA111111', 'fatima123', '1990-05-12', 'Rabat', '0678901234', 'Avenue Annakhil, Rabat', 'F', 'fatima.bennani@gmail.com'),
('El Idrissi', 'Omar', 'PA222222', 'omar456', '1985-11-23', 'Casablanca', '0689012345', 'Rue Taddart, Casablanca', 'M', 'omar.idrissi@gmail.com'),
('Alaoui', 'Khadija', 'PA333333', 'khadija789', '1995-03-30', 'Fès', '0690123456', 'Quartier Atlas, Fès', 'F', 'khadija.alaoui@gmail.com'),
('Mouline', 'Yassine', 'PA444444', 'yassine321', '1988-07-19', 'Marrakech', '0678123456', 'Rue Lalla Yacout, Marrakech', 'M', 'yassine.mouline@gmail.com'),
('Zouiten', 'Sara', 'PA555555', 'sara654', '1992-09-25', 'Agadir', '0667890123', 'Avenue Hassan II, Agadir', 'F', 'sara.zouiten@gmail.com'),
('Tahiri', 'Mohamed', 'PA666666', 'mohamed987', '1980-01-15', 'Tanger', '0656789012', 'Boulevard Pasteur, Tanger', 'M', 'mohamed.tahiri@gmail.com'),
('El Fassi', 'Imane', 'PA777777', 'imane852', '1998-12-05', 'Meknès', '0645678901', 'Rue Volubilis, Meknès', 'F', 'imane.elfassi@gmail.com'),
('Raji', 'Soufiane', 'PA888888', 'soufiane741', '1991-03-22', 'Oujda', '0634567890', 'Avenue Maghreb Arabe, Oujda', 'M', 'soufiane.raji@gmail.com'),
('Kabbaj', 'Nadia', 'PA999999', 'nadia159', '1987-06-10', 'Kenitra', '0623456789', 'Quartier Maamora, Kenitra', 'F', 'nadia.kabbaj@gmail.com'),
('Sbai', 'Hicham', 'PA101010', 'hicham753', '1993-08-18', 'El Jadida', '0612345678', 'Avenue Bir Anzarane, El Jadida', 'M', 'hicham.sbai@gmail.com'),
('El Mansouri', 'Salwa', 'PA111112', 'salwa951', '1996-11-29', 'Tétouan', '0678912345', 'Rue Moulay Abbas, Tétouan', 'F', 'salwa.elmansouri@gmail.com'),
('Bennis', 'Karim', 'PA121212', 'karim357', '1989-02-14', 'Safi', '0689123456', 'Avenue OCP, Safi', 'M', 'karim.bennis@gmail.com'),
('Chraibi', 'Laila', 'PA131313', 'laila258', '1994-04-07', 'Settat', '0691234567', 'Quartier El Massira, Settat', 'F', 'laila.chraibi@gmail.com'),
('El Ghazali', 'Rachid', 'PA141414', 'rachid654', '1982-10-21', 'Khouribga', '0678123490', 'Rue Oued Zem, Khouribga', 'M', 'rachid.elghazali@gmail.com'),
('Tazi', 'Meryem', 'PA151515', 'meryem147', '1997-01-03', 'Nador', '0667890132', 'Avenue Ennakhil, Nador', 'F', 'meryem.tazi@gmail.com');

-- Liaisons Doctor_Patient (chaque docteur suit 5 patients)
INSERT INTO Doctor_Patient (id_doctor, nom, prenom, cnie, date_naissance, ville, telephone, adresse, sexe, remarque) VALUES
(1, 'Bennani', 'Fatima', 'PA111111', '1990-05-12', 'Rabat', '0678901234', 'Avenue Annakhil, Rabat', 'F', 'Allergique à la pénicilline'),
(1, 'El Idrissi', 'Omar', 'PA222222', '1985-11-23', 'Casablanca', '0689012345', 'Rue Taddart, Casablanca', 'M', 'Diabétique'),
(1, 'Alaoui', 'Khadija', 'PA333333', '1995-03-30', 'Fès', '0690123456', 'Quartier Atlas, Fès', 'F', 'Asthme chronique'),
(1, 'Mouline', 'Yassine', 'PA444444', '1988-07-19', 'Marrakech', '0678123456', 'Rue Lalla Yacout, Marrakech', 'M', ''),
(1, 'Zouiten', 'Sara', 'PA555555', '1992-09-25', 'Agadir', '0667890123', 'Avenue Hassan II, Agadir', 'F', ''),

(2, 'Tahiri', 'Mohamed', 'PA666666', '1980-01-15', 'Tanger', '0656789012', 'Boulevard Pasteur, Tanger', 'M', ''),
(2, 'El Fassi', 'Imane', 'PA777777', '1998-12-05', 'Meknès', '0645678901', 'Rue Volubilis, Meknès', 'F', ''),
(2, 'Raji', 'Soufiane', 'PA888888', '1991-03-22', 'Oujda', '0634567890', 'Avenue Maghreb Arabe, Oujda', 'M', ''),
(2, 'Kabbaj', 'Nadia', 'PA999999', '1987-06-10', 'Kenitra', '0623456789', 'Quartier Maamora, Kenitra', 'F', ''),
(2, 'Sbai', 'Hicham', 'PA101010', '1993-08-18', 'El Jadida', '0612345678', 'Avenue Bir Anzarane, El Jadida', 'M', ''),

(3, 'El Mansouri', 'Salwa', 'PA111112', '1996-11-29', 'Tétouan', '0678912345', 'Rue Moulay Abbas, Tétouan', 'F', ''),
(3, 'Bennis', 'Karim', 'PA121212', '1989-02-14', 'Safi', '0689123456', 'Avenue OCP, Safi', 'M', ''),
(3, 'Chraibi', 'Laila', 'PA131313', '1994-04-07', 'Settat', '0691234567', 'Quartier El Massira, Settat', 'F', ''),
(3, 'El Ghazali', 'Rachid', 'PA141414', '1982-10-21', 'Khouribga', '0678123490', 'Rue Oued Zem, Khouribga', 'M', ''),
(3, 'Tazi', 'Meryem', 'PA151515', '1997-01-03', 'Nador', '0667890132', 'Avenue Ennakhil, Nador', 'F', '');

-- Rendez-vous (chaque patient a au moins un rendez-vous avec son docteur)
INSERT INTO Rendez_vous (id_doctor, cnie_patient, date_rendez_vous, heure, raison, statut) VALUES
(1, 'PA111111', '2024-06-10', '10:00:00', 'Contrôle annuel', 'à venir'),
(1, 'PA222222', '2024-06-11', '11:00:00', 'Suivi diabète', 'à venir'),
(1, 'PA333333', '2024-06-12', '09:30:00', 'Asthme', 'à venir'),
(1, 'PA444444', '2024-06-13', '14:00:00', 'Consultation générale', 'à venir'),
(1, 'PA555555', '2024-06-14', '15:30:00', 'Douleurs abdominales', 'à venir'),

(2, 'PA666666', '2024-06-15', '10:15:00', 'Dermatite', 'à venir'),
(2, 'PA777777', '2024-06-16', '11:45:00', 'Allergie', 'à venir'),
(2, 'PA888888', '2024-06-17', '09:00:00', 'Eczéma', 'à venir'),
(2, 'PA999999', '2024-06-18', '13:30:00', 'Boutons', 'à venir'),
(2, 'PA101010', '2024-06-19', '16:00:00', 'Rougeurs', 'à venir'),

(3, 'PA111112', '2024-06-20', '10:30:00', 'Vaccination', 'à venir'),
(3, 'PA121212', '2024-06-21', '12:00:00', 'Fièvre', 'à venir'),
(3, 'PA131313', '2024-06-22', '09:45:00', 'Toux', 'à venir'),
(3, 'PA141414', '2024-06-23', '15:00:00', 'Consultation pédiatrique', 'à venir'),
(3, 'PA151515', '2024-06-24', '11:30:00', 'Suivi croissance', 'à venir');

-- Médicaments (exemples pour chaque patient)
INSERT INTO Medicament (id_doctor, cnie_patient, nom, date_debut, periode, description) VALUES
(1, 'PA111111', 'Paracétamol', '2024-06-10', 7, '1 comprimé matin et soir'),
(1, 'PA222222', 'Metformine', '2024-06-11', 30, '500mg matin et soir'),
(1, 'PA333333', 'Ventoline', '2024-06-12', 15, '2 bouffées en cas de crise'),
(1, 'PA444444', 'Ibuprofène', '2024-06-13', 5, '400mg après repas'),
(1, 'PA555555', 'Spasfon', '2024-06-14', 3, '2 comprimés par jour'),

(2, 'PA666666', 'Cicalfate', '2024-06-15', 10, 'Appliquer matin et soir'),
(2, 'PA777777', 'Aerius', '2024-06-16', 7, '1 comprimé par jour'),
(2, 'PA888888', 'Dermoval', '2024-06-17', 14, 'Crème, 2 fois par jour'),
(2, 'PA999999', 'Fucidine', '2024-06-18', 7, 'Appliquer sur les boutons'),
(2, 'PA101010', 'Hydrocortisone', '2024-06-19', 5, 'Crème sur les rougeurs'),

(3, 'PA111112', 'Infanrix Hexa', '2024-06-20', 1, 'Vaccin'),
(3, 'PA121212', 'Doliprane', '2024-06-21', 3, 'Suppositoire 300mg'),
(3, 'PA131313', 'Toplexil', '2024-06-22', 5, '5ml le soir'),
(3, 'PA141414', 'Augmentin', '2024-06-23', 7, '1g matin et soir'),
(3, 'PA151515', 'Fer Inofer', '2024-06-24', 30, '1 comprimé par jour');

-- Tests médicaux (exemples pour chaque patient)
INSERT INTO Test_Medical (id_doctor, cnie_patient, type_test, resultat, date_test, type_doctor, statut) VALUES
(1, 'PA111111', 'Analyse sanguine', 'Normale', '2024-06-10', 'interne', 'effectué'),
(1, 'PA222222', 'Glycémie', 'Élevée', '2024-06-11', 'interne', 'effectué'),
(1, 'PA333333', 'Spirométrie', 'Asthme léger', '2024-06-12', 'interne', 'effectué'),
(1, 'PA444444', 'ECG', 'Normal', '2024-06-13', 'interne', 'effectué'),
(1, 'PA555555', 'Échographie abdominale', 'RAS', '2024-06-14', 'externe', 'en attente'),

(2, 'PA666666', 'Biopsie cutanée', 'En attente', '2024-06-15', 'externe', 'en attente'),
(2, 'PA777777', 'Test allergique', 'Positif', '2024-06-16', 'interne', 'effectué'),
(2, 'PA888888', 'Prélèvement cutané', 'Eczéma confirmé', '2024-06-17', 'interne', 'effectué'),
(2, 'PA999999', 'Dermatoscopie', 'Pas de lésion suspecte', '2024-06-18', 'interne', 'effectué'),
(2, 'PA101010', 'Patch test', 'Négatif', '2024-06-19', 'interne', 'effectué'),

(3, 'PA111112', 'Sérologie', 'Immunisé', '2024-06-20', 'interne', 'effectué'),
(3, 'PA121212', 'CRP', 'Normale', '2024-06-21', 'interne', 'effectué'),
(3, 'PA131313', 'Radio thorax', "Pas d'anomalie", '2024-06-22', 'externe', 'en attente'),
(3, 'PA141414', 'Hémogramme', 'Anémie légère', '2024-06-23', 'interne', 'effectué'),
(3, 'PA151515', 'Bilan ferritine', 'Normale', '2024-06-24', 'interne', 'effectué');
