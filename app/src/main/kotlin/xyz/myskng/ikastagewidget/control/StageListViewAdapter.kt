package xyz.myskng.ikastagewidget.control

import android.content.Context
import android.content.res.Resources
import android.graphics.Typeface
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import butterknife.bindView
import xyz.myskng.ikastagewidget.R
import xyz.myskng.ikastagewidget.model.StageListViewItem
import xyz.myskng.ikastagewidget.ui.StageImage
import java.text.SimpleDateFormat
import java.util.*

class StageListViewAdapter(context : Context) : BaseAdapter() {
    var layoutinflater : LayoutInflater? = null
    var list : ArrayList<StageListViewItem> = ArrayList();
    var ikamodoki : Typeface? = null;

    init{
        layoutinflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        ikamodoki = Typeface.createFromAsset(context.getAssets(), "ProjectPaintballKai.ttf");
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var holder : StageListViewHolder = StageListViewHolder()
        val listitem : StageListViewItem = list.get(position)
        var cView : View? = null
        if(convertView != null){
            holder = convertView.tag as StageListViewHolder
            cView = convertView
        }else{
            cView = layoutinflater?.inflate(R.layout.stagelistview_layout,parent,false)
            //get control
            holder.datetimetext = cView?.findViewById(R.id.stagelistview_datetimetext) as AppCompatTextView;
            holder.gachimatch = cView?.findViewById(R.id.stagelistview_gachimatch) as AppCompatTextView
            holder.gachimatch_stage_1 = cView?.findViewById(R.id.stagelistview_gachimatch_stage_1) as AppCompatTextView
            holder.gachimatch_stage_2 = cView?.findViewById(R.id.stagelistview_gachimatch_stage_2) as AppCompatTextView
            holder.gachimatch_stage_1_image = cView?.findViewById(R.id.stagelistview_gachimatch_stage_image_1) as AppCompatImageView
            holder.gachimatch_stage_2_image = cView?.findViewById(R.id.stagelistview_gachimatch_stage_image_2) as AppCompatImageView
            holder.regularmatch = cView?.findViewById(R.id.stagelistview_regularmatch) as AppCompatTextView
            holder.regularmatch_stage_1 = cView?.findViewById(R.id.stagelistview_regularmatch_stage_1) as AppCompatTextView
            holder.regularmatch_stage_2 = cView?.findViewById(R.id.stagelistview_regularmatch_stage_2) as AppCompatTextView
            holder.regularmatch_stage_1_image = cView?.findViewById(R.id.stagelistview_regularmatch_stage_image_1) as AppCompatImageView
            holder.regularmatch_stage_2_image = cView?.findViewById(R.id.stagelistview_regularmatch_stage_image_2) as AppCompatImageView
        }

        holder.datetimetext?.text = SimpleDateFormat("kk:mm~\n").format(listitem.gachistage?.startTime) + SimpleDateFormat("kk:mm~").format(listitem.gachistage?.endTime)
        holder.gachimatch?.text = holder.gachimatch?.text.toString() + "(" + listitem.gachistage?.rule + ")"
        holder.gachimatch_stage_1?.text = listitem.gachistage?.maps?.get(0)
        holder.gachimatch_stage_2?.text = listitem.gachistage?.maps?.get(1)
        holder.gachimatch_stage_1_image?.setImageResource(StageImage.getStageImage(listitem.gachistage?.maps?.get(0).toString()))
        holder.gachimatch_stage_2_image?.setImageResource(StageImage.getStageImage(listitem.gachistage?.maps?.get(1).toString()))
        holder.regularmatch_stage_1?.text = listitem.stage?.maps?.get(0)
        holder.regularmatch_stage_2?.text = listitem.stage?.maps?.get(1)
        holder.regularmatch_stage_1_image?.setImageResource(StageImage.getStageImage(listitem.stage?.maps?.get(0).toString()))
        holder.regularmatch_stage_2_image?.setImageResource(StageImage.getStageImage(listitem.stage?.maps?.get(1).toString()))

        //set fonts
        holder.datetimetext?.typeface = ikamodoki
        holder.gachimatch?.typeface = ikamodoki
        holder.regularmatch?.typeface = ikamodoki

        return cView
    }

    override fun getItem(position: Int): Any? {
        return list.get(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return list.count()
    }

}