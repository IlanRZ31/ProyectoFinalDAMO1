package ni.edu.uca.petscare.entidades

import android.media.Image
import com.google.android.material.imageview.ShapeableImageView
import java.time.LocalDate
import java.util.*

class Mascota(
    var idMascota: Int,
    var nombre: String,
    var tipo: String,
    var raza: String,
    var fechaNacimiento: LocalDate,
    var peso: Int,
    var image: ShapeableImageView

    ) {
}