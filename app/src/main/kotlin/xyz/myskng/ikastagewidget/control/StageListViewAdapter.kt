package xyz.myskng.ikastagewidget.control

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.AppCompatTextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import butterknife.bindView
import xyz.myskng.ikastagewidget.R
import xyz.myskng.ikastagewidget.model.StageListViewItem
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
        //TODO : ViewHolderを使った実装に変更
        val listitem : StageListViewItem = list.get(position)
        val cView : View? = layoutinflater?.inflate(R.layout.stagelistview_layout,parent,false)
        val datetimetext : AppCompatTextView? = cView?.findViewById(R.id.stagelistview_datetimetext) as AppCompatTextView;
        datetimetext?.text = SimpleDateFormat("kk:mm~\n").format(listitem.gachistage?.startTime) + SimpleDateFormat("kk:mm~").format(listitem.gachistage?.endTime)
        val gachimatch : AppCompatTextView? = cView?.findViewById(R.id.stagelistview_gachimatch) as AppCompatTextView
        val gachimatch_stage : AppCompatTextView? = cView?.findViewById(R.id.stagelistview_gachimatch_stage) as AppCompatTextView
        gachimatch_stage?.text = listitem.gachistage?.maps?.get(0) + "\n" + listitem.gachistage?.maps?.get(1) + "\n" + listitem.gachistage?.rule
        val regularmatch : AppCompatTextView? = cView?.findViewById(R.id.stagelistview_regularmatch) as AppCompatTextView
        val regularmatch_stage : AppCompatTextView? = cView?.findViewById(R.id.stagelistview_regularmatch_stage) as AppCompatTextView
        regularmatch_stage?.text = listitem.stage?.maps?.get(0) + "\n" + listitem.stage?.maps?.get(1)

        //set fonts
        datetimetext?.typeface = ikamodoki
        gachimatch?.typeface = ikamodoki
        regularmatch?.typeface = ikamodoki

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