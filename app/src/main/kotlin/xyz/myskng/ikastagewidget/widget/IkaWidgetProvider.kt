package xyz.myskng.ikastagewidget.widget

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import xyz.myskng.ikastagewidget.service.WidgetUpdaterService

class IkaWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        //set service alarm
        var sintent : Intent = Intent(context,WidgetUpdaterService::class.java)
        context?.startService(sintent)
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }
}