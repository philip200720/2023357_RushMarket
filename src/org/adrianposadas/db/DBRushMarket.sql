drop database if exists DBRushMarket;
create database DBRushMarket;
use DBRushMarket;
SET time_zone = '-06:00';

create table Clientes(
	codigoCliente int not null,
	nitCliente varchar(10) not null,
	nombreCliente varchar(50) not null,
	apellidoCliente varchar(50) not null,
	direccionCliente varchar(150) not null,
	telefonoCliente varchar(15) not null,
	correoCliente varchar(50) not null,
	primary key PK_codigoCliente(codigoCliente)
);

create table Proveedores(
	codigoProveedor int not null,
    nitProveedor varchar(10) not null,
    nombreProveedor varchar (60),
    apellidoProveedor varchar (60),
    direccionProveedor varchar (150),
    razonSocial varchar (60),
    contactoPrincipal varchar (100),
    paginaWeb varchar (50),
    primary key PK_codigoProveedor (codigoProveedor)
);



-- CRUD
-- Clientes
delimiter $$
create procedure sp_AgregarClientes(in codigoCliente int, in nitCliente varchar(10), in nombreCliente varchar(50), 
in apellidoCliente varchar(50), in direccionCliente varchar(150), in telefonoCliente varchar(15), in correoCliente varchar(50))
begin 
	insert into Clientes (codigoCliente, nitCliente, nombreCliente, apellidoCliente, direccionCliente, telefonoCliente, correoCliente) values 
    (codigoCliente, nitCliente, nombreCliente, apellidoCliente, direccionCliente, telefonoCliente, correoCliente);
    end $$
delimiter ;
 
call sp_AgregarClientes (1, '12457878', 'harol', 'luna', 'El basurero zona 3', '21215498', 'harolxluna4ever.com');
call sp_AgregarClientes (2, '15423874', 'oliver', 'sisimit', 'El basurero km. 22', '45875221', 'xdxsdadasd.com');
call sp_AgregarClientes (3, '27895544', 'weso', 'keso', 'El pepe km. 75', '9976541', 'wesoweso.com');

delimiter $$
create procedure sp_ListarClientes()
begin 
	select
    C.codigoCliente,
    C.nitCliente, 
    C.nombreCliente, 
    C.apellidoCliente, 
    C.direccionCliente, 
    C.telefonoCliente, 
    C.correoCliente
    from Clientes  C;
    end $$
 
delimiter ;
 
delimiter $$
create procedure sp_BuscarClientes (in codigoCliente int)
begin 
	select 
		C.codigoCliente,
		C.nitCliente, 
		C.nombreCliente, 
		C.apellidoCliente, 
		C.direccionCliente, 
		C.telefonoCliente, 
		C.correoCliente
        from Clientes C
        where C.codigoCliente = codigoCliente ;
    end $$
delimiter ;

Delimiter $$
	create procedure sp_EliminarClientes (in codigoCliente int)
		begin 
			delete from Clientes
            where Clientes.codigoCliente = codigoCliente;
	end $$
Delimiter;
 
delimiter $$
create procedure sp_EditarClientes(in codigoCliente int, in nitCliente varchar(10), in nombreCliente varchar(50), 
in apellidoCliente varchar(50), in direccionCliente varchar(150), in telefonoCliente varchar(15), in correoCliente varchar(50))
begin 
	Update Clientes  C
    set 		
		C.nitCliente = nitCliente,
		C.nombreCliente = nombreCliente,
		C.apellidoCliente = apellidoCliente,
		C.direccionCliente = direccionCliente, 
		C.telefonoCliente = telefonoCliente, 
		C.correoCliente = correoCliente
	where C.codigoCliente = codigoCliente;
    end $$
delimiter ;

-- Proveedor
delimiter $$
create procedure sp_AgregarProveedores(
    in codigoProveedor int,
    in nitProveedor varchar(10),
    in nombreProveedor varchar(60),
    in apellidoProveedor varchar(60),
    in direccionProveedor varchar(150),
    in razonSocial varchar(60),
    in contactoPrincipal varchar(100),
    in paginaWeb varchar(50)
)
begin
    insert into Proveedores (codigoProveedor, nitProveedor, nombreProveedor, apellidoProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb)
    values (codigoProveedor, nitProveedor, nombreProveedor, apellidoProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb);
end $$
delimiter ;

call sp_AgregarProveedores(01, '8465453', 'adrian', 'posadas', 'zona 10','diana', '758491', 'diana.com');
call sp_AgregarProveedores (02, '984654145', 'jim', 'andre', 'zona 8','chikis', '1315646', 'chikis.gt');

delimiter $$
create procedure sp_ListarProveedores()
begin
    select
        P.codigoProveedor,
        P.nitProveedor,
        P.nombreProveedor,
        P.apellidoProveedor,
        P.direccionProveedor,
        P.razonSocial,
        P.contactoPrincipal,
        P.paginaWeb
    from Proveedores P;
end $$
delimiter ;

delimiter $$
create procedure sp_EliminarProveedores(
    in codigoProveedor int
)
begin
    delete from Proveedores where codigoProveedor = codigoProveedor;
end $$
delimiter ;

delimiter $$
create procedure sp_EditarProveedores(
    in codigoProveedor int,
    in nitProveedor varchar(10),
    in nombreProveedor varchar(60),
    in apellidoProveedor varchar(60),
    in direccionProveedor varchar(150),
    in razonSocial varchar(60),
    in contactoPrincipal varchar(100),
    in paginaWeb varchar(50)
)
begin
    update Proveedores P
    set P.nitProveedor = nitProveedor,
        P.nombreProveedor = nombreProveedor,
        P.apellidoProveedor = apellidoProveedor,
        P.direccionProveedor = direccionProveedor,
        P.razonSocial = razonSocial,
        P.contactoPrincipal = contactoPrincipal,
        P.paginaWeb = paginaWeb
    where P.codigoProveedor = codigoProveedor;
end $$
delimiter ;