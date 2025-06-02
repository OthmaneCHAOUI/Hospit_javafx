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
    
    UNIQUE (id_doctor, cnie),
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

    FOREIGN KEY (id_doctor) REFERENCES Doctor(id) ON DELETE CASCADE,
    UNIQUE (cnie_patient, nom, date_debut, id_doctor)
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
('Ouazzani', 'Hamza', 'CC345678', 'Pediatre', 'Cabinet Nour', '0634567890', 'Boulevard Zerktouni, Fes', 'pass789');

-- Patients
INSERT INTO Patient (nom, prenom, cnie, mot_de_passe, date_naissance, ville, telephone, adresse, sexe, email) VALUES
('Bennani', 'Fatima', 'PA111111', 'fatima123', '1990-05-12', 'Rabat', '0678901234', 'Avenue Annakhil, Rabat', 'F', 'fatima.bennani@gmail.com'),
('El Idrissi', 'Omar', 'PA222222', 'omar456', '1985-11-23', 'Casablanca', '0689012345', 'Rue Taddart, Casablanca', 'M', 'omar.idrissi@gmail.com'),
('Alaoui', 'Khadija', 'PA333333', 'khadija789', '1995-03-30', 'Fes', '0690123456', 'Quartier Atlas, Fes', 'F', 'khadija.alaoui@gmail.com'),
('Mouline', 'Yassine', 'PA444444', 'yassine321', '1988-07-19', 'Marrakech', '0678123456', 'Rue Lalla Yacout, Marrakech', 'M', 'yassine.mouline@gmail.com'),
('Zouiten', 'Sara', 'PA555555', 'sara654', '1992-09-25', 'Agadir', '0667890123', 'Avenue Hassan II, Agadir', 'F', 'sara.zouiten@gmail.com'),
('Tahiri', 'Mohamed', 'PA666666', 'mohamed987', '1980-01-15', 'Tanger', '0656789012', 'Boulevard Pasteur, Tanger', 'M', 'mohamed.tahiri@gmail.com'),
('El Fassi', 'Imane', 'PA777777', 'imane852', '1998-12-05', 'Meknes', '0645678901', 'Rue Volubilis, Meknes', 'F', 'imane.elfassi@gmail.com'),
('Raji', 'Soufiane', 'PA888888', 'soufiane741', '1991-03-22', 'Oujda', '0634567890', 'Avenue Maghreb Arabe, Oujda', 'M', 'soufiane.raji@gmail.com'),
('Kabbaj', 'Nadia', 'PA999999', 'nadia159', '1987-06-10', 'Kenitra', '0623456789', 'Quartier Maamora, Kenitra', 'F', 'nadia.kabbaj@gmail.com'),
('Sbai', 'Hicham', 'PA101010', 'hicham753', '1993-08-18', 'El Jadida', '0612345678', 'Avenue Bir Anzarane, El Jadida', 'M', 'hicham.sbai@gmail.com'),
('El Mansouri', 'Salwa', 'PA111112', 'salwa951', '1996-11-29', 'Tetouan', '0678912345', 'Rue Moulay Abbas, Tetouan', 'F', 'salwa.elmansouri@gmail.com'),
('Bennis', 'Karim', 'PA121212', 'karim357', '1989-02-14', 'Safi', '0689123456', 'Avenue OCP, Safi', 'M', 'karim.bennis@gmail.com'),
('Chraibi', 'Laila', 'PA131313', 'laila258', '1994-04-07', 'Settat', '0691234567', 'Quartier El Massira, Settat', 'F', 'laila.chraibi@gmail.com'),
('El Ghazali', 'Rachid', 'PA141414', 'rachid654', '1982-10-21', 'Khouribga', '0678123490', 'Rue Oued Zem, Khouribga', 'M', 'rachid.elghazali@gmail.com'),
('Tazi', 'Meryem', 'PA151515', 'meryem147', '1997-01-03', 'Nador', '0667890132', 'Avenue Ennakhil, Nador', 'F', 'meryem.tazi@gmail.com');

