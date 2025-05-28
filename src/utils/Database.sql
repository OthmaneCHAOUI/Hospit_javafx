DROP DATABASE IF EXISTS cabinet_medical;
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
    cnie VARCHAR(15) NOT NULL,
    date_naissance DATE,
    ville VARCHAR(30),
    telephone VARCHAR(15),
    adresse VARCHAR(255),
    sexe ENUM('F', 'M') NOT NULL,
    UNIQUE (cnie),
    FOREIGN KEY (id_doctor) REFERENCES Doctor(id) ON DELETE CASCADE
) ENGINE=InnoDB ;

CREATE TABLE Rendez_vous (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_doctor INT NOT NULL,
    cnie_patient VARCHAR(15) NOT NULL,
    date_rendez_vous DATE NOT NULL,
    heure TIME,
    raison VARCHAR(255),
    statut ENUM('à venir', 'présent', 'absent') DEFAULT 'à venir',
    FOREIGN KEY (id_doctor) REFERENCES Doctor(id) ON DELETE CASCADE,
    -- FOREIGN KEY (cnie_patient) REFERENCES Doctor_Patient(cnie) ON DELETE CASCADE,
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
    -- FOREIGN KEY (cnie_patient) REFERENCES Doctor_Patient(cnie) ON DELETE CASCADE,
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
    -- FOREIGN KEY (cnie_patient) REFERENCES Doctor_Patient(cnie) ON DELETE CASCADE,
) ENGINE=InnoDB ;

-- Doctors
<<<<<<< HEAD
INSERT INTO Doctor (nom, prenom, cnie, specialite, nom_cabinet, telephone, adresse_cabinet, mot_de_passe) VALUES
('El Amrani', 'Youssef', 'AA123456', 'Cardiologie', 'Cabinet Atlas', '0612345678', 'Rue Mohammed V, Rabat', 'pass123'),
('Bennani', 'Fatima', 'BB234567', 'Dermatologie', 'Clinique Al Amal', '0623456789', 'Avenue Hassan II, Casablanca', 'pass456'),
('Ouazzani', 'Hicham', 'CC345678', 'Pédiatrie', 'Centre Médical Oujda', '0634567890', 'Boulevard Zerktouni, Oujda', 'pass789');

-- Patients
INSERT INTO Patient (nom, prenom, cnie, mot_de_passe, date_naissance) VALUES
('El Fassi', 'Sara', 'PA100001', 'sara123', '1995-03-12'),
('Bouazza', 'Omar', 'PA100002', 'omar456', '1988-07-21'),
('Alaoui', 'Imane', 'PA100003', 'imane789', '1992-11-05'),
('Naciri', 'Khalid', 'PA100004', 'khalid321', '1980-01-17'),
('Berrada', 'Salma', 'PA100005', 'salma654', '1999-09-09'),
('Tazi', 'Mohamed', 'PA100006', 'mohamed987', '1975-05-30'),
('Chakiri', 'Rachid', 'PA100007', 'rachid111', '1985-12-25'),
('Sbai', 'Meryem', 'PA100008', 'meryem222', '1997-04-14'),
('El Idrissi', 'Yassine', 'PA100009', 'yassine333', '1990-06-18'),
('Kabbaj', 'Laila', 'PA100010', 'laila444', '1993-08-23'),
('Zouiten', 'Hamza', 'PA100011', 'hamza555', '1982-02-02'),
('El Ghazali', 'Nadia', 'PA100012', 'nadia666', '1996-10-10'),
('Mouline', 'Said', 'PA100013', 'said777', '1987-03-03'),
('El Mansouri', 'Hajar', 'PA100014', 'hajar888', '1991-12-12'),
('Raji', 'Karim', 'PA100015', 'karim999', '1984-07-07'),
('El Khatib', 'Samira', 'PA100016', 'samira000', '1998-05-05'),
('Bennis', 'Youssef', 'PA100017', 'youssef101', '1994-09-19'),
('El Alaoui', 'Amina', 'PA100018', 'amina202', '1990-11-11'),
('El Yacoubi', 'Soufiane', 'PA100019', 'soufiane303', '1986-06-06'),
('El Hachimi', 'Rania', 'PA100020', 'rania404', '1997-01-01');

