USE biblioteca_db;
START TRANSACTION;
INSERT INTO usuarios(id,nombre,login,clave_hash,rol) VALUES
 (1,'Bibliotecaria Escolar','bibliotecario','120000$YmlibGlvdGVjYS1kZW1vMQ==$qhn639FPx+opGnu8RRc6ExgXu/TOWMBsiddaK7uiNJ0=','BIBLIOTECARIO'),
 (2,'Ana Torres','ana','120000$YmlibGlvdGVjYS1kZW1vMg==$uO4H0/z4hc0SWeo3KzLZ0ICniPAOJ7oEOJ+5lHuB5tg=','ESTUDIANTE'),
 (3,'Diego Soto','diego','120000$YmlibGlvdGVjYS1kZW1vMw==$e3sGcVbIRHayT4RJnbXBPedeVNHWti5abuQmeL0Zz/0=','ESTUDIANTE');
INSERT INTO estudiantes(id,usuario_id,rut,curso,correo) VALUES
 (1,2,'21.345.678-9','3° Medio A','ana@example.com'),(2,3,'22.345.678-5','2° Medio B','diego@example.com');
INSERT INTO categorias(id,nombre) VALUES (1,'Literatura'),(2,'Ciencias'),(3,'Historia');
INSERT INTO libros(id,titulo,autor,editorial,isbn,categoria_id,stock,stock_total) VALUES
 (1,'El principito','Antoine de Saint-Exupéry','Salamandra','9788478887194',1,3,3),
 (2,'Sub terra','Baldomero Lillo','Zig-Zag','9789561201477',1,1,2),
 (3,'Breve historia del tiempo','Stephen Hawking','Crítica','9788498925127',2,2,2),
 (4,'Historia de Chile','Sergio Villalobos','Universitaria','9789561120006',3,0,1);
INSERT INTO prestamos(libro_id,estudiante_id,fecha_prestamo,fecha_vencimiento,fecha_devolucion,atraso) VALUES
 (1,1,DATE_SUB(CURRENT_DATE,INTERVAL 30 DAY),DATE_SUB(CURRENT_DATE,INTERVAL 16 DAY),DATE_SUB(CURRENT_DATE,INTERVAL 15 DAY),TRUE),
 (2,1,DATE_SUB(CURRENT_DATE,INTERVAL 2 DAY),DATE_ADD(CURRENT_DATE,INTERVAL 12 DAY),NULL,FALSE),
 (4,2,DATE_SUB(CURRENT_DATE,INTERVAL 20 DAY),DATE_SUB(CURRENT_DATE,INTERVAL 6 DAY),NULL,FALSE);
COMMIT;
