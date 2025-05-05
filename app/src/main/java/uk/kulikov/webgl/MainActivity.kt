package uk.kulikov.webgl

import android.opengl.GLSurfaceView
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import edu.util.RawShaderLoader
import uk.kulikov.webgl.databinding.ActivityMainBinding
import uk.kulikov.webgl.native.TestRenderer
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        RawShaderLoader.mContext = WeakReference(
            applicationContext
        )

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}