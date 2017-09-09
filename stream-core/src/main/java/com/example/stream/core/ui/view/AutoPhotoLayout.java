package com.example.stream.core.ui.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.stream.core.R;
import com.example.stream.core.delegates.StreamDelegate;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by StReaM on 9/9/2017.
 */

public class AutoPhotoLayout extends LinearLayoutCompat {

    // 目前第几张图片
    private int mCurrentImage = 0;

    // 最大允许多少张图片
    private int mTotalImages = 0;

    // 一行里多少张图片
    private int mNumberOfImagesPerLine = 0;
    private IconTextView mIconAdd = null;
    private LayoutParams mLayoutParams = null;

    private int mDeleteId = 0;
    private AppCompatImageView mTargetImageView = null;
    private int mImageMargin = 0;
    private StreamDelegate mDelegate = null;
    private List<View> mLineViews = null;
    private AlertDialog mTargetDialog = null;
    private static final String ICON_TEXT = "{fa-plus}";
    private float mIconSize = 0;

    private boolean mMeasured = false;
    private boolean mLayouted = false;
    private static final RequestOptions OPTIONS = new RequestOptions()
            .centerCrop()
            .dontAnimate();

    private static final List<List<View>> ALL_VIEWS = new ArrayList<>();
    private static final List<Integer> LINE_HEIGHTS = new ArrayList<>();

    public AutoPhotoLayout(Context context) {
        this(context, null);
    }

