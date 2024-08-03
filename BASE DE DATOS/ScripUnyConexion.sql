create database UNY_conecction;	
use UNY_conecction;	
-- Creamos las tablas correspondiente
	
create table Estado_Civil(
Id_EstadoCivil int auto_increment not null primary key,
Estado_Civil varchar(15)  
);

-- Insercion de los estados civiles 
insert into Estado_Civil (Estado_Civil) values ("Casado(a)");
insert into Estado_Civil (Estado_Civil) values ("Soltero(a)");
insert into Estado_Civil (Estado_Civil) values ("Divorciado(a)");
insert into Estado_Civil (Estado_Civil) values ("Viudo(a)");

-- Validacion de cada id

select * from Estado_Civil;

-- Creacion de tabla Estado que contendra todos los estados de venezuela
create table Estado(
Id_Estado int auto_increment not null primary key,
Nombre_Estado varchar(75)
);
-- Insercion de los estados
select * from Estado;
insert into Estado (Nombre_Estado) values(" Caracas");
insert into Estado (Nombre_Estado) values("Miranda");
insert into Estado (Nombre_Estado) values("Aragua");
insert into Estado (Nombre_Estado) values("Carabobo");
insert into Estado (Nombre_Estado) values("Zulia");
insert into Estado (Nombre_Estado) values("Bolívar");
insert into Estado (Nombre_Estado) values("Anzoátegui");
insert into Estado (Nombre_Estado) values("Barinas");
insert into Estado (Nombre_Estado) values("Táchira");
insert into Estado (Nombre_Estado) values("Falcón");
insert into Estado (Nombre_Estado) values("Lara");
insert into Estado (Nombre_Estado) values("Yaracuy");
insert into Estado (Nombre_Estado) values("Nueva Esparta");
insert into Estado (Nombre_Estado) values("Monagas");
insert into Estado (Nombre_Estado) values("Guárico");
insert into Estado (Nombre_Estado) values("Cojedes");
insert into Estado (Nombre_Estado) values("Apure");
insert into Estado (Nombre_Estado) values("Mérida");
insert into Estado (Nombre_Estado) values("Trujillo");
insert into Estado (Nombre_Estado) values("Sucre");
insert into Estado (Nombre_Estado) values("Amazonas");
insert into Estado (Nombre_Estado) values("Delta Amacuro");
insert into Estado (Nombre_Estado) values("Vargas");

select * from Estado;

create table Genero(
Id_Genero int auto_increment not null primary key,
Genero varchar (25)
);

insert into Genero(Genero) values ("Masculino");
insert into Genero(Genero) values ("Femenino");
insert into Genero(Genero) values ("No binario");
insert into Genero(Genero) values ("Otro");

select * from Genero;

create table Estudiantes(
Id int auto_increment not null primary key,
Nombre_Estudiante varchar(75) not null,
Apellido_Estudiante varchar(75),
Cedula varchar(9),
Num_telf varchar(12),
email varchar (100) not null,
Direccion varchar(75),
Fecha_Nac date,
Fk_Estado int(24) not null,
Fk_EstadoCivil int not null,
Fk_Genero int not null,
foreign key(Fk_Estado) references Estado(Id_Estado),
foreign key(Fk_EstadoCivil) references Estado_Civil(Id_EstadoCivil),
foreign key(Fk_Genero) references Genero(Id_Genero)
);

-- Prueba de insercion para usarlo en el programa
insert into Estudiantes(Nombre_Estudiante, Apellido_Estudiante,Cedula,Num_telf,email,Direccion,Fecha_Nac,Fk_Genero, Fk_Estado,Fk_EstadoCivil)
 values ("Eduar","g","31","3","a","asd","12-02-24", 3, 5, 1);
 
 select * from Estudiantes;

create view GeneralEstudiantes as select e.Id as Id, e.Nombre_Estudiante as Nombre,e.Apellido_Estudiante as Apellido,e.Fecha_Nac as Fecha_Naca,
 e.Cedula as Cedula, e.Num_Telf as Num_telf, e.email as Email, e.Direccion as Direccion, g.Genero as Genero,
 Es.Nombre_Estado as Estado, EsCi.Estado_Civil as Estado_Civil from Estudiantes as e inner join Genero as g on  
 e.Fk_Genero = g.Id_Genero inner join Estado as Es on e.Fk_Estado = Es.Id_Estado inner join Estado_Civil as EsCi
 on e.Fk_EstadoCivil = EsCi.Id_EstadoCivil;
 select * from GeneralEstudiantes;

 
-- Prueba de la consulta sql update para el programa
update Estudiantes set Estudiantes.Nombre_Estudiante="asd", Estudiantes.Apellido_Estudiante="GONZALES", Estudiantes.Fecha_Nac="12-02-24", Estudiantes.Cedula="31", Estudiantes.Num_telf="ASD", Estudiantes.email="ASD",Estudiantes.Direccion="AVPALOGRANDE", Estudiantes.Fk_Genero=1, Estudiantes.Fk_Estado=3,Estudiantes.Fk_EstadoCivil=2 where Estudiantes.Id=3;