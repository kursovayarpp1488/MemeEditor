package com.mem.androiddraw

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.WindowManager
import android.widget.EditText
import com.mem.draw.activity.DrawingActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.collections.ArrayList

private const val REQUEST_CODE_DRAW = 101
private const val REQUEST_CODE_COLOR = 103;
private const val REQUEST_CODE_IMG = 104;
private const val REQUEST_CODE_CAMERA = 105;
private const val REQUEST_CODE_GALLERY = 106;



private const val PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 102
var ColorNubm = Int.MIN_VALUE;
var ImgId = Int.MIN_VALUE;
var ImgCam: Bitmap? = null;
var ImgPath = "";

class MainActivity : AppCompatActivity() {

    lateinit var adapter: DrawAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE)
        }else{
            adapter = DrawAdapter(this,getFilesPath())
            recycler_view.adapter = adapter
        }



        SColor.setOnClickListener{
            var intent2 = android.content.Intent(this@MainActivity, ColorChoice::class.java)
            startActivityForResult(intent2, REQUEST_CODE_COLOR);
        }



        SImage.setOnClickListener{
            var intent3 = android.content.Intent(this@MainActivity, ImageChoice::class.java);
            startActivityForResult(intent3, REQUEST_CODE_IMG);
        }


        SCamera.setOnClickListener{
            var intentTakePhoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if(intentTakePhoto.resolveActivity(packageManager) != null){
                startActivityForResult(intentTakePhoto, REQUEST_CODE_CAMERA);
            }
        }



        SFilse.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_GALLERY);
        }



        fab_add_draw.setOnClickListener {

            val intent = Intent(this, DrawingActivity::class.java)
            intent.putExtra("color", ColorNubm);
            intent.putExtra("imgid", ImgId);
            intent.putExtra("cambitmap", ImgCam);
            intent.putExtra("gallerypic", ImgPath);
            startActivityForResult(intent, REQUEST_CODE_DRAW)
        }
    }

    private fun getFilesPath(): ArrayList<String>{
        val resultList = ArrayList<String>()
        val imageDir = "${Environment.DIRECTORY_PICTURES}/Meme_Editor/"
        val path = Environment.getExternalStoragePublicDirectory(imageDir)
        path.mkdirs()
        val imageList = path.listFiles()
        for (imagePath in imageList){
            resultList.add(imagePath.absolutePath)
        }
        return resultList
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null && resultCode == Activity.RESULT_OK) {
            when(requestCode){
                REQUEST_CODE_DRAW -> {
                    val result= data.getByteArrayExtra("bitmap")
                    val bitmap = BitmapFactory.decodeByteArray(result, 0, result.size)
                    showSaveDialog(bitmap)
                }

                REQUEST_CODE_COLOR ->{
                    val result: Int = data.extras.getInt("color");
                    ColorNubm = result;
                    ImgId = Int.MIN_VALUE;
                    ImgCam = null;
                    ImgPath = "";
                }

                REQUEST_CODE_IMG ->{
                    val result: Int = data.extras.getInt("imgid");
                    ImgId = result;
                    ColorNubm = Int.MIN_VALUE;
                    ImgCam = null;
                    ImgPath = "";
                }

                REQUEST_CODE_CAMERA->{
                    ImgCam = data.extras.get("data") as Bitmap?;
                    ImgId = Int.MIN_VALUE;
                    ColorNubm = Int.MIN_VALUE;
                    ImgPath = "";
                }

                REQUEST_CODE_GALLERY->{
                    var picUri = data.data as Uri;
                    ImgPath = picUri.toString();
                    ImgCam = null;
                    ColorNubm = Int.MIN_VALUE;
                    ImgId = Int.MIN_VALUE;
                }

            }
        }
    }

    private fun showSaveDialog(bitmap: Bitmap) {
        val alertDialog = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_save, null)
        alertDialog.setView(dialogView)
        val fileNameEditText: EditText = dialogView.findViewById(R.id.editText_file_name)
        val filename = UUID.randomUUID().toString()
        fileNameEditText.setSelectAllOnFocus(true)
        fileNameEditText.setText(filename)
        alertDialog.setTitle("Save Drawing")
                .setPositiveButton("ok") { _, _ -> saveImage(bitmap,fileNameEditText.text.toString()) }
                .setNegativeButton("Cancel") { _, _ -> }

        val dialog = alertDialog.create()
        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        dialog.show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    adapter = DrawAdapter(this,getFilesPath())
                    recycler_view.adapter = adapter
                }else{
                    finish()
                }
                return
            }
            else -> {}
        }
    }

    private fun saveImage(bitmap: Bitmap, fileName: String) {
        val imageDir = "${Environment.DIRECTORY_PICTURES}/Meme_Editor/"
        val path = Environment.getExternalStoragePublicDirectory(imageDir)
        Log.e("path",path.toString())
        val file = File(path, "$fileName.png")
        path.mkdirs()
        file.createNewFile()
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream)
        outputStream.flush()
        outputStream.close()
        updateRecyclerView(Uri.fromFile(file))
    }

    private fun updateRecyclerView(uri: Uri) {
        adapter.addItem(uri)
    }

    public override fun onPause() {
        super.onPause()
    }
}