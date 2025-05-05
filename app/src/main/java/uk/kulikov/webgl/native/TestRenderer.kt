package uk.kulikov.webgl.native

import android.opengl.GLSurfaceView
import edu.three.buffergeometries.PlaneBufferGeometry
import edu.three.cameras.Camera
import edu.three.cameras.PerspectiveCamera
import edu.three.math.Vector2
import edu.three.math.Vector3
import edu.three.objects.Mesh
import edu.three.renderers.GLRenderer
import edu.three.scenes.Scene
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.properties.Delegates

private const val Z_NEAR = 0.1
private const val Z_FAR = 1000.0

class TestRenderer(
    private val view: GLSurfaceView
) : GLSurfaceView.Renderer {
    private var mWidth by Delegates.notNull<Int>()
    private var mHeight by Delegates.notNull<Int>()
    private var aspect by Delegates.notNull<Double>()

    private lateinit var renderer: GLRenderer
    private lateinit var scene: Scene
    private lateinit var camera: Camera
    private lateinit var waveMaterial: WaveMaterial

    private var startTime by Delegates.notNull<Long>()


    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        mWidth = view.width
        mHeight = view.height
        aspect = mWidth.toDouble() / mHeight

        scene = Scene()

        val param = GLRenderer.Param()
        //param.antialias = true
        renderer = GLRenderer(param, mWidth, mHeight)
        renderer.setClearColor(0xf4f4f4, 1f)

        val geometry = PlaneBufferGeometry(mWidth.toFloat(), mHeight.toFloat())

        waveMaterial = WaveMaterial(Vector2(mWidth.toDouble(), mHeight.toDouble()))
        startTime = System.currentTimeMillis()
        val mesh = Mesh(geometry, waveMaterial)

        scene.add(mesh)

        camera = PerspectiveCamera(
            45.0,
            aspect,
            Z_NEAR,
            Z_FAR
        )
        camera.position = Vector3(0.0, 0.0, 30.0)
        camera.lookAt(scene.position)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        mWidth = width
        mHeight = height
        aspect = width.toDouble() / height

        renderer.setSize(mWidth.toFloat(), mHeight.toFloat())
    }

    override fun onDrawFrame(gl: GL10) {
        waveMaterial.uniforms.hashMap["time"] =
            ((System.currentTimeMillis() - startTime).toDouble() / 1000).toFloat()
        renderer.render(scene, camera)
    }
}

