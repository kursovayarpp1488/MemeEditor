package com.mem.androiddraw

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

class ImageAdapter : BaseAdapter {

    private var Context: android.content.Context? = null;
    var ImgList: MutableList<Int>? = null;


    constructor(contect: android.content.Context, lst: MutableList<Int>?){
        this?.Context = contect;
        this?.ImgList = lst;
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        var VIEW = convertView;
        if (VIEW == null)
        {
            VIEW = LayoutInflater.from(this.Context).inflate(R.layout.img_element, null, false);
        }

        var Clr = VIEW?.findViewById<ImageView>(R.id.image_View_element)

        Clr?.setImageResource(ImgList!![position]);

        return VIEW;
    }

    override fun getItem(p0: Int): Any {
        return ImgList!![p0];
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    override fun getCount(): Int {
        return  ImgList!!.count();
    }
}