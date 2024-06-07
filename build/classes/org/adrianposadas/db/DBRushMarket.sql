drop database if exists DBRushMarket;
create database DBRushMarket;
use DBRushMarket;
SET time_zone = '-06:00';

create table Clientes(
	codigoCliente int auto_increment,
	nitCliente varchar(10) not null,
	nombreCliente varchar(50) not null,
	apellidoCliente varchar(50) not null,
	direccionCliente varchar(150) not null,
	telefonoCliente varchar(15) not null,
	correoCliente varchar(50) not null,
	primary key PK_codigoCliente(codigoCliente)
);

create table Proveedores(
	codigoProveedor int auto_increment,
    nitProveedor varchar(10) not null,
    nombreProveedor varchar (60),
    apellidoProveedor varchar (60),
    direccionProveedor varchar (150),
    razonSocial varchar (60),
    contactoPrincipal varchar (100),
    paginaWeb varchar (50),
    primary key PK_codigoProveedor (codigoProveedor)
);

create table TelefonoProveedor(
	telefonoProveedorId int auto_increment,
    numeroPrincipal varchar(8),
    numeroSecundario varchar(8),
    observaciones varchar(45),
    codigoProveedor int,
    primary key PK_telefonoProveedorId (telefonoProveedorId),
    foreign key FK_telefonoCodigoProveedor (codigoProveedor)
		references Proveedores(codigoProveedor)
);

create table EmailProveedor(
	emailProveedorId int auto_increment,
    emailProveedor varchar(50),
    descripcion varchar(100),
    codigoProveedor int,
    primary key PK_emailProveedorId (emailProveedorId),
    foreign key FK_emailCodigoProveedor (codigoProveedor)
		references Proveedores(codigoProveedor)
);

create table TipoProducto (
    codigoTipoProducto int auto_increment,
    descripcion varchar(45) not null,
    primary key PK_codigoTipoDeProducto(codigoTipoProducto)
);

create table Productos (
    productoId int auto_increment,
    descripcionProducto varchar(100),
    precioUnitario decimal(10,2) default 0,
    precioDocena decimal(10,2) default 0,
    precioMayor decimal(10,2) default 0,
    imagenProducto varchar (45),
    existencia int default 0,
    codigoProveedor int,
    codigoTipoDeProducto int,
    primary key PK_productoId (productoId),
    foreign key FK_codigoProveedor (codigoProveedor)
		references Proveedores(codigoProveedor),
	foreign key FK_codigoTipoProducto (codigoTipoDeProducto )
		references TipoProducto (codigoTipoProducto )
);

create table Compras(
    compraId int auto_increment,
    fechaCompra date,
    descripcion varchar(60),
    totalCompra decimal(10,2) default 0 ,
    primary key PK_CompraId(compraId)
);

create table DetalleCompra(
	detalleCompraId int auto_increment,
    costoUnitario decimal(10,2) default 0,
    cantidad int,
    productoId int,
    compraId int,
    primary key PK_detalleCompraId (detalleCompraId),
    foreign key FK_detalleCompraProductoId (productoId)
		references Productos (productoId),
	foreign key FK_compraId (compraId)
		references Compras (compraId)
);

create table CargoEmpleado(
    codigoCargoEmpleado int auto_increment,
    nombreCargo varchar(45),
    descripcionCargo varchar(45),
    primary key PK_codigoCargoEmpleado(codigoCargoEmpleado)
);

create table Empleados(
	empleadoId int auto_increment,
    nombreEmpleado varchar(30),
    apellidoEmpleado varchar(30),
    sueldo decimal(10,2),
    direccion varchar(150),
    turno varchar(15),
    cargoId int,
    primary key PK_empleadoId (empleadoId),
    foreign key FK_cargo (cargoId)
		references CargoEmpleado (codigoCargoEmpleado)
);

create table Facturas(
    facturaId int auto_increment,
    estado varchar (50),
    totalFactura decimal (10,2) default 0,
    fechaFactura date,
    codigoCliente int,
    empleadoId int,
    primary key PK_facturaId (facturaId),
    foreign key FK_facturaClienteId (codigoCliente)
		references Clientes (codigoCliente),
	foreign key FK_facturaEmpleadoId (empleadoId)
		references Empleados (empleadoId)
);

