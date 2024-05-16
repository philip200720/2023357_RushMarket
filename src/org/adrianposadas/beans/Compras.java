package org.adrianposadas.beans;

import java.time.LocalDate;

/*create table Compras(
    numeroDocumento int,
    fechaDocumento date,
    descripcion varchar(60),
    totalDocumento decimal(10,2),
    primary key PK_numeroDocumento(numerodocumento)
);
*/
public class Compras {
    private int numeroDocumento;
    private LocalDate fechaDocumento;
    private String descripcion;
    private double totalDocumento;
}
