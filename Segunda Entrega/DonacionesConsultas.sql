-- CONSULTAS DE LA BASE DE DATOS
-- a) Devolver por cada programa, el total de aportes mensuales.


SELECT p.id_programa, p.nombre, p.descripcion, COUNT(a.id_programa) AS total_aportes FROM 
       (Programa p LEFT JOIN Aporta a ON p.id_programa = a.id_programa AND a.frecuencia = 'MENSUAL')
       GROUP BY p.id_programa, p.nombre, p.descripcion;

-- b) Devolver los donantes que aportan a más de dos programas.

-- usar GROUP BY con filtro having.

SELECT d.dni, p.nombre, p.apellido, COUNT(a.id_programa) AS cantidad_programas 
	   FROM (Donante d JOIN Padrino p ON d.dni = p.dni JOIN Aporta a ON d.dni = a.dni_d)
	   GROUP BY d.dni, p.nombre, p.apellido HAVING COUNT(a.id_programa) > 2;

-- c) Listado de Donantes con aportes mensuales y los datos de los medios de
-- pago..

-- Hay varias formas de hacerlo, con un left/right join dejando campos en NULL o haciendo tres tablas
-- Dos con cada metodo de pago y la ultima con la tabla madr

-- SELECT dni, nombre, apellido, direccion, cod_postal, email, facebook, tel_fijo, celular, fecha_nacimiento
-- 	   cuil, ocupacion, id_pago FROM (Padrino NATURAL JOIN Donante NATURAL JOIN Programa NATURAL JOIN Aporta) WHERE frecuencia = 'MENSUAL';
       
       