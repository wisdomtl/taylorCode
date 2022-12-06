//package com.bilibili.studio.search.template.ui
//
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.constraintlayout.widget.ConstraintLayout
//import androidx.core.view.updateLayoutParams
//import androidx.recyclerview.widget.RecyclerView
//import com.bilibili.baseui.extension.*
//import com.bilibili.studio.module.freedata.FreeDataImageLoader
//import com.bilibili.studio.search.R
//import com.bilibili.utils.DensityUtil
//import com.bilibili.utils.ImageLoaderUtil
//import com.bilibili.utils.ScreenUtil
//import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
//import com.facebook.drawee.generic.RoundingParams
//import com.facebook.drawee.view.SimpleDraweeView
//import com.facebook.imagepipeline.common.ResizeOptions
//import com.tab.bean.TemplateTabItemBean
//import com.tab.utils.TemplateUtils
//import taylor.com.varietyadapter.VarietyAdapter
//
//class SearchResultItemBuilder : VarietyAdapter.ItemBuilder<TemplateTabItemBean, SearchResultViewHolder>() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        val itemView = parent.context.run {
//            ConstraintLayout {
//                layout_width = match_parent
//                layout_height = wrap_content
//
//                SimpleDraweeView {
//                    layout_id = "sdvCover"
//                    layout_width = match_parent
//                    layout_height = wrap_content
//                    scaleType = scale_center_crop
//                    hierarchy = GenericDraweeHierarchyBuilder(this.resources)
//                        .setRoundingParams(RoundingParams.fromCornersRadius(12f.dp))
//                        .build()
//                }
//
//                LinearLayout {
//                    layout_width = wrap_content
//                    layout_height = wrap_content
//                    start_toStartOf = "sdvCover"
//                    bottom_toBottomOf = "sdvCover"
//                    margin_start = 8
//                    margin_bottom = 8
//                    shape = shape {
//                        solid_color = "#80000000"
//                        corner_radius = 12
//                    }
//
//                    TextView{
//                        layout_id = "tvUsage"
//                        layout_width = wrap_content
//                        layout_height = wrap_content
//                        textSize = 10f
//                        textColor = "#ffffff"
//                        gravity = gravity_center
//                        padding_vertical = 3
//                        padding_start = 10
//                        padding_end = 5
//                    }
//
//                    View {
//                        layout_id = "vSeparate"
//                        layout_width = 1
//                        layout_height = 8
//                        visibility = gone
//                        gravity= gravity_center_vertical
//                        shape = shape {
//                            solid_color = "#A2A7AE"
//                        }
//                    }
//
//                    TextView {
//                        layout_id = "tvSegments"
//                        layout_width = wrap_content
//                        layout_height = wrap_content
//                        textSize = 10f
//                        textColor = "#ffffff"
//                        gravity = gravity_center
//                        padding_vertical = 3
//                        padding_start = 5
//                        padding_end = 10
//                        visibility = gone
//                    }
//                }
//
//                TextView {
//                    layout_id = "tvTitle"
//                    layout_width = wrap_content
//                    layout_height = wrap_content
//                    textSize = 14f
//                    textColor = "#ffffff"
//                    gravity = gravity_left
//                    top_toBottomOf = "sdvCover"
//                    start_toStartOf = parent_id
//                    maxLines = 1
//                    ellipsize = ellipsize_end
//                    margin_top = 8
//                }
//
//                SimpleDraweeView {
//                    layout_id = "ivAvatar"
//                    layout_width = 22
//                    layout_height = 22
//                    scaleType = scale_center_crop
//                    start_toStartOf = parent_id
//                    top_toBottomOf = "tvTitle"
//                    margin_top = 6
//                    hierarchy = GenericDraweeHierarchyBuilder(this.resources)
//                        .setRoundingParams(RoundingParams.asCircle())
//                        .setFailureImage(R.drawable.template_ic_default_img)
//                        .build()
//                }
//
//                TextView {
//                    layout_id = "tvName"
//                    layout_width = wrap_content
//                    layout_height = wrap_content
//                    textSize = 11f
//                    textColor = "#686A72"
//                    gravity = gravity_center
//                    start_toEndOf = "ivAvatar"
//                    align_vertical_to = "ivAvatar"
//                    margin_start = 6
//                    maxLines = 1
//                    ellipsize = ellipsize_end
//                }
//
//                BTextView {
//                    layout_id = "tvHot"
//                    layout_width = wrap_content
//                    layout_height = wrap_content
//                    textSize = 10f
//                    textColor = "#ffffff"
//                    gravity = gravity_center
//                    background_res = R.drawable.template_tab_official_text_background
//                    end_toEndOf = parent_id
//                    top_toTopOf = parent_id
//                    padding_horizontal = 6
//                    margin_end = 12
//                    margin_top = 12
//                    visibility = gone
//                }
//
//                BTextView {
//                    layout_id = "tvCollect"
//                    layout_width = wrap_content
//                    layout_height = wrap_content
//                    text = "我的收藏"
//                    textSize = 10f
//                    textColor = "#ffffff"
//                    gravity = gravity_center
//                    top_toTopOf = parent_id
//                    end_toEndOf = parent_id
//                    background_res = R.drawable.template_tab_official_text_background
//                    margin_end = 12
//                    margin_top = 12
//                    padding_horizontal = 6
//                    padding_vertical = 1
//                    visibility = gone
//                }
//            }
//        }
//
//        return SearchResultViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: SearchResultViewHolder, data: TemplateTabItemBean, index: Int, action: ((Any?) -> Unit)?) {
//        holder.ivCover?.also {
//            val ratio = data.aspect_ratio.takeIf { it != 0f } ?: 1f
//            val coverWidth = (ScreenUtil.getScreenWidth(holder.itemView.context) - ScreenUtil.dip2px(52f)) / 2
//            val coverHeight = (coverWidth / ratio).toInt()
//            it.updateLayoutParams<ConstraintLayout.LayoutParams> {
//                width = coverWidth
//                height = coverHeight
//            }
//            FreeDataImageLoader.getInstance().displayImageToRGB565(
//                ImageLoaderUtil.getQualityUrl(
//                    data.coverUrl,
//                    coverWidth,
//                    coverHeight
//                ), it, ResizeOptions(coverWidth, coverHeight)
//            )
//        }
//        holder.tvSegments?.apply {
//            text = TemplateUtils.getTemplatePiece(data)
//            visibility = if(data.is_operating == 1) gone else visible
//        }
//        holder.separate?.apply {
//            visibility = if (data.is_operating == 1) gone else visible
//        }
//        holder.tvUsage?.apply {
//            text = TemplateUtils.getTemplateUsage(data)
//            visibility = if (data.is_operating == 1) gone else visible
//        }
//        holder.tvTitle?.apply {
//            text = data.name
//            visibility = if(data.is_operating == 1) gone else visible
//        }
//        if (!data.author?.name.isNullOrEmpty() && !data.author?.face.isNullOrEmpty()) {
//            holder.ivAvatar?.also {
//                FreeDataImageLoader.getInstance().displayImageToRGB565(
//                    ImageLoaderUtil.getQualityUrl(
//                        data.author?.face,
//                        DensityUtil.dp2px(22f),
//                        DensityUtil.dp2px(22f)
//                    ),
//                    it,
//                    ResizeOptions(DensityUtil.dp2px(22f), DensityUtil.dp2px(22f))
//                )
//            }
//            holder.tvName?.text = data.author.name
//        } else {
//            holder.ivAvatar?.visibility = gone
//            holder.tvName?.visibility = gone
//        }
//
//        holder.tvHot?.apply {
//            if (!data.op_tag.isNullOrEmpty()) {
//                text = data.op_tag
//                visibility = visible
//            } else {
//                visibility = gone
//            }
//        }
//
//        holder.tvCollect?.apply {
//            visibleOrGone(data.fav == 1)
//            if (isVisible()) {
//                holder.tvHot?.gone()
//            }
//        }
//
//        action?.invoke(index to data)
//    }
//}
//
//class SearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//    val ivCover = itemView.find<SimpleDraweeView>("sdvCover")
//    val tvSegments = itemView.find<TextView>("tvSegments")
//    val tvUsage = itemView.find<TextView>("tvUsage")
//    val separate = itemView.find<View>("separate")
//    val tvTitle = itemView.find<TextView>("tvTitle")
//    val ivAvatar = itemView.find<SimpleDraweeView>("ivAvatar")
//    val tvName = itemView.find<TextView>("tvName")
//    val tvHot = itemView.find<TextView>("tvHot")
//    val tvCollect = itemView.find<TextView>("tvCollect")
//}