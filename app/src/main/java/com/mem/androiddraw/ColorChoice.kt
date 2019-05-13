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

        for (name in colorArray)
            ListOfColors!!.add(Color.parseColor(name));


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

    var colorArray = arrayOf("#ff0000", "#ff8000", "#ffbf00", "#ffff00", "#bfff00", "#80ff00", "#40ff00", "#00ff00",
            "#00ff40", "#00ff80", "#00ffbf", "#00ffff", "#00bfff", "#0080ff", "#0040ff", "#0000ff", "#4000ff",
            "#8000ff", "#bf00ff", "#ff00ff", "#ff00bf", "#ff0080", "#ff0040", "#ff0000");


}