package com.djavid.checksonline.features.receipt_input

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.design.widget.TextInputLayout
import android.view.View
import android.widget.TextView
import com.djavid.checksonline.R
import com.djavid.checksonline.features.root.ViewRoot
import com.djavid.checksonline.model.networking.bodies.FnsValues
import com.djavid.checksonline.utils.addTextChangedListener
import com.djavid.checksonline.utils.removeLeadingZeros
import kotlinx.android.synthetic.main.dialog_success.view.*
import kotlinx.android.synthetic.main.fragment_receipt_input.view.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.joda.time.DateTime
import java.util.*
import javax.inject.Inject

class ReceiptView @Inject constructor(
        @ViewRoot private val viewRoot: View
) : ReceiptContract.View {

    private var dateTime: DateTime? = null
    private var stateDialog: Dialog? = null

    private lateinit var progressDialog: Dialog
    private lateinit var presenter: ReceiptContract.Presenter


    override fun init(presenter: ReceiptContract.Presenter) {
        this.presenter = presenter

        initProgressDialog()
        initEditTextListeners()

        viewRoot.btn_back.setOnClickListener { presenter.onBackPressed() }

        viewRoot.input_date.setOnClickListener { createDatePicker() }

        viewRoot.btn_submit.setOnClickListener {
            if (validateForm()) {
                presenter.sendCheck(getFnsValues())
            }
        }

        viewRoot.toolbar_title.text = viewRoot.context?.getString(R.string.title_manual_input)

    }

    private fun initEditTextListeners() {
        viewRoot.input_fn.addTextChangedListener { fieldNoError(viewRoot.text_layout_fn) }
        viewRoot.input_fd.addTextChangedListener { fieldNoError(viewRoot.text_layout_fd) }
        viewRoot.input_fp.addTextChangedListener { fieldNoError(viewRoot.text_layout_fp) }
        viewRoot.input_sum.addTextChangedListener { fieldNoError(viewRoot.text_layout_sum) }
        viewRoot.input_date.addTextChangedListener { fieldNoError(viewRoot.text_layout_date) }
    }

    private fun createDatePicker() {
        val calendar = Calendar.getInstance()

        DatePickerDialog(viewRoot.context, { view, year, month, dayOfMonth ->
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
        TimePickerDialog(viewRoot.context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)

            dateTime = DateTime(calendar)
            viewRoot.input_date.setText(dateTime?.toString("dd/MM/yyyy HH:mm") ?: "")
        },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        ).show()
    }

    private fun validateForm(): Boolean {
        if (validateFn() and validateFd() and validateFp() and validateSum() and validateDate()) {
            return true
        }

        return false
    }

    private fun validateFn(): Boolean {
        val text = viewRoot.input_fn.text

        if (text.isEmpty()) {
            return fieldError(viewRoot.text_layout_fn, R.string.error_empty_field)
        }

        return fieldNoError(viewRoot.text_layout_fn)
    }

    private fun validateFd(): Boolean {
        val text = viewRoot.input_fd.text

        if (text.isEmpty()) {
            return fieldError(viewRoot.text_layout_fd, R.string.error_empty_field)
        }

        return fieldNoError(viewRoot.text_layout_fd)
    }

    private fun validateFp(): Boolean {
        val text = viewRoot.input_fp.text

        if (text.isEmpty()) {
            return fieldError(viewRoot.text_layout_fp, R.string.error_empty_field)
        }

        return fieldNoError(viewRoot.text_layout_fp)
    }

    private fun getFnsValues(): FnsValues {
        return FnsValues(dateTime!!.toString(),
                viewRoot.input_sum.text.toString(),
                viewRoot.input_fn.text.toString().removeLeadingZeros(),
                viewRoot.input_fd.text.toString().removeLeadingZeros(),
                viewRoot.input_fp.text.toString().removeLeadingZeros())
    }

    private fun validateSum(): Boolean {
        val text = viewRoot.input_sum.text

        if (text.isEmpty()) {
            return fieldError(viewRoot.text_layout_sum, R.string.error_empty_field)
        }

        return fieldNoError(viewRoot.text_layout_sum)
    }

    private fun validateDate(): Boolean {
        val text = viewRoot.input_date.text

        if (text.isEmpty()) {
            return fieldError(viewRoot.text_layout_date, R.string.error_empty_field)
        } else if (text.isNotEmpty()) {
            if (dateTime != null) {
                if (dateTime!!.isAfterNow) {
                    return fieldError(viewRoot.text_layout_date, R.string.error_future_date)
                }
            } else {
                return fieldError(viewRoot.text_layout_date, R.string.error_empty_field)
            }
        }

        return fieldNoError(viewRoot.text_layout_date)
    }

    private fun fieldNoError(layout: TextInputLayout): Boolean {
        layout.error = ""
        return true
    }

    private fun fieldError(layout: TextInputLayout, error: Int): Boolean {
        layout.error = viewRoot.context?.getString(error)
        return false
    }


    override fun showProgress(show: Boolean) {
        if (show) {
            progressDialog.show()
        } else {
            progressDialog.dismiss()
        }
    }

    private fun initProgressDialog() {
        progressDialog = Dialog(viewRoot.context).apply {
            setContentView(R.layout.dialog_progress)
            setCancelable(false)
        }
    }

    override fun showFailDialog() {
        stateDialog = Dialog(viewRoot.context).apply {
            setContentView(R.layout.dialog_fail)
            setCancelable(true)

            window?.let { w ->
                w.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                w.attributes?.dimAmount = 0.7f
            }

            btn_back.setOnClickListener { presenter.onBackPressed() }
            findViewById<TextView>(R.id.btn_scan_more).apply {
                text = context.getString(R.string.continue_manual_input)
                setOnClickListener { dismiss() }
            }
            show()
        }
    }

    override fun showSuccessDialog(receiptId: String) {
        stateDialog = Dialog(viewRoot.context).apply {

            setContentView(R.layout.dialog_success)
            setCancelable(true)

            window?.let { w ->
                w.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                w.attributes?.dimAmount = 0.7f
            }

            viewRoot.btn_open.setOnClickListener { presenter.onOpenButtonClicked(receiptId) }
            findViewById<TextView>(R.id.btn_scan_more).apply {
                text = context.getString(R.string.continue_manual_input)
                setOnClickListener { dismiss() }
            }
            show()
        }
    }

    override fun showWaitDialog() {
        stateDialog = Dialog(viewRoot.context).apply {
            setContentView(R.layout.dialog_wait)
            setCancelable(true)

            window?.let { w ->
                w.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                w.attributes?.dimAmount = 0.7f
            }

            viewRoot.btn_back.setOnClickListener { presenter.onBackPressed() }
            findViewById<TextView>(R.id.btn_scan_more).apply {
                text = context.getString(R.string.continue_manual_input)
                setOnClickListener { dismiss() }
            }
            show()
        }
    }

    //    fun showDialog(type: ReceiptContract.Dialog, layoutRes: Int) {
//        val map = mapOf<ReceiptContract.Dialog, Pair<Int, () -> Unit>>(
//                ReceiptContract.Dialog.FAIL to Pair(R.id.btn_back, presenter.onBackPressed()),
//                ReceiptContract.Dialog.SUCCESS to Pair(R.id.btn_open, { presenter.onOpenButtonClicked(receiptId) } ),
//                ReceiptContract.Dialog.WAIT to Pair(R.id.btn_back, presenter.onBackPressed())
//        )
//
//        context?.let {
//            stateDialog = Dialog(it).apply {
//                setContentView(layoutRes)
//                setCancelable(true)
//
//                window?.let { w ->
//                    w.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//                    w.attributes?.dimAmount = 0.7f
//                }
//
//                findViewById()
//            }
//        }
//    }


}