package ni.edu.uca.petscare.dao

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.annotation.RequiresApi
import ni.edu.uca.petscare.entidades.Mascota
import ni.edu.uca.petscare.entidades.Vacuna
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class DaoVacuna() : Parcelable {
    var listVacuna = ArrayList<Vacuna>()

    constructor(parcel: Parcel) : this() {
    }


    fun agregarVacuna(
        idMascota: Int,
        nombreVacunas: String,
        fechaDeVacunacion: LocalDate,
        nombreClinica: String,
        _estado: String
    ): Boolean {
        val idVacuna = crearIdVacuna()
        try {
            Log.wtf("DAO_VACUNA", "AL MENOS LLEGA ACA")
            val vacuna =
                Vacuna(
                    idVacuna,
                    idMascota,
                    nombreVacunas,
                    fechaDeVacunacion,
                    nombreClinica,
                    _estado
                )
            listVacuna.add(vacuna)
            return true
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun obtenerEstado(estado: String, vacuna: Vacuna): String {
        try {
            val yearActual = Calendar.getInstance().get(Calendar.YEAR)
            val date: Date = Date()

            val mesFormat: DateFormat = SimpleDateFormat("MM")
            val mesActual = mesFormat.format(date)

            val fechaFormat: DateFormat = SimpleDateFormat("dd")
            val fechaActual = fechaFormat.format(date)

            if (vacuna.fechaVacunacion.year > yearActual) {
                return "Programado"
            } else if (vacuna.fechaVacunacion.month.value > mesActual.toInt()) {
                return "Programado"
            } else if (vacuna.fechaVacunacion.dayOfMonth > fechaActual.toInt()) {
                return "Programado"
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return estado
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun obtenerFechaVacunacion(vacuna: Vacuna): String {
        var fecha = ""
        try {
            fecha =
                "${vacuna.fechaVacunacion.year}-${vacuna.fechaVacunacion.month.value}-${vacuna.fechaVacunacion.dayOfMonth}"
        }catch (ex:Exception){
            ex.printStackTrace()
        }
        return fecha

    }

    private fun crearIdVacuna(): Int {
        var idVacuna = 1
        var i = 0
        try {
            do {
                if (listVacuna.isEmpty()) {
                    return idVacuna
                } else {
                    if (idVacuna <= listVacuna[i].idVacuna) {
                        idVacuna = listVacuna[i].idVacuna
                    }
                }
                i++
            } while (i < listVacuna.size)
        } catch (ex: Exception) {
            ex.printStackTrace()
            return 0
        }
        return idVacuna + 1
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun editarVacuna(
        idVacuna: Int,
        nombreVacunas: String,
        fechaDeVacunacion: LocalDate,
        nombreClinica: String,
        _estado: String
    ): Boolean {
        try {
            var i = 0
            while (i < listVacuna.size) {
                if (listVacuna[i].idVacuna == idVacuna) {
                    listVacuna[i].nombreVacuna = nombreVacunas
                    listVacuna[i].fechaVacunacion = fechaDeVacunacion
                    listVacuna[i].clinica = nombreClinica
                    listVacuna[i].estado = obtenerEstado(_estado, listVacuna[i])
                    return true
                }
                i++
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return false
    }

    fun eliminarVacuna(idVacuna: Int): Boolean {
        try {
            var i = 0
            while (i < listVacuna.size) {
                if (listVacuna[i].idVacuna == idVacuna) {
                    listVacuna.removeAt(i)
                    return true
                }
                i++
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return false
    }

    fun buscarVacunaID(idVacuna: Int): Vacuna? {
        var vacuna: Vacuna
        try {
            var i = 0
            while (i < listVacuna.size) {
                if (listVacuna[i].idVacuna == idVacuna) {
                    vacuna = listVacuna[i]
                    return vacuna
                }
                i++
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun mostrarVacuna(idMascota: Int): ArrayList<Vacuna> {
        var vacuna = listVacuna
        try {
            val vacunaDeMascota = ArrayList<Vacuna>()
            vacuna = ArrayList(
                vacuna.sortedWith(
                    compareBy(
                        { it.fechaVacunacion.year },
                        { it.fechaVacunacion.month.value },
                        Vacuna::estado
                    )
                )
            )
            for (x in vacuna) {
                if (x.idMascota == idMascota) {
                    vacunaDeMascota.add(x)
                }
            }
            return vacunaDeMascota
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return vacuna
    }

    fun eliminarVacunaWhere(idMascota: Int): Boolean {
        try {
            var i = 0
            while (i < listVacuna.size) {
                if (listVacuna[i].idMascota == idMascota) {
                    listVacuna.removeAt(i)
                }
                i++
            }
            return true
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return false
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DaoVacuna> {
        override fun createFromParcel(parcel: Parcel): DaoVacuna {
            return DaoVacuna(parcel)
        }

        override fun newArray(size: Int): Array<DaoVacuna?> {
            return arrayOfNulls(size)
        }
    }
}