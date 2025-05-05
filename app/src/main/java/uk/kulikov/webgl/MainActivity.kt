package uk.kulikov.webgl

import android.opengl.GLSurfaceView
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

        binding.rootView.addView(getGlSurfaceView())
    }

    private fun getGlSurfaceView(): GLSurfaceView {
        val glSurfaceView = GLSurfaceView(this)

        // Create an OpenGL ES 3.0 context.
        glSurfaceView.setEGLContextClientVersion(3)
        glSurfaceView.setEGLConfigChooser(true)
        glSurfaceView.debugFlags = GLSurfaceView.DEBUG_LOG_GL_CALLS

        glSurfaceView.setRenderer(TestRenderer(glSurfaceView))
        glSurfaceView.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY

        return glSurfaceView
    }
}