    public AutoPhotoLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoPhotoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.camera_flow_layout);

        mTotalImages = typedArray.getInt(R.styleable.camera_flow_layout_total_images, 1);
        mImageMargin = typedArray.getInt(R.styleable.camera_flow_layout_item_margin, 0);
        mNumberOfImagesPerLine = typedArray.getInt(R.styleable.camera_flow_layout_line_count, 3);
        mIconSize = typedArray.getDimension(R.styleable.camera_flow_layout_icon_size, 20);

        typedArray.recycle();
    }

    public void setDelegate(StreamDelegate delegate) {
        mDelegate = delegate;
    }

    public final void onCrop(Uri uri) {
        createNewImageView();
        Glide.with(mDelegate)
                .load(uri)
                .apply(OPTIONS)
                .into(mTargetImageView);
    }

    private void createNewImageView() {
        mTargetImageView = new AppCompatImageView(getContext());
        mTargetImageView.setId(mCurrentImage);
        mTargetImageView.setLayoutParams(mLayoutParams);
        mTargetImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mDeleteId = view.getId();
                mTargetDialog.show();
                final Window window = mTargetDialog.getWindow();
                if (window != null) {
                    window.setContentView(R.layout.dialog_image_click_panel);
                    window.setGravity(Gravity.BOTTOM);
                    window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    WindowManager.LayoutParams layoutParams = window.getAttributes();
                    layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                    layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                    layoutParams.dimAmount = 0.5f;
                    window.setAttributes(layoutParams);
                    window.findViewById(R.id.dialog_image_clicked_btn_delete)
                            .setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    AppCompatImageView imageViewToDelete = (AppCompatImageView)
                                            findViewById(mDeleteId);
                                    AlphaAnimation animation = new AlphaAnimation(1, 0);
                                    animation.setDuration(500);
                                    animation.setRepeatCount(0);
                                    animation.setFillAfter(true);
                                    animation.setStartOffset(0);
                                    imageViewToDelete.setAnimation(animation);
                                    animation.start();
                                    AutoPhotoLayout.this.removeView(imageViewToDelete);
                                    mCurrentImage--;

                                    if (mCurrentImage < mTotalImages) {
                                        mIconAdd.setVisibility(VISIBLE);
                                    }
                                    mTargetDialog.cancel();
                                }
                            });
                    window.findViewById(R.id.dialog_image_clicked_btn_cancel)
                            .setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mTargetDialog.cancel();
                                }
                            });
                    window.findViewById(R.id.dialog_image_clicked_btn_undetermined)
                            .setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mTargetDialog.cancel();
                                }
                            });
                }
            }
        });
        this.addView(mTargetImageView, mCurrentImage);
        mCurrentImage++;
        if (mCurrentImage >= mTotalImages) {
            mIconAdd.setVisibility(GONE);
        }
    }

    private void initIcon () {
        mIconAdd = new IconTextView(getContext());
        mIconAdd.setText(ICON_TEXT);
        mIconAdd.setGravity(Gravity.CENTER);
        mIconAdd.setBackgroundColor(Color.WHITE);
        mIconAdd.setTextSize(mIconSize);
        mIconAdd.setBackgroundResource(R.drawable.border);
        mIconAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mDelegate.startCameraWithCheck();
            }
        });
        addView(mIconAdd);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initIcon();
        mTargetDialog = new AlertDialog.Builder(getContext()).create();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        ALL_VIEWS.clear();
        LINE_HEIGHTS.clear();
        final int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;

        if (!mLayouted) {
            mLineViews = new ArrayList<>();
            mLayouted = true;
        }
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            final MarginLayoutParams mlp = (MarginLayoutParams) child.getLayoutParams();
            final int childWidth = child.getMeasuredWidth();
            final int childHeight = child.getMeasuredHeight();
            // need change line
            if (childWidth + lineWidth + mlp.leftMargin + mlp.rightMargin >
                    width - getPaddingRight() - getPaddingLeft()) {
                LINE_HEIGHTS.add(lineHeight);
                ALL_VIEWS.add(mLineViews);
                lineWidth = 0;
                lineHeight = childHeight + mlp.bottomMargin + mlp.topMargin;
                mLineViews.clear();
            }
            lineWidth += childWidth + mlp.leftMargin + mlp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + mlp.bottomMargin + mlp.topMargin);
        }
        // last line
        LINE_HEIGHTS.add(lineHeight);
        ALL_VIEWS.add(mLineViews);

        int left = getPaddingLeft();
        int top = getPaddingTop();

        final int lineNum = ALL_VIEWS.size();
        for(int i = 0; i < lineNum; i++) {
            mLineViews = ALL_VIEWS.get(i);
            lineHeight = LINE_HEIGHTS.get(i);
            final int size = mLineViews.size();
            for(int j = 0; j < size; j++) {
                final View child = mLineViews.get(j);
                if (child.getVisibility() == GONE) {
                    continue;
                }
                final MarginLayoutParams mlp = (MarginLayoutParams) child.getLayoutParams();
                final int lp = left + mlp.leftMargin;
                final int tp = top + mlp.topMargin;
                final int rp = lp +  child.getMeasuredWidth() - mImageMargin;
                final int bp = tp + child.getMeasuredHeight();
                child.layout(lp, tp, rp, bp);
                left += child.getMeasuredWidth() + mlp.leftMargin + mlp.rightMargin;
            }
            left = getPaddingLeft();
            top += lineHeight;
        }
        mIconAdd.setLayoutParams(mLayoutParams);
        mLayouted = false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        // wrap_content
        int width = 0;
        int height = 0;
        // mark width and height every line
        int lineWidth = 0;
        int lineHeight = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            // measure
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            final MarginLayoutParams mlp = (MarginLayoutParams) child.getLayoutParams();
            final int childWidth = child.getMeasuredWidth() + mlp.leftMargin + mlp.rightMargin;
            final int childHeight = child.getMeasuredHeight() + mlp.topMargin + mlp.bottomMargin;
            // // change line
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                width = Math.max(width, lineWidth);
                // re initialize lineWidth
                lineWidth = childWidth;
                // increase view height
                height += lineHeight;
                // re initialize lineHeight
                lineHeight = childHeight;
            } else {
                // don't change line
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            // if it was last and didn't change line last time
            if (i == childCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }
        setMeasuredDimension(
                modeWidth == MeasureSpec.EXACTLY ?
                sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                modeHeight == MeasureSpec.EXACTLY ?
                sizeHeight: height + getPaddingTop() + getPaddingBottom()
        );
        // space between images
        final int imageSpace = sizeWidth / mNumberOfImagesPerLine;
        if (!mMeasured) {
            mLayoutParams = new LayoutParams(imageSpace, imageSpace);
            mMeasured = true;
        }
    }
}
