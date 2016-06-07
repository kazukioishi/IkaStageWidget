package xyz.myskng.ikastagewidget.wear

import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.Wearable
import com.google.android.gms.wearable.WearableListenerService
import xyz.myskng.ikastagewidget.api.StageInfo
import xyz.myskng.ikastagewidget.model.GachiStage
import xyz.myskng.ikastagewidget.model.Stage
import xyz.myskng.ikastagewidget.model.StageListViewItem
import java.util.*

class WearMessageReceiver : WearableListenerService() {
    var gclient : GoogleApiClient? = null

    override fun onMessageReceived(p0: MessageEvent?) {
        super.onMessageReceived(p0)
        if(p0?.path == "/ikaget"){
            //make return JSON
            var json : String = ""
            try{
                val ikalist : ArrayList<StageListViewItem> = ArrayList();
                val regularlist: ArrayList<Stage> = StageInfo.GetNextRegularStageList()
                val gachilist: ArrayList<GachiStage> = StageInfo.GetNextGachiStageList()
                regularlist.add(0, StageInfo.GetRegularStageList())
                gachilist.add(0, StageInfo.GetGachiStageList())
                regularlist.forEach {
                    val stageitm: StageListViewItem = StageListViewItem()
                    stageitm.stage = it
                    stageitm.gachistage = gachilist.get(regularlist.indexOf(it))
                    ikalist.add(stageitm)
                }
                val mapper : ObjectMapper = ObjectMapper()
                json = mapper.writeValueAsString(ikalist)
            }catch(ex : Exception){
                return
            }
            //return data to wear device
            val nodes = Wearable.NodeApi.getConnectedNodes(gclient)
            nodes.setResultCallback {
                it.nodes.forEach {
                    if(it.id == p0?.sourceNodeId){
                        //send data to wear
                        val mresult = Wearable.MessageApi.sendMessage(gclient,it.id,"/ikareturn", json.toByteArray(Charsets.UTF_8))
                        mresult.setResultCallback {
                            Log.d("IkaWearService","Status:" + it.status)
                        }
                    }
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        gclient = GoogleApiClient.Builder(this).addApi(Wearable.API).build()
        gclient?.connect()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(gclient != null && gclient!!.isConnected){
            gclient?.disconnect()
        }
    }
}