create table Detallefactura (
    detalleFacturaId int auto_increment,
    precioUnitario decimal(10, 2),
    cantidad int,
    facturaId int,
    productoId int,
    Primary key PK_detalleFacturaId (detalleFacturaId),
    foreign key FK_detalleFacturaFacturaId (facturaId)
		references Facturas (facturaId),
	foreign key FK_detalleFacturaProductoId(productoId)
		references Productos (productoId)
);

-- CRUD
-- --------------------------------------------------- Clientes
delimiter $$
create procedure sp_AgregarClientes(in nitCliente varchar(10), in nombreCliente varchar(50), 
in apellidoCliente varchar(50), in direccionCliente varchar(150), in telefonoCliente varchar(15), in correoCliente varchar(50))
begin 
	insert into Clientes (nitCliente, nombreCliente, apellidoCliente, direccionCliente, telefonoCliente, correoCliente) values 
    (nitCliente, nombreCliente, apellidoCliente, direccionCliente, telefonoCliente, correoCliente);
    end $$
delimiter ;

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

-- --------------------------------------------------- Proveedor
delimiter $$
create procedure sp_AgregarProveedores(
    in nitProveedor varchar(10),
    in nombreProveedor varchar(60),
    in apellidoProveedor varchar(60),
    in direccionProveedor varchar(150),
    in razonSocial varchar(60),
    in contactoPrincipal varchar(100),
    in paginaWeb varchar(50)
)
begin
    insert into Proveedores (nitProveedor, nombreProveedor, apellidoProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb)
    values (nitProveedor, nombreProveedor, apellidoProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb);
end $$
delimiter ;

delimiter $$
create procedure sp_BuscarProveedores(in codigoProveedor int)
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
    from Proveedores P
	where P.codigoProveedor = codigoProveedor;
end $$
delimiter ;

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

-- --------------------------------------------------- Telefono Proveedor
delimiter $$
create procedure sp_AgregarTelefonoProveedor(
	in numeroPrincipal varchar(8),
    in numeroSecundario varchar(8),
    in observaciones varchar(45),
    in codigoProveedor int)
begin
    insert into TelefonoProveedor (numeroPrincipal, numeroSecundario, observaciones, codigoProveedor)
    values (numeroPrincipal, numeroSecundario, observaciones, codigoProveedor);
end $$
delimiter ;


delimiter $$
create procedure sp_ListarTelefonoProveedor()
begin
    select
        telefonoProveedorId,
        numeroPrincipal,
        numeroSecundario,
        observaciones,
        codigoProveedor
    from TelefonoProveedor;
end $$
delimiter ;

delimiter $$
create procedure sp_BuscarTelefonoProveedor (in telefonoProveedorId int)
begin
	select
    T.telefonoProveedorId,
    T.numeroPrincipal,
    T.numeroSecundario,
    T.observaciones,
    T.codigoProveedor
    from TelefonoProveedor T 
    where T.telefonoProveedorId = telefonoProveedorId;
end $$
delimiter ;

delimiter $$
create procedure sp_EliminarTelefonoProveedor(in telefonoProveedorId int)
begin
    delete from telefonoProveedor 
    where telefonoProveedorId = telefonoProveedorId;
end $$
delimiter ;

-- Editar
delimiter $$
create procedure sp_EditarTelefonoProveedor(
	in telefonoProveedorId int,
	in numeroPrincipal varchar(8),
    in numeroSecundario varchar(8),
    observaciones varchar(45),
    codigoProveedor int)
begin 
	Update TelefonoProveedor T
		set 
			T.numeroPrincipal = numeroPrincipal,
			T.numeroSecundario = numeroSecundario,
			T.observaciones = observaciones,
			T.codigoProveedor = codigoProveedor
        where T.telefonoProveedorId = telefonoProveedorId ;
end $$
delimiter ;

-- --------------------------------------------------- Email Proveedor
-- Agregar
delimiter $$
create procedure sp_AgregarEmailProveedor(
	in emailProveedor varchar(50),
    in descripcion varchar(100),
    in codigoProveedor int)
begin
    insert into EmailProveedor (emailProveedor, descripcion, codigoProveedor)
    values (emailProveedor, descripcion, codigoProveedor);
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarEmailProveedor()
begin
    select
        emailProveedorId,
        emailProveedor,
        descripcion,
        codigoProveedor
    from EmailProveedor;
