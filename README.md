# 🩺 Application de Suivi de Traitements Médicaux

## 🎯 Objectif du Projet

Cette application permet aux docteurs de gérer les dossiers de leurs patients et les traitements médicaux associés. Les patients peuvent consulter leurs informations médicales de manière.

---

## 👤 Utilisateurs

### 1. Docteur

- Se connecter à son espace personnel
- Ajouter, modifier et supprimer des patients
- Créer, modifier et supprimer des traitements
- Visualiser l’historique médical des patients
- Ajouter les résultats de tests médicaux
- Supprimer un patient (et toutes ses données associées)

### 2. Patient

- Se connecter à son espace personnel
- Consulter ses traitements en cours ou passés
- Voir ses résultats de tests médicaux

---

## 🔧 Fonctionnalités par Utilisateur

### 👨‍⚕️ Docteur

#### Fonctionnalité : Ajout d’un patient

- **Objectif** : Ajouter un nouveau patient dans la base.
- **Règles de gestion** :
  1. Le champ Nom est obligatoire.
  2. Le numéro de CNI doit être unique.
  3. L’âge est calculé automatiquement via la date de naissance.
  4. Le mot de passe initial doit contenir au moins 8 caractères.

#### Fonctionnalité : Consultation de la fiche d’un patient

- **Objectif** : Visualiser les informations médicales du patient.
- **Règles de gestion** :
  - Accessible uniquement pour les docteurs connectés.
  - Affichage en lecture seule sauf en mode édition.

#### Fonctionnalité : Ajout d’un traitement médical

- **Objectif** : Prescrire un traitement à un patient.
- **Règles de gestion** :
  - Nom du médicament obligatoire.
  - Durée de traitement et posologie requises.
  - Date de début automatiquement définie à aujourd’hui.

#### Fonctionnalité : Enregistrement d’un test médical

- **Objectif** : Ajouter un résultat de test/examen.
- **Règles de gestion** :
  - Type de test et date sont obligatoires.
  - Résultat sous forme de texte ou fichier joint.

#### Fonctionnalité : Suppression d’un patient

- **Objectif** : Supprimer un patient ainsi que ses données.
- **Règles de gestion** :
  - Confirmation requise avant suppression.
  - Suppression de toutes les données liées.
  - Patient supprimé ne peut plus se connecter.

---

### 🧑‍⚕️ Patient

#### Fonctionnalité : Connexion à l’espace personnel

- **Objectif** : Accéder à ses données personnelles.
- **Règles de gestion** :
  - Authentification par CNI et mot de passe.
  - Blocage temporaire après 3 échecs.
  - Fonction de réinitialisation disponible.

#### Fonctionnalité : Consultation des traitements

- **Objectif** : Voir les traitements prescrits.
- **Règles de gestion** :
  - Accès en lecture seule.
  - Affichage chronologique.

#### Fonctionnalité : Consultation des résultats de tests

- **Objectif** : Voir les résultats des examens médicaux.
- **Règles de gestion** :
  - Accès uniquement à ses propres résultats.
  - Fichiers disponibles en téléchargement.

---

## 🛠️ Technologies Utilisées

- **JavaFX** (interface graphique)
- **JDBC** (connexion base de données)
- **Java** (POO, logique métier)

---

## ✅ Fonctionnalités Incluses

- Gestion CRUD des patients et traitements
- Interface conviviale en JavaFX
- Tableaux et listes dynamiques (TableView, ListView)
- Filtres et recherche par nom ou type de traitement
- Dialogues de confirmation, notifications d'erreurs
- Export possible des données (CSV/PDF)

---

## 📈 Évolutions Possibles

- Ajout de rôle secrétaire (si besoin dans le futur)
- Intégration avec un service web distant
- Ajout des statistiques
- La possibilité d'importer des dossiers patients
- Application mobile ou version web
- ameliorer la securite : injection SQL, backdoors...

---

## 📅 Équipe et Crédits

Projet réalisé dans le cadre du module Javafx
Encadré par : Mme OUADI Hayat

## 📦 Structure du Projet

```shell
Hospit
├── README.md
├── .gitignore
├── src
│   ├── controller
│   │   ├── DoctorDashboardController.java
│   │   ├── DoctorFormController.java
│   │   ├── DoctorMTRController.java
│   │   ├── LoginController.java
│   │   ├── PatientDashboardController.java
│   │   └── PatientFormController.java
│   ├── main
│   │   └── Main.java
│   ├── model
│   │   ├── DBConnection.java
│   │   ├── DoctorDAO.java
│   │   ├── Doctor.java
│   │   ├── DoctorPatientDAO.java
│   │   ├── DoctorPatient.java
│   │   ├── MedicamentDAO.java
│   │   ├── Medicament.java
│   │   ├── PatientDAO.java
│   │   ├── Patient.java
│   │   ├── RendezVousDAO.java
│   │   ├── RendezVous.java
│   │   ├── TestMedicalDAO.java
│   │   └── TestMedical.java
│   ├── resources
│   │   ├── 1564491_add_create_new_plus_icon.png
│   │   ├── 211618_c_left_arrow_icon.png
│   │   ├── 326497_account_circle_icon.png
│   │   ├── 3669476_add_circle_ic_icon.png
│   │   ├── 392505_eye_preview_see_seen_view_icon.png
│   │   ├── 4829862_arrow_left_icon.png
│   │   ├── 4829864_arrow_back_left_icon.png
│   │   ├── 9042444_import_icon.png
│   │   ├── 9110784_circle_x_icon.png
│   │   ├── add.png
│   │   ├── AppIcon.png
│   │   ├── delete.png
│   │   ├── edit.png
│   │   ├── export.png
│   │   ├── group.png
│   │   ├── iconmonstr-key-8-48.png
│   │   ├── info.png
│   │   ├── search-interface-symbol.png
│   │   ├── settings.png
│   │   └── user.png
│   ├── style
│   │   ├── connexionStyle.css
│   │   ├── doctorDashboardStyle.css
│   │   ├── formDoctor.css
│   │   ├── formPatient.css
│   │   └── patientDashboardStyle.css
│   ├── utils
│   │   ├── Database4.sql
│   │   ├── Database5.sql
│   │   └── Database.sql
│   └── view
│       ├── connexion_view.fxml
│       ├── doctor_dashboard_view.fxml
│       ├── doctor_form.fxml
│       ├── DoctorMTR_view.fxml
│       ├── patient_dashboard_view.fxml
│       └── patient_form.fxml
```