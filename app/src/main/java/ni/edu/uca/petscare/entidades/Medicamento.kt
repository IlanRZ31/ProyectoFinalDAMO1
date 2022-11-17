package ni.edu.uca.petscare.entidades

import java.sql.Time
import java.time.LocalDate

class Medicamento(
    var idMedicamento: Int,
    var idMascota: Int,
    var nombreMedicamento: String,
    var intervaloTiempo: Int,
    var horaInicial: Time,
    var fechaFin: LocalDate
) {
}