-- Doctor_Patient (linking doctors to patients, with extra info)
INSERT INTO Doctor_Patient (id_doctor, nom, prenom, date_naissance, cnie, ville, telephone, adresse, sexe) VALUES
(1, 'El Fassi', 'Sara', '1995-03-12', 'PA100001', 'Rabat', '0611111111', 'Quartier Agdal', 'F'),
(1, 'Bouazza', 'Omar', '1988-07-21', 'PA100002', 'Rabat', '0611111112', 'Avenue Annakhil', 'M'),
(1, 'Alaoui', 'Imane', '1992-11-05', 'PA100003', 'Rabat', '0611111113', 'Rue Oued Fes', 'F'),
(2, 'Naciri', 'Khalid', '1980-01-17', 'PA100004', 'Casablanca', '0622222221', 'Maarif', 'M'),
(2, 'Berrada', 'Salma', '1999-09-09', 'PA100005', 'Casablanca', '0622222222', 'Sidi Maarouf', 'F'),
(2, 'Tazi', 'Mohamed', '1975-05-30', 'PA100006', 'Casablanca', '0622222223', 'Oasis', 'M'),
(2, 'Chakiri', 'Rachid', '1985-12-25', 'PA100007', 'Casablanca', '0622222224', 'Bourgogne', 'M'),
(3, 'Sbai', 'Meryem', '1997-04-14', 'PA100008', 'Oujda', '0633333331', 'Hay Al Qods', 'F'),
(3, 'El Idrissi', 'Yassine', '1990-06-18', 'PA100009', 'Oujda', '0633333332', 'Hay Salam', 'M'),
(3, 'Kabbaj', 'Laila', '1993-08-23', 'PA100010', 'Oujda', '0633333333', 'Hay Nahda', 'F'),
(1, 'Zouiten', 'Hamza', '1982-02-02', 'PA100011', 'Rabat', '0611111114', 'Avenue Fal Ould Oumeir', 'M'),
(1, 'El Ghazali', 'Nadia', '1996-10-10', 'PA100012', 'Rabat', '0611111115', 'Avenue France', 'F'),
(2, 'Mouline', 'Said', '1987-03-03', 'PA100013', 'Casablanca', '0622222225', 'Hay Hassani', 'M'),
(2, 'El Mansouri', 'Hajar', '1991-12-12', 'PA100014', 'Casablanca', '0622222226', 'Ain Sebaa', 'F'),
(3, 'Raji', 'Karim', '1984-07-07', 'PA100015', 'Oujda', '0633333334', 'Hay El Fath', 'M'),
(3, 'El Khatib', 'Samira', '1998-05-05', 'PA100016', 'Oujda', '0633333335', 'Hay Al Baraka', 'F'),
(1, 'Bennis', 'Youssef', '1994-09-19', 'PA100017', 'Rabat', '0611111116', 'Avenue Annasr', 'M'),
(2, 'El Alaoui', 'Amina', '1990-11-11', 'PA100018', 'Casablanca', '0622222227', 'Hay Riad', 'F'),
(3, 'El Yacoubi', 'Soufiane', '1986-06-06', 'PA100019', 'Oujda', '0633333336', 'Hay Salam', 'M'),
(1, 'El Hachimi', 'Rania', '1997-01-01', 'PA100020', 'Rabat', '0611111117', 'Avenue Ibn Sina', 'F');

-- Rendez_vous (appointments)
INSERT INTO Rendez_vous (id_doctor, cnie_patient, date_rendez_vous, heure, raison, statut) VALUES
(1, 'PA100001', '2025-06-01', '09:00:00', 'Contrôle annuel', 'à venir'),
(1, 'PA100002', '2025-06-02', '10:00:00', 'Douleurs thoraciques', 'à venir'),
(2, 'PA100005', '2025-06-03', '11:00:00', 'Problèmes de peau', 'à venir'),
(2, 'PA100006', '2025-06-04', '12:00:00', 'Consultation', 'à venir'),
(3, 'PA100008', '2025-06-05', '13:00:00', 'Vaccination', 'à venir'),
(3, 'PA100009', '2025-06-06', '14:00:00', 'Fièvre', 'à venir');

-- Medicament (prescriptions)
INSERT INTO Medicament (id_doctor, cnie_patient, nom, date_debut, periode, description) VALUES
(1, 'PA100001', 'Aspirine', '2025-05-20', 10, 'Prendre 1 comprimé par jour'),
(1, 'PA100002', 'Atorvastatine', '2025-05-21', 30, 'Pour le cholestérol'),
(2, 'PA100005', 'Crème hydratante', '2025-05-22', 15, 'Appliquer matin et soir'),
(3, 'PA100008', 'Paracétamol', '2025-05-23', 5, 'En cas de douleur'),
(3, 'PA100009', 'Ibuprofène', '2025-05-24', 7, 'Après les repas');