end $$
delimiter ;

-- Buscar
delimiter $$
create procedure sp_BuscarEmailProveedor (in emailProveedorId int)
begin
	select
    E.emailProveedorId,
    E.emailProveedor,
    E.descripcion,
    E.codigoProveedor
    from EmailProveedor E 
    where E.emailProveedorId = emailProveedorId;
end $$
delimiter ;

-- Eliminar
delimiter $$
create procedure sp_EliminarEmailProveedor(in emailProveedorId int)
begin
    delete from EmailProveedor 
    where emailProveedorId = emailProveedorId;
end $$
delimiter ;

-- Editar
delimiter $$
create procedure sp_EditarEmailProveedor(
	in emailProveedorId int,
	in emailProveedor varchar(50),
    in descripcion varchar(100),
    in codigoProveedor int)
begin 
	Update EmailProveedor E
		set 
			E.emailProveedor = emailProveedor,
			E.descripcion = descripcion,
			E.codigoProveedor = codigoProveedor 
        where E.emailProveedorId = emailProveedorId ;
end $$
delimiter ;

-- --------------------------------------------------- Tipo Producto
delimiter $$
create procedure sp_AgregarTipoProducto(
    in descripcion varchar(45)
)
begin
    insert into TipoProducto (descripcion) values (descripcion);
end $$
delimiter ;

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

-- --------------------------------------------------- productos
-- Agregar
delimiter $$
create procedure sp_AgregarProductos(
	in descripcionProducto varchar(100),
    in imagenProducto varchar(45),
    in codigoProveedor int,
    in codigoTipoDeProducto int)
begin
    insert into Productos (
        descripcionProducto,
        imagenProducto,
        codigoProveedor,
        codigoTipoDeProducto)
    values (
        descripcionProducto,
        imagenProducto,
        codigoProveedor,
        codigoTipoDeProducto);
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarProductos()
begin
    select
         productoId,
         descripcionProducto,
         precioUnitario,
         precioDocena,
         precioMayor,
         imagenProducto,
         existencia,
         codigoProveedor,
         codigoTipoDeProducto
    from Productos;
end $$
delimiter ;

 -- Buscar
delimiter $$
create procedure sp_BuscarProductos (in  productoId int)
begin
	select
	P.descripcionProducto,
	P.precioUnitario,
	P.precioDocena,
	P.precioMayor,
	P.imagenProducto,
	P.existencia,
	P.codigoProveedor,
	P.codigoTipoDeProducto
    from Productos P
    where P.productoId = productoId;
end $$
delimiter ;

-- Eliminar
delimiter $$
create procedure sp_EliminarProducto(in productoId int)
begin
    delete from Productos 
    where productoId = productoId;
end $$
delimiter ;

         productoId,
         descripcionProducto,
         precioUnitario,
         precioDocena,
         precioMayor,
         imagenProducto,
         existencia,
         codigoProveedor,
         codigoTipoDeProducto
         
-- Editar
delimiter $$
create procedure sp_EditarProducto(
	in productoId int,
	in descripcionProducto varchar(100),
    in precioUnitario decimal(10,2),
    in precioDocena decimal(10,2),
    in precioMayor decimal(10,2),
    in imagenProducto varchar(45),
    in codigoProveedor int,
    in codigoTipoDeProducto int)
begin 
		Update Productos  P
		set 		
		P.descripcionProducto = descripcionProducto,
        P.precioUnitario = precioUnitario,
        P.precioDocena = precioDocena,
		P.precioMayor = precioMayor,
		P.imagenProducto = imagenProducto,
		P.codigoProveedor = codigoProveedor,
		P.codigoTipoDeProducto = codigoTipoDeProducto
        where p.productoId = productoId;
end $$
delimiter ;

-- --------------------------------------------------- Compras
delimiter $$
create procedure sp_AgregarCompras(
    in fechaCompra date,
    in descripcion varchar(60)
)
begin
    insert into Compras(fechaCompra, descripcion)
    values (fechaCompra, descripcion);
end $$
delimiter ;

delimiter $$
create procedure sp_ListarCompras()
begin
    select
        C.compraId ,
		C.fechaCompra,
		C.descripcion,
		C.totalCompra 
    from Compras C;
end $$
delimiter ;

