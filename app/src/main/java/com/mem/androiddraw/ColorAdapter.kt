package com.mem.androiddraw

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ColorAdapter : BaseAdapter{
    private var Context: android.content.Context? = null;
    var ColorList: MutableList<Int>? = null;

    constructor(contect: android.content.Context, lst: MutableList<Int>?){
        this?.Context = contect;
        this?.ColorList = lst;
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        var VIEW = convertView;
        if (VIEW == null)
        {
            VIEW = LayoutInflater.from(this.Context).inflate(R.layout.color_element, null, false);
        }

        var Clr = VIEW?.findViewById<TextView>(R.id.textView)

        Clr?.text = " ";
        Clr?.setBackgroundColor(ColorList!![position]);

        return VIEW;
    }

    override fun getItem(p0: Int): Any {
        return ColorList!![p0];
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    override fun getCount(): Int {
        return  ColorList!!.count();
    }
}