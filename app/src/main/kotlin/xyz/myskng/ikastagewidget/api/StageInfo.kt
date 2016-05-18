package xyz.myskng.ikastagewidget.api

import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Created by Kazuki on 2016/05/19.
 */
class StageInfo {
    companion object{
        val GACHI_STAGE_INFO_API_ENDPOINT : String = "http://splapi.retrorocket.biz/gachi/now"
        val REGULAR_STAGE_INFO_API_ENDPOINT : String = "http://splapi.retrorocket.biz/regular/now"
        fun GetGachiStageList() : Array<String>?{
            var client : OkHttpClient = OkHttpClient()
            var req : Request.Builder = Request.Builder()
            req.url(GACHI_STAGE_INFO_API_ENDPOINT)
            var json : String =  client.newCall(req.build()).execute().body().string()
            return null
        }
        fun GetRegularStageList() : Array<String>?{
            return null
        }
    }
}