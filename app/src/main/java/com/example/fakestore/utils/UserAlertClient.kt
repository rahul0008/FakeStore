package com.example.fakestore.utils

import android.app.Activity
import android.app.ProgressDialog
import android.widget.Toast
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AlertDialog
import java.lang.Exception

class UserAlertClient(private val activityRef: Activity) {
    private var userDialog: AlertDialog? = null
    private var waitDialog: ProgressDialog? = null
    private val TAG = "UserAlertClient"
    fun showToast(message: String?) {
        Toast.makeText(activityRef.applicationContext, message, Toast.LENGTH_LONG).show()
    }

    fun showWaitDialog(message: String?) {
        closeWaitDialog()
        waitDialog = ProgressDialog(activityRef)
        waitDialog!!.setTitle(message)
        waitDialog!!.setCancelable(false)
        waitDialog!!.show()
    }

    fun showDialogMessage(title: String?, body: String?, exit: Boolean) {
        val builder = AlertDialog.Builder(activityRef)
        builder.setTitle(title).setMessage(body).setNeutralButton("OK") { dialog, which ->
            try {
                userDialog!!.dismiss()
                if (exit) activityRef.onBackPressed()
            } catch (e: Exception) {
                // Log failure
                Log.e(TAG, " -- Dialog dismiss failed")
            }
        }
        if (exit) builder.setCancelable(false)
        userDialog = builder.create()
        userDialog!!.show()
    }

    fun showDialogMessage(title: String?, body: String?, intent: Intent?) {
        val builder = AlertDialog.Builder(activityRef)
        builder.setTitle(title).setMessage(body).setNeutralButton("OK") { dialog, which ->
            try {
                userDialog!!.dismiss()
                activityRef.startActivity(intent)
                activityRef.finish()
            } catch (e: Exception) {
                // Log failure
                Log.e(TAG, " -- Dialog dismiss failed")
            }
        }
        builder.setCancelable(false)
        userDialog = builder.create()
        userDialog!!.show()
    }

    fun closeWaitDialog() {
        try {
            waitDialog!!.dismiss()
        } catch (e: Exception) {
            //
        }
    }
}