-- test_medical (medical tests)
INSERT INTO test_medical (id_doctor, cnie_patient, type_test, resultat, date_test, type_doctor, statut) VALUES
(1, 'PA100001', 'ECG', 'Normal', '2025-05-25', 'interne', 'effectué'),
(2, 'PA100005', 'Biopsie', 'En attente', '2025-05-26', 'externe', 'en attente'),
(3, 'PA100008', 'Test sanguin', 'Anémie détectée', '2025-05-27', 'interne', 'effectué');
=======
INSERT INTO Doctor (nom, prenom, cnie, specialite, nom_cabinet, telephone, adresse_cabinet, mot_de_passe)
VALUES 
('Benali', 'Youssef', 'D123456', 'Cardiologie', 'Cabinet Atlas', '0612345678', '10 Rue Atlas, Fes', 'pass1'),
('El Amrani', 'Sara', 'D654321', 'Dermatologie', 'Cabinet Oasis', '0623456789', '20 Rue Oasis, Rabat', 'pass2'),
('Mouline', 'Omar', 'D789012', 'Pédiatrie', 'Cabinet Enfant', '0634567890', '30 Rue Enfant, Casa', 'pass3');

-- Patients
INSERT INTO Patient (nom, prenom, cnie, mot_de_passe, date_naissance) VALUES
('Ait', 'Ali', 'P1001', 'p1pass', '1990-01-01'),
('Bennani', 'Salma', 'P1002', 'p2pass', '1985-02-02'),
('Chakiri', 'Hassan', 'P1003', 'p3pass', '1978-03-03'),
('Draoui', 'Imane', 'P1004', 'p4pass', '1992-04-04'),
('El Idrissi', 'Karim', 'P1005', 'p5pass', '1980-05-05'),
('Fassi', 'Meryem', 'P1006', 'p6pass', '1995-06-06'),
('Ghazali', 'Younes', 'P1007', 'p7pass', '1988-07-07'),
('Hajji', 'Fatima', 'P1008', 'p8pass', '1991-08-08'),
('Iraqi', 'Othman', 'P1009', 'p9pass', '1975-09-09'),
('Jabri', 'Nadia', 'P1010', 'p10pass', '1983-10-10'),
('Kabbaj', 'Samir', 'P1011', 'p11pass', '1996-11-11'),
('Lahlou', 'Rania', 'P1012', 'p12pass', '1987-12-12'),
('Mansouri', 'Ayoub', 'P1013', 'p13pass', '1993-01-13'),
('Naciri', 'Siham', 'P1014', 'p14pass', '1982-02-14'),
('Ouazzani', 'Mohamed', 'P1015', 'p15pass', '1979-03-15'),
('Qadiri', 'Leila', 'P1016', 'p16pass', '1994-04-16'),
('Raji', 'Said', 'P1017', 'p17pass', '1986-05-17'),
('Slaoui', 'Hiba', 'P1018', 'p18pass', '1997-06-18'),
('Touhami', 'Yassine', 'P1019', 'p19pass', '1984-07-19'),
('Zahidi', 'Soukaina', 'P1020', 'p20pass', '1998-08-20');

