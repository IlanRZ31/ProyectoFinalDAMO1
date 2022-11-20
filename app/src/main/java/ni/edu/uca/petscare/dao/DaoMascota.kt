package ni.edu.uca.petscare.dao

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.imageview.ShapeableImageView
import ni.edu.uca.petscare.entidades.Mascota
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class DaoMascota() : Parcelable {
    private var _mascota = MutableLiveData<Mascota>()
    var listMascota = ArrayList<Mascota>()

    constructor(parcel: Parcel) : this() {

    }

    /**
     * Agrega una mascota al arrayList
     * Devuelve true si fue exitoso, false si hubo un error
     * */
    fun agregarMascota(
        nombreMascota: String,
        tipoMascota: String,
        raza: String,
        date: LocalDate,
        peso: Int,
        image: ShapeableImageView
    ): Boolean {
        val idMascota = crearIdMascota()
        try {
            Log.wtf("DAO_MASCOTA", "AL MENOS LLEGA ACA")
            val mascota =
                Mascota(idMascota, nombreMascota, tipoMascota, raza, date, peso, image)
            listMascota.add(mascota)
            return true
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return false
    }

    /**
     * Este metodo obtiene el ultimo idMascota del registro con el fin de crear una nueva id de mascota
     * */
    private fun crearIdMascota(): Int {
        var idMascota = 1
        var i = 0
        try {
            do {
                if (listMascota.isEmpty()) {
                    return idMascota
                } else {
                    if (idMascota <= listMascota[i].idMascota) {
                        idMascota = listMascota[i].idMascota
                    }
                }
                i++
            } while (i < listMascota.size)
        } catch (ex: Exception) {
            ex.printStackTrace()
            return 0
        }
        return idMascota + 1
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
    fun eliminarMascota(idMascota: Int): Boolean {
        try {
            var i = 0
            while (i < listMascota.size) {
                if (listMascota[i].idMascota == idMascota) {
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
     * Este metodo busca una mascota del array list por medio de id
     * */
    fun buscarMascotaID(idMascota: Int): Mascota? {
        var mascota: Mascota
        try {
            var i = 0
            while (i < listMascota.size) {
                if (listMascota[i].idMascota == idMascota) {
                    mascota = listMascota[i]
                    return mascota
                }
                i++
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
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

    fun setListMascotas(mascotas: ArrayList<Mascota>) {
        listMascota = mascotas
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun obtenerFechaNacimiento(mascota: Mascota): String? {
        var fecha: String
        try {
            //Nació el 4 de abril del 2020 (2 años)
            val mes: String = when(mascota.fechaNacimiento.month.value){
                1 -> "Enero"
                2 -> "Febrero"
                3 -> "Marzo"
                4 -> "Abril"
                5 -> "Mayo"
                6 -> "Junio"
                7 -> "Julio"
                8 -> "Agosto"
                9 -> "Septiembre"
                10 -> "Octubre"
                11 -> "Noviembre"
                12 -> "Diciembre"
                else -> {"wtf: what a terrible failure!"}
            }
            val nacimientoYear = mascota.fechaNacimiento.year
            val yearActual = Calendar.getInstance().get(Calendar.YEAR)
            fecha =
                "Nació el ${mascota.fechaNacimiento.dayOfMonth} de ${mes}" +
                        " del ${mascota.fechaNacimiento.year} (${(yearActual - nacimientoYear)} años)"
            return fecha
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DaoMascota> {
        override fun createFromParcel(parcel: Parcel): DaoMascota {
            return DaoMascota(parcel)
        }

        override fun newArray(size: Int): Array<DaoMascota?> {
            return arrayOfNulls(size)
        }
    }


}