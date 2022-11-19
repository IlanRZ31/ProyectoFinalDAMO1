package ni.edu.uca.petscare.entidades

import java.time.LocalDate

class Vacuna(
    val idVacuna: Int,
    var idMascota: Int,
    var nombreVacuna: String,
    var fechaVacunacion: LocalDate,
    var clinica: String,
    var estado: String
) {
}