package net.touchsf.appclinica.ui.content.dates

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_date.*
import net.touchsf.appclinica.R
import net.touchsf.appclinica.database.Database
import net.touchsf.appclinica.database.entity.Date
import net.touchsf.appclinica.preference.AppPrefs
import net.touchsf.appclinica.ui.adapter.GeneralSpinnerAdapter
import net.touchsf.appclinica.ui.base.BaseActivity
import net.touchsf.appclinica.util.AlertDialogs
import net.touchsf.appclinica.util.Constants
import java.util.*

class AddDateActivity : BaseActivity() {

    lateinit var appPrefs: AppPrefs

    override fun setUp() {
        appPrefs = AppPrefs(this)

        setUpSpeciality()
        setUpDoctors()
        setUpTimes()

        tvCancel.setOnClickListener {
            finish()
        }

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                // do nothing
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // do nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                validateFields()
            }
        }
        etDate.addTextChangedListener(textWatcher)

        etDate.setOnTouchListener { _, event ->
            if (MotionEvent.ACTION_UP == event.action) {
                openDatePickerDialog()
            }
            true
        }

        btnPay.setOnClickListener {
            validateDateToSave()
        }

        ivBack.setOnClickListener {
            finish()
        }

        validateFields()
    }

    private fun validateDateToSave() {
        val newCalendar = Calendar.getInstance()
        newCalendar.add(Calendar.DAY_OF_MONTH, +1)

        val nYear = newCalendar.get(Calendar.YEAR)
        val nMonth = newCalendar.get(Calendar.MONTH)
        val nDay = newCalendar.get(Calendar.DAY_OF_MONTH)
        var monthStr = "${nMonth + 1}"
        if (nMonth < 10) {
            monthStr = "0${nMonth + 1}"
        }
        var dayStr = "$nDay"
        if (nDay < 10) {
            dayStr = "0$nDay"
        }
        val tomorrow = "$dayStr/$monthStr/$nYear"

        val dateToSave = etDate.text.toString().trim()
        if (dateToSave != tomorrow) {
            val intent = Intent(this, PayDateActivity::class.java)
            intent.putExtra("price", defaultPrice)
            startActivityForResult(intent, 123)
        } else {
            AlertDialogs.showMessage(this, R.string.date_is_no_available_for_dates)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 123 && resultCode == Activity.RESULT_OK) {
            saveDate()
        }
    }

    private fun saveDate() {
        Toast.makeText(this, R.string.your_date_has_been_saved, Toast.LENGTH_LONG).show()

        Database.getDatabase(this).dateDao()
                .insertDate(Date(
                        user_id = appPrefs.usetId,
                        speciality = Constants.SPECIALITY_TYPES[spSpeciality.selectedItemPosition - 1],
                        doctor = Constants.DOCTORS[spDoctor.selectedItemPosition - 1],
                        date = etDate.text.toString().trim(),
                        time = Constants.TIMES[spTime.selectedItemPosition - 1]
                ))
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun openDatePickerDialog() {
        val maxCalendar = Calendar.getInstance()
        maxCalendar.add(Calendar.MONTH, +2)

        val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            var monthStr = "${month + 1}"
            if (month < 10) {
                monthStr = "0${month + 1}"
            }

            var dayStr = "$dayOfMonth"
            if (dayOfMonth < 10) {
                dayStr = "0$dayOfMonth"
            }
            etDate.setText("$dayStr/$monthStr/$year")
        }, year, month, day)

        datePicker.datePicker.minDate = java.util.Date().time
        datePicker.datePicker.maxDate = maxCalendar.timeInMillis
        datePicker.show()
    }

    private val defaultPrice = 50.00

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    private fun validateFields() {
        val date = etDate.text.toString().trim()

        if (date.isNotEmpty() && spSpeciality.selectedItem != -1 &&
                spDoctor.selectedItem != -1 && spTime.selectedItem != -1) {
            tvTotalPrice.text = "S/ $defaultPrice"
            btnPay.isEnabled = true
            btnPay.isClickable = true
            btnPay.alpha = 1.0f
        } else {
            tvTotalPrice.text = "S/ --"
            btnPay.isEnabled = false
            btnPay.isClickable = false
            btnPay.alpha = 0.5f
        }
    }

    private fun setUpTimes() {
        val adapter = GeneralSpinnerAdapter(spTime)
        adapter.nothingSelectedDataItem = "-- Seleccione uno --"
        adapter.update(Constants.TIMES)
        adapter.setSelection(-1)
        spTime.adapter = adapter
    }

    private fun setUpDoctors() {
        val adapter = GeneralSpinnerAdapter(spDoctor)
        adapter.nothingSelectedDataItem = "-- Seleccione uno --"
        adapter.update(Constants.DOCTORS)
        adapter.setSelection(-1)
        spDoctor.adapter = adapter
    }

    private fun setUpSpeciality() {
        val adapter = GeneralSpinnerAdapter(spSpeciality)
        adapter.nothingSelectedDataItem = "-- Seleccione uno --"
        adapter.update(Constants.SPECIALITY_TYPES)
        adapter.setSelection(-1)
        spSpeciality.adapter = adapter
    }

    override fun getLayout() = R.layout.activity_add_date

}
