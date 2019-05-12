package com.mem.androiddraw

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast

class ColorChoice : Activity(){

    var ClrList: ListView? = null;
    var CAdapter: ColorAdapter? = null;
    var ListOfColors: MutableList<Int>? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice_layout);
        ListOfColors = mutableListOf<Int>();
        ListOfColors!!.add(Color.BLUE);
        ListOfColors!!.add(Color.BLACK);
        ListOfColors!!.add(Color.CYAN);
        ListOfColors!!.add(Color.DKGRAY);
        ListOfColors!!.add(Color.GRAY);
        ListOfColors!!.add(Color.LTGRAY);
        ListOfColors!!.add(Color.MAGENTA);
        ListOfColors!!.add(Color.RED);
        ListOfColors!!.add(Color.WHITE);
        ListOfColors!!.add(Color.YELLOW);
        ListOfColors!!.add(Color.rgb(128, 0, 128));
        ListOfColors!!.add(Color.rgb(255,0,255));
        ListOfColors!!.add(Color.rgb(0,255,255));
        ListOfColors!!.add(Color.rgb(255,69,0));
        ListOfColors!!.add(Color.rgb(128,0,0));
        ListOfColors!!.add(Color.rgb(0,255,0));
        ListOfColors!!.add(Color.rgb(0,0,128));
        ListOfColors!!.add(Color.rgb(218,165,32));
        ListOfColors!!.add(Color.rgb(255,228,196));
        ListOfColors!!.add(Color.rgb(128,128,0));
        ListOfColors!!.add(Color.rgb(255,215,0));


        ClrList = findViewById<ListView>(R.id.LV)
        CAdapter= ColorAdapter(this, ListOfColors);

        ClrList?.adapter = CAdapter;
        ClrList?.setOnItemClickListener{parent, view, position, id -> {} }


        ClrList?.onItemClickListener= object : AdapterView.OnItemClickListener{
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var tmpIntent = Intent();
                tmpIntent.putExtra("color", ListOfColors!![p2]);
                tmpIntent.putExtra("imgid", Int.MIN_VALUE);
                setResult(RESULT_OK, tmpIntent);
                finish();
            }
        }

    }


}