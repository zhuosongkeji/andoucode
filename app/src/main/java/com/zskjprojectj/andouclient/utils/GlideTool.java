package com.zskjprojectj.andouclient.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.zskjprojectj.andouclient.R;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.Nullable;

public class GlideTool {
    public static final int NONE = -1;

    /**
     * glide加载grideview的图片（所有的图片都使用居中裁剪）
     *
     * @param context
     * @param uri
     * @param view
     */
    public static void glide(Context context, Object uri, ImageView view) {
        glide(context, uri, view, NONE, R.mipmap.icon_center_browsing_traces);
    }

    public static void glide(Context context, Object uri, ImageView view, float thumbnail, @DrawableRes int resultId) {
        glideBuilder(context, uri, true, NONE, NONE, thumbnail, NONE, null).into(view);
    }

    /**
     * 手动加载指定大小的图片
     *
     * @param context
     * @param uri
     * @param view
     * @param x
     * @param y
     */
    public static void glideThumbnail(Context context, String uri, ImageView view, int x, int y) {
        glideBuilder(context, uri, true, R.mipmap.ic_ad_1, R.mipmap.ic_ad_1, NONE, NONE, null).into(view);
    }

    /**
     * 加载圆角图片
     *
     * @param context
     * @param uri
     * @param view
     */
    public static void glideRoundImg(Context context, String uri, ImageView view) {
        glideRoundImg(context, uri, view, 5);
    }

    public static void glideRoundImg(Context context, String uri, ImageView view, int radius) {
        glideRoundImg(context, uri, view, radius, GlideRoundTransform.CornerType.ALL);
    }

    private static void glideRoundImg(Context context, String uri, ImageView view, int radius, GlideRoundTransform.CornerType cornerType) {
        glideBuilder(context, uri, true, NONE,NONE, NONE, radius, cornerType).into(view);
    }

    /**
     * 加载原图z
     *
     * @param context
     * @param uri
     * @param view
     */
    public static void glideNoCrop(Context context, String uri, ImageView view) {
        glideNoCrop(context, uri, view, null);
    }

    public static void glideNoCrop(Context context, String uri, ImageView view, RequestListener<Drawable> listener) {
        glideBuilder(context, uri, false, NONE, R.mipmap.icon_baidu, NONE, NONE, null, listener).into(view);
    }

    public static void glideNoCropNoDis(Context context, String uri, ImageView view) {
        //Glide.with(context).load(uri).error(R.mipmap.ic_placeholder).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(view);
    }

    /**
     * glide加载头像
     *
     * @param context
     * @param uri
     * @param view
     */
    public static void glideHead(Context context, String uri, ImageView view) {
//        glideBuilder(context, uri, false, NONE, R.mipmap.ic_launcher, 0.6f, NONE, null).into(view);
    }

    public static String getPath(String path, ImageView view) {
        int x = view.getMeasuredWidth();
        int y = view.getMeasuredHeight();
        return getPath(path, x, y);
    }

    public static String getPath(String path, int x, int y) {
        if (TextUtils.isEmpty(path)) return "0";
        if (!TextUtils.isEmpty(path) && (path.startsWith("http://") || path.startsWith("https://"))) {
            path = path.substring(0, path.lastIndexOf(".")) + "_" + x + "_" + y + path.substring(path.lastIndexOf("."), path.length());
            return path;
        } else if (!TextUtils.isEmpty(path) && path.startsWith("file:")) {
            return path;
        } else {
            return path;
        }
    }

    public static class GetImageCacheAsyncTask extends AsyncTask<List<String>, Void, List<String>> {
        private final Context context;

        public GetImageCacheAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected List<String> doInBackground(List<String>[] lists) {
            List<String> strings = new ArrayList<>();
            for (int i = 0; i < lists[0].size(); i++) {
                try {
                    String u = lists[0].get(i);
                    strings.add(Glide.with(context)
                            .load(u)
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get().getPath());
                } catch (Exception ex) {
                    return null;
                }
            }
            return strings;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            if (result == null || result.size() == 0) {
                return;
            }
            if (getImageCachePathListener != null)
                getImageCachePathListener.getPath(result);
        }

        private GetImageCachePathListener getImageCachePathListener;

        public void setGetImageCachePathListener(GetImageCachePathListener getImageCachePathListener) {
            this.getImageCachePathListener = getImageCachePathListener;
        }

        public interface GetImageCachePathListener {
            void getPath(List<String> list);
        }
    }

    // =======================================================

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final GlideTool INSTANCE = new GlideTool();
    }

    //获取单例
    public static GlideTool getInstance() {
        return SingletonHolder.INSTANCE;
    }

    RequestListener<Drawable> listener = new RequestListener<Drawable>() {

        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            return false;
        }
    };

    private static RequestBuilder<Drawable> glideBuilder(Context context, Object url, boolean isCenterCrop, int placeRes, int errorRes, float thumbnail, int round, GlideRoundTransform.CornerType cornerType) {
        return glideBuilder(context, url, isCenterCrop, placeRes, errorRes, thumbnail, round, cornerType, null);
    }

    private static RequestBuilder<Drawable> glideBuilder(Context context, Object url, boolean isCenterCrop, int placeRes, int errorRes, float thumbnail, int round, GlideRoundTransform.CornerType cornerType, RequestListener<Drawable> listener) {
        if (url==null) {
            url = "";
        }

        RequestBuilder<Drawable> builder = Glide.with(context).load(url);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.timeout(10 * 1000);
        //requestOptions.fallback(R.mipmap.banner_bg);
        if (placeRes != NONE) {
            requestOptions.dontAnimate();
            requestOptions.placeholder(placeRes);
        }
        if (errorRes != NONE) {
            requestOptions.error(errorRes);
        }
        if (isCenterCrop) {
            requestOptions.centerCrop();
        }
        if (listener != null)
            builder.listener(listener);

        if (thumbnail != NONE) {
            builder.thumbnail(thumbnail);
        }

        if (round != NONE) {
            if (cornerType == null) {
                if (isCenterCrop) {
                    requestOptions.bitmapTransform(new GlideRoundTransform(context, round, 0, GlideRoundTransform.CornerType.ALL));
                } else
                    requestOptions.bitmapTransform(new GlideRoundTransform(context, round, 0, GlideRoundTransform.CornerType.ALL));
            } else {
                if (isCenterCrop) {
                    requestOptions.bitmapTransform(new GlideRoundTransform(context, round, 0, cornerType));
                } else
                    requestOptions.bitmapTransform(new GlideRoundTransform(context, round, 0, cornerType));
            }
        }
        builder.apply(requestOptions);
        return builder;
    }
}