-- Liaisons Doctor_Patient (chaque docteur suit 5 patients)
INSERT INTO Doctor_Patient (id_doctor, nom, prenom, cnie, date_naissance, ville, telephone, adresse, sexe, remarque) VALUES
(1, 'Bennani', 'Fatima', 'PA111111', '1990-05-12', 'Rabat', '0678901234', 'Avenue Annakhil, Rabat', 'F', 'Allergique a la penicilline'),
(1, 'El Idrissi', 'Omar', 'PA222222', '1985-11-23', 'Casablanca', '0689012345', 'Rue Taddart, Casablanca', 'M', 'Diabetique'),
(1, 'Alaoui', 'Khadija', 'PA333333', '1995-03-30', 'Fes', '0690123456', 'Quartier Atlas, Fes', 'F', 'Asthme chronique'),
(1, 'Mouline', 'Yassine', 'PA444444', '1988-07-19', 'Marrakech', '0678123456', 'Rue Lalla Yacout, Marrakech', 'M', ''),
(1, 'Zouiten', 'Sara', 'PA555555', '1992-09-25', 'Agadir', '0667890123', 'Avenue Hassan II, Agadir', 'F', ''),

(2, 'Tahiri', 'Mohamed', 'PA666666', '1980-01-15', 'Tanger', '0656789012', 'Boulevard Pasteur, Tanger', 'M', ''),
(2, 'El Fassi', 'Imane', 'PA777777', '1998-12-05', 'Meknes', '0645678901', 'Rue Volubilis, Meknes', 'F', ''),
(2, 'Raji', 'Soufiane', 'PA888888', '1991-03-22', 'Oujda', '0634567890', 'Avenue Maghreb Arabe, Oujda', 'M', ''),
(2, 'Kabbaj', 'Nadia', 'PA999999', '1987-06-10', 'Kenitra', '0623456789', 'Quartier Maamora, Kenitra', 'F', ''),
(2, 'Sbai', 'Hicham', 'PA101010', '1993-08-18', 'El Jadida', '0612345678', 'Avenue Bir Anzarane, El Jadida', 'M', ''),

(3, 'El Mansouri', 'Salwa', 'PA111112', '1996-11-29', 'Tetouan', '0678912345', 'Rue Moulay Abbas, Tetouan', 'F', ''),
(3, 'Bennis', 'Karim', 'PA121212', '1989-02-14', 'Safi', '0689123456', 'Avenue OCP, Safi', 'M', ''),
(3, 'Chraibi', 'Laila', 'PA131313', '1994-04-07', 'Settat', '0691234567', 'Quartier El Massira, Settat', 'F', ''),
(3, 'El Ghazali', 'Rachid', 'PA141414', '1982-10-21', 'Khouribga', '0678123490', 'Rue Oued Zem, Khouribga', 'M', ''),
(3, 'Tazi', 'Meryem', 'PA151515', '1997-01-03', 'Nador', '0667890132', 'Avenue Ennakhil, Nador', 'F', '');

-- Rendez-vous (chaque patient a au moins un rendez-vous avec son docteur)
INSERT INTO Rendez_vous (id_doctor, cnie_patient, date_rendez_vous, heure, raison, statut) VALUES
(1, 'PA111111', '2024-06-10', '10:00:00', 'Controle annuel', 'a venir'),
(1, 'PA222222', '2024-06-11', '11:00:00', 'Suivi diabete', 'a venir'),
(1, 'PA333333', '2024-06-12', '09:30:00', 'Asthme', 'a venir'),
(1, 'PA444444', '2024-06-13', '14:00:00', 'Consultation generale', 'a venir'),
(1, 'PA555555', '2024-06-14', '15:30:00', 'Douleurs abdominales', 'a venir'),

