package xyz.myskng.ikastagewidget

import android.app.Activity
import android.os.Bundle
import android.support.wearable.view.WatchViewStub
import android.util.Log
import android.widget.TextView
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.wearable.MessageApi
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.PutDataRequest
import com.google.android.gms.wearable.Wearable

class MainActivity : Activity() {
    var tview : TextView? = null
    var gclient : GoogleApiClient? = null
    var concallback : ConCallBack = ConCallBack()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val stub = findViewById(R.id.watch_view_stub) as WatchViewStub
        stub.setOnLayoutInflatedListener({
            //on layout inflated
            tview = stub.findViewById(R.id.text) as TextView
            tview?.text = "マンメンミ"
            //init google api
            gclient = GoogleApiClient.Builder(this).addApi(Wearable.API).addConnectionCallbacks(concallback).build()
            gclient?.connect()
        })
    }

    override fun onPause() {
        super.onPause()
        if(gclient != null && gclient!!.isConnected){
            gclient?.disconnect()
            concallback.removeme()
        }
    }

    inner class ConCallBack : GoogleApiClient.ConnectionCallbacks, MessageApi.MessageListener {
        override fun onMessageReceived(p0: MessageEvent?) {
            //update view
            tview?.text = "got message"
            if(p0?.path == "/ikareturn"){
                val json : String = p0!!.data.toString(Charsets.UTF_8)
                tview?.text = json
            }
        }

        override fun onConnected(p0: Bundle?) {
            //regist listner
            Wearable.MessageApi.addListener(gclient, this)
            //send ika request
            val nodes = Wearable.NodeApi.getConnectedNodes(gclient)
            nodes.setResultCallback {
                it.nodes.forEach {
                    Wearable.MessageApi.sendMessage(gclient,it.id,"/ikaget", ByteArray(0)).setResultCallback {
                        Log.d("IkaStageWidget","status:" + it.status)
                    }
                    Log.d("IkaStageWidget","sent message:" + it.displayName)
                    tview?.text = "sent message:" + it.displayName
                }
                Log.d("IkaStageWidget","node count:" + it.nodes.count())
            }
        }

        override fun onConnectionSuspended(p0: Int) {

        }

        fun removeme(){
            Wearable.MessageApi.removeListener(gclient,this)
        }
    }
}