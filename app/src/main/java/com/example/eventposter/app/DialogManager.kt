package com.example.eventposter.app

import android.app.AlertDialog
import android.content.Context

object DialogManager {

    fun toLocationSettingsDialog(context: Context, listener: ClickListener) {
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        dialog.setTitle("Включить геопозицию?")
        dialog.setMessage("Вы действительно хотите включить геопозицию?")
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Да") { _,_ ->
            listener.onClick()
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Нет") { _,_ ->
            dialog.dismiss()
        }
        dialog.show()
    }

    fun toPermissionGpsMessage(context: Context, listener: ClickListener) {
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.setTitle("Недостаточно прав доступа")
        dialog.setMessage("Разрешите приложению использовать местоположение для корректной работы.")
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "К настройкам") { _,_ ->
            listener.onClick()
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Отмена") { _,_ ->
            dialog.dismiss()
        }
        dialog.show()
    }

    interface ClickListener {
        fun onClick()
    }
}