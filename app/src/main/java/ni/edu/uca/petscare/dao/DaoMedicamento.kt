package ni.edu.uca.petscare.dao

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import ni.edu.uca.petscare.entidades.Medicamento
import ni.edu.uca.petscare.entidades.Vacuna
import java.sql.Time
import java.time.LocalDate

class DaoMedicamento() : Parcelable {

    var listMedicamento = ArrayList<Medicamento>()

    constructor(parcel: Parcel) : this() {
    }

    fun agregarMedic(
        idMascota: Int,
        nombreMedicamento: String,
        intervaloTiempo: Int,
        horaInicial: String,
        fechaFin: LocalDate,
        siguienteDosis: String
    ): Boolean {
        var horaInicio = obtenerHora(horaInicial)
        val idMedic = crearIdMedic()
        try {
            Log.wtf(
                "DAO_MEDICAMENTO_AGREGAR",
                "AL MENOS LLEGA ACA / ${horaInicio.hours}:${horaInicio.minutes}"
            )
            val medic =
                Medicamento(
                    idMedic,
                    idMascota,
                    nombreMedicamento,
                    intervaloTiempo,
                    horaInicio,
                    fechaFin,
                    siguienteDosis
                )
            listMedicamento.add(medic)
            return true
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return false
    }

    fun obtenerHora(tiempo: String): Time {
        var hora: Int = 0
        var minutos: Int = 0

        var actualHora: Boolean = true
        var cadenaTemp = ""
        try {
            for (ch in tiempo.iterator()) {
                if (ch != ':') {
                    cadenaTemp += "$ch"
                } else if (actualHora) {
                    hora = cadenaTemp.toInt()
                    cadenaTemp = ""
                    actualHora = false

                }
                Log.wtf("OBTENER_HORA", ">>>>>>>$cadenaTemp")
            }
            minutos = cadenaTemp.toInt()
            Log.wtf("OBTENER_HORA", ">>>>>>>>>>>>>>$hora , $minutos")
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return Time(hora, minutos, 0)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun obtenerFechaFin(medicamento: Medicamento): String {
        var fechaFin = ""
        try {

            if (medicamento.fechaFin.dayOfMonth >= 1 && medicamento.fechaFin.dayOfMonth <= 9 && medicamento.fechaFin.month.value >9){
                fechaFin = "${medicamento.fechaFin.year}-${medicamento.fechaFin.month.value}-0${medicamento.fechaFin.dayOfMonth}"
            }else if (medicamento.fechaFin.dayOfMonth >= 1 && medicamento.fechaFin.dayOfMonth <= 9 &&medicamento.fechaFin.month.value >=1 && medicamento.fechaFin.month.value <=9){
                fechaFin = "${medicamento.fechaFin.year}-0${medicamento.fechaFin.month.value}-0${medicamento.fechaFin.dayOfMonth}"
            }else if (medicamento.fechaFin.dayOfMonth >9 && medicamento.fechaFin.month.value >=1 && medicamento.fechaFin.month.value <=9){
                fechaFin = "${medicamento.fechaFin.year}-0${medicamento.fechaFin.month.value}-${medicamento.fechaFin.dayOfMonth}"
            }else{
                fechaFin = "${medicamento.fechaFin.year}-${medicamento.fechaFin.month.value}-${medicamento.fechaFin.dayOfMonth}"
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return fechaFin
    }

    fun buscarMedicamento(idMedicamento: Int): Medicamento? {
        var medicamento: Medicamento
        try {
            var i = 0
            while (i < listMedicamento.size) {
                if (listMedicamento[i].idMedicamento == idMedicamento) {
                    medicamento = listMedicamento[i]
                    return medicamento
                }
                i++
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    private fun crearIdMedic(): Int {
        var idMedic = 1
        var i = 0
        try {
            do {
                if (listMedicamento.isEmpty()) {
                    return idMedic
                } else {
                    if (idMedic <= listMedicamento[i].idMedicamento) {
                        idMedic = listMedicamento[i].idMedicamento
                    }
                }
                i++
            } while (i < listMedicamento.size)
        } catch (ex: Exception) {
            ex.printStackTrace()
            return 0
        }
        return idMedic + 1
    }

    fun editarMedic(
        idMedicamento: Int,
        nombreMedicamento: String,
        intervaloTiempo: Int,
        horaInicial: String,
        fechaFin: LocalDate,
        siguienteDosis: String
    ): Boolean {
        try {
            val horaInit = obtenerHora(horaInicial)
            Log.wtf("DAO_MEDICAMENTO_EDITAR", ">>>>>>>>>>>>>>>>${horaInit.hours.toString()}")
            var i = 0
            while (i < listMedicamento.size) {
                if (listMedicamento[i].idMedicamento == idMedicamento) {
                    listMedicamento[i].nombreMedicamento = nombreMedicamento
                    listMedicamento[i].intervaloTiempo = intervaloTiempo
                    listMedicamento[i].horaInicial = horaInit
                    listMedicamento[i].fechaFin = fechaFin
                    listMedicamento[i].siguienteDosis = siguienteDosis
                    return true
                }
                i++
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return false
    }


    fun eliminarMedic(idMedicamento: Int): Boolean {
        try {
            var i = 0
            while (i < listMedicamento.size) {
                if (listMedicamento[i].idMedicamento == idMedicamento) {
                    listMedicamento.removeAt(i)
                    return true
                }
                i++
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return false
    }

    fun mostrarMedic(idMascota: Int): ArrayList<Medicamento> {
        var medicamento = listMedicamento
        try {
            val medicDeMascota = ArrayList<Medicamento>()

            for (x in medicamento) {
                if (x.idMascota == idMascota) {
                    medicDeMascota.add(x)
                }
            }
            return medicDeMascota
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return medicamento
    }

    fun eliminarMedicamentoWhere(idMascota: Int): Boolean {
        try {
            var i = 0
            while (i < listMedicamento.size) {
                if (listMedicamento[i].idMascota == idMascota) {
                    listMedicamento.removeAt(i)
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

    companion object CREATOR : Parcelable.Creator<DaoMedicamento> {
        override fun createFromParcel(parcel: Parcel): DaoMedicamento {
            return DaoMedicamento(parcel)
        }

        override fun newArray(size: Int): Array<DaoMedicamento?> {
            return arrayOfNulls(size)
        }
    }
}