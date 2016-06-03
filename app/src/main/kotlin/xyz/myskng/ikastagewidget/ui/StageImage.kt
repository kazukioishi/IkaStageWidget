package xyz.myskng.ikastagewidget.ui

import xyz.myskng.ikastagewidget.R

class StageImage {
    companion object{
        val stageHashMap = mapOf(
                Pair("デカライン高架下",R.drawable.urchin_underpass),
                Pair("シオノメ油田",R.drawable.saltspray_rig),
                Pair("Bバスパーク",R.drawable.blackbelly_skatepark),
                Pair("ハコフグ倉庫",R.drawable.walleye_warehouse),
                Pair("アロワナモール",R.drawable.arowana_mall),
                Pair("ホッケふ頭",R.drawable.port_mackerel),
                Pair("モズク農園",R.drawable.kelp_dome),
                Pair("ネギトロ炭鉱",R.drawable.bluefin_depot),
                Pair("タチウオパーキング",R.drawable.moray_towers),
                Pair("モンガラキャンプ場",R.drawable.camp_triggerfish),
                Pair("ヒラメが丘団地",R.drawable.flounder_heights),
                Pair("マサバ海峡大橋",R.drawable.hammerhead_bridge),
                Pair("キンメダイ美術館",R.drawable.museum_d_alfonsino),
                Pair("マヒマヒリゾート&スパ",R.drawable.mahi_mahi_resort),
                Pair("ショッツル鉱山",R.drawable.piranha_pit),
                Pair("アンチョビットゲームズ",R.drawable.ancho_v_games)
        )

        fun getStageImage(stagename : String) : Int{
            val retval : Int? = stageHashMap[stagename]
            if(retval != null){
                return retval
            }else{
                //とりあえずなんか返しとけ
                return R.drawable.ancho_v_games
            }
        }
    }
}