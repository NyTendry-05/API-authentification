CREATE DATABASE utilisateur;
\c utilisateur

CREATE TABLE Utilisateur(
   id_utilisateur SERIAL,
   email VARCHAR(50) ,
   identifiant VARCHAR(50) ,
   mot_de_passe VARCHAR(255) ,
   is_valide BOOLEAN,
   code_validation VARCHAR(255) ,
   PRIMARY KEY(id_utilisateur),
   UNIQUE(email),
   UNIQUE(identifiant)
);
