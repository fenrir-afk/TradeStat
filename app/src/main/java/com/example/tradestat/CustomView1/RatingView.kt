package com.example.tradestat.CustomView1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.tradestat.R


class RatingView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    companion object {
        private const val DEFAULT_GLOBAL_COLOR = Color.GRAY
        private const val DEFAULT_BORDER_COLOR = Color.BLACK
        private const val DEFAULT_BORDER_WIDTH = 16.0f
        private const val DEFAULT_TEXT = "22"
        private const val DEFAULT_TEXT_SIZE = 16.0f
        const val amount = 0L
        const val percent = 1L
    }

    private var borderColor = DEFAULT_BORDER_COLOR
    private var borderWidth = DEFAULT_BORDER_WIDTH
    private var globalColor = DEFAULT_GLOBAL_COLOR
    private var textSize = DEFAULT_TEXT_SIZE
    private var text = DEFAULT_TEXT

    private val paint = Paint()
    private val mouthPath = Path()
    private var size = 0
    var rateState = amount
        set(state) {
            field = state
            // 4
            invalidate()
        }
    init {
        paint.isAntiAlias = true
        setupAttributes(attrs)
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.RatingView,
            0, 0)
        rateState = typedArray.getInt(R.styleable.RatingView_state, amount.toInt()).toLong()


        text = typedArray.getString(R.styleable.RatingView_text) ?: "0"
        globalColor = typedArray.getColor(R.styleable.RatingView_globalColor, DEFAULT_GLOBAL_COLOR)
        borderColor = typedArray.getColor(R.styleable.RatingView_borderColor,
            DEFAULT_BORDER_COLOR)
        borderWidth = typedArray.getDimension(R.styleable.RatingView_borderWidth,
            DEFAULT_BORDER_WIDTH)
        // TypedArray objects are shared and must be recycled.
        typedArray.recycle()
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBackground(canvas)
        drawNewText(canvas)


    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {      super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        size = Math.min(measuredWidth, measuredHeight)
        setMeasuredDimension(size, size)
    }

    private fun drawBackground(canvas: Canvas) {
        paint.color = globalColor
        paint.style = Paint.Style.FILL
        val radius = size / 2f
        canvas.drawCircle(size / 2f, size / 2f, radius, paint)
        paint.color = borderColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidth
        canvas.drawCircle(size / 2f, size / 2f, radius - borderWidth / 2f, paint)
    }


    private fun drawNewText(canvas: Canvas) {
        if(rateState == RatingView.amount){
            paint.isAntiAlias = true
            val fontPath = "font/edgedisplayregular.ttf"
            var typeface: Typeface? = ResourcesCompat.getFont(context, R.font.pro_regular);
            paint.typeface = typeface
            paint.color = Color.WHITE
            paint.textSize = 32.0f
            paint.strokeWidth = 2.0f
            paint.style = Paint.Style.FILL
            canvas.drawText("All", size * 0.40f, size*0.40f, paint)
            paint.textSize = 20.0f
            canvas.drawText("―――", size * 0.37f, size*0.52f, paint);
            paint.textSize = 32.0f
            if(text.toInt() < 9 ){
                canvas.drawText(text, size * 0.43f, size*0.7f, paint)
            }else{
                canvas.drawText(text, size * 0.40f, size*0.7f, paint)
            }
        }else{
            paint.isAntiAlias = true
            paint.color = Color.WHITE
            paint.textSize = 32.0f
            paint.strokeWidth = 2.0f
            paint.style = Paint.Style.FILL
            canvas.drawText("66.1%", size * 0.33f, size*0.56f, paint);
        }

    }

}