(2, 'PA666666', '2024-06-15', '10:15:00', 'Dermatite', 'a venir'),
(2, 'PA777777', '2024-06-16', '11:45:00', 'Allergie', 'a venir'),
(2, 'PA888888', '2024-06-17', '09:00:00', 'Eczema', 'a venir'),
(2, 'PA999999', '2024-06-18', '13:30:00', 'Boutons', 'a venir'),
(2, 'PA101010', '2024-06-19', '16:00:00', 'Rougeurs', 'a venir'),

(3, 'PA111112', '2024-06-20', '10:30:00', 'Vaccination', 'a venir'),
(3, 'PA121212', '2024-06-21', '12:00:00', 'Fievre', 'a venir'),
(3, 'PA131313', '2024-06-22', '09:45:00', 'Toux', 'a venir'),
(3, 'PA141414', '2024-06-23', '15:00:00', 'Consultation pediatrique', 'a venir'),
(3, 'PA151515', '2024-06-24', '11:30:00', 'Suivi croissance', 'a venir');

-- Medicaments (exemples pour chaque patient)
INSERT INTO Medicament (id_doctor, cnie_patient, nom, date_debut, periode, description) VALUES
(1, 'PA111111', 'Paracetamol', '2024-06-10', 7, '1 comprime matin et soir'),
(1, 'PA222222', 'Metformine', '2024-06-11', 30, '500mg matin et soir'),
(1, 'PA333333', 'Ventoline', '2024-06-12', 15, '2 bouffees en cas de crise'),
(1, 'PA444444', 'Ibuprofene', '2024-06-13', 5, '400mg apres repas'),
(1, 'PA555555', 'Spasfon', '2024-06-14', 3, '2 comprimes par jour'),

(2, 'PA666666', 'Cicalfate', '2024-06-15', 10, 'Appliquer matin et soir'),
(2, 'PA777777', 'Aerius', '2024-06-16', 7, '1 comprime par jour'),
(2, 'PA888888', 'Dermoval', '2024-06-17', 14, 'Creme, 2 fois par jour'),
(2, 'PA999999', 'Fucidine', '2024-06-18', 7, 'Appliquer sur les boutons'),
(2, 'PA101010', 'Hydrocortisone', '2024-06-19', 5, 'Creme sur les rougeurs'),

(3, 'PA111112', 'Infanrix Hexa', '2024-06-20', 1, 'Vaccin'),
(3, 'PA121212', 'Doliprane', '2024-06-21', 3, 'Suppositoire 300mg'),
(3, 'PA131313', 'Toplexil', '2024-06-22', 5, '5ml le soir'),
(3, 'PA141414', 'Augmentin', '2024-06-23', 7, '1g matin et soir'),
(3, 'PA151515', 'Fer Inofer', '2024-06-24', 30, '1 comprime par jour');

-- Tests medicaux (exemples pour chaque patient)
INSERT INTO Test_Medical (id_doctor, cnie_patient, type_test, resultat, date_test, type_doctor, statut) VALUES
(1, 'PA111111', 'Analyse sanguine', 'Normale', '2024-06-10', 'interne', 'effectue'),
(1, 'PA222222', 'Glycemie', 'Elevee', '2024-06-11', 'interne', 'effectue'),
(1, 'PA333333', 'Spirometrie', 'Asthme leger', '2024-06-12', 'interne', 'effectue'),
(1, 'PA444444', 'ECG', 'Normal', '2024-06-13', 'interne', 'effectue'),
(1, 'PA555555', 'Echographie abdominale', 'RAS', '2024-06-14', 'externe', 'en attente'),

(2, 'PA666666', 'Biopsie cutanee', 'En attente', '2024-06-15', 'externe', 'en attente'),
(2, 'PA777777', 'Test allergique', 'Positif', '2024-06-16', 'interne', 'effectue'),
(2, 'PA888888', 'Prelevement cutane', 'Eczema confirme', '2024-06-17', 'interne', 'effectue'),
(2, 'PA999999', 'Dermatoscopie', 'Pas de lesion suspecte', '2024-06-18', 'interne', 'effectue'),
(2, 'PA101010', 'Patch test', 'Negatif', '2024-06-19', 'interne', 'effectue'),

