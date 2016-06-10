package xyz.myskng.ikastagewidget

import android.app.Activity
import android.app.Fragment
import android.app.FragmentManager
import android.os.Bundle
import android.support.wearable.view.FragmentGridPagerAdapter
import android.support.wearable.view.GridViewPager
import android.support.wearable.view.WatchViewStub
import android.util.Log
import android.widget.TextView
import butterknife.bindView
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.yamamotoj.subskription.AutoUnsubscribable
import com.github.yamamotoj.subskription.AutoUnsubscribableDelegate
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.wearable.MessageApi
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.PutDataRequest
import com.google.android.gms.wearable.Wearable
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.observable
import rx.schedulers.Schedulers
import xyz.myskng.ikastagewidget.model.StageListViewItem
import java.util.*

class MainActivity : Activity() , AutoUnsubscribable by AutoUnsubscribableDelegate() {
    val tview : TextView by bindView(R.id.text)
    val gridPager : GridViewPager by bindView(R.id.viewpager)
    var gclient : GoogleApiClient? = null
    var concallback : ConCallBack = ConCallBack()
    var adapter : GridPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        tview.text = "マンメンミ"
        //init adapter
        adapter = GridPagerAdapter(fragmentManager)
        gridPager.adapter = adapter
        //init google api
        gclient = GoogleApiClient.Builder(this).addApi(Wearable.API).addConnectionCallbacks(concallback).build()
        gclient?.connect()
    }

    override fun onPause() {
        super.onPause()
        if(gclient != null && gclient!!.isConnected){
            gclient?.disconnect()
            concallback.removeme()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unsubscribe()
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    inner class ConCallBack : GoogleApiClient.ConnectionCallbacks, MessageApi.MessageListener {
        override fun onMessageReceived(p0: MessageEvent?) {
            //update view
            var ikalist : ArrayList<StageListViewItem> = ArrayList()
            tview?.text = "got message"
            if(p0?.path == "/ikareturn"){
                //decode json
                observable<String>{
                    val json : String = p0!!.data.toString(Charsets.UTF_8)
                    var mapper : ObjectMapper = ObjectMapper()
                    ikalist = mapper.readValue(json,mapper.typeFactory.constructCollectionType(ArrayList::class.java,StageListViewItem::class.java))
                    it.onCompleted()
                }.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .autoUnsubscribe()
                        .subscribe({
                            //on next
                        },{
                            //on error
                            tview?.text = "ERROR"
                        },{
                            //on complete
                            tview?.text = "OK"
                            adapter?.ikaList = ikalist
                            adapter?.notifyDataSetChanged()
                        })
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

    inner class GridPagerAdapter(fm: FragmentManager?) : FragmentGridPagerAdapter(fm) {
        var ikaList : ArrayList<StageListViewItem> = ArrayList()
        override fun getFragment(row: Int, column: Int): Fragment? {
            val fragment : IkaFragment? = IkaFragment()
            //row1:レギュラー row2:ガチマッチ
            //get parent item index
            val idx : Int
            if(row % 2 == 0){
                idx = row / 2
            }else{
                idx = (row+1) / 2
            }
            val stageItem : StageListViewItem = ikaList[idx]
            fragment?.stageViewItem = stageItem
            fragment?.row = row
            fragment?.column = column
            //set value on fragment
            return fragment
        }

        override fun getRowCount(): Int {
            return ikaList.count() * 2
        }

        override fun getColumnCount(p0: Int): Int {
            if(ikaList.count() == 0) return 0
            return 2
        }
    }
}