delimiter $$
create procedure sp_EliminarCompras(
    in compraId  int
)
begin
    delete from Compras where compraId  = compraId ;
end $$
delimiter ;

delimiter $$
create procedure sp_EditarCompras(
    in compraId int,
    in fechaCompra date,
    in descripcion varchar(60),
    in totalCompra  decimal(10,2)
)
begin
    update Compras C
    set C.fechaCompra = fechaCompra,
        C.descripcion = descripcion,
        C.totalCompra  = totalCompra 
    where C.compraId  = compraId;
end $$
delimiter ;

-- --------------------------------------------------- detalle compra
-- Agregar 
delimiter $$
create procedure sp_AgregarDetalleCompra(
    in costoUnitario decimal(10,2),
    in cantidad int,
    in productoId int,
    in compraId int)
begin
    insert into DetalleCompra (costoUnitario, cantidad, productoId, compraId)
    values (costoUnitario, cantidad, productoId, compraId);
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarDetalleCompra()
begin
	select
    detalleCompraId,
    costoUnitario,
    cantidad,
    productoId,
    compraId
    from DetalleCompra;
end $$
delimiter ;

-- Buscar
delimiter $$
create procedure sp_BuscarDetalleCompra(in detalleCompraId int)
begin
	select
    D.detalleCompraId,
    D.costoUnitario,
    D.cantidad,
    D.productoId,
    D.compraId
    from DetalleCompra D
    where D.detalleCompraId = detalleCompraId ;
end $$
delimiter ;

-- Eliminar 
delimiter $$
create procedure sp_EliminarDetalleCompra(
    in detalleCompraId int
)
begin
    delete from DetalleCompra where detalleCompraId = detalleCompraId;
end $$
delimiter ;

-- Editar
delimiter $$
create procedure sp_EditarDetalleCompra(
	in detalleCompraId int,
    in costoUnitario int,
    in cantidad int,
    in productoId int,
    in compraId int)
begin
    update DetalleCompra D
    set D.costoUnitario = costoUnitario,
        D.cantidad = cantidad,
        D.productoId = productoId,
        D.compraId = compraId
    where D.detalleCompraId = detalleCompraId;
end $$
delimiter ;

-- --------------------------------------------------- CargoEmpleado
delimiter $$
create procedure sp_AgregarCargoEmpleado(
    in nombreCargo varchar(45),
    in descripcionCargo  varchar(45)
)
begin
    insert into CargoEmpleado (nombreCargo, descripcionCargo) values (nombreCargo, descripcionCargo);
end $$
delimiter ;



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

-- -------------------------------------------------- empleados 
-- Agregar
delimiter $$
create procedure sp_AgregarEmpleado(
    in nombreEmpleado varchar(30),
    in apellidoEmpleado varchar(30),
    in sueldo decimal(10,2),
    in direccion varchar(150),
    in turno varchar(15),
    in cargoId int
)
begin
    insert into Empleados (nombreEmpleado, apellidoEmpleado, sueldo, direccion, turno, cargoId)
    values (nombreEmpleado, apellidoEmpleado, sueldo, direccion, turno, cargoId);
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarEmpleados()
begin
    select
        empleadoId,
        nombreEmpleado,
        apellidoEmpleado,
        sueldo,
        direccion,
        turno,
        cargoId
    from Empleados;
end $$
delimiter ;

-- Buscar
delimiter $$
create procedure sp_BuscarEmpleados(in empleadoId int)
begin
    select
        E.empleadoId,
        E.nombreEmpleado,
        E.apellidoEmpleado,
        E.sueldo,
        E.direccion,
        E.turno,
        E.cargoId
    from Empleados E
    where E.empleadoId = empleadoId;
end $$
delimiter ;

-- Eliminar
delimiter $$
create procedure sp_EliminarEmpleado(
    in empleadoId int
)
begin
    delete from Empleados where empleadoId = empleadoId;
end $$
delimiter ;

-- Editar
delimiter $$
create procedure sp_EditarEmpleado(
    in empleadoId int,
    in nombreEmpleado varchar(30),
    in apellidoEmpleado varchar(30),
    in sueldo decimal(10,2),
	in direccion varchar(150),
    in turno varchar(15),
    in cargoId int
)
begin
    update Empleados E
    set E.nombreEmpleado = nombreEmpleado,
        E.apellidoEmpleado = apellidoEmpleado,
        E.sueldo = sueldo,
        E.direccion = direccion,
        E.turno = turno,
        E.cargoId = cargoId
    where E.empleadoId = empleadoId;
