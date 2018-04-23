package com.djavid.checksonline.presenter.qrcode

import android.graphics.PointF
import com.arellomobile.mvp.InjectViewState
import com.djavid.checksonline.base.BasePresenter
import com.djavid.checksonline.interactors.QrCodeInteractor
import com.djavid.checksonline.model.entities.Receipt
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import ru.terrakok.cicerone.Router
import java.io.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject

@InjectViewState
class QRCodePresenter @Inject constructor(
        private val interactor: QrCodeInteractor,
        router: Router
) : BasePresenter<QRCodeView>(router) {

    var inProcess: Boolean = false

    override fun onFirstViewAttach() {

    }

    fun onQrCodeRead(text: String, points: Array<PointF>) {
        val m = getMatcher(text)

        if (m.matches()) {
            viewState.setDecodingEnabled(false)
            getCheck(m.group(3), m.group(4), m.group(5), false)
        }
    }

    fun onScanBtnClick() {
        if (!inProcess) {
            viewState.setDecodingEnabled(true)
        }
    }

    private fun getMatcher(code: String): Matcher {
        val pattern = "t=(\\d+T\\d+)&s=(\\d+\\.\\d+)&fn=(\\d+)&i=(\\d+)&fp=(\\d+)&n=(\\d+)"
        val r = Pattern.compile(pattern)

        return r.matcher(code)
    }

    private fun getCheck(fiscalDriveNumber: String, fiscalDocumentNumber: String, fiscalSign: String,
                         sendToEmail: Boolean) {

        inProcess = true

        interactor.getCheck(fiscalDriveNumber, fiscalDocumentNumber, fiscalSign, sendToEmail)
                .doOnSubscribe(this::unsubscribeOnDestroy)
                .retry(2L)
                .subscribe({

                    try {
                        val body = it.string()
                        val receiptJsonObject = JsonParser().parse(body)
                                .asJsonObject.get("document")
                                .asJsonObject.get("receipt").asJsonObject
                        saveJsonToFile(receiptJsonObject)

                        val receipt: Receipt = GsonBuilder().create()
                                .fromJson(receiptJsonObject, Receipt::class.java)
                        sendCheck(receipt)

                    } catch (e: Exception) {
                        e.printStackTrace()
                        viewState.showMessage("Invalid JSON")
                        inProcess = false
                    }


                    /*
                    val check: CheckResponseFns.Document = gson.fromJson(body, CheckResponseFns.Document::class.java)

                    val receipt = check.receipt
                    sendCheck(receipt)

                    val receipt = it.document.receipt
                    */

                }, {
                    inProcess = false
                    viewState.showMessage("Try again!")
                    processError(it)
                })
    }

    private fun sendCheck(receipt: Receipt) {

        inProcess = true

        interactor.sendCheck(receipt)
                .doOnSubscribe(this::unsubscribeOnDestroy)
                .subscribe({
                    viewState.showMessage("Sent successfully!")
                    inProcess = false
                }, {
                    inProcess = false
                    viewState.showMessage("Try again!")
                    processError(it)
                })
    }

    private fun saveJsonToFile(jsonObject: JsonObject) {

        try {
            var output: Writer? = null
            val fileJson = File("/sdcard/jsons.json")

            if (!fileJson.exists()) {
                fileJson.createNewFile();
                output = BufferedWriter(FileWriter(fileJson, true))
                output.append("[")
            }

            if (output == null) output = BufferedWriter(FileWriter(fileJson, true))
            val pr = PrintWriter(output)

            pr.println(jsonObject.toString() + ",\n")
            pr.flush()
            pr.close()

            viewState.showMessage("Written to file")

        } catch (e : Exception) {
            e.printStackTrace()
            viewState.showMessage("Error writing to file!")
        }
    }

}