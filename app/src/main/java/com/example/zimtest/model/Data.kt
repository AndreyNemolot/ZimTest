package com.example.zimtest.model

import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.zimtest.R
import com.squareup.picasso.Picasso
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Data(
    val title: String = "",
    val url: String = "",
    var index: Int = 0

) : Parcelable{

    companion object {
        @JvmStatic
        @BindingAdapter("app:url")
        fun loadImage(view: ImageView?, url: String?) {
            Picasso.get().load(url)
                .error(R.drawable.baseline_image_black)
                .placeholder(R.drawable.progress_animation).into(view)
        }
    }
}
