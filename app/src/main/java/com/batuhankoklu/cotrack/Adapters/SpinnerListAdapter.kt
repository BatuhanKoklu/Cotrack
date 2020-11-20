package com.batuhankoklu.cotrack.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.batuhankoklu.cotrack.R
import kotlinx.android.synthetic.main.cell_unit_spinner.view.*

class SpinnerListAdapter<T>(ctx : Context, list : List<T>) : ArrayAdapter<T>(ctx, 0 , list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return this.createView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return this.createView(position, convertView, parent)
    }

    private fun createView(position: Int, recycledView: View?, parent: ViewGroup): View {
        val item = getItem(position)

        //Unit
        item as String

        val view = recycledView ?: LayoutInflater.from(context).inflate(
            R.layout.cell_unit_spinner,
            parent,
            false)

        view.txtUnitName.text = item
        return view

    }


}