end $$
delimiter ;

-- -------------------------------------------------- facturas 
-- Agregar
delimiter $$
create procedure sp_AgregarFactura(
    in estado varchar (50),
    in fechaFactura date,
    in codigoCliente int,
    in empleadoId int
)
begin
    insert into Facturas (estado, fechaFactura, codigoCliente, empleadoId)
    values (estado, fechaFactura, codigoCliente, empleadoId);
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarFacturas()
begin
    select
        facturaId,
        estado,
        totalFactura,
        fechaFactura,
        codigoCliente,
        empleadoId
    from Facturas;
end $$
delimiter ;

-- Buscar
delimiter $$
create procedure sp_BuscarFacturas(in facturaId int)
begin
    select
        F.facturaId,
        F.estado,
        F.totalFactura,
        F.fechaFactura,
        F.codigoCliente,
        F.empleadoId
    from Facturas F
    where F.facturaId = facturaId;
end $$
delimiter ;

-- Elimnar
delimiter $$
create procedure sp_EliminarFactura(
    in facturaId int
)
begin
    delete from Facturas where facturaId = facturaId;
end $$
delimiter ;

-- Editar
delimiter $$
create procedure sp_EditarFactura(
    in facturaId int,
    in estado varchar (50),
    in totalFactura decimal (10,2),
    in fechaFactura date,
    in codigoCliente int,
    in empleadoId int)
begin
    update Facturas F
    set F.estado = estado,
        F.totalFactura = totalFactura,
        F.fechaFactura = fechaFactura,
        F.codigoCliente = codigoCliente,
        F.empleadoId = empleadoId
    where F.facturaId = facturaId;
end $$
-- -------------------------------------------------- detalle factura 
-- Agregar
delimiter $$
create procedure sp_AgregarDetalleFactura(
    in cantidad int,
    in facturaId int,
    in productoId int
)
begin
    insert into DetalleFactura (cantidad, facturaId, productoId)
    values (cantidad, facturaId, productoId);
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarDetalleFacturas()
begin
    select
        detalleFacturaId,
        precioUnitario,
        cantidad,
        facturaId,
        productoId
    from DetalleFactura;
end $$
delimiter ;

-- Bucar
delimiter $$
create procedure sp_BuscarDetalleFacturas(in detalleFacturaId int)
begin
    select
        D.detalleFacturaId,
        D.precioUnitario,
        D.cantidad,
        D.facturaId,
        D.productoId
    from DetalleFactura D
    where D.detalleFacturaId = detalleFacturaId;
end $$
delimiter ;

-- Eliminar
delimiter $$
create procedure sp_EliminarDetalleFactura(
    in detalleFacturaId int
)
begin
    delete from DetalleFactura where detalleFacturaId = detalleFacturaId;
end $$
delimiter ;

-- Editar
delimiter $$
create procedure sp_EditarDetalleFactura(
    in detalleFacturaId int,
    in precioUnitario decimal(10, 2),
    in cantidad int,
    in facturaId int,
    in productoId int
)
begin
    update DetalleFactura D
    set D.precioUnitario = precioUnitario,
        D.cantidad = cantidad,
        D.facturaId = facturaId,
        D.productoId = productoId
    where D.detalleFacturaId = detalleFacturaId;
end $$
delimiter ;

-- -------------------------------------------------- Triggers 
-- --------------------------------------- insertar detalle compra 
delimiter $$
create trigger tr_DetalleCompra_After_Insert after insert on DetalleCompra
for each row
begin
	declare _totalCompra decimal(10,2);
    
    update Productos
    set precioUnitario = (new.costoUnitario * 1.40),
		precioDocena = (new.costoUnitario * 1.35),
        precioMayor = (new.costoUnitario * 1.25),
        existencia = (existencia + new.cantidad)
	where productoId = new.productoId;
    
	select (costoUnitario * new.cantidad) into _totalCompra
    from DetalleCompra
    where compraId = new.compraId;
    
    update Compras
    set totalCompra = _totalCompra
    where compraId = new.compraId;
end $$
delimiter ;
    
