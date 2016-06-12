package xyz.myskng.ikastagewidget.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.internal.widget.ListViewCompat
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import butterknife.bindView
import com.github.yamamotoj.subskription.AutoUnsubscribable
import com.github.yamamotoj.subskription.AutoUnsubscribableDelegate
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.observable
import rx.lang.kotlin.onError
import rx.schedulers.Schedulers
import xyz.myskng.ikastagewidget.R
import xyz.myskng.ikastagewidget.api.StageInfo
import xyz.myskng.ikastagewidget.control.StageListViewAdapter
import xyz.myskng.ikastagewidget.model.GachiStage
import xyz.myskng.ikastagewidget.model.Stage
import xyz.myskng.ikastagewidget.model.StageListViewItem
import java.util.*

class MainActivity : AppCompatActivity(), AutoUnsubscribable by AutoUnsubscribableDelegate() {
    val swipe_refresh_layout : SwipeRefreshLayout by bindView<SwipeRefreshLayout>(R.id.swipe_refresh_layout)
    val ikalistview : ListViewCompat by bindView<ListViewCompat>(R.id.ikalistview)
    val adview : AdView by bindView(R.id.adView)
    var ikalist : ArrayList<StageListViewItem> = ArrayList();
    var ikaadapter : StageListViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        ikaadapter = StageListViewAdapter(this)
        swipe_refresh_layout.setOnRefreshListener({
            updateIkaList()
        })
        val adrequest : AdRequest = AdRequest.Builder().build()
        adview.loadAd(adrequest)
    }

    override fun onStart() {
        super.onStart()
        swipe_refresh_layout.isRefreshing = true
        updateIkaList()
    }

    fun updateIkaList() {
        observable<String> {
            ikalist.clear()
            var regularlist: ArrayList<Stage> = StageInfo.GetNextRegularStageList()
            var gachilist: ArrayList<GachiStage> = StageInfo.GetNextGachiStageList()
            regularlist.add(0,StageInfo.GetRegularStageList())
            gachilist.add(0,StageInfo.GetGachiStageList())
            regularlist.forEach {
                var stageitm: StageListViewItem = StageListViewItem()
                stageitm.stage = it
                stageitm.gachistage = gachilist.get(regularlist.indexOf(it))
                ikalist.add(stageitm)
            }
            it.onCompleted()
        }
                .subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread())
                .autoUnsubscribe()
                .subscribe({
                    //on next
                },{
                    //on error
                    Toast.makeText(this,"取得できませんでした",Toast.LENGTH_LONG).show()
                    swipe_refresh_layout.isRefreshing = false
                },{
                    //on complete
                    //set listview
                    ikaadapter?.list = ikalist
                    ikalistview.adapter = ikaadapter
                    swipe_refresh_layout.isRefreshing = false
                })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_settings ->{
                startActivity(Intent(this,SettingActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        // unsubscribe all subscriptions added to AutoUnsubscribableDelegate
        unsubscribe()
    }
}