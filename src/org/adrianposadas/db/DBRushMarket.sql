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

create table Compras(
    numeroDocumento int,
    fechaDocumento date,
    descripcion varchar(60),
    totalDocumento decimal(10,2),
    primary key PK_numeroDocumento(numerodocumento)
);

create table TipoProducto (
    codigoTipoProducto int primary key,
    descripcion varchar(45) not null
);

create table CargoEmpleado(
    codigoCargoEmpleado int,
    nombreCargo varchar(45),
    descripcionCargo varchar(45),
    primary key PK_codigoCargoEmpleado(codigoCargoEmpleado)
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
call sp_ListarClientes();
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
Delimiter ;
 
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

call sp_AgregarProveedores(1, '8465453', 'adrian', 'posadas', 'zona 10','diana', '758491', 'diana.com');
call sp_AgregarProveedores (2, '984654145', 'jim', 'andre', 'zona 8','chikis', '1315646', 'chikis.gt') ;

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

-- Compras
delimiter $$
create procedure sp_AgregarCompras(
    in numeroDocumento int,
    in fechaDocumento date,
    in descripcion varchar(60),
    in totalDocumento decimal(10,2)
)
begin
    insert into Compras(numeroDocumento, fechaDocumento, descripcion, totalDocumento)
    values (numeroDocumento, fechaDocumento, descripcion, totalDocumento);
end $$
delimiter ;

call sp_AgregarCompras(1, '2024-05-10', 'tortillas de harina', 12.3);
call sp_AgregarCompras(2, '2024-05-27', 'tortillas de maíz', 15.2);

delimiter $$
create procedure sp_ListarCompras()
begin
    select
        C.numeroDocumento,
		C.fechaDocumento,
		C.descripcion,
		C.totalDocumento
    from Compras C;
end $$
delimiter ;

delimiter $$
create procedure sp_EliminarCompras(
    in numeroDocumento int
)
begin
    delete from Compras where numeroDocumento = numeroDocumento;
end $$
delimiter ;

delimiter $$
create procedure sp_EditarCompras(
    in numeroDocumento int,
    in fechaDocumento date,
    in descripcion varchar(60),
    in totalDocumento decimal(10,2)
)
begin
    update Compras C
    set C.fechaDocumento = fechaDocumento,
        C.descripcion = descripcion,
        C.totalDocumento = totalDocumento
    where C.numeroDocumento = numeroDocumento;
end $$
delimiter ;

-- TipoProducto
delimiter $$
create procedure sp_AgregarTipoProducto(
    in codigoTipoProducto int,
    in descripcion varchar(45)
)
begin
    insert into TipoProducto (codigoTipoProducto, descripcion) values (codigoTipoProducto, descripcion);
end $$
delimiter ;

call sp_AgregarTipoProducto(1, 'pastas');
call sp_AgregarTipoProducto(2, 'cereales');

delimiter $$
create procedure sp_ListarTipoProducto()
begin
    select
		T.codigoTipoProducto,
		T.descripcion
	from TipoProducto T;
end $$
delimiter ;

delimiter $$
create procedure sp_EliminarTipoProducto(
    in codigoTipoProducto int
)
begin
    delete from TipoProducto where TipoProducto.codigotipoproducto = codigoTipoProducto;
end $$
delimiter ;

delimiter $$
create procedure sp_EditarTipoProducto(
    in codigoTipoProducto int,
    in descripcion varchar(45)
)
begin
    update tipoproducto T
    set
		T.descripcion = descripcion 
    where T.codigoTipoProducto = codigotipoproducto;
end $$
delimiter ;

-- CargoEmpleado
delimiter $$
create procedure sp_AgregarCargoEmpleado(
    in codigoCargoEmpleado  int,
    in nombreCargo varchar(45),
    in descripcionCargo  varchar(45)
)
begin
    insert into CargoEmpleado (codigoCargoEmpleado, nombreCargo, descripcionCargo) values (codigoCargoEmpleado, nombreCargo, descripcionCargo);
end $$
delimiter ;

call sp_AgregarCargoEmpleado(1, 'gerente', 'gerente de ventas');
call sp_AgregarCargoEmpleado(2, 'general militar', 'general del peloton 2');

delimiter $$
create procedure sp_ListarCargoEmpleado()
begin
    select
		C.codigoCargoEmpleado,
		C.nombreCargo,
        C.descripcionCargo
	from CargoEmpleado C;
end $$
delimiter ;

delimiter $$
create procedure sp_EliminarCargoEmpleado(
    in codigoCargoEmpleado int
)
begin
    delete from CargoEmpleado where CargoEmpleado.codigoCargoEmpleado = codigoCargoEmpleado;
end $$
delimiter ;

delimiter $$
create procedure sp_EditarCargoEmpleado(
    in codigoCargoEmpleado int,
    in nombreCargo varchar(45),
    in descripcionCargo  varchar(45)
)
begin
    update CargoEmpleado C
    set
        C.nombreCargo = nombreCargo,
        C.descripcionCargo  = descripcionCargo
    where C.codigoCargoEmpleado = codigoCargoEmpleado;
end $$
delimiter ;