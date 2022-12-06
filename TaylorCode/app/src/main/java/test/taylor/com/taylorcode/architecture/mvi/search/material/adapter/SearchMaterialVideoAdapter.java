//package test.taylor.com.taylorcode.architecture.mvi.search.material.adapter;
//
//import android.graphics.Typeface;
//import android.net.Uri;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.cardview.widget.CardView;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bilibili.commons.StringUtils;
//import com.bilibili.downloader.DownloadTaskState;
//import com.bilibili.lib.image.ImageLoader;
//import com.bilibili.studio.constants.CommonConstant;
//import com.bilibili.studio.module.album.MaterialConfig;
//import com.bilibili.studio.module.album.adapter.BaseMediaAdapter;
//import com.bilibili.studio.module.album.loader.MediaItem;
//import com.bilibili.studio.module.album.report.MaterialReport;
//import com.bilibili.studio.module.album.util.VideoUtil;
//import com.bilibili.studio.module.cloud.ui.CloudMaterialListEvent;
//import com.bilibili.studio.module.freedata.FreeDataImageLoader;
//import com.bilibili.studio.report.StudioReport;
//import com.bilibili.studio.search.R;
//import com.bilibili.utils.DateUtil;
//import com.bilibili.utils.FontUtils;
//import com.bilibili.utils.ImageLoaderUtil;
//import com.bilibili.widgets.SnackBarBuilder;
//import com.facebook.drawee.drawable.ScalingUtils;
//import com.facebook.drawee.generic.GenericDraweeHierarchy;
//import com.facebook.drawee.view.SimpleDraweeView;
//
//import org.greenrobot.eventbus.EventBus;
//
//import java.io.File;
//import java.util.Locale;
//
///**
// * @author: masque
// * @date: 2020/6/20 14:48
// * @email: wangjixiang@bilibili.com
// */
//public class SearchMaterialVideoAdapter extends BaseMediaAdapter {
//
//    public SearchMaterialVideoAdapter() {
//        super();
//    }
//
//    public SearchMaterialVideoAdapter(boolean multiSelectMode) {
//        super(multiSelectMode);
//    }
//
//    @Override
//    protected RecyclerView.ViewHolder onCreateItemViewHolder(@NonNull ViewGroup parent) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item_video_thumb, parent, false);
//        return new ItemViewHolder(view);
//    }
//
//    @Override
//    protected void onBindItemViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
//        final MediaItem media = mFullItems.get(position);
//        String tag = (String) itemViewHolder.thumb.getTag();
//        StudioReport.INSTANCE.reportMaterialCenterSingleIconShow(19, media.id, media.cat_id, "search_detail", media.badge,"",0,position+1,"",0);
//        if (media.dataType == MediaItem.DataType.REMOTE) {
//            if (StringUtils.isEmpty(tag) || !media.cover.equals(tag)) {
//                FreeDataImageLoader.getInstance().displayImage(ImageLoaderUtil.INSTANCE.getQualityUrl(media.cover, mResizeOptions.width, mResizeOptions.height), itemViewHolder.thumb, mResizeOptions);
//                itemViewHolder.thumb.setTag(media.cover);
//            }
//        } else {
//            if (!media.path.isEmpty()) {
//                if (StringUtils.isEmpty(tag) || !media.path.equals(tag)) {
//                    FreeDataImageLoader.getInstance().displayImage(Uri.fromFile(new File(media.path)).toString(), itemViewHolder.thumb, mResizeOptions);
//                    itemViewHolder.thumb.setTag(media.path);
//                }
//            }
//            itemViewHolder.inCloudTv.setVisibility(media.inCloud ? View.VISIBLE : View.GONE);
//        }
//
//        GenericDraweeHierarchy hierarchy = itemViewHolder.thumb.getHierarchy();
//        if (media.dataType == MediaItem.DataType.REMOTE) {
//            itemViewHolder.downloadStatusIv.setVisibility(View.VISIBLE);
//            switch (media.downloadStatus) {
//                case DownloadTaskState.DOWNLOADING:
//                    ImageLoader.getInstance()
//                            .displayImage(R.drawable.material_ic_bgm_download_loading, itemViewHolder.downloadStatusIv);
//                    break;
//                case DownloadTaskState.ERROR:
//                    ImageLoader.getInstance()
//                            .displayImage(R.drawable.material_ic_material_download_retry, itemViewHolder.downloadStatusIv);
//                    break;
//                case DownloadTaskState.FINISH:
//                    itemViewHolder.downloadStatusIv.setVisibility(View.GONE);
//                    break;
//                default:
//                    ImageLoader.getInstance()
//                            .displayImage(R.drawable.material_ic_material_download, itemViewHolder.downloadStatusIv);
//                    break;
//            }
//            if (uiStyle == 1) {
//                itemViewHolder.card_view.setCardBackgroundColor(itemViewHolder.itemView.getContext().getResources().getColor(R.color.material_222331));
//            }
//            hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
//            itemViewHolder.itemName.setVisibility(View.VISIBLE);
//            itemViewHolder.itemName.setText(media.name);
//            if (!TextUtils.isEmpty(media.badge)){
//                itemViewHolder.badge.setVisibility(View.VISIBLE);
//                itemViewHolder.badge.setText(media.badge);
//            }else {
//                itemViewHolder.badge.setVisibility(View.GONE);
//            }
//        } else {
//            itemViewHolder.itemName.setVisibility(View.GONE);
//            itemViewHolder.badge.setVisibility(View.GONE);
//            hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
//        }
//
//
//        itemViewHolder.durationTv.setVisibility(media.isVideo() ? View.VISIBLE : View.GONE);
//        itemViewHolder.selectBg.setVisibility(View.GONE);
//
//        if (mSelectedDatas != null) {
//            int index = 1;
//            for (int i = 0; i < mSelectedDatas.size(); ++i) {
//                if (mSelectedDatas.get(i).equals(media)) {
//                    index += i;
//                        itemViewHolder.selectBg.setVisibility(View.VISIBLE);
//                    break;
//                }
//            }
//            if (mShowSelectIndex) {
//                itemViewHolder.selectIndexTv.setText(String.format(Locale.getDefault(), "%02d", index));
//            }
//        }
//        itemViewHolder.durationTv.setText(DateUtil.INSTANCE.duration2time(media.duration));
//    }
//
//    @Override
//    protected String getTag() {
//        return "SearchMaterialVideoAdapter";
//    }
//
//    @Override
//    public void chooseItem(RecyclerView recyclerView, int position) {
//        recyclerView.postDelayed(() -> {
//            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
//            if (viewHolder instanceof ItemViewHolder) {
//                ((ItemViewHolder) viewHolder).onClick(viewHolder.itemView, true);
//            }
//        }, 1000);
//    }
//
//    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//        public SimpleDraweeView thumb;
//        public ConstraintLayout selectBg;
//        public TextView selectIndexTv;
//        public TextView durationTv;
//        public TextView inCloudTv;
////        public SimpleDraweeView previewIv;
//        public SimpleDraweeView downloadStatusIv;
//        public TextView itemName;
//        public TextView badge;
//        public CardView card_view;
//
//        public ItemViewHolder(@NonNull View itemView) {
//            super(itemView);
//            itemView.setOnClickListener(this);
//            Typeface typeface = FontUtils.getFont(itemView.getContext(), CommonConstant.FONT.STUDIO_REGULAR);
//            Typeface typefaceBold = FontUtils.getFont(itemView.getContext(), CommonConstant.FONT.STUDIO_BOLD);
//            thumb = itemView.findViewById(R.id.item_material_thumb_im);
//            selectBg = itemView.findViewById(R.id.thumb_select_bg_cl);
//            selectIndexTv = itemView.findViewById(R.id.thumb_select_index_tv);
//            selectIndexTv.setTypeface(typefaceBold);
//            durationTv = itemView.findViewById(R.id.material_duration_tv);
//            durationTv.setTypeface(typeface);
//            inCloudTv = itemView.findViewById(R.id.tv_in_cloud);
////            previewIv = itemView.findViewById(R.id.item_material_preview_im);
//            itemName = itemView.findViewById(R.id.material_item_name);
//            badge = itemView.findViewById(R.id.material_item_badge);
//            card_view = itemView.findViewById(R.id.card_view);
//
////            previewIv.setOnClickListener(this);
//            downloadStatusIv = itemView.findViewById(R.id.item_material_preview_download_status_iv);
//            downloadStatusIv.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View v) {
//            onClick(v, false);
//        }
//
//        public void onClick(View v, boolean isAutoSelect) {
//            final int pos = getAdapterPosition();
//            if (pos == -1) {
//                return;
//            }
//            final MediaItem item = mFullItems.get(pos);
//            if (item == null) {
//                return;
//            }
//            //如果从相册中取到的时长为0，从视频文件的metadata中兜底。
//            if (item.duration == 0) {
//                item.duration = VideoUtil.getVideoDuration(item.path);
//            }
//            if (item.downloadStatus != DownloadTaskState.FINISH && onMediaDownloadListener != null) {
//                onMediaDownloadListener.downloadMediaItem(item, pos, true, isAutoSelect);
//            } else {
//                if (onItemClickListener != null) {
//                    onItemClickListener.onPreviewClick(getAdapterPosition());
//                }
//            }
//            if (item.dataType == MediaItem.DataType.REMOTE) {
//                if (uiStyle == 0) {
//                    MaterialReport.INSTANCE.reportAlbumVideoItemClick(item.dataSource == MediaItem.Source.COLLECT ? -5 : tabId, item.id, abTag, enterFrom, isHot, "preview", isSearch, item.biz_from);
//                }
//            }
//        }
//    }
//}
