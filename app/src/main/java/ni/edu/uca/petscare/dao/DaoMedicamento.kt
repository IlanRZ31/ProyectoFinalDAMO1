package ni.edu.uca.petscare.dao

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import ni.edu.uca.petscare.entidades.Medicamento
import java.sql.Time
import java.time.LocalDate

class DaoMedicamento(): Parcelable {

    var listMedicamento = ArrayList<Medicamento>()

    constructor(parcel: Parcel) : this() {
    }

    fun agregarMedic(
        idMedicamento: Int,
        idMascota: Int,
        nombreMedicamento: String,
        intervaloTiempo: Int,
        horaInicial: Time,
        fechaFin: LocalDate
    ): Boolean{
        val idMedic = crearIdMedic()
        try {
            Log.wtf("DAO_MEDICAMENTO", "AL MENOS LLEGA ACA")
            val medic =
                Medicamento(idMedicamento, idMascota, nombreMedicamento, intervaloTiempo, horaInicial, fechaFin)
            listMedicamento.add(medic)
            return true
        }catch (ex: Exception){
            ex.printStackTrace()
        }
        return false
    }

    private fun crearIdMedic(): Int{
        var idMedic = 1
        var i = 0
        try{
            do {
                if (listMedicamento.isEmpty()){
                    return idMedic
                }else{
                    if(idMedic <= listMedicamento[i].idMedicamento){
                        idMedic = listMedicamento[i].idMedicamento
                    }
                }
                i++
            }while (i < listMedicamento.size)
        }catch (ex: Exception){
            ex.printStackTrace()
            return 0
        }
        return idMedic + 1
    }
    fun editarMedic(
        idMedicamento: Int,
        idMascota: Int,
        nombreMedicamento: String,
        intervaloTiempo: Int,
        horaInicial: Time,
        fechaFin: LocalDate
    ): Boolean{
        try{
            var i = 0
            while (i < listMedicamento.size){
                if (listMedicamento[i].idMedicamento == idMedicamento){
                    listMedicamento[i].idMascota = idMascota
                    listMedicamento[i].nombreMedicamento = nombreMedicamento
                    listMedicamento[i].intervaloTiempo = intervaloTiempo
                    listMedicamento[i].horaInicial = horaInicial
                    listMedicamento[i].fechaFin = fechaFin
                    return true
                }
                i++
            }
        }catch (ex: Exception){
            ex.printStackTrace()
        }
        return false
    }

    fun eliminarMedic(idMedicamento: Int): Boolean{
        try{
            var i = 0
            while(i < listMedicamento.size){
                if (listMedicamento[i].idMedicamento == idMedicamento){
                    listMedicamento.removeAt(i)
                    return true
                }
                i++
            }
        }catch (ex: Exception){
            ex.printStackTrace()
        }
        return false
    }

    fun mostrarMedic(): ArrayList<Medicamento>{
        val medicamento = listMedicamento
        try{
            return ArrayList(medicamento.sortedWith(compareBy(Medicamento::nombreMedicamento)))
        }catch (ex: Exception){
            ex.printStackTrace()
        }
        return medicamento
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