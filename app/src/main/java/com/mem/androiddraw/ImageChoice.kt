package com.mem.androiddraw

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView

class ImageChoice : Activity() {

    var ImgList: ListView? = null;
    var ListOfImages: MutableList<Int>? = null;
    var IAdapter: ImageAdapter? = null;

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.img_choice_layout);

        ListOfImages = mutableListOf<Int>();
        ListOfImages!!.add(com.mem.draw.R.drawable.alex);
        ListOfImages!!.add(com.mem.draw.R.drawable.ricard1);
        ListOfImages!!.add(com.mem.draw.R.drawable.ricard2);
        ListOfImages!!.add(com.mem.draw.R.drawable.ricard3);
        ListOfImages!!.add(com.mem.draw.R.drawable.richard4);
        ListOfImages!!.add(com.mem.draw.R.drawable.zubenko);


        ImgList = findViewById(R.id.img_ch) as ListView;
        IAdapter = ImageAdapter(this, ListOfImages);

        ImgList?.adapter = IAdapter;
        ImgList?.setOnItemClickListener{parent, view, position, id -> {} }


        ImgList?.onItemClickListener= object : AdapterView.OnItemClickListener{
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var tmpIntent = Intent();
                tmpIntent.putExtra("color", Int.MIN_VALUE);
                tmpIntent.putExtra("imgid", ListOfImages!![p2]);
                setResult(RESULT_OK, tmpIntent);
                finish();
            }
        }

    }
}