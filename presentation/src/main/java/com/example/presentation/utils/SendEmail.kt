package com.example.presentation.utils
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class SendEmail(private val activity: Activity) {
    private fun sendEmail() {
        val phoneNumber = Build.MODEL
        val androidVersion = Build.VERSION.RELEASE
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "message/rfc822"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("tradestatistics@inbox.ru"))
        intent.putExtra(Intent.EXTRA_SUBJECT, "Error")
        intent.putExtra(Intent.EXTRA_TEXT, "Describe here what actions led to this error and other information that could help us\n\n\n\nPhone model: $phoneNumber\nAndroid version: $androidVersion")

        try {
            activity.startActivity(Intent.createChooser(intent, "Send mail..."))
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(activity, "There are no email clients installed.", Toast.LENGTH_SHORT).show()
        }
    }

    fun showAlertDialog() {
        val alertDialogBuilder = AlertDialog.Builder(activity)
        alertDialogBuilder.setTitle("Do you find errors?")
        alertDialogBuilder.setMessage("Would you like to send an error report and make the app better?")
        alertDialogBuilder.setPositiveButton("Send") { _: DialogInterface, _: Int ->
            sendEmail()
        }
        alertDialogBuilder.setNegativeButton("Cancel") { _: DialogInterface, _: Int -> }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}