-- CONSULTAS DE LA BASE DE DATOS
-- a) Devolver por cada programa, el total de aportes mensuales.

SELECT Count(id_programa) FROM (Aporta NATURAL JOIN Programa) WHERE frecuencia = 'MENSUAL';

-- b) Devolver los donantes que aportan a más de dos programas.

-- usar GROUP BY con filtro having.

SELECT dni, nombre, apellido, direccion, cod_postal, email, facebook, tel_fijo, celular, fecha_nacimiento,
 	   cuil, ocupacion, id_pago FROM (Padrino NATURAL JOIN Donante NATURAL JOIN Programa) GROUP BY id


-- c) Listado de Donantes con aportes mensuales y los datos de los medios de
-- pago..

-- Hay varias formas de hacerlo, con un left/right join dejando campos en NULL o haciendo tres tablas
-- Dos con cada metodo de pago y la ultima con la tabla madr

SELECT dni, nombre, apellido, direccion, cod_postal, email, facebook, tel_fijo, celular, fecha_nacimiento
	   cuil, ocupacion, id_pago FROM (Padrino NATURAL JOIN Donante NATURAL JOIN Programa NATURAL JOIN Aporta) WHERE frecuencia = 'MENSUAL';
       
       