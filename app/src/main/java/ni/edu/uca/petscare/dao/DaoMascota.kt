package ni.edu.uca.petscare.dao

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.android.material.imageview.ShapeableImageView
import ni.edu.uca.petscare.entidades.Mascota
import java.time.LocalDate

class DaoMascota {
    var listMascota = ArrayList<Mascota>()

    /**
     * Agrega una mascota al arrayList
     * Devuelve true si fue exitoso, false si hubo un error
     * */
    fun agregarMascota(
        idMascota: Int,
        nombreMascota: String,
        tipoMascota: String,
        raza: String,
        fechaNacimiento: LocalDate,
        peso: Int,
        image: ShapeableImageView
    ): Boolean {
        try {
            val mascota =
                Mascota(idMascota, nombreMascota, tipoMascota, raza, fechaNacimiento, peso, image)
            listMascota.add(mascota)
            return true
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return false
    }

    /**
     * Edita la mascota especificada por id, y remplasa los datos con los argumentos enviados
     * Devuelve true si fue exitoso, false si hubo un error
     * */
    fun editarMascota(
        idMascota: Int,
        nombreMascota: String,
        tipoMascota: String,
        raza: String,
        fechaNacimiento: LocalDate,
        peso: Int,
        image: ShapeableImageView
    ): Boolean {
        try {
            var i = 0
            while (i < listMascota.size) {
                if (listMascota[i].idMascota == idMascota) {
                    listMascota[i].nombre = nombreMascota
                    listMascota[i].tipo = tipoMascota
                    listMascota[i].raza = raza
                    listMascota[i].fechaNacimiento = fechaNacimiento
                    listMascota[i].peso = peso
                    listMascota[i].image = image
                    return true
                }
                i++
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return false
    }

    /**
     * Este metodo elimina un registro de mascota especificado por el id de mascota
     * Devuelve true si fue exitoso, falsse si hubo un error
     * */
    fun eliminarMascota(idMascota: Int): Boolean{
        try {
            var i = 0
            while (i < listMascota.size) {
                if(listMascota[i].idMascota == idMascota){
                    listMascota.removeAt(i)
                    return true
                }
                i++
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return false
    }

    /**
     * Este metodo devuelve todas las mascotas que tengan el nombre que el usuario haya ingresado
     * y que desee buscar
     * */
    fun buscarMascotaNombre(nombreMascota: String): ArrayList<Mascota> {
        val mascotas = ArrayList<Mascota>()
        try {
            var i = 0
            while (i < listMascota.size) {
                if (listMascota[i].nombre.equals(nombreMascota)) {
                    mascotas.add(listMascota[i])
                }
                i++
            }
            return mascotas
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return mascotas
    }

    /**
     * Este metodo devuelve un ArrayList de las mascotas ordenadas por raza y nombre, alfabeticamente
     * */
    fun ordenarMascotaRaza(): ArrayList<Mascota> {
        val mascotas = listMascota
        try {
            return ArrayList(mascotas.sortedWith(compareBy(Mascota::raza, Mascota::nombre)))
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return mascotas
    }

    /**
     * Este metodo devuelve un ArrayList de las mascotas ordenadas por especie y nombre, alfabeticamente
     * */
    fun ordenarMascotaEspecie(): ArrayList<Mascota> {
        val mascotas = ArrayList<Mascota>()
        try {
            return ArrayList(mascotas.sortedWith(compareBy(Mascota::tipo, Mascota::nombre)))
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return mascotas
    }

    /**
     * Este metodo devuelve las mascotas ordenadas por edad, desde mas viejo a mas joven, y nombre,
     * alfabeticamente
     * El rango de busqueda del ordenamiento por edad se limita a año y mes
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun ordernarMascotaEdad(): ArrayList<Mascota> {
        val mascotas = ArrayList<Mascota>()
        try {
            return ArrayList(
                mascotas.sortedWith(
                    compareBy(
                        { it.fechaNacimiento.year },
                        { it.fechaNacimiento.month.value },
                        Mascota::nombre
                    )
                )
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return mascotas
    }

    /**
     * Este metodo devuelve todas las mascotas sin filtro a esepcion del ordenamiento alfabetico para
     * el nombre de la mascota
     * */
    fun mostrarMascotas(): ArrayList<Mascota> {
        val mascotas = listMascota
        try {
            return ArrayList(mascotas.sortedWith(compareBy(Mascota::nombre)))
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return mascotas
    }

    fun setListMascotas(mascotas: ArrayList<Mascota>){
        listMascota = mascotas
    }
}