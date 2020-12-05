package app.fujii.iropic.color2

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.blue
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.green
import androidx.core.graphics.red
import kotlinx.android.synthetic.main.activity_camera.*
import kotlinx.android.synthetic.main.activity_camera.codeTextView1


class CameraActivity : AppCompatActivity() {

    companion object {
        const val CAMERA_REQUEST_CODE = 1
        const val CAMERA_PERMISSION_REQUEST_CODE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)


            imageView.setOnTouchListener{ view, motionEvent ->
            val image = view as ImageView

            val bitmap = Bitmap.createBitmap(image.drawable.toBitmap())

            val ratio = bitmap.width.toFloat() / image.width.toFloat()
            var x = (motionEvent.x * ratio).toInt()
            var y = (motionEvent.y * ratio).toInt()

            if (x < 0) {
                x = 0
            } else if (x > bitmap.width - 1) {
                x = bitmap.width - 1
            }

            if (y < 0) {
                y = 0
            } else if (y > bitmap.height - 1) {
                y = bitmap.height - 1


                codeTextView1.text = "#000000"
                codeTextView1.setTextColor(Color.parseColor("#000000"))
            }

            val pixel = bitmap.getPixel(x, y)
            rgbTextView.text = "(${pixel.red},${pixel.green},${pixel.blue})"
            rgbTextView.setTextColor(pixel)
            //colorView.setBackgroundColor(pixel)
            //    potionTextView.text = "x: $x, y: $y"

            codeTextView1.text = String.format("#%06x", (0xFFFFFF and pixel))
            codeTextView1.setTextColor(pixel)
            colorView.setBackgroundColor(pixel)

                pickerView.setBackgroundColor(pixel)
                changePickerViewPosition(motionEvent.x, motionEvent.y)


            true


        }
    }

    private fun changePickerViewPosition(x: Float,y: Float){

        val offsetX: Int = 30
        val offsetY: Int = -80

        val left = x.toInt() + offsetX
        val top =y.toInt() + offsetY
        val right = (x + pickerView.width).toInt() + offsetX
        val bottom = (y + pickerView.height).toInt() + offsetY

        pickerView.layout(left, top, right, bottom)

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)

        val inflater = menuInflater
        //メニューのリソース選択
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    //メニューのアイテムを押下した時の処理の関数
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            //作成ボタンを押したとき
            R.id.camera -> {
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).resolveActivity(packageManager)?.let {
                    if (checkCameraPermission()) {
                        takePicture()
                    } else {
                        grantCameraPermission()
                    }
                } ?: Toast.makeText(this, "カメラを扱うアプリがありません", Toast.LENGTH_LONG).show()

                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }


//
//    override fun onResume() {
//        super.onResume()
//
//        btnLaunchCamera.setOnClickListener {
//            // カメラ機能を実装したアプリが存在するかチェック
//            Intent(MediaStore.ACTION_IMAGE_CAPTURE).resolveActivity(packageManager)?.let {
//                if (checkCameraPermission()) {
//                    takePicture()
//                } else {
//                    grantCameraPermission()
//                }
//            } ?: Toast.makeText(this, "カメラを扱うアプリがありません", Toast.LENGTH_LONG).show()
//
//
//        }
//    }


    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val image = data?.extras?.get("data")?.let {
                imageView.setImageBitmap(it as Bitmap)
            }
        }
    }

    private fun takePicture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
        }

        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    private fun checkCameraPermission() = PackageManager.PERMISSION_GRANTED ==
            ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)


    private fun grantCameraPermission() =
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE)


    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePicture()
            }
        }
    }

}