(3, 'PA111112', 'Serologie', 'Immunise', '2024-06-20', 'interne', 'effectue'),
(3, 'PA121212', 'CRP', 'Normale', '2024-06-21', 'interne', 'effectue'),
(3, 'PA131313', 'Radio thorax', "Pas d'anomalie", '2024-06-22', 'externe', 'en attente'),
(3, 'PA141414', 'Hemogramme', 'Anemie legere', '2024-06-23', 'interne', 'effectue'),
(3, 'PA151515', 'Bilan ferritine', 'Normale', '2024-06-24', 'interne', 'effectue');

-- Medicaments supplementaires pour chaque patient
INSERT INTO Medicament (id_doctor, cnie_patient, nom, date_debut, periode, description) VALUES
(1, 'PA111111', 'Vitamine C', '2024-06-15', 10, '1 comprime par jour'),
(1, 'PA111111', 'Omeprazole', '2024-06-20', 7, '20mg matin a jeun'),
(1, 'PA111111', 'Magnesium', '2024-06-25', 15, '1 sachet matin et soir'),

(1, 'PA222222', 'Insuline', '2024-06-16', 30, 'Injection quotidienne'),
(1, 'PA222222', 'Aspirine', '2024-06-21', 10, '100mg par jour'),
(1, 'PA222222', 'Vitamine D', '2024-06-26', 20, '1 ampoule/semaine'),

(1, 'PA333333', 'Singulair', '2024-06-17', 30, '1 comprime le soir'),
(1, 'PA333333', 'Clarityne', '2024-06-22', 15, '1 comprime par jour'),
(1, 'PA333333', 'Flixotide', '2024-06-27', 10, '2 bouffees matin et soir'),

(1, 'PA444444', 'Amoxicilline', '2024-06-18', 7, '500mg matin et soir'),
(1, 'PA444444', 'Doliprane', '2024-06-23', 5, '1 comprime matin et soir'),
(1, 'PA444444', 'Zyrtec', '2024-06-28', 7, '1 comprime par jour'),

(1, 'PA555555', 'Gaviscon', '2024-06-19', 10, 'Apres chaque repas'),
(1, 'PA555555', 'Motilium', '2024-06-24', 7, 'Avant repas'),
(1, 'PA555555', 'Smecta', '2024-06-29', 5, '3 sachets/jour'),

(2, 'PA666666', 'Eucerin', '2024-06-20', 15, 'Creme matin et soir'),
(2, 'PA666666', 'Zyrtec', '2024-06-25', 10, '1 comprime par jour'),
(2, 'PA666666', 'Bepanthen', '2024-06-30', 7, 'Appliquer localement'),

(2, 'PA777777', 'Polaramine', '2024-06-21', 7, '1 comprime soir'),
(2, 'PA777777', 'Cetirizine', '2024-06-26', 10, '1 comprime par jour'),
(2, 'PA777777', 'Advantan', '2024-07-01', 5, "Creme sur l'allergie"),

(2, 'PA888888', 'Bioderma', '2024-06-22', 14, 'Appliquer matin et soir'),
(2, 'PA888888', 'Efferalgan', '2024-06-27', 5, '1 comprime si fievre'),
(2, 'PA888888', 'Mometasone', '2024-07-02', 7, 'Creme soir'),

(2, 'PA999999', 'Ketoderm', '2024-06-23', 10, 'Appliquer matin et soir'),
(2, 'PA999999', 'Zovirax', '2024-06-28', 7, 'Creme sur lesions'),
(2, 'PA999999', 'Loratadine', '2024-07-03', 14, '1 comprime par jour'),

(2, 'PA101010', 'Dafalgan', '2024-06-24', 5, '1 comprime si douleur'),
(2, 'PA101010', 'Dermoval', '2024-06-29', 10, 'Creme matin et soir'),
(2, 'PA101010', 'Cicalfate', '2024-07-04', 7, 'Sur zones rouges'),

