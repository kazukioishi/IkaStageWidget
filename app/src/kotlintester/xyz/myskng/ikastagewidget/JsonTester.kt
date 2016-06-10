package xyz.myskng.ikastagewidget

import com.fasterxml.jackson.databind.ObjectMapper
import xyz.myskng.ikastagewidget.api.StageInfo
import xyz.myskng.ikastagewidget.model.GachiStage
import xyz.myskng.ikastagewidget.model.Stage
import xyz.myskng.ikastagewidget.model.StageListViewItem
import java.util.*

class JsonTester {
    companion object{
        @JvmStatic fun main(args : Array<String>){
            //make return JSON
            val ikalist : ArrayList<StageListViewItem> = ArrayList()
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
            var mapper : ObjectMapper = ObjectMapper()
            var json : String = mapper.writeValueAsString(ikalist)
            println(json)
        }
    }
}