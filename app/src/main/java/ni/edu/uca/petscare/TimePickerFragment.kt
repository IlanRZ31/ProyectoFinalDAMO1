package ni.edu.uca.petscare

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment

class TimePickerFragment(val listener: (String)-> Unit): DialogFragment(), TimePickerDialog.OnTimeSetListener {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour: Int = calendar.get(Calendar.HOUR_OF_DAY)
        val minute: Int = calendar.get(Calendar.MINUTE)
        val dialog = TimePickerDialog(activity as Context, this, hour, minute, false )
        return dialog
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        if(hourOfDay >= 0 && hourOfDay < 10 && minute > 9){
            listener("0$hourOfDay:$minute")
        }else if(hourOfDay >= 0 && hourOfDay < 10 && minute < 10){
            listener("0$hourOfDay:0$minute")
        }else if(hourOfDay > 9 && minute < 10){
            listener("$hourOfDay:0$minute")
        }else{
            listener("$hourOfDay:$minute")
        }

    }
}