-- Doctor_Patient (linking each patient to a doctor)
INSERT INTO Doctor_Patient (id_doctor, nom, prenom, cnie, date_naissance, ville, telephone, adresse, sexe) VALUES
(1, 'Ait', 'Ali', 'P1001', '1990-01-01', 'Fes', '0600000001', '1 Rue Fes', 'M'),
(1, 'Bennani', 'Salma', 'P1002', '1985-02-02', 'Fes', '0600000002', '2 Rue Fes', 'F'),
(1, 'Chakiri', 'Hassan', 'P1003', '1978-03-03', 'Fes', '0600000003', '3 Rue Fes', 'M'),
(1, 'Draoui', 'Imane', 'P1004', '1992-04-04', 'Fes', '0600000004', '4 Rue Fes', 'F'),
(2, 'El Idrissi', 'Karim', 'P1005', '1980-05-05', 'Rabat', '0600000005', '5 Rue Rabat', 'M'),
(2, 'Fassi', 'Meryem', 'P1006', '1995-06-06', 'Rabat', '0600000006', '6 Rue Rabat', 'F'),
(2, 'Ghazali', 'Younes', 'P1007', '1988-07-07', 'Rabat', '0600000007', '7 Rue Rabat', 'M'),
(2, 'Hajji', 'Fatima', 'P1008', '1991-08-08', 'Rabat', '0600000008', '8 Rue Rabat', 'F'),
(2, 'Iraqi', 'Othman', 'P1009', '1975-09-09', 'Rabat', '0600000009', '9 Rue Rabat', 'M'),
(2, 'Jabri', 'Nadia', 'P1010', '1983-10-10', 'Rabat', '0600000010', '10 Rue Rabat', 'F'),
(3, 'Kabbaj', 'Samir', 'P1011', '1996-11-11', 'Casa', '0600000011', '11 Rue Casa', 'M'),
(3, 'Lahlou', 'Rania', 'P1012', '1987-12-12', 'Casa', '0600000012', '12 Rue Casa', 'F'),
(3, 'Mansouri', 'Ayoub', 'P1013', '1993-01-13', 'Casa', '0600000013', '13 Rue Casa', 'M'),
(3, 'Naciri', 'Siham', 'P1014', '1982-02-14', 'Casa', '0600000014', '14 Rue Casa', 'F'),
(3, 'Ouazzani', 'Mohamed', 'P1015', '1979-03-15', 'Casa', '0600000015', '15 Rue Casa', 'M'),
(3, 'Qadiri', 'Leila', 'P1016', '1994-04-16', 'Casa', '0600000016', '16 Rue Casa', 'F'),
(1, 'Raji', 'Said', 'P1017', '1986-05-17', 'Fes', '0600000017', '17 Rue Fes', 'M'),
(2, 'Slaoui', 'Hiba', 'P1018', '1997-06-18', 'Rabat', '0600000018', '18 Rue Rabat', 'F'),
(3, 'Touhami', 'Yassine', 'P1019', '1984-07-19', 'Casa', '0600000019', '19 Rue Casa', 'M'),
(1, 'Zahidi', 'Soukaina', 'P1020', '1998-08-20', 'Fes', '0600000020', '20 Rue Fes', 'F');

-- Rendez_vous (appointments)
INSERT INTO Rendez_vous (id_doctor, cnie_patient, date_rendez_vous, heure, raison, statut) VALUES
(1, 'P1001', '2025-06-01', '09:00:00', 'Contrôle', 'à venir'),
(1, 'P1002', '2025-06-02', '10:00:00', 'Consultation', 'à venir'),
(2, 'P1005', '2025-06-03', '11:00:00', 'Suivi', 'à venir'),
(2, 'P1006', '2025-06-04', '12:00:00', 'Dermatologie', 'à venir'),
(3, 'P1011', '2025-06-05', '13:00:00', 'Vaccin', 'à venir'),
(3, 'P1012', '2025-06-06', '14:00:00', 'Contrôle', 'à venir');

-- Medicament (prescriptions)
INSERT INTO Medicament (id_doctor, cnie_patient, nom, date_debut, periode, description) VALUES
(1, 'P1001', 'Paracetamol', '2025-06-01', 5, 'Douleur légère'),
(1, 'P1002', 'Ibuprofen', '2025-06-02', 7, 'Anti-inflammatoire'),
(2, 'P1005', 'Cetrizine', '2025-06-03', 10, 'Allergie'),
(2, 'P1006', 'Amoxicilline', '2025-06-04', 7, 'Infection'),
(3, 'P1011', 'Vitamine D', '2025-06-05', 30, 'Carence'),
(3, 'P1012', 'Paracetamol', '2025-06-06', 5, 'Fièvre');

-- test_medical (medical tests)
INSERT INTO test_medical (id_doctor, cnie_patient, type_test, resultat, date_test, type_doctor, statut) VALUES
(1, 'P1001', 'ECG', 'Normal', '2025-06-01', 'interne', 'effectué'),
(1, 'P1002', 'Prise de sang', 'Anémie détectée', '2025-06-02', 'interne', 'effectué'),
(2, 'P1005', 'Allergie', 'Poussière', '2025-06-03', 'externe', 'en attente'),
(2, 'P1006', 'Biopsie', 'En cours', '2025-06-04', 'externe', 'en attente'),
(3, 'P1011', 'Radiographie', 'Normal', '2025-06-05', 'interne', 'effectué'),
(3, 'P1012', 'Test auditif', 'Légère perte', '2025-06-06', 'interne', 'effectué');
>>>>>>> structure/review