-- --------------------------------------- actualizar detalle compra 
delimiter $$
create trigger tr_DetalleCompra_After_Update after update on DetalleCompra
for each row
begin
	declare _totalCompra decimal(10,2);
    
    update Productos
    set precioUnitario = (new.costoUnitario * 1.40),
		precioDocena = (new.costoUnitario * 1.35),
        precioMayor = (new.costoUnitario * 1.25)
	where productoId = (existencia + new.cantidad);
    
	select (costoUnitario * cantidad) into _totalCompra
    from DetalleCompra
    where compraId = new.compraId;
    
    update Compras
    set totalCompra = _totalCompra
    where compraId = new.compraId;
end $$
delimiter ;

-- --------------------------------------- insertar factura
delimiter $$
create trigger tr_DetalleFactura_Before_Insert before insert on DetalleFactura
for each row
begin
	declare _precioUnitario decimal(10,2);
    
    if new.cantidad < 12 then
		select precioUnitario into _precioUnitario
		from Productos
		where productoId = new.productoId;
	elseif new.cantidad = 12 then
		select precioDocena into _precioUnitario
		from Productos
		where productoId = new.productoId;
	else 
		select precioMayor into _precioUnitario
		from Productos
		where productoId = new.productoId;
	end if;
    
    set new.precioUnitario = _precioUnitario;
    
    update Facturas
    set totalFactura = (_precioUnitario * new.cantidad)
    where facturaId = new.facturaId;
end $$
delimiter ;

-- ------------------------------------------------- Inserciones 
 
call sp_AgregarClientes ('2345345345', 'Jim', 'Posadas', 'zona 5', '23456787', 'jim.com');
call sp_AgregarClientes ('3464654565', 'Claudia', 'del Cid', 'zona 5', '7564563', 'claudia.com');
call sp_AgregarClientes ('3454567657', 'Estela', 'Gil', 'zona 21', '5657685', 'estela.com');

call sp_AgregarProveedores('8465453', 'Luis', 'Hernandez', 'zona 15','diana', '758491', 'diana.com');
call sp_AgregarProveedores ('984654145', 'Andre', 'Lopez', 'zona 8','chikis', '1315646', 'chikis.gt') ;

call sp_AgregarTelefonoProveedor('4536456', '3564657', 'telefono de casa', 1);
call sp_AgregarTelefonoProveedor('1344534', '6575675', 'telefono de oficina', 2);

call sp_AgregarEmailProveedor('zoe@gmail.com', 'dominio kinal', 1);
call sp_AgregarEmailProveedor('candy@gmail.com', 'correo personal', 2);

call sp_AgregarTipoProducto ('Cereal');
call sp_AgregarTipoProducto ('Lacteo');

call sp_AgregarProductos ('Cereal de chocolate', '1010100110', 1, 1);
call sp_AgregarProductos ('Yogurt', '0101010101', 2, 2);

call sp_AgregarCompras('2022-06-20', 'Compra por mayor de cereal');
call sp_AgregarCompras('2023-01-28', 'compra por mayor de Yogurt');

call sp_AgregarDetalleCompra(12.5, 2, 1, 1);
call sp_AgregarDetalleCompra(10.5, 3, 2, 2);

call sp_AgregarCargoEmpleado('gerente', 'gerente general');
call sp_AgregarCargoEmpleado('ingeniero', 'ingeniero en sistemas');

call sp_AgregarEmpleado('Jose', 'García', 7000, 'zona 15', '11AM - 4PM', 1);
call sp_AgregarEmpleado('Ricardo', 'Reyes', 8000, 'zona 5', '9AM - 2PM', 2);

call sp_AgregarFactura('acitva', '2012-12-12', 1, 1);
call sp_AgregarFactura('acitva', '2023-6-1', 2, 2);

call sp_AgregarDetalleFactura(1, 1, 1);
call sp_AgregarDetalleFactura(2, 2, 2);

call sp_ListarClientes ();

call sp_ListarProveedores ();

call sp_ListarTelefonoProveedor();

call sp_ListarEmailProveedor();

call sp_ListarTipoProducto ();

call sp_ListarProductos ();

call sp_ListarCompras ();

call sp_ListarDetalleCompra();

call sp_ListarCargoEmpleado ();

call sp_ListarEmpleados();

call sp_ListarFacturas();

call sp_ListarDetalleFacturas();