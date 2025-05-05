package uk.kulikov.webgl.native

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.opengl.GLSurfaceView
import edu.three.cameras.Camera
import edu.three.cameras.PerspectiveCamera
import edu.three.materials.SpriteMaterial
import edu.three.math.Vector3
import edu.three.objects.Sprite
import edu.three.renderers.GLRenderer
import edu.three.scenes.Scene
import edu.three.textures.Texture
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.properties.Delegates

private const val Z_NEAR = 0.1
private const val Z_FAR = 1000.0

class TestTextRenderer(
    private val view: GLSurfaceView
) : GLSurfaceView.Renderer {
    private var mWidth by Delegates.notNull<Int>()
    private var mHeight by Delegates.notNull<Int>()
    private var aspect by Delegates.notNull<Double>()

    private lateinit var renderer: GLRenderer
    private lateinit var scene: Scene
    private lateinit var camera: Camera


    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        mWidth = view.width
        mHeight = view.height
        aspect = mWidth.toDouble() / mHeight

        scene = Scene()

        val param = GLRenderer.Param()
        //param.antialias = true
        renderer = GLRenderer(param, mWidth, mHeight)
        renderer.setClearColor(0xf4f4f4, 1f)

        //renderer.setClearColor(0x000000, 1f)

        /* User code start */

        val bitmap: Bitmap = drawText(
            "Hello",
        ) //b5 新希望国际A
        val texture = Texture()
        texture.setImage(bitmap)

        val material = SpriteMaterial()
        material.map = texture
        material.depthTest = false.also { material.depthWrite = it }
        val sprite = Sprite(material)
        sprite.scale[(bitmap.width * 0.03f).toDouble(), (bitmap.height * 0.03f).toDouble()] = 1.0
        scene.add(sprite)

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
        renderer.render(scene, camera)
    }
}


private fun drawText(
    text: String
): Bitmap {
    val p = Paint()
    p.color = -0x1000000
    p.setTypeface(Typeface.SERIF)
    p.textSize = 24.toFloat()

    val metrics = p.fontMetricsInt
    val height = metrics.bottom - metrics.top

    val rect = Rect()
    p.getTextBounds(text, 0, text.length, rect)
    val width = rect.width()

    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    canvas.drawText(text, 0f, -metrics.ascent.toFloat(), p)

    //    p.setStyle(Paint.Style.STROKE);
//    p.setStrokeWidth(4);
//    canvas.drawRect(0, 0, width, height, p);
    return bitmap
}