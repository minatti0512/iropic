package app.fujii.iropic.color2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val chengebutton1 = findViewById<Button>(R.id.chengebutton1)
        chengebutton1.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(this@MainActivity, CameraActivity::class.java)
                startActivity(intent)


            }
        })
  //      val changebutton2 = findViewById<Button>(R.id.chengebutton2)
  //      changebutton2.setOnClickListener (object : View.OnClickListener {
  //          override fun onClick(v: View?) {
  //              val intent = Intent(this@MainActivity, SaveActivity::class.java)
  //              startActivity(intent)
//
  //          }
  //      })


        val changebutton3 = findViewById<Button>(R.id.chengebutton3)
        changebutton3.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(this@MainActivity, ColorActivity::class.java)
                startActivity(intent)

            }
        })

    }

}

