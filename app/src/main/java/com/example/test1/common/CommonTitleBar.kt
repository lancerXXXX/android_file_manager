package com.example.test1.common

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.test1.R
import com.example.test1.databinding.CommonTitleBarBinding

/**
 * TODO: document your custom view class.
 */
class CommonTitleBar : ConstraintLayout{

    private lateinit var binding: CommonTitleBarBinding

    private var _exampleString: String? = null // TODO: use a default from R.string...
    private var _exampleColor: Int = Color.RED // TODO: use a default from R.color...
    private var _exampleDimension: Float = 0f // TODO: use a default from R.dimen...

    private lateinit var textPaint: TextPaint
    private var textWidth: Float = 0f
    private var textHeight: Float = 0f

    /**
     * The text to draw
     */
    var exampleString: String?
        get() = _exampleString
        set(value) {
            _exampleString = value
            invalidateTextPaintAndMeasurements()
        }


    /**
     * The font color
     */
    var exampleColor: Int
        get() = _exampleColor
        set(value) {
            _exampleColor = value
            invalidateTextPaintAndMeasurements()
        }

    /**
     * In the example view, this dimension is the font size.
     */
    var exampleDimension: Float
        get() = _exampleDimension
        set(value) {
            _exampleDimension = value
            invalidateTextPaintAndMeasurements()
        }

    /**
     * In the example view, this drawable is drawn above the text.
     */
    var exampleDrawable: Drawable? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        binding = CommonTitleBarBinding.inflate(LayoutInflater.from(context), this, true)

        // Load attributes
        fun loadAttributes() {
            var xmlAttribute = context.obtainStyledAttributes(
                attrs, R.styleable.CommonTitleBar, defStyle, 0
            )
            _exampleString = xmlAttribute.getString(
                R.styleable.CommonTitleBar_exampleString
            )
            _exampleColor = xmlAttribute.getColor(
                R.styleable.CommonTitleBar_exampleColor,
                exampleColor
            )
            // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
            // values that should fall on pixel boundaries.
            _exampleDimension = xmlAttribute.getDimension(
                R.styleable.CommonTitleBar_exampleDimension,
                exampleDimension
            )
            if (xmlAttribute.hasValue(R.styleable.CommonTitleBar_exampleDrawable)) {
                exampleDrawable = xmlAttribute.getDrawable(
                    R.styleable.CommonTitleBar_exampleDrawable
                )
                exampleDrawable?.callback = this
            }

            xmlAttribute.recycle()
        }

        // Set up a default TextPaint object
        fun setUpDefaultTextPaint() {
            textPaint = TextPaint().apply {
                flags = Paint.ANTI_ALIAS_FLAG
                textAlign = Paint.Align.LEFT
            }
        }

        loadAttributes()
        setUpDefaultTextPaint()
        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements()
    }

    private fun invalidateTextPaintAndMeasurements() {
        textPaint.let {
            it.textSize = exampleDimension
            it.color = exampleColor
            textWidth = it.measureText(exampleString)
            textHeight = it.fontMetrics.bottom
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        val paddingRight = paddingRight
        val paddingBottom = paddingBottom

        val contentWidth = width - paddingLeft - paddingRight
        val contentHeight = height - paddingTop - paddingBottom

//        exampleString?.let {
//            // Draw the text.
//            canvas.drawText(
//                it,
//                paddingLeft + (contentWidth - textWidth) / 2,
//                paddingTop + (contentHeight + textHeight) / 2,
//                textPaint
//            )
//        }
//
//        // Draw the example drawable on top of the text.
//        exampleDrawable?.let {
//            it.setBounds(
//                paddingLeft, paddingTop,
//                paddingLeft + contentWidth, paddingTop + contentHeight
//            )
//            it.draw(canvas)
//        }
    }
}