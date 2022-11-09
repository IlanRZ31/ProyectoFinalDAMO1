package ni.edu.uca.petscare.entidades

import android.media.Image
import com.google.android.material.imageview.ShapeableImageView
import java.time.LocalDate
import java.util.*

class Mascota(
    val nombre: String,
    val tipo: String,
    val raza: String,
    val fechaNacimiento: LocalDate,
    val peso: Int,
    val image: ShapeableImageView

    ) {
}