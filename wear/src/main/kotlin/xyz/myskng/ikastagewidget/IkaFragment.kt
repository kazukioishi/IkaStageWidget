package xyz.myskng.ikastagewidget

import android.app.Fragment
import android.graphics.Typeface
import android.os.Bundle
import android.support.wearable.view.BoxInsetLayout
import android.support.wearable.view.CardScrollView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.bindView
import xyz.myskng.ikastagewidget.model.StageListViewItem
import xyz.myskng.ikastagewidget.tools.Views
import xyz.myskng.ikastagewidget.ui.StageImage
import java.text.SimpleDateFormat

class IkaFragment() : Fragment() {
    var stageViewItem : StageListViewItem? = null
    val ruleTextView : TextView by Views.bind(this,R.id.ikarule)
    val dateTextView : TextView by Views.bind(this,R.id.ikadate)
    val stageTextView : TextView by Views.bind(this,R.id.ikastagename)
    val parentlayout : RelativeLayout by Views.bind(this,R.id.ika_fragment_layout)
    val background_imageview : ImageView by Views.bind(this,R.id.background_imageview)
    var row : Int = 0
    var column : Int = 0
    companion object{
        var ikamodoki : Typeface? = null
        val dateFormat : SimpleDateFormat = SimpleDateFormat("kk:mm")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val layout : View? = inflater?.inflate(R.layout.ika_fragment_layout,container,false)
        //load typeface if not loaded
        if(ikamodoki == null) ikamodoki = Typeface.createFromAsset(activity.assets, "ProjectPaintballKai.ttf");
        return layout
    }

    override fun onResume() {
        super.onResume()
        //set ikamodoki fonts
        ruleTextView.typeface = ikamodoki
        dateTextView.typeface = ikamodoki
        //set text
        if(column == 0){
            //regular match
            ruleTextView.text = activity.getText(R.string.regularmatch)
            //odd number => 1nd stage
            if((row+1) % 2 != 0){
                stageTextView.text = stageViewItem!!.stage!!.maps!![0]
                dateTextView.text = dateFormat.format(stageViewItem!!.stage!!.startTime) + "~" + dateFormat.format(stageViewItem!!.stage!!.endTime)
            }else{
                //even number -> 2nd stage
                stageTextView.text = stageViewItem!!.stage!!.maps!![1]
                dateTextView.text = dateFormat.format(stageViewItem!!.stage!!.startTime) + "~" + dateFormat.format(stageViewItem!!.stage!!.endTime)
            }
        }else if(column == 1){
            //gachi match
            ruleTextView.text = stageViewItem!!.gachistage!!.rule
            if((row+1) % 2 != 0){
                stageTextView.text = stageViewItem!!.gachistage!!.maps!![0]
                dateTextView.text = dateFormat.format(stageViewItem!!.stage!!.startTime) + "~" + dateFormat.format(stageViewItem!!.stage!!.endTime)
            }else{
                stageTextView.text = stageViewItem!!.gachistage!!.maps!![1]
                dateTextView.text = dateFormat.format(stageViewItem!!.stage!!.startTime) + "~" + dateFormat.format(stageViewItem!!.stage!!.endTime)
            }
        }
        //set background
        background_imageview.setImageResource(StageImage.getStageImage(stageTextView.text.toString()))
    }
}