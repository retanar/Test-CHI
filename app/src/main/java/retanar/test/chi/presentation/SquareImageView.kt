package retanar.test.chi.presentation

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily

class SquareImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ShapeableImageView(context, attrs, defStyleAttr) {

    init {
        shapeAppearanceModel = shapeAppearanceModel.toBuilder()
            .setAllCorners(
                CornerFamily.ROUNDED,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics)
            )
            .build()
    }

    override fun onMeasure(width: Int, height: Int) {
        super.onMeasure(width, width)
    }
}