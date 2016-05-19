package xyz.myskng.ikastagewidget.api

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import okhttp3.Request
import xyz.myskng.ikastagewidget.model.GachiStage
import xyz.myskng.ikastagewidget.model.Stage
import java.text.SimpleDateFormat

class StageInfo {
    companion object{
        val GACHI_STAGE_INFO_API_ENDPOINT : String = "http://splapi.retrorocket.biz/gachi/now"
        val REGULAR_STAGE_INFO_API_ENDPOINT : String = "http://splapi.retrorocket.biz/regular/now"
        val API_TIMEFORMAT : SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")

        @JvmStatic fun GetGachiStageList() : GachiStage {
            var client : OkHttpClient = OkHttpClient()
            var req : Request.Builder = Request.Builder()
            req.url(GACHI_STAGE_INFO_API_ENDPOINT)
            var json : String =  client.newCall(req.build()).execute().body().string()
            var node : JsonNode = ObjectMapper().readTree(json)
            var stg : GachiStage = GachiStage()
            stg.maps = arrayOf(node.at("/result")[0].at("/maps")[0].asText(),node.at("/result")[0].at("/maps")[1].asText())
            stg.startTime = API_TIMEFORMAT.parse(node.at("/result")[0].at("/start").asText())
            stg.endTime = API_TIMEFORMAT.parse(node.at("/result")[0].at("/end").asText())
            stg.rule = node.at("/result")[0].at("/rule").asText()
            return stg
        }

        @JvmStatic fun GetRegularStageList() : Stage{
            var client : OkHttpClient = OkHttpClient()
            var req : Request.Builder = Request.Builder()
            req.url(REGULAR_STAGE_INFO_API_ENDPOINT)
            var json : String =  client.newCall(req.build()).execute().body().string()
            var node : JsonNode = ObjectMapper().readTree(json)
            var stg : Stage = Stage()
            stg.maps = arrayOf(node.at("/result")[0].at("/maps")[0].asText(),node.at("/result")[0].at("/maps")[1].asText())
            stg.startTime = API_TIMEFORMAT.parse(node.at("/result")[0].at("/start").asText())
            stg.endTime = API_TIMEFORMAT.parse(node.at("/result")[0].at("/end").asText())
            return stg
        }
    }
}