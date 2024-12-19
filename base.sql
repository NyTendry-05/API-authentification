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

INSERT INTO Utilisateur (email, identifiant, mot_de_passe, is_valide, code_validation) 
VALUES ('user1@example.com', 'user1', 'password123', True, 'CODE123');

INSERT INTO Utilisateur (email, identifiant, mot_de_passe, is_valide, code_validation) 
VALUES ('user2@example.com', 'user2', 'password456', False, 'CODE456');

INSERT INTO Utilisateur (email, identifiant, mot_de_passe, is_valide, code_validation) 
VALUES ('user3@example.com', 'user3', 'securepass789', True, 'CODE789');