(3, 'PA111112', 'ZincoVit', '2024-06-25', 15, '1 cuillere par jour'),
(3, 'PA111112', 'Vitamine A', '2024-06-30', 10, '1 goutte par jour'),
(3, 'PA111112', 'Paracetamol', '2024-07-05', 5, 'Suppositoire si fievre'),

(3, 'PA121212', 'Amoxil', '2024-06-26', 7, '250mg matin et soir'),
(3, 'PA121212', 'Ferograd', '2024-07-01', 30, '1 comprime par jour'),
(3, 'PA121212', 'Vitamine C', '2024-07-06', 10, '1 sachet matin'),

(3, 'PA131313', 'Rhinathiol', '2024-06-27', 7, '5ml matin et soir'),
(3, 'PA131313', 'Clarix', '2024-07-02', 5, '5ml le soir'),
(3, 'PA131313', 'Doliprane', '2024-07-07', 3, 'Suppositoire si fievre'),

(3, 'PA141414', 'Ferrostrane', '2024-06-28', 20, '1 cuillere matin'),
(3, 'PA141414', 'Bactrim', '2024-07-03', 7, '1 comprime matin et soir'),
(3, 'PA141414', 'Vitamine B12', '2024-07-08', 10, 'Injection 1/semaine'),

(3, 'PA151515', 'Tardyferon', '2024-06-29', 30, '1 comprime matin'),
(3, 'PA151515', 'Calcium', '2024-07-04', 15, '1 comprime soir'),
(3, 'PA151515', 'Zinc', '2024-07-09', 20, '1 comprime par jour');

-- Rendez-vous supplementaires pour chaque patient
INSERT INTO Rendez_vous (id_doctor, cnie_patient, date_rendez_vous, heure, raison, statut) VALUES
(1, 'PA111111', '2024-07-01', '09:00:00', 'Suivi traitement', 'a venir'),
(1, 'PA111111', '2024-07-15', '10:30:00', 'Controle general', 'a venir'),
(1, 'PA111111', '2024-08-01', '11:00:00', 'Resultats analyses', 'a venir'),

(1, 'PA222222', '2024-07-02', '09:30:00', 'Controle glycemie', 'a venir'),
(1, 'PA222222', '2024-07-16', '11:15:00', 'Prescription insuline', 'a venir'),
(1, 'PA222222', '2024-08-02', '12:00:00', 'Bilan trimestriel', 'a venir'),

(1, 'PA333333', '2024-07-03', '10:00:00', 'Asthme suivi', 'a venir'),
(1, 'PA333333', '2024-07-17', '09:45:00', 'Renouvellement ordonnance', 'a venir'),
(1, 'PA333333', '2024-08-03', '10:30:00', 'Bilan respiratoire', 'a venir'),

(1, 'PA444444', '2024-07-04', '14:30:00', 'Consultation douleur', 'a venir'),
(1, 'PA444444', '2024-07-18', '15:00:00', 'Resultat examens', 'a venir'),
(1, 'PA444444', '2024-08-04', '11:30:00', 'Suivi general', 'a venir'),

(1, 'PA555555', '2024-07-05', '16:00:00', 'Douleurs abdominales', 'a venir'),
(1, 'PA555555', '2024-07-19', '10:00:00', 'Suivi traitement', 'a venir'),
(1, 'PA555555', '2024-08-05', '09:30:00', 'Consultation', 'a venir'),

(2, 'PA666666', '2024-07-06', '10:15:00', 'Controle dermatologique', 'a venir'),
(2, 'PA666666', '2024-07-20', '11:30:00', 'Resultat biopsie', 'a venir'),
(2, 'PA666666', '2024-08-06', '12:15:00', 'Suivi traitement', 'a venir'),

(2, 'PA777777', '2024-07-07', '13:00:00', 'Test allergique', 'a venir'),
(2, 'PA777777', '2024-07-21', '14:15:00', 'Controle', 'a venir'),
(2, 'PA777777', '2024-08-07', '15:30:00', 'Bilan allergie', 'a venir'),

