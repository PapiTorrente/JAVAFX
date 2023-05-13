SET NAMES 'latin1';
DROP DATABASE IF EXISTS clubNautico;
CREATE DATABASE IF NOT EXISTS clubNautico DEFAULT CHARACTER SET utf8;
USE clubNautico;

CREATE TABLE socios(
matricula_socio			INTEGER NOT NULL AUTO_INCREMENT,
nombres_socio			VARCHAR(40) BINARY NOT NULL,
ap_paterno_socio		VARCHAR(20) NOT NULL,
ap_materno_socio 		VARCHAR(20) NOT NULL,
es_socio_dueno			BOOLEAN DEFAULT FALSE,
fecha_nacimiento_socio	DATE NOT NULL,
no_celular_socio		VARCHAR(10) NOT NULL,

CONSTRAINT minimo_4_caract_ns CHECK (CHAR_LENGTH(nombres_socio)>3),
CONSTRAINT minimo_3_caract_apps CHECK (CHAR_LENGTH(ap_paterno_socio)>2),
CONSTRAINT minimo_3_caract_apms CHECK (CHAR_LENGTH(ap_materno_socio)>2),

PRIMARY KEY(matricula_socio)
)DEFAULT CHARACTER SET utf8;

CREATE TABLE patrones(
matricula_patron		INTEGER NOT NULL AUTO_INCREMENT,
nombres_patron			VARCHAR(40) NOT NULL,
ap_paterno_patron		VARCHAR(20) NOT NULL,
ap_materno_patron 		VARCHAR(20) NOT NULL,
es_patron_dueno			BOOLEAN DEFAULT FALSE,
fecha_nacimiento_patron	DATE NOT NULL,
no_celular_patron		VARCHAR(10) NOT NULL,

CONSTRAINT minimo_4_caract_np CHECK (CHAR_LENGTH(nombres_patron)>3),
CONSTRAINT minimo_3_caract_appp CHECK (CHAR_LENGTH(ap_paterno_patron)>2),
CONSTRAINT minimo_3_caract_apmp CHECK (CHAR_LENGTH(ap_materno_patron)>2),

PRIMARY KEY(matricula_patron)
)DEFAULT CHARACTER SET utf8;

CREATE TABLE destinos(
salida_dest				DATETIME,
llegada_dest			DATETIME NOT NULL,
puerto_dest				VARCHAR(30),

PRIMARY KEY(salida_dest, llegada_dest)
)DEFAULT CHARACTER SET utf8;

CREATE TABLE barcos(
no_serie_barco			INTEGER(5) NOT NULL AUTO_INCREMENT,
nom_barco				VARCHAR(40) NOT NULL,
cuota					VARCHAR(5) NOT NULL,
no_amarre				INTEGER NOT NULL,
matricula_socio			INTEGER NOT NULL,
matricula_patron 		INTEGER NOT NULL,
salida_barcos			DATETIME NOT NULL,
llegada_barcos			DATETIME NOT NULL,

CONSTRAINT solo_pesos CHECK(cuota LIKE '$%'),

PRIMARY KEY(no_serie_barco),
FOREIGN KEY(matricula_socio) REFERENCES socios(matricula_socio),
FOREIGN KEY(matricula_patron) REFERENCES patrones(matricula_patron)
)DEFAULT CHARACTER SET utf8;

CREATE TABLE destinos_Barcos(
no_serie_barco_d		INTEGER(5) NOT NULL,
salida_barcos 			DATETIME NOT NULL,
llegada_barcos			DATETIME NOT NULL,

FOREIGN KEY(salida_barcos, llegada_barcos) REFERENCES destinos(salida_dest, llegada_dest),
FOREIGN KEY(no_serie_barco_d) REFERENCES barcos(no_serie_barco)
)DEFAULT CHARACTER SET utf8;

source insertClub.sql;