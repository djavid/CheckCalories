package com.djavid.checksonline.features.receipt_input

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.djavid.checksonline.R
import com.djavid.checksonline.features.base.BaseFragment
import com.djavid.checksonline.model.networking.bodies.FnsValues
import com.djavid.checksonline.utils.removeLeadingZeros
import kotlinx.android.synthetic.main.fragment_receipt_input.*
import kotlinx.android.synthetic.main.toolbar.*
import org.joda.time.DateTime
import java.util.*

class ReceiptInputFragment : BaseFragment(), ReceiptInputView {

    companion object {
        fun newInstance(): ReceiptInputFragment = ReceiptInputFragment()
    }

    @InjectPresenter
    lateinit var presenter: ReceiptInputPresenter

    @ProvidePresenter
    fun providePresenter(): ReceiptInputPresenter =
            Toothpick.openScopes(activity, this).getInstance(ReceiptInputPresenter::class.java)

    override val layoutResId = R.layout.fragment_receipt_input
    private var dateTime: DateTime? = null

    private lateinit var progressDialog: Dialog
    private var stateDialog: Dialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, Toothpick.openScopes(activity, this))
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isRemoving) Toothpick.closeScope(this)
    }

    override fun onPause() {
        super.onPause()

        if (progressDialog.isShowing) progressDialog.dismiss()
        if (stateDialog != null && stateDialog?.isShowing == true) stateDialog?.dismiss()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbar_title.text = context?.getString(R.string.title_manual_input)

        initProgressDialog()
        initEditTextListeners()

        btn_back.setOnClickListener { presenter.onBackPressed() }

        input_date.setOnClickListener { createDatePicker() }

        btn_submit.setOnClickListener {
            if (validateForm()) {
                presenter.sendCheck(getFnsValues())
            }
        }
    }

    private fun initEditTextListeners() {
        input_fn.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                fieldNoError(text_layout_fn)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        input_fd.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                fieldNoError(text_layout_fd)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        input_fp.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                fieldNoError(text_layout_fp)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        input_sum.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                fieldNoError(text_layout_sum)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        input_date.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                fieldNoError(text_layout_date)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })
    }


    private fun createDatePicker() {

        val calendar = Calendar.getInstance()

        DatePickerDialog(context, { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            createTimePicker(calendar)
        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun createTimePicker(calendar: Calendar) {

        TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)

            dateTime = DateTime(calendar)
            input_date.setText(dateTime?.toString("dd/MM/yyyy HH:mm") ?: "")
        },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        ).show()
    }


    private fun getFnsValues() : FnsValues {
        return FnsValues(dateTime!!.toString(),
                input_sum.text.toString(),
                input_fn.text.toString().removeLeadingZeros(),
                input_fd.text.toString().removeLeadingZeros(),
                input_fp.text.toString().removeLeadingZeros())
    }

    private fun validateForm() : Boolean {
        if (validateFn() and validateFd() and validateFp() and validateSum() and validateDate()) {
            return true
        }

        return false
    }

    private fun validateFn() : Boolean {
        val text = input_fn.text

        if (text.isEmpty()) {
            return fieldError(text_layout_fn, R.string.error_empty_field)
        }

        return fieldNoError(text_layout_fn)
    }

    private fun validateFd() : Boolean {
        val text = input_fd.text

        if (text.isEmpty()) {
            return fieldError(text_layout_fd, R.string.error_empty_field)
        }

        return fieldNoError(text_layout_fd)
    }

    private fun validateFp() : Boolean {
        val text = input_fp.text

        if (text.isEmpty()) {
            return fieldError(text_layout_fp, R.string.error_empty_field)
        }

        return fieldNoError(text_layout_fp)
    }

    private fun validateSum() : Boolean {
        val text = input_sum.text

        if (text.isEmpty()) {
            return fieldError(text_layout_sum, R.string.error_empty_field)
        }

        return fieldNoError(text_layout_sum)
    }

    private fun validateDate() : Boolean {
        val text = input_date.text

        if (text.isEmpty()) {
            return fieldError(text_layout_date, R.string.error_empty_field)
        } else if (text.isNotEmpty()) {
            if (dateTime != null) {
                if (dateTime!!.isAfterNow) {
                    return fieldError(text_layout_date, R.string.error_future_date)
                }
            } else {
                return fieldError(text_layout_date, R.string.error_empty_field)
            }
        }

        return fieldNoError(text_layout_date)
    }


    private fun fieldNoError(layout: TextInputLayout) : Boolean {
        layout.error = ""
        return true
    }

    private fun fieldError(layout: TextInputLayout, error: Int) : Boolean {
        layout.error = context?.getString(error)
        return false
    }


    override fun showProgress(show: Boolean) {
        if (show) {
            progressDialog.show()
        }
        else {
            progressDialog.dismiss()
        }
    }

    private fun initProgressDialog() {
        progressDialog = Dialog(context).apply {
            setContentView(R.layout.dialog_progress)
            setCancelable(false)
        }
    }

    override fun showFailDialog() {
        stateDialog = Dialog(context).apply {
            setContentView(R.layout.dialog_fail)
            setCancelable(true)

            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window.attributes.dimAmount = 0.7f

            findViewById<Button>(R.id.btn_back)
                    .setOnClickListener { presenter.onBackPressed() }
            findViewById<TextView>(R.id.btn_scan_more).apply {
                text = context.getString(R.string.continue_manual_input)
                setOnClickListener { dismiss() }
            }
            show()
        }
    }

    override fun showSuccessDialog(receiptId: String) {
        stateDialog = Dialog(context).apply {

            setContentView(R.layout.dialog_success)
            setCancelable(true)

            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window.attributes.dimAmount = 0.7f

            findViewById<Button>(R.id.btn_open)
                    .setOnClickListener { presenter.onOpenButtonClicked(receiptId) }
            findViewById<TextView>(R.id.btn_scan_more).apply {
                text = context.getString(R.string.continue_manual_input)
                setOnClickListener { dismiss() }
            }
            show()
        }
    }

    override fun showWaitDialog() {
        stateDialog = Dialog(context).apply {
            setContentView(R.layout.dialog_wait)
            setCancelable(true)

            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window.attributes.dimAmount = 0.7f

            findViewById<Button>(R.id.btn_back)
                    .setOnClickListener { presenter.onBackPressed() }
            findViewById<TextView>(R.id.btn_scan_more).apply {
                text = context.getString(R.string.continue_manual_input)
                setOnClickListener { dismiss() }
            }
            show()
        }
    }

}
