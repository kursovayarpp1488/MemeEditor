package com.mem.draw.activity

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.constraint.ConstraintLayout
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.widget.ImageViewCompat
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import com.mem.draw.R
import com.mem.draw.widget.DrawView
import kotlinx.android.synthetic.main.activity_drawing.*
import kotlinx.android.synthetic.main.color_palette_view.*
import java.io.ByteArrayOutputStream
import java.time.*;
import kotlin.math.roundToInt


class DrawingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing)

        image_close_drawing.setOnClickListener {
            finish()
        }
        var ClrNumb = intent.extras.getInt("color");
        var ImgId = intent.extras.getInt("imgid");
        var ImgCam = intent.getParcelableExtra<Bitmap?>("cambitmap");
        var ImgUri = Uri.parse(intent.extras["gallerypic"] as String);


        var tmpDRW = findViewById<DrawView>(R.id.draw_view);
        var tmpIMCLOSE = findViewById<ImageView>(R.id.image_close_drawing);
        var tmpClayout = findViewById<ConstraintLayout>(R.id.draw_tools)


        if (ImgUri != null){
            var tmpBitM = MediaStore.Images.Media.getBitmap(contentResolver, ImgUri);
            var bitmapDrawable = BitmapDrawable(tmpBitM);
            tmpDRW.setBackgroundDrawable(bitmapDrawable);
            tmpClayout.setBackgroundColor(Color.WHITE);
            tmpIMCLOSE.setBackgroundColor(Color.WHITE);
        }

        if (ImgId != Int.MIN_VALUE) {
            tmpDRW.setBackgroundResource(ImgId);
            tmpClayout.setBackgroundColor(Color.WHITE);
            tmpIMCLOSE.setBackgroundColor(Color.WHITE);
            tmpDRW.CurrentColor = Color.WHITE;
        }
        if (ClrNumb != Int.MIN_VALUE) {
            tmpDRW.CurrentColor = ClrNumb;
            tmpDRW.setBackgroundColor(ClrNumb);
            tmpClayout.setBackgroundColor(ClrNumb);
            tmpIMCLOSE.setBackgroundColor(ClrNumb);
        }
        if (ImgCam != null){
            //var newImg = Bitmap.createBitmap(ImgCam, 0, 0, ImgCam.width, (ImgCam.height * 0.9625).roundToInt());
            var bitmpDrawable = BitmapDrawable(resources, ImgCam);
            tmpDRW.setBackground(bitmpDrawable);
            tmpClayout.setBackgroundColor(Color.WHITE);
            tmpIMCLOSE.setBackgroundColor(Color.WHITE);
        }








        fab_send_drawing.setOnClickListener {
            val bStream = ByteArrayOutputStream()
            val bitmap = draw_view.getBitmap();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
            val byteArray = bStream.toByteArray();
            val returnIntent = Intent();
            returnIntent.putExtra("bitmap", byteArray);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }

        setUpDrawTools()

        colorSelector()

        setPaintAlpha()

        setPaintWidth()
    }

    private fun setUpDrawTools() {
        circle_view_opacity.setCircleRadius(100f)
        image_draw_eraser.setOnClickListener {
            draw_view.toggleEraser()
            image_draw_eraser.isSelected = draw_view.isEraserOn
            toggleDrawTools(draw_tools,false)
        }
        image_draw_eraser.setOnLongClickListener {
            draw_view.clearCanvas()
            toggleDrawTools(draw_tools,false)
            true
        }


        image_draw_width.setOnClickListener {
            if (draw_tools.translationY == (56).toPx){
                toggleDrawTools(draw_tools,true)
            }else if (draw_tools.translationY == (0).toPx && seekBar_width.visibility == View.VISIBLE){
                toggleDrawTools(draw_tools,false)
            }
            circle_view_width.visibility = View.VISIBLE
            circle_view_opacity.visibility = View.GONE
            seekBar_width.visibility = View.VISIBLE
            seekBar_opacity.visibility = View.GONE
            draw_color_palette.visibility = View.GONE
        }
        image_draw_opacity.setOnClickListener {
            if (draw_tools.translationY == (56).toPx){
                toggleDrawTools(draw_tools,true)
            }else if (draw_tools.translationY == (0).toPx && seekBar_opacity.visibility == View.VISIBLE){
                toggleDrawTools(draw_tools,false)
            }
            circle_view_width.visibility = View.GONE
            circle_view_opacity.visibility = View.VISIBLE
            seekBar_width.visibility = View.GONE
            seekBar_opacity.visibility = View.VISIBLE
            draw_color_palette.visibility = View.GONE
        }
        image_draw_color.setOnClickListener {
            if (draw_tools.translationY == (56).toPx){
                toggleDrawTools(draw_tools,true)
            }else if (draw_tools.translationY == (0).toPx && draw_color_palette.visibility == View.VISIBLE){
                toggleDrawTools(draw_tools,false)
            }
            circle_view_width.visibility = View.GONE
            circle_view_opacity.visibility = View.GONE
            seekBar_width.visibility = View.GONE
            seekBar_opacity.visibility = View.GONE
            draw_color_palette.visibility = View.VISIBLE
        }
        image_draw_undo.setOnClickListener {
            draw_view.undo()
            toggleDrawTools(draw_tools,false)
        }
        image_draw_redo.setOnClickListener {
            draw_view.redo()
            toggleDrawTools(draw_tools,false)
        }
    }

    private fun toggleDrawTools(view: View, showView: Boolean = true) {
        if (showView){
            view.animate().translationY((0).toPx)
        }else{
            view.animate().translationY((56).toPx)
        }
    }

    private fun colorSelector() {
        image_color_black.setOnClickListener {
            val color = ResourcesCompat.getColor(resources, R.color.color_black,null)
            draw_view.setColor(color)
            circle_view_opacity.setColor(color)
            circle_view_width.setColor(color)
            scaleColorView(image_color_black)
        }
        image_color_red.setOnClickListener {
            val color = ResourcesCompat.getColor(resources, R.color.color_red,null)
            draw_view.setColor(color)
            circle_view_opacity.setColor(color)
            circle_view_width.setColor(color)
            scaleColorView(image_color_red)
        }
        image_color_yellow.setOnClickListener {
            val color = ResourcesCompat.getColor(resources, R.color.color_yellow,null)
            draw_view.setColor(color)
            circle_view_opacity.setColor(color)
            circle_view_width.setColor(color)
            scaleColorView(image_color_yellow)
        }
        image_color_green.setOnClickListener {
            val color = ResourcesCompat.getColor(resources, R.color.color_green,null)
            draw_view.setColor(color)
            circle_view_opacity.setColor(color)
            circle_view_width.setColor(color)
            scaleColorView(image_color_green)
        }
        image_color_blue.setOnClickListener {
            val color = ResourcesCompat.getColor(resources, R.color.color_blue,null)
            draw_view.setColor(color)
            circle_view_opacity.setColor(color)
            circle_view_width.setColor(color)
            scaleColorView(image_color_blue)
        }
        image_color_pink.setOnClickListener {
            val color = ResourcesCompat.getColor(resources, R.color.color_pink,null)
            draw_view.setColor(color)
            circle_view_opacity.setColor(color)
            circle_view_width.setColor(color)
            scaleColorView(image_color_pink)
        }
        image_color_brown.setOnClickListener {
            val color =  ResourcesCompat.getColor(resources, R.color.color_brown,null)
            draw_view.setColor(color)
            circle_view_opacity.setColor(color)
            circle_view_width.setColor(color)
            scaleColorView(image_color_brown)
        }
    }

    private fun scaleColorView(view: View) {
        //reset scale of all views
        image_color_black.scaleX = 1f
        image_color_black.scaleY = 1f

        image_color_red.scaleX = 1f
        image_color_red.scaleY = 1f

        image_color_yellow.scaleX = 1f
        image_color_yellow.scaleY = 1f

        image_color_green.scaleX = 1f
        image_color_green.scaleY = 1f

        image_color_blue.scaleX = 1f
        image_color_blue.scaleY = 1f

        image_color_pink.scaleX = 1f
        image_color_pink.scaleY = 1f

        image_color_brown.scaleX = 1f
        image_color_brown.scaleY = 1f

        //set scale of selected view
        view.scaleX = 1.5f
        view.scaleY = 1.5f
    }

    private fun setPaintWidth() {
        seekBar_width.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                draw_view.setStrokeWidth(progress.toFloat())
                circle_view_width.setCircleRadius(progress.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun setPaintAlpha() {
        seekBar_opacity.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                draw_view.setAlpha(progress)
                circle_view_opacity.setAlpha(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private val Int.toPx: Float
        get() = (this * Resources.getSystem().displayMetrics.density)
}
