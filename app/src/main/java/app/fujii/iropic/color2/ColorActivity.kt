package app.fujii.iropic.color2

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView
import androidx.core.graphics.blue
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.green
import androidx.core.view.MotionEventCompat
import kotlinx.android.synthetic.main.activity_color.*
import androidx.core.graphics.red as red1

class ColorActivity : AppCompatActivity() {

    private var touchIDs: MutableList<Int> = mutableListOf();

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color)



        ImageView.setOnTouchListener { view, motionEvent ->
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

            val  pixel = bitmap.getPixel(x, y)
            textView4.text = "(${pixel.red1},${pixel.green},${pixel.blue})"
            textView4.setTextColor(pixel)
            //colorView.setBackgroundColor(pixel)
        //    potionTextView.text = "x: $x, y: $y"

            codeTextView1.text = String.format("#%06x",(0xFFFFFF and pixel))
            codeTextView1.setTextColor(pixel)

            true


        }


    }
    override fun onTouchEvent(event: MotionEvent): Boolean {

        val action: Int = MotionEventCompat.getActionMasked(event)

        return when (action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d("test", "Action was DOWN")


                true
            }

            else -> super.onTouchEvent(event)
        }

    }

}

