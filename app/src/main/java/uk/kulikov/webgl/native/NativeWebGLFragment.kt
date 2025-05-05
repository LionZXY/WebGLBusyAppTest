package uk.kulikov.webgl.native

import android.content.Context
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uk.kulikov.webgl.databinding.FragmentWebglBinding

class NativeWebGLFragment: Fragment() {
    private var _binding: FragmentWebglBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebglBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rootWebglView.addView(getGlSurfaceView(view.context))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getGlSurfaceView(context: Context): GLSurfaceView {
        val glSurfaceView = GLSurfaceView(context)

        // Create an OpenGL ES 3.0 context.
        glSurfaceView.setEGLContextClientVersion(3)
        glSurfaceView.setEGLConfigChooser(true)
        glSurfaceView.debugFlags = GLSurfaceView.DEBUG_LOG_GL_CALLS

        glSurfaceView.setRenderer(TestRenderer(glSurfaceView))
        glSurfaceView.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY

        return glSurfaceView
    }
}