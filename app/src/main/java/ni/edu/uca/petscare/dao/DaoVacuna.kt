package ni.edu.uca.petscare.dao

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import ni.edu.uca.petscare.entidades.Mascota
import ni.edu.uca.petscare.entidades.Vacuna
import java.time.LocalDate

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

    fun editarVacuna(idVacuna: Int,
        idMascota: Int,
        nombreVacunas: String,
        fechaDeVacunacion: LocalDate,
        nombreClinica: String,
        _estado: String
    ) : Boolean {
        try {
            var i = 0
            while (i < listVacuna.size) {
                if (listVacuna[i].idVacuna == idVacuna) {
                    listVacuna[i].idMascota == idMascota
                    listVacuna[i].nombreVacuna = nombreVacunas
                    listVacuna[i].fechaVacunacion = fechaDeVacunacion
                    listVacuna[i].clinica = nombreClinica
                    listVacuna[i].estado = _estado
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
                    vacuna= listVacuna[i]
                    return vacuna
                }
                i++
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    fun mostrarVacuna(): ArrayList<Vacuna> {
        val vacuna = listVacuna
        try {
            return ArrayList(vacuna.sortedWith(compareBy(Vacuna::nombreVacuna)))
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return vacuna
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