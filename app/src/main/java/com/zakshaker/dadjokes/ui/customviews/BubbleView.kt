package com.zakshaker.dadjokes.ui.customviews


import android.animation.Animator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.core.graphics.drawable.toBitmap
import com.zakshaker.dadjokes.R
import kotlin.random.Random

/**
 * Joke bubble is a quadratic Bézier curve bubble with 4 control points.
 * End points are located on 4 corners of a given rectangle of a canvas.
 * End points are moving on edges of a rectangle
 */
private const val ANIMATION_DURATION = 2000L
private const val IMAGE_ROTATION_RANGE = 10
// Swing factor defines shift of an edge end point between corners
private const val SWING_FACTOR = 20

class BubbleView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val outerBubbleColorPaint: Paint
    private var shaderPaint: Paint

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.BubbleView,
            0,
            0
        )

        val imgSrc = typedArray.getDrawable(R.styleable.BubbleView_fillSrc)
            ?: throw Resources.NotFoundException("Please specify parameter imgSrc for a view")
        shaderPaint = Paint().apply {
            shader = BitmapShader(
                imgSrc.toBitmap(),
                Shader.TileMode.REPEAT,
                Shader.TileMode.REPEAT
            )
            isAntiAlias = true
        }

        outerBubbleColorPaint = Paint().apply {
            color = typedArray.getColor(R.styleable.BubbleView_fillColor, Color.BLACK)
            isAntiAlias = true
        }

        typedArray.recycle()

        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }


    private val bubblePath = Path()
    private val outerBubblePoints = BubblePathPoints()
    private val imgCropMaskBubblePoints = BubblePathPoints()


    /**
     * Outer bubble occupies all of the view.
     * Cropped Image bubble is placed in the center of a view.
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = suggestedMinimumWidth + paddingLeft + paddingRight
        val desiredHeight = suggestedMinimumHeight + paddingTop + paddingBottom
        setMeasuredDimension(
            resolveSize(desiredWidth, widthMeasureSpec),
            resolveSize(desiredHeight, heightMeasureSpec)
        )
    }

    private lateinit var croppedBitmap: Bitmap
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        // Cropped Image bubble is placed in the center of a view.
        // Thus, control points are placed with padding from view's rectangle
        imgCropMaskBubblePoints.apply {
            topLeftControlPoint.set(0f + paddingLeft, 0f + paddingTop)
            topRightControlPoint.set(measuredWidth.toFloat() - paddingRight, 0f + paddingTop)
            bottomRightControlPoint.set(
                measuredWidth.toFloat() - paddingRight,
                measuredHeight.toFloat() - paddingBottom
            )
            bottomLeftControlPoint.set(0f + paddingLeft, measuredHeight.toFloat() - paddingBottom)

            leftEdgeEndPoint.set(0f + paddingLeft, getHeight() * definedRandomNumber1)
            topEdgeEndPoint.set(getWidth() * definedRandomNumber1, 0f + paddingTop)
            rightEdgeEndPoint.set(paddingLeft + getWidth(), getHeight() * definedRandomNumber2)
            bottomEdgeEndPoint.set(getWidth() * definedRandomNumber2, paddingTop + getHeight())
        }

        // Outer bubble occupies all of the view.
        // Thus, control points are placed on corner of a full view's rectangle
        outerBubblePoints.apply {
            topLeftControlPoint.set(0f, 0f)
            topRightControlPoint.set(measuredWidth.toFloat(), 0f)
            bottomRightControlPoint.set(measuredWidth.toFloat(), measuredHeight.toFloat())
            bottomLeftControlPoint.set(0f, measuredHeight.toFloat())

            leftEdgeEndPoint.set(0f, getHeight() * definedRandomNumber1)
            topEdgeEndPoint.set(getWidth() * definedRandomNumber1, 0f)
            rightEdgeEndPoint.set(getWidth(), getHeight() * definedRandomNumber2)
            bottomEdgeEndPoint.set(getWidth() * definedRandomNumber2, getHeight())
        }

        // creating bubble path shape mask and shader paint
        val bubbleMaskBitmap: Bitmap =
            createBitmapMask(
                bubblePath.formBubblePath(imgCropMaskBubblePoints),
                outerBubbleColorPaint,
                measuredWidth,
                measuredHeight
            )
        croppedBitmap = cropDrawableWithMask(bubbleMaskBitmap, shaderPaint)

        bubbleAnimator = getBubbleAnimator(outerBubblePoints).apply {
            start()
        }
    }

    private val canvasMatrix = Matrix()
    override fun onDraw(canvas: Canvas) {
        canvas.apply {
            drawPath(bubblePath.formBubblePath(outerBubblePoints), outerBubbleColorPaint)
            drawBitmap(
                croppedBitmap,
                canvasMatrix.apply {
                    setRotate(
                        matrixRotation,
                        croppedBitmap.width.toFloat() / 2,
                        croppedBitmap.height.toFloat() / 2
                    )
                },
                null
            )
        }

    }

    private var matrixRotation: Float = 0f
    private var bubbleAnimator: Animator? = null
    private fun getBubbleAnimator(bubblePathPoints: BubblePathPoints): Animator {
        val propertyImageRotationName = "IMAGE_ROTATION"
        val propertyImageRotation = PropertyValuesHolder.ofFloat(
            propertyImageRotationName,
            -Random.nextInt(0, IMAGE_ROTATION_RANGE / 2).toFloat(),
            Random.nextInt(0, IMAGE_ROTATION_RANGE / 2).toFloat()
        )

        val propertyLeftEdgeEndPointName = "LEFT_EDGE_END_POINT_PROPERTY"
        val propertyLeftEdgeEndPoint = PropertyValuesHolder.ofFloat(
            propertyLeftEdgeEndPointName,
            bubblePathPoints.getHeight() * bubblePathPoints.definedRandomNumber1,
            bubblePathPoints.getHeight() * bubblePathPoints.definedRandomNumber2
        )
        val propertyTopEdgeEndPointName = "TOP_EDGE_END_POINT_PROPERTY"
        val propertyTopEdgeEndPoint = PropertyValuesHolder.ofFloat(
            propertyTopEdgeEndPointName,
            bubblePathPoints.getWidth() * bubblePathPoints.definedRandomNumber1,
            bubblePathPoints.getWidth() * bubblePathPoints.definedRandomNumber2
        )
        val propertyRightEdgeEndPointName = "RIGHT_EDGE_END_POINT_PROPERTY"
        val propertyRightEdgeEndPoint = PropertyValuesHolder.ofFloat(
            propertyRightEdgeEndPointName,
            bubblePathPoints.getHeight() * bubblePathPoints.definedRandomNumber2,
            bubblePathPoints.getHeight() * bubblePathPoints.definedRandomNumber1
        )
        val propertyBottomEdgeEndPointName = "BOTTOM_EDGE_END_POINT_PROPERTY"
        val propertyBottomEdgeEndPoint = PropertyValuesHolder.ofFloat(
            propertyBottomEdgeEndPointName,
            bubblePathPoints.getWidth() * bubblePathPoints.definedRandomNumber2,
            bubblePathPoints.getWidth() * bubblePathPoints.definedRandomNumber1
        )


        return ValueAnimator().apply {
            setValues(
                propertyImageRotation,
                propertyLeftEdgeEndPoint,
                propertyTopEdgeEndPoint,
                propertyRightEdgeEndPoint,
                propertyBottomEdgeEndPoint
            )
            duration = ANIMATION_DURATION
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            interpolator = AnticipateInterpolator()
            addUpdateListener { animation ->
                matrixRotation =
                    animation.getAnimatedValue(propertyImageRotationName) as Float
                bubblePathPoints.leftEdgeEndPoint.y =
                    animation.getAnimatedValue(propertyLeftEdgeEndPointName) as Float
                bubblePathPoints.topEdgeEndPoint.x =
                    animation.getAnimatedValue(propertyTopEdgeEndPointName) as Float
                bubblePathPoints.rightEdgeEndPoint.y =
                    animation.getAnimatedValue(propertyRightEdgeEndPointName) as Float
                bubblePathPoints.bottomEdgeEndPoint.x =
                    animation.getAnimatedValue(propertyBottomEdgeEndPointName) as Float
                invalidate()
            }
        }
    }

    override fun onDetachedFromWindow() {
        bubbleAnimator?.pause()
        super.onDetachedFromWindow()
    }

    // Data class for storing path points of a bubble
    private data class BubblePathPoints(
        // Control points are placed on corners of a rectangle
        val topLeftControlPoint: PointF = PointF(),
        val topRightControlPoint: PointF = PointF(),
        val bottomRightControlPoint: PointF = PointF(),
        val bottomLeftControlPoint: PointF = PointF(),
        // End points are placed on edges of a rectangle
        val leftEdgeEndPoint: PointF = PointF(),
        val topEdgeEndPoint: PointF = PointF(),
        val rightEdgeEndPoint: PointF = PointF(),
        val bottomEdgeEndPoint: PointF = PointF(),
        val definedRandomNumber1: Float = 0.5f + Random.nextInt(
            0,
            SWING_FACTOR
        ).toFloat() / 100,
        val definedRandomNumber2: Float = 0.5f + Random.nextInt(
            0,
            SWING_FACTOR
        ).toFloat() / 100
    ) {
        fun getWidth() = topRightControlPoint.x - topLeftControlPoint.x

        fun getHeight() = bottomLeftControlPoint.y - topLeftControlPoint.y
    }

    private fun Path.formBubblePath(bubblePathPoints: BubblePathPoints): Path {
        // moving from top-left corner in a clock-wise fashion
        reset()
        bubblePathPoints.run {
            // top left corner
            moveTo(leftEdgeEndPoint.x, leftEdgeEndPoint.y)
            quadTo(
                topLeftControlPoint.x, topLeftControlPoint.y,
                topEdgeEndPoint.x, topEdgeEndPoint.y
            )

            //top right corner
            quadTo(
                topRightControlPoint.x, topRightControlPoint.y,
                rightEdgeEndPoint.x, rightEdgeEndPoint.y
            )

            //bottom right corner
            quadTo(
                bottomRightControlPoint.x, bottomRightControlPoint.y,
                bottomEdgeEndPoint.x, bottomEdgeEndPoint.y
            )

            //bottom left corner
            quadTo(
                bottomLeftControlPoint.x, bottomLeftControlPoint.y,
                leftEdgeEndPoint.x, leftEdgeEndPoint.y
            )

            close()
            return this@formBubblePath
        }
    }

    private fun createBitmapMask(path: Path, paint: Paint, width: Int, height: Int): Bitmap {
        val mask = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8)
        val maskCanvas = Canvas(mask)
        maskCanvas.drawPath(path, paint)
        return mask
    }

    private fun cropDrawableWithMask(bitmapMask: Bitmap, shaderPaint: Paint): Bitmap {
        val crop = Bitmap.createBitmap(bitmapMask.width, bitmapMask.height, Bitmap.Config.ARGB_8888)
        val cropCanvas = Canvas(crop)
        cropCanvas.drawBitmap(bitmapMask, 0f, 0f, shaderPaint)
        return crop
    }
}