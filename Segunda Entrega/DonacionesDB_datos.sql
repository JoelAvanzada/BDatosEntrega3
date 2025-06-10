-- Programas
INSERT INTO Programa (id_programa, nombre, descripcion)
VALUES 
('EDU001', 'Educación', 'Apoyo escolar'),
('SAL002', 'Salud', 'Atención médica primaria'),
('NUT003', 'Nutrición', 'Alimentación infantil'),
('DEP004', 'Deporte', 'Actividades deportivas'),
('ADM005', 'Admin', 'Actividades administrativas');

-- Padrinos (5)
INSERT INTO Padrino (dni, nombre, apellido, direccion, cod_postal, email, facebook, tel_fijo, celular, fecha_nacimiento)
VALUES 
('10000001', 'Ana', 'Martínez', 'Belgrano 123', '1406', 'ana.martinez@mail.com', 'anamartinez', '40112233', '1155551234', '1985-04-12'),
('10000002', 'Luis', 'Ramírez', 'Corrientes 456', '1001', 'luis.ramirez@mail.com', NULL, NULL, '1167894561', '1979-11-23'),
('10000003', 'María', 'López', 'Santa Fe 789', '1200', 'maria.lopez@mail.com', 'marial', '43002233', '1176543210', '1992-08-30'),
('10000004', 'Carlos', 'Gómez', 'Mitre 1010', '1033', 'carlos.gomez@mail.com', 'cgomez', NULL, '1156789123', '1980-10-10'),
('10000005', 'Lucía', 'Silva', 'Perón 2222', '1045', 'lucia.silva@mail.com', NULL, NULL, '1144455566', '1995-12-12');

-- MedioPago (5)
INSERT INTO MedioPago (nombre_titular)
VALUES 
('Ana Martínez'), ('Luis Ramírez'), ('María López'), ('Carlos Gómez'), ('Lucía Silva');

-- Donantes
INSERT INTO Donante (dni, cuil, ocupacion, id_pago)
VALUES 
('10000001', '27-10000001-1', 'Docente', 1),
('10000002', '20-10000002-3', 'Ingeniero', 2),
('10000003', '23-10000003-2', 'Estudiante', 3),
('10000004', '27-10000004-9', 'Administrador', 4),
('10000005', '20-10000005-5', 'Diseñadora', 5);

-- Contactos
INSERT INTO Contacto (dni, fecha_primer_contacto, fecha_de_alta, fecha_de_baja, fecha_rechazo_adhesion, estado)
VALUES 
('10000001', '2023-01-10', '2023-02-01', NULL, NULL, 'Adherido'),
('10000002', '2023-01-15', '2023-02-10', NULL, NULL, 'Amigo'),
('10000003', '2023-01-20', NULL, NULL, '2023-02-01', 'No acepta'),
('10000004', '2023-01-25', '2023-02-15', NULL, NULL, 'En gestión'),
('10000005', '2023-02-01', NULL, NULL, NULL, 'Sin llamar');

-- Aporta
INSERT INTO Aporta (dni_d, id_programa, monto, frecuencia)
VALUES 
-- Ana aporta a 3 programas → cumple consulta
('10000001', 'EDU001', 4000, 'mensual'),
('10000001', 'SAL002', 3000, 'mensual'),
('10000001', 'NUT003', 3200, 'mensual'),

-- Luis aporta a 1 programa
('10000002', 'SAL002', 2000, 'mensual'),

-- María aporta a 2 programas
('10000003', 'NUT003', 1500, 'mensual'),
('10000003', 'DEP004', 1800, 'semestral'),

-- Carlos aporta a 3 programas → cumple consulta
('10000004', 'DEP004', 3500, 'mensual'),
('10000004', 'SAL002', 2700, 'mensual'),
('10000004', 'EDU001', 3100, 'mensual'),

-- Lucía aporta a 1 programa
('10000005', 'NUT003', 2300, 'mensual');
