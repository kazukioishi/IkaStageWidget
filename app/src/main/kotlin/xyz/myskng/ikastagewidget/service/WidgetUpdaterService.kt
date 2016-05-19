package xyz.myskng.ikastagewidget.service

import android.app.AlarmManager
import android.app.IntentService
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import xyz.myskng.ikastagewidget.api.StageInfo
import xyz.myskng.ikastagewidget.model.Stage

class WidgetUpdaterService(name: String?) : IntentService(name) {
    override fun onHandleIntent(intent: Intent?) {
        //search widgets

        //get data from API
        try{
            var regular : Stage = StageInfo.GetRegularStageList()
            var gachi : Stage = StageInfo.GetRegularStageList()
        }catch(e:Exception){

        }
        //set timer
        var context : Context = getBaseContext()
        var sintent : Intent = Intent(context,WidgetUpdaterService::class.java)
        var pintent : PendingIntent = PendingIntent.getService(context,-1,sintent, PendingIntent.FLAG_UPDATE_CURRENT)
        var amanager : AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager;
    }
}