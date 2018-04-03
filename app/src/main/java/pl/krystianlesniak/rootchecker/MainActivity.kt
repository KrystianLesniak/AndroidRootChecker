package pl.krystianlesniak.rootchecker

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.io.DataOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val text = findViewById<TextView>(R.id.text)
            val p: Process
            try {
                // Preform su to get root privledges
                p = Runtime.getRuntime().exec("su")

                // Attempt to write a file to a root-only
                val os = DataOutputStream(p.outputStream)
                os.writeBytes("echo \"Do I have root?\" >/system/sd/temporary.txt\n")

                // Close the terminal
                os.writeBytes("exit\n")
                os.flush()
                try {
                    p.waitFor()
                    if (p.exitValue() != 255) {
                        // TODO Code to run on success
                        text.text = "Your device is rooted"
                        text.setTextColor(Color.GREEN);
                    } else {
                        // TODO Code to run on unsuccessful
                        text.text = "Your device is not rooted"
                        text.setTextColor(Color.RED);
                    }
                } catch (e: InterruptedException) {
                    // TODO Code to run in interrupted exception
                    text.text = "Your device is not rooted"
                    text.setTextColor(Color.RED);
                }

            } catch (e: IOException) {
                // TODO Code to run in input/output exception
                text.text = "Your device is not rooted"
                text.setTextColor(Color.RED);
            }


        }

    }
}
