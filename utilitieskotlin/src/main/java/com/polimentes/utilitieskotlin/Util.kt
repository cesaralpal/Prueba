package com.polimentes.utilitieskotlin

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import java.io.*
import java.text.DecimalFormat
import java.util.regex.Pattern


object Util {

    fun callFragment(fragmentManager: FragmentManager, containerId: Int, fragment: Fragment, addToStack: Boolean, tag: String) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(containerId, fragment, tag)
        if (addToStack) {
            fragmentTransaction.addToBackStack(tag)
        }
        fragmentTransaction.commit()
    }


    fun showMessage(context: Context, messageResource: Any) {
        Toast.makeText(context, if (messageResource is Int) context.getString(messageResource) else messageResource as String, Toast.LENGTH_SHORT).show()
    }

    fun logout(context: Context, classToRedirect: Class<*>) {
        val intent: Intent = Intent(context, classToRedirect)
        SharedPreferencesManager[context, Constants.SPM_ACCESS_TOKEN] = Constants.SPM_DEFAULT_STRING
        SharedPreferencesManager[context, Constants.SPM_REFRESH_TOKEN] = Constants.SPM_DEFAULT_STRING
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
        (context as FragmentActivity).finish()
    }

    fun redirectTo(context: Context, classToRedirect: Class<*>,
                   flags: Array<Int>? = arrayOf(), params: Bundle? = null, finish: Boolean? = false) {
        val intent: Intent = Intent(context, classToRedirect)
        flags?.let {
            for (flag in flags) {
                intent.addFlags(flag)
            }
        }
        params?.let {
            intent.putExtras(params)
        }
        context.startActivity(intent)
        if (finish!!) {
            (context as FragmentActivity).finish()
        }
    }

    fun redirectToResult(context: Context, classToRedirect: Class<*>, requestCode: Int, params: Bundle? = null) {
        val intent: Intent = Intent(context, classToRedirect)
        params?.let {
            intent.putExtras(params)
        }
        (context as FragmentActivity).startActivityForResult(intent, requestCode)
    }

    /**
     * Método que separa con comas una cantidad
     *
     * @param amount flotante con la cantidad a formatear
     * @return String formateado con la cantidad que se paso como argumento
     */
    fun commaSeparatedFloat(amount: Float): String {
        val formatter = DecimalFormat("###,###,###.##")
        return formatter.format(amount)
    }


    fun createAlertDialog(context: Context, title: Any?, message: Any?, isCancelabe: Boolean = false): AlertDialog {
        val alertDialog: AlertDialog = AlertDialog.Builder(context).create()
        alertDialog.setTitle(
                when (title) {
                    is String -> title
                    is Int -> context.getString(title)
                    else -> context.getString(R.string.app_name)
                }
        )
        alertDialog.setMessage(
                when (message) {
                    is String -> message
                    is Int -> context.getString(message)
                    else -> "Wait a moment..."
                }
        )
        alertDialog.setCancelable(isCancelabe)

        return alertDialog
    }

    fun checkFields(conditionStatement: () -> Boolean, actionToTakeOnFail: () -> Unit): Boolean {
        if (conditionStatement.invoke()) {
            return true
        }
        actionToTakeOnFail.invoke()
        return false
    }

    fun matchRegex(regex: String, textToMatch: String?): Boolean {
        textToMatch?.let {
            val pattern: Pattern = Pattern.compile(regex)
            return pattern.matcher(textToMatch).matches()
        }
        return false
    }

    fun hideSoftKeyboard(activity: Activity) {
        val v = activity.currentFocus
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        v?.let {
            val windowToken = it.windowToken
            if (windowToken != null) {
                inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
            }
        }
    }

    fun getImageUri(delete: Boolean, context: Context): Uri? {
        var m_imgUri: Uri? = null
        val m_file: File
        try {
            val m_imagePath = context.getExternalFilesDir(null)!!.path + File.separator + "profile.jpg"
            m_file = File(m_imagePath)
            if (m_file.exists() && delete) {
                m_file.delete()
            }
            m_imgUri = Uri.fromFile(m_file)
        } catch (p_e: Exception) {
        }

        return m_imgUri
    }

    /**
     * Metodo encargado de redimensionar la imagen desde una uri
     * @param bitmap
     * @param newWidth
     * @param newHeight
     * @param path
     * @return
     */
    fun redimenBitmap(bitmap: Bitmap, newWidth: Float, newHeight: Float, path: String): Uri {
        val scaledBitmap: Bitmap
        val width = bitmap.width
        val height = bitmap.height
        val scaleWidth = newWidth / width.toFloat()
        val scaleHeight = newHeight / height.toFloat()

        val matrix = Matrix()
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight)
        // recreate the new Bitmap
        scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false)
        val stream = ByteArrayOutputStream()
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val image = stream.toByteArray()
        val out: OutputStream

        val file = File(path)

        try {
            file.createNewFile()
            out = FileOutputStream(file)
            if (image != null)
                out.write(image)
            out.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return Uri.fromFile(file)
    }
}