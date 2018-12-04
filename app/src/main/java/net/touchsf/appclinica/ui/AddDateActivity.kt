package net.touchsf.appclinica.ui

import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_add_date.*
import net.touchsf.appclinica.R
import net.touchsf.appclinica.ui.base.BaseActivity
import net.touchsf.appclinica.util.Constants

class AddDateActivity : BaseActivity() {

    override fun setUp() {
        setUpSpeciality()
        setUpDoctors()
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
