-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS DonacionesDB;
USE DonacionesDB;

-- Tablas

CREATE TABLE Padrino (
	dni VARCHAR(10) PRIMARY KEY NOT NULL,
	nombre VARCHAR(50) NOT NULL,
	apellido VARCHAR(50) NOT NULL,
	direccion VARCHAR(50),
	cod_postal VARCHAR(10),
	email VARCHAR(100),
	facebook VARCHAR(50),
	tel_fijo VARCHAR(20),
	celular VARCHAR(20),
	fecha_nacimiento DATE
);

CREATE TABLE MedioPago(
	id_pago INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
	nombre_titular VARCHAR(50)
);

CREATE TABLE Donante (
	dni VARCHAR(10) PRIMARY KEY NOT NULL,
	cuil VARCHAR(15) NOT NULL,
	ocupacion VARCHAR(50),
	id_pago INTEGER,
	FOREIGN KEY(dni) references Padrino (dni)
	ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (id_pago) REFERENCES MedioPago (id_pago)
            ON UPDATE CASCADE ON DELETE SET NULL,
	UNIQUE(cuil)
);

CREATE TABLE Contacto (
	dni VARCHAR(10) PRIMARY KEY NOT NULL,
	fecha_primer_contacto DATE,
	fecha_de_alta DATE,
	fecha_de_baja DATE,
	fecha_rechazo_adhesion DATE,
	estado VARCHAR(50),
	FOREIGN KEY (dni) references Padrino (dni)
	ON UPDATE CASCADE ON DELETE CASCADE 
);


CREATE TABLE Programa (
	id_programa VARCHAR(20) PRIMARY KEY NOT NULL,
	nombre VARCHAR(20),
	descripcion VARCHAR(50)
);

CREATE TABLE Aporta(
	dni_d VARCHAR(10),
	id_programa VARCHAR (20),
	monto INTEGER,
	frecuencia VARCHAR(50),
	PRIMARY KEY (dni_d, id_programa),
	FOREIGN KEY (dni_d) REFERENCES Donante(dni)
		ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (id_programa) REFERENCES Programa (id_programa)
		ON UPDATE CASCADE ON DELETE CASCADE

);

CREATE TABLE Credito(
	id_pago INTEGER,
    nombre_tarjeta VARCHAR(50),
    fecha_venc DATE,
    nro_tarjeta VARCHAR(20),
    FOREIGN KEY (id_pago) REFERENCES MedioPago (id_pago)
		ON UPDATE CASCADE ON DELETE CASCADE,
	UNIQUE(nro_tarjeta)
);

CREATE TABLE DebitoTransferencia(
	id_pago INTEGER,
    nombre_sucursal VARCHAR(20),
    tipo_cuenta VARCHAR(20),
    nro_cuenta VARCHAR(20),
    cbu VARCHAR(22),
    FOREIGN KEY (id_pago) REFERENCES MedioPago (id_pago)
		ON UPDATE CASCADE ON DELETE CASCADE,
	UNIQUE(cbu)
);

CREATE TABLE AuditoriaEliminacionDonante (
    id_auditoria INT AUTO_INCREMENT PRIMARY KEY,
    dni_donante VARCHAR(10),
    nombre VARCHAR(50),
    apellido VARCHAR(50),
    fecha_eliminacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    usuario VARCHAR(50)
);

-- restricciones adicionales, Trigger para el borrado y un check para el Estado

DELIMITER //

CREATE TRIGGER donante_delete
BEFORE DELETE ON Donante
FOR EACH ROW
BEGIN
    INSERT INTO AuditoriaEliminacionDonante (dni_donante, nombre, apellido, usuario)
    SELECT OLD.dni, p.nombre, p.apellido, CURRENT_USER(), NOW() 
    FROM Padrino p
    WHERE p.dni = OLD.dni;
END;
//

DELIMITER ;

ALTER TABLE Contacto
ADD CONSTRAINT estado_contacto
CHECK (estado IN (
    'Sin llamar', 'ERROR', 'En gestión', 'Adherido',
    'Amigo', 'No acepta', 'Baja', 'Voluntario'
));

ALTER TABLE Aporta
ADD CONSTRAINT frecuencia_aporte
CHECK (frecuencia IN (
    'mensual', 'semestral')
);