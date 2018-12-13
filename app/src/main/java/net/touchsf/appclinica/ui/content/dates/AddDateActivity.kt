package net.touchsf.appclinica.ui.content.dates

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_date.*
import net.touchsf.appclinica.R
import net.touchsf.appclinica.database.Database
import net.touchsf.appclinica.database.entity.Date
import net.touchsf.appclinica.preference.AppPrefs
import net.touchsf.appclinica.ui.base.BaseActivity
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
            val intent = Intent(this, PayDateActivity::class.java)
            intent.putExtra("price", defaultPrice)
            startActivityForResult(intent, 123)
        }

        validateFields()
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
                        speciality = spSpeciality.selectedItem.toString(),
                        doctor = spDoctor.selectedItem.toString(),
                        date = etDate.text.toString().trim(),
                        time = spTime.selectedItem.toString()
                ))
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun openDatePickerDialog() {
        val maxCalendar = Calendar.getInstance()
        maxCalendar.add(Calendar.DAY_OF_YEAR, +7)

        val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            etDate.setText("$dayOfMonth/${month + 1}/$year")
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
        if (date.isNotEmpty()) {
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
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Constants.TIMES)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spTime.adapter = adapter
    }

    private fun setUpDoctors() {
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Constants.DOCTORS)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spDoctor.adapter = adapter
    }

    private fun setUpSpeciality() {
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Constants.SPECIALITY_TYPES)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spSpeciality.adapter = adapter
    }

    override fun getLayout() = R.layout.activity_add_date

}
