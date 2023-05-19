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
id_puerto				INTEGER NOT NULL AUTO_INCREMENT,
puerto_dest				VARCHAR(30),

PRIMARY KEY(id_puerto)
)DEFAULT CHARACTER SET utf8;

CREATE TABLE barcos(
no_serie_barco			INTEGER(5) NOT NULL AUTO_INCREMENT,
nom_barco				VARCHAR(40) NOT NULL,
cuota					VARCHAR(5) NOT NULL,
no_amarre				INTEGER NOT NULL,

CONSTRAINT solo_pesos CHECK(cuota LIKE '$%'),

PRIMARY KEY (no_serie_barco)
)DEFAULT CHARACTER SET utf8;

CREATE TABLE registros(
no_serie_barcoREGISTROS	INTEGER NOT NULL,
matricula_socioR		INTEGER NOT NULL,
matricula_patronR		INTEGER NOT NULL,
salida					DATETIME NOT NULL,
llegada					DATETIME NOT NULL,
id_puertoREGISTROS		INTEGER NOT NULL,


FOREIGN KEY(no_serie_barcoREGISTROS) REFERENCES barcos(no_serie_barco),
FOREIGN KEY(matricula_socioR) REFERENCES socios(matricula_socio),
FOREIGN KEY(matricula_patronR) REFERENCES patrones(matricula_patron),
FOREIGN KEY(id_puertoREGISTROS) REFERENCES destinos(id_puerto)
)DEFAULT CHARACTER SET utf8;


source insertClubv2.sql;