(2, 'PA888888', '2024-07-08', '09:00:00', 'Eczema suivi', 'a venir'),
(2, 'PA888888', '2024-07-22', '10:45:00', 'Prescription creme', 'a venir'),
(2, 'PA888888', '2024-08-08', '11:20:00', 'Consultation', 'a venir'),

(2, 'PA999999', '2024-07-09', '13:30:00', 'Dermatoscopie', 'a venir'),
(2, 'PA999999', '2024-07-23', '15:00:00', 'Resultat test', 'a venir'),
(2, 'PA999999', '2024-08-09', '16:15:00', 'Suivi', 'a venir'),

(2, 'PA101010', '2024-07-10', '09:15:00', 'Patch test', 'a venir'),
(2, 'PA101010', '2024-07-24', '10:30:00', 'Consultation', 'a venir'),
(2, 'PA101010', '2024-08-10', '11:45:00', 'Resultat test', 'a venir'),

(3, 'PA111112', '2024-07-11', '10:30:00', 'Vaccin rappel', 'a venir'),
(3, 'PA111112', '2024-07-25', '12:00:00', 'Controle pediatrique', 'a venir'),
(3, 'PA111112', '2024-08-11', '09:50:00', 'Bilan sante', 'a venir'),

(3, 'PA121212', '2024-07-12', '13:15:00', 'Fievre suivi', 'a venir'),
(3, 'PA121212', '2024-07-26', '14:30:00', 'Prescription fer', 'a venir'),
(3, 'PA121212', '2024-08-12', '15:45:00', 'Consultation', 'a venir'),

(3, 'PA131313', '2024-07-13', '10:45:00', 'Toux suivi', 'a venir'),
(3, 'PA131313', '2024-07-27', '11:00:00', 'Renouvellement ordonnance', 'a venir'),
(3, 'PA131313', '2024-08-13', '12:15:00', 'Bilan', 'a venir'),

(3, 'PA141414', '2024-07-14', '15:00:00', 'Consultation anemie', 'a venir'),
(3, 'PA141414', '2024-07-28', '16:30:00', 'Resultat hemogramme', 'a venir'),
(3, 'PA141414', '2024-08-14', '09:30:00', 'Suivi', 'a venir'),

(3, 'PA151515', '2024-07-15', '11:30:00', 'Suivi croissance', 'a venir'),
(3, 'PA151515', '2024-07-29', '13:00:00', 'Controle general', 'a venir'),
(3, 'PA151515', '2024-08-15', '10:00:00', 'Consultation', 'a venir');

-- Tests medicaux supplementaires pour chaque patient
INSERT INTO Test_Medical (id_doctor, cnie_patient, type_test, resultat, date_test, type_doctor, statut) VALUES
(1, 'PA111111', 'Bilan lipidique', 'Normale', '2024-07-01', 'interne', 'en attente'),
(1, 'PA111111', 'Ionogramme', 'En cours', '2024-07-10', 'externe', 'en attente'),
(1, 'PA111111', 'Bilan hepatique', 'Normale', '2024-07-20', 'interne', 'effectue'),

(1, 'PA222222', 'HbA1c', '8.2%', '2024-07-02', 'interne', 'effectue'),
(1, 'PA222222', 'ECG', 'Normal', '2024-07-12', 'externe', 'en attente'),
(1, 'PA222222', 'Microalbuminurie', 'Normale', '2024-07-22', 'interne', 'effectue'),

(1, 'PA333333', 'Radiographie thorax', 'Normale', '2024-07-03', 'externe', 'en attente'),
(1, 'PA333333', 'Test effort', 'Bonne tolerance', '2024-07-13', 'interne', 'effectue'),
(1, 'PA333333', 'Gaz du sang', 'Normale', '2024-07-23', 'interne', 'en attente'),

(1, 'PA444444', 'Echographie abdominale', 'RAS', '2024-07-04', 'externe', 'en attente'),
(1, 'PA444444', 'Bilan renal', 'Normale', '2024-07-14', 'interne', 'effectue'),
(1, 'PA444444', 'NFS', 'Normale', '2024-07-24', 'interne', 'effectue'),

