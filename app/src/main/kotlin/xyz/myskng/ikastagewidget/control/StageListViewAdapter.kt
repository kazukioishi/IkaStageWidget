package xyz.myskng.ikastagewidget.control

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import xyz.myskng.ikastagewidget.model.StageListViewItem
import java.util.*

class StageListViewAdapter(context : Context) : BaseAdapter() {
    var layoutinflater : LayoutInflater? = null
    var list : ArrayList<StageListViewItem> = ArrayList();

    init{
        layoutinflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        throw UnsupportedOperationException()
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