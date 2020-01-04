package com.cdh.wandroid.ui.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bigkoo.convenientbanner.holder.Holder
import com.cdh.wandroid.model.bean.BannerBean
import com.cdh.wandroid.util.ImageLoader
import com.makeramen.roundedimageview.RoundedImageView

/**
 * Created by chidehang on 2020-01-01
 */
class BannerImageHolderView : Holder<BannerBean> {

    private lateinit var imageView: RoundedImageView

    override fun UpdateUI(context: Context?, position: Int, data: BannerBean?) {
        data?.let {
            ImageLoader.display(imageView, it.imagePath)
        }
    }

    override fun createView(context: Context?): View {
        imageView = RoundedImageView(context)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setCornerRadius(8.0f)
        return imageView
    }
}