(1, 'PA555555', 'Scanner abdo', 'En attente', '2024-07-05', 'externe', 'en attente'),
(1, 'PA555555', 'CRP', 'Normale', '2024-07-15', 'interne', 'effectue'),
(1, 'PA555555', 'Amylasemie', 'Normale', '2024-07-25', 'interne', 'effectue'),

(2, 'PA666666', 'Mycose cutanee', 'Negatif', '2024-07-06', 'interne', 'effectue'),
(2, 'PA666666', 'IgE totales', 'Haute', '2024-07-16', 'externe', 'en attente'),
(2, 'PA666666', 'Bilan allergie', 'En attente', '2024-07-26', 'interne', 'en attente'),

(2, 'PA777777', 'Prick test', 'Positif', '2024-07-07', 'interne', 'effectue'),
(2, 'PA777777', 'Bilan immuno', 'Normale', '2024-07-17', 'externe', 'en attente'),
(2, 'PA777777', 'Biopsie', 'En attente', '2024-07-27', 'interne', 'en attente'),

(2, 'PA888888', 'PCR viral', 'Negatif', '2024-07-08', 'interne', 'effectue'),
(2, 'PA888888', 'Culture bacterienne', 'Negatif', '2024-07-18', 'externe', 'en attente'),
(2, 'PA888888', 'Bilan hepatique', 'Normale', '2024-07-28', 'interne', 'effectue'),

(2, 'PA999999', 'Patch test', 'Negatif', '2024-07-09', 'interne', 'effectue'),
(2, 'PA999999', 'Bilan allergie', 'En attente', '2024-07-19', 'externe', 'en attente'),
(2, 'PA999999', 'Serologie HSV', 'Negatif', '2024-07-29', 'interne', 'effectue'),

(2, 'PA101010', 'Test urticaire', 'Negatif', '2024-07-10', 'interne', 'effectue'),
(2, 'PA101010', 'Bilan immunitaire', 'Normale', '2024-07-20', 'externe', 'en attente'),
(2, 'PA101010', 'Prelevement cutane', 'En attente', '2024-07-30', 'interne', 'en attente'),

(3, 'PA111112', 'Bilan nutrition', 'Normale', '2024-07-11', 'interne', 'effectue'),
(3, 'PA111112', 'Serologie rougeole', 'Immunise', '2024-07-21', 'externe', 'en attente'),
(3, 'PA111112', 'NFS', 'Normale', '2024-07-31', 'interne', 'effectue'),

(3, 'PA121212', 'Bilan fer', 'Faible', '2024-07-12', 'interne', 'effectue'),
(3, 'PA121212', 'TSH', 'Normale', '2024-07-22', 'externe', 'en attente'),
(3, 'PA121212', 'CRP', 'Normale', '2024-08-01', 'interne', 'effectue'),

(3, 'PA131313', 'Radio sinus', 'Normale', '2024-07-13', 'externe', 'en attente'),
(3, 'PA131313', 'Bilan viral', 'Negatif', '2024-07-23', 'interne', 'effectue'),
(3, 'PA131313', 'Ionogramme', 'Normale', '2024-08-02', 'interne', 'en attente'),

(3, 'PA141414', 'Bilan renal', 'Normale', '2024-07-14', 'interne', 'effectue'),
(3, 'PA141414', 'Echographie abdo', 'RAS', '2024-07-24', 'externe', 'en attente'),
(3, 'PA141414', 'Hemoglobine', 'Basse', '2024-08-03', 'interne', 'effectue'),

(3, 'PA151515', 'Ferritine', 'Normale', '2024-07-15', 'interne', 'effectue'),
(3, 'PA151515', 'Serologie', 'Negatif', '2024-07-25', 'externe', 'en attente'),
(3, 'PA151515', 'Bilan nutrition', 'Normale', '2024-08-04', 'interne', 'effectue');