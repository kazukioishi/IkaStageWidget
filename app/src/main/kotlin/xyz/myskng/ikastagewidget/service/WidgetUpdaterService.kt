package xyz.myskng.ikastagewidget.service

import android.app.AlarmManager
import android.app.IntentService
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.widget.RemoteViews
import xyz.myskng.ikastagewidget.R
import xyz.myskng.ikastagewidget.activity.MainActivity
import xyz.myskng.ikastagewidget.api.StageInfo
import xyz.myskng.ikastagewidget.model.GachiStage
import xyz.myskng.ikastagewidget.model.Stage
import xyz.myskng.ikastagewidget.widget.IkaWidgetProvider
import xyz.myskng.widget.TextRenderer
import java.text.SimpleDateFormat
import java.util.*

class WidgetUpdaterService : IntentService("WidgetUpdaterService") {
    override fun onHandleIntent(intent: Intent?) {
        var context : Context = getBaseContext()
        //search widgets
        var widgetmanager : AppWidgetManager? = AppWidgetManager.getInstance(context)
        var componentname : ComponentName? = ComponentName(context,IkaWidgetProvider::class.java)
        //if there is no widgets then quit
        if(widgetmanager?.getAppWidgetIds(componentname)?.count() == 0) return
        //get remote view
        var remoteview : RemoteViews = RemoteViews(context.packageName, R.layout.ikawidget_layout)
        //initialize view
        var regularbitmap : Bitmap = TextRenderer.getFontBitmap(context,context.getString(R.string.regularmatch), Color.GREEN,25f)
        var gachibitmap : Bitmap = TextRenderer.getFontBitmap(context,context.getString(R.string.gachimatch), Color.RED,25f)
        remoteview.setImageViewBitmap(R.id.gachi_battle_imageview,gachibitmap)
        remoteview.setImageViewBitmap(R.id.regular_battle_imageview,regularbitmap)
        //get timer
        var sintent : Intent = Intent(context,WidgetUpdaterService::class.java)
        var pintent : PendingIntent = PendingIntent.getService(context,-1,sintent, PendingIntent.FLAG_UPDATE_CURRENT)
        var amanager : AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager;
        //get data from API
        try{
            var regular : Stage? = StageInfo.GetRegularStageList()
            var gachi : GachiStage? = StageInfo.GetGachiStageList()
            //update textview
            remoteview.setTextViewText(R.id.gachi_battle_stage1_textview,gachi?.maps?.get(0))
            remoteview.setTextViewText(R.id.gachi_battle_stage2_textview,gachi?.maps?.get(1))
            remoteview.setTextViewText(R.id.regular_battle_stage1_textview,regular?.maps?.get(0))
            remoteview.setTextViewText(R.id.regular_battle_stage2_textview,regular?.maps?.get(1))
            remoteview.setTextViewText(R.id.gachi_battle_rule_textview,"(" + gachi?.rule + ")")
            //draw time
            val timeformat : SimpleDateFormat? = SimpleDateFormat("MM/dd kk:mm")
            var timetext : String = timeformat?.format(regular?.startTime) + "~" + timeformat?.format(regular?.endTime)
            var timetextbitmap : Bitmap = TextRenderer.getFontBitmap(context,timetext,Color.WHITE,20f)
            remoteview.setImageViewBitmap(R.id.time_text_imageview,timetextbitmap)
            //set on click listener
            val mainintent : Intent = Intent(context,MainActivity::class.java)
            val pmainintent : PendingIntent = PendingIntent.getActivity(context, 0, mainintent, 0)
            remoteview.setOnClickPendingIntent(R.id.widget_background,pmainintent)
            //set timer
            var calendar : Calendar = Calendar.getInstance()
            calendar.time = regular?.endTime
            amanager.set(AlarmManager.RTC,calendar.timeInMillis,pintent)
        }catch(e:Exception){
            //got an error retry later
            var calendar : Calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.MINUTE, 10);
            amanager.set(AlarmManager.RTC,calendar.timeInMillis,pintent)
            return
        }
        //update widget
        widgetmanager?.updateAppWidget(componentname,remoteview)
    }
}