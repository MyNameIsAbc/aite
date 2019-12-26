package com.example.jianancangku.view.adpter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.jianancangku.R;
import com.example.jianancangku.utils.SystemUtil;

/**
 * @Auther: valy
 * @datetime: 2019-11-23
 * @desc:
 */
public class BaseItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * 分割线高度
     */
    protected int mDivider;
    /**
     * 分割间距
     */
    protected int mSpace;
    /**
     * 第一行 间距
     */
    protected int mRowFirstSpace;
    /**
     * 第一列 间距
     */
    protected int mColumnFirstSpace;
    /**
     * 最后一行 间距
     */
    protected int mRowEndSpace;
    /**
     * 最后一列 间距
     */
    protected int mColumnEndSpace;
    /**
     * 分割线 颜色
     */
    @ColorInt
    protected int mDividerColor;
    /**
     * 上下文
     */
    protected Context mContext;
    /**
     * 列数
     */
    protected float mSpanCount;
    /**
     * itemView 宽高比
     * 格式{'w/h width:height'}
     *
     * @example {'w 1:1'}
     */
    protected String ratio;
    /**
     * 如果父布局是RecyclerView 给出父布局的 {@param mColumnFirstSpace} + {@param mColumnEndSpace}
     */
    private int mParentColumnSpace = 0;

    protected Paint mDividerPaint;
    // 第一列
    protected boolean hasFirstDivider;
    // 尾列
    protected boolean hasEndDivider;
    // 第一行
    protected boolean hasTopDivider;
    // 尾行
    protected boolean hasBottomDivider;

    public BaseItemDecoration setFirstDividerUsed(boolean b) {
        this.hasFirstDivider = b;
        return this;
    }

    public BaseItemDecoration setEndDividerUsed(boolean b) {
        this.hasEndDivider = b;
        return this;
    }

    public BaseItemDecoration setTopDividerUsed(boolean b) {
        this.hasTopDivider = b;
        return this;
    }

    public BaseItemDecoration setBottomDividerUsed(boolean b) {
        this.hasBottomDivider = b;
        return this;
    }

    protected RecyclerView.Adapter mAdapter;

    /**
     * LinearLayoutManger
     *
     * @param context
     * @see #mDivider 割线高度为1
     * @see #mDividerColor 划线颜色 #E6E6E6
     */
    public BaseItemDecoration(Context context) {
        this(SystemUtil.dp2px(context, 1)
                , 0, 0, 0, 0, 0
                , context.getResources().getColor(R.color.noglay), context
                , 1, null);
    }

    /**
     * LinearLayoutManger
     */
    public BaseItemDecoration(int mDivider, int mSpace, int mRowFirstSpace, int mColumnFirstSpace, int mRowEndSpace, int mColumnEndSpace, Context mContext) {
        this(mDivider, mSpace, mRowFirstSpace, mColumnFirstSpace, mRowEndSpace, mColumnEndSpace
                , mContext.getResources().getColor(R.color.noglay), mContext
                , 0, null);
    }

    /**
     * @param context
     * @see #mDividerColor 划线颜色 #E6E6E6
     */
    public BaseItemDecoration(int mSpace, Context context) {
        this(0, mSpace, 0, 0, 0, 0
                , context.getResources().getColor(R.color.noglay), context
                , 1, null);
    }

    public BaseItemDecoration(int mDivider, int mRowFirstSpace, Context context) {
        this(mDivider, 0, mRowFirstSpace, 0, 0, 0
                , context.getResources().getColor(R.color.noglay), context
                , 1, null);
    }

    /**
     * 横向LinearLayoutManger
     *
     * @param mDivider
     * @param mContext
     * @param mSpanCount
     * @param ratio
     */
    public BaseItemDecoration(int mDivider, Context mContext, float mSpanCount, String ratio) {
        this(0, mDivider, 0, 0, 0, 0
                , mContext.getResources().getColor(R.color.noglay), mContext
                , mSpanCount, ratio);
    }

    /**
     * GridLinearManager
     *
     * @param mContext
     * @param ratio
     */
    public BaseItemDecoration(Context mContext, String ratio) {
        this(0, SystemUtil.dp2px(mContext, 8)
                , 0, 0, 0, 0
                , 0, mContext
                , 1, ratio);
    }


    public BaseItemDecoration(int mDivider, int mSpace, int mRowFirstSpace, int mColumnFirstSpace, int mRowEndSpace, int mColumnEndSpace
            , int mDividerColor, Context mContext, float mSpanCount, String ratio) {
        this.mDivider = mDivider;
        this.mRowFirstSpace = mRowFirstSpace;
        this.mColumnFirstSpace = mColumnFirstSpace;
        this.mRowEndSpace = mRowEndSpace;
        this.mColumnEndSpace = mColumnEndSpace;
        this.mDividerColor = mDividerColor;
        this.mContext = mContext;
        this.mSpanCount = mSpanCount;
        this.ratio = ratio;
        this.mSpace = mSpace;

        hasFirstDivider = false;
        hasEndDivider = false;

        if (mDividerColor == 0)
            return;

        mDividerPaint = new Paint();
        mDividerPaint.setAntiAlias(true);
        mDividerPaint.setDither(true);
        mDividerPaint.setColor(mDividerColor);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        mAdapter = parent.getAdapter();
        Rect overRect = new Rect();
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        mSpanCount = getSpanCount(parent);
        if (mSpanCount <= 0)
            return;
        int childCount = parent.getAdapter().getItemCount();
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            int maxAllDividerWidth = 0; //
            try {
                maxAllDividerWidth = getMaxDividerWidth(view, parent);
            } catch (Exception e) {
                e.printStackTrace();
            }

            int eachItemWidth = (int) (maxAllDividerWidth / mSpanCount);//每个Item left+right  除首尾
            int dividerItemWidth;
            if (mSpanCount == 1) {
                dividerItemWidth = 0;
            } else {
                dividerItemWidth = (int) (maxAllDividerWidth / (mSpanCount - 1));//item与item之间的距离
            }

            if (isFirstColumn(parent, itemPosition, (int) mSpanCount)) {
                overRect.left = mColumnFirstSpace;
                overRect.right = eachItemWidth;
            } else if (isLastColumn(parent, itemPosition, (int) mSpanCount)) {
                overRect.left = (int) (itemPosition % mSpanCount * (dividerItemWidth - eachItemWidth));
                overRect.right = mColumnEndSpace;
            } else {
                overRect.left = (int) (itemPosition % mSpanCount * (dividerItemWidth - eachItemWidth));
                overRect.right = eachItemWidth - overRect.left;
            }
        } else if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            try {
                if (ratio != null && !ratio.isEmpty()) {
                    getMaxDividerWidth(view, parent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (((LinearLayoutManager) parent.getLayoutManager()).getOrientation() == RecyclerView.HORIZONTAL) {
                try {
                        getMaxDividerWidth(view, parent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (isFirstColumn(parent, itemPosition, (int) mSpanCount)) {
                    overRect.left = mColumnFirstSpace;
                    overRect.right = (mDivider + mSpace) / 2;
                } else if (isLastColumn(parent, itemPosition, (int) mSpanCount)) {
                    overRect.left = (mDivider + mSpace) / 2;
                    overRect.right = mColumnEndSpace;
                } else {
                    overRect.left = (mDivider + mSpace) / 2;
                    overRect.right = (mDivider + mSpace) / 2;
                }
            } else {
                overRect.left = mColumnFirstSpace;
                overRect.right = mColumnEndSpace;
            }
        }
        if (isFirstRow(parent, itemPosition)) {
            overRect.top = mRowFirstSpace;
        } else {
            overRect.top = (mDivider + mSpace) / 2;
        }
        if (isLastRow(parent, itemPosition, childCount)) {
            overRect.bottom = mRowEndSpace;
        } else {
            overRect.bottom = (mDivider + mSpace) / 2;
        }

//        LogHelper.e("       dividerItemWidth            " + dividerItemWidth
//                + "       eachItemWidth  " + eachItemWidth + "       " + mDivider + "       " + mSpace);
//        LogHelper.e("top  :   " + top + "      left  :  " + left + "       bottom  :  "
//                + bottom + "         right :  " + right + "    分割线：  "
//                + mDivider + "      间距    " + mSpace + "     view  " + itemPosition + "     spanCount      " + mSpanCount);

        configExtraSpace(itemPosition, childCount, overRect);

        if (parent.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            outRect.set(0, 0, 0, 0);
        } else {
            outRect.set(overRect);
        }
    }

    protected void configExtraSpace(int position, int count, Rect rect) {
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mDividerColor == 0 || mDivider == 0)
            return;
        int itemCount = parent.getChildCount();
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager) {
            for (int i = 0; i < itemCount; i++) {
                View child = parent.getChildAt(i);
                int right = child.getRight() + mSpace / 2;
                int left = child.getLeft() - mSpace / 2;
                int top = child.getTop() - (mSpace / 2);
                int bottom = child.getBottom() + (mSpace / 2);
                int rightOffset = mDivider;
                int bottomOffset = mDivider;
                // 第一行
                if (isFirstRow(parent, i)) {
                    top = child.getTop() - mSpace / 2;
                    if (hasTopDivider) {
                        c.drawRect(left, top, right, top + mDivider, mDividerPaint);
                    }
                }
                // 最后一行
                if (isLastRow(parent, i, itemCount)) {
                    bottom = child.getBottom() + mSpace / 2;
                    if (hasBottomDivider) {
                        c.drawRect(left, bottom, right, bottom + mDivider, mDividerPaint);
                    }
                    bottomOffset = 0;
                }
                // 第一列
                if (isFirstColumn(parent, i, (int) mSpanCount)) {
                    left = mColumnFirstSpace / 2;
                    if (hasFirstDivider) {
                        c.drawRect(left, top, left + mDivider, bottom, mDividerPaint);
                    }
                }
                // 最后一列
                if (isLastColumn(parent, i, (int) mSpanCount)) {
                    right = child.getRight() + mColumnEndSpace / 2;
                    if (hasEndDivider) {
                        c.drawRect(right, top, right + mDivider, bottom, mDividerPaint);
                    }
                    rightOffset = 0;
                }
                c.drawRect(left, bottom, right, bottom + bottomOffset, mDividerPaint);// bottom line
                c.drawRect(right, top, right + rightOffset, bottom, mDividerPaint); // right line
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            if (((LinearLayoutManager) layoutManager).getOrientation() == RecyclerView.VERTICAL) {
                Rect rect = new Rect();
                for (int i = 0; i < itemCount; i++) {
                    if (hasTopDivider) {
                        if (i == 0 && ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition() == 0) {
                            c.drawRect(0, 0, parent.getMeasuredWidth(), mRowFirstSpace, mDividerPaint);
                        }
                    }
                    View child = parent.getChildAt(i);
//                    rect.left = mColumnFirstSpace;
//                    rect.right = parent.getMeasuredWidth() - mColumnEndSpace;
                    rect.left = 0;
                    rect.right = parent.getMeasuredWidth();
                    rect.top = child.getBottom() + (mSpace + mDivider) / 2;
                    rect.bottom = rect.top + mDivider;

                    if (i == itemCount - 1 && ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition() == itemCount - 1) {
                        if (hasBottomDivider) {
                            c.drawRect(rect, mDividerPaint);
                        }
                    } else if (i != itemCount - 1) {
                        doRule(i, rect);
                        c.drawRect(rect, mDividerPaint);
                    }
                    drawText(i, c, child);
                }
            } else if (((LinearLayoutManager) layoutManager).getOrientation() == RecyclerView.HORIZONTAL) {
                for (int i = 0; i < itemCount; i++) {
                    View child = parent.getChildAt(i);
                    int left = (int) ((i % mSpanCount + 1) * (child.getWidth() + (mSpace + mDivider) / 2) + mColumnFirstSpace);
                    int top = child.getTop();
                    if (isFirstRow(parent, i)) {
                        top += mRowFirstSpace;
                    }
                    int bottom = top + mSpace + mDivider + child.getHeight();
                    if (isLastRow(parent, i, itemCount)) {
                        bottom = top + child.getHeight() + mRowEndSpace;
                    }
                    if (hasFirstDivider) {
                        if (i == 0 && ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition() == 0) {
                            c.drawRect(left, top, mDivider, bottom, mDividerPaint);
                        }
                    }
                    if (i == itemCount - 1 && ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition() == itemCount - 1) {
                        if (hasEndDivider) {
                            c.drawRect(left, top, mDivider, bottom, mDividerPaint);
                        }
                    } else if (i != itemCount - 1) {
                        c.drawRect(left, top, mDivider, bottom, mDividerPaint);
                    }

                }
            }
        }
    }

    protected void drawText(int i, Canvas c, View child) {

    }

    protected void doRule(int i, Rect rect) {

    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    /**
     * 获取Item View的大小，若无则自动分配空间
     * 并根据 屏幕宽度-View的宽度*spanCount 得到屏幕剩余空间
     *
     * @param view
     * @return
     */
    protected int getMaxDividerWidth(View view, RecyclerView parent) throws Exception {
        int itemWidth = view.getLayoutParams().width;
        int itemHeight = view.getLayoutParams().height;
        int parentMargin = computeParentMarging(parent, 0) + mParentColumnSpace;

        int screenWidth = (mContext.getResources().getDisplayMetrics().widthPixels > mContext.getResources().getDisplayMetrics().heightPixels
                ? mContext.getResources().getDisplayMetrics().heightPixels : mContext.getResources().getDisplayMetrics().widthPixels) - parentMargin;
        int maxDividerWidth = (int) (screenWidth - itemWidth * mSpanCount - mColumnFirstSpace - mColumnEndSpace);
        if (itemHeight < 0 || itemWidth < 0 || (mDivider + mSpace > 0 && maxDividerWidth != (mSpanCount - 1) * (mDivider + mSpace))) {
            if (ratio != null && !ratio.isEmpty()) {
                String type = ratio;
                String rateString = ratio;
                if (ratio.contains(" ")) {
                    rateString = rateString.substring(type.indexOf(" ") + 1);
                    type = type.substring(0, type.indexOf(" "));
                } else {
                    type = "w";
                }
                if (type.equalsIgnoreCase("w")) {
                    String[] location = rateString.split(":");
                    if (location.length < 1) {
                        throw new Exception("使用错误");
                    }
                    float rate = Float.parseFloat(location[1]) / Float.parseFloat(location[0]);
                    view.getLayoutParams().width = getAttachColumnWidth(parentMargin);
                    view.getLayoutParams().height = (int) (getAttachColumnWidth(parentMargin) * rate);
                } else {
                    String[] location = rateString.split(":");
                    if (location.length < 1) {
                        throw new Exception("使用错误");
                    }
                    float rate = Float.parseFloat(location[0]) / Float.parseFloat(location[1]);
                    view.getLayoutParams().width = getAttachColumnWidth(parentMargin);
                    view.getLayoutParams().height = (int) (getAttachColumnWidth(parentMargin) * rate);
                }
            } else {
                if (parent.getLayoutManager() instanceof GridLayoutManager) {
                    view.getLayoutParams().width = getAttachColumnWidth(parentMargin);
                    view.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                } else if (parent.getLayoutManager() instanceof LinearLayoutManager) {
                    if (((LinearLayoutManager) parent.getLayoutManager()).getOrientation() == RecyclerView.HORIZONTAL) {
                        view.getLayoutParams().width = getAttachColumnWidth(parentMargin);
                    }
                }
            }
            maxDividerWidth = (int) (screenWidth - view.getLayoutParams().width * mSpanCount - mColumnFirstSpace - mColumnEndSpace);
        }
//        LogHelper.e("    maxDividerWidth       " + maxDividerWidth + "       " + view.getLayoutParams().width
//                + "        " + mSpanCount + "      " + screenWidth + "     " + mDivider + "       " + mSpace + "  " + ratio+"     "+parentMargin);
        return maxDividerWidth;
    }

    public BaseItemDecoration setParentColumnSpace(int mParentColumnSpace) {
        this.mParentColumnSpace = mParentColumnSpace;
        return this;
    }

    private int computeParentMarging(View parent, int margin) {

        if (parent.getParent() instanceof LinearLayout) {
            LinearLayout view = (LinearLayout) parent.getParent();
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) parent.getLayoutParams();
            return margin + view.getPaddingLeft() + view.getPaddingRight() + params.leftMargin + params.rightMargin + computeParentMarging(view, margin);
        } else if (parent.getParent() instanceof RelativeLayout) {
            RelativeLayout view = (RelativeLayout) parent.getParent();
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) parent.getLayoutParams();
            return margin + view.getPaddingLeft() + view.getPaddingRight() + params.leftMargin + params.rightMargin + computeParentMarging(view, margin);
        } else if (parent.getParent() instanceof FrameLayout) {
            FrameLayout view = (FrameLayout) parent.getParent();
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) parent.getLayoutParams();
            return margin + view.getPaddingLeft() + view.getPaddingRight() + params.leftMargin + params.rightMargin + computeParentMarging(view, margin);
        } else if (parent.getParent() instanceof ConstraintLayout) {
            ConstraintLayout view = (ConstraintLayout) parent.getParent();
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) parent.getLayoutParams();
            int left = view.getPaddingLeft();
            int right = view.getPaddingRight();
            left += params.leftMargin;
            right += params.rightMargin;
            if (params.leftToLeft > 0) {
                View view1 = view.findViewById(params.leftToLeft);
                left += ((ConstraintLayout.LayoutParams) view1.getLayoutParams()).leftMargin;
            }
            if (params.leftToRight > 0) {
                View view1 = view.findViewById(params.leftToRight);
                left += view1.getRight();
            }
            if (params.rightToRight > 0) {
                View view2 = view.findViewById(params.rightToRight);
                right += ((ConstraintLayout.LayoutParams) view2.getLayoutParams()).rightMargin;
            }
            if (params.rightToLeft > 0) {
                View view2 = view.findViewById(params.rightToLeft);
                right += view2.getRight() - view2.getLeft();
            }
            return margin + left + right + computeParentMarging(view, margin);
        } else {
            return margin;
        }
    }

    /**
     * 根据屏幕宽度和item数量分配 item View的width和height
     *
     * @param parentMargin
     * @return
     */
    protected int getAttachColumnWidth(int parentMargin) {
        int itemWidth = 0;
        try {
            int width = (mContext.getResources().getDisplayMetrics().widthPixels > mContext.getResources().getDisplayMetrics().heightPixels
                    ? mContext.getResources().getDisplayMetrics().heightPixels : mContext.getResources().getDisplayMetrics().widthPixels) - parentMargin;
            itemWidth = (int) ((width - (mDivider + mSpace) * (mSpanCount - 1) - mColumnFirstSpace - mColumnEndSpace) / mSpanCount);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return itemWidth;
    }

    /**
     * 判读是否是第一列
     *
     * @param parent
     * @param pos
     * @param spanCount
     * @return
     */
    protected boolean isFirstColumn(RecyclerView parent, int pos, int spanCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if (pos % spanCount == 0) {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if (pos % spanCount == 0) {// 第一列
                    return true;
                }
            } else {

            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            if (((LinearLayoutManager) layoutManager).getOrientation() == RecyclerView.HORIZONTAL) {
                if (pos == 0)
                    return true;
            } else
                return true;
        }
        return false;
    }

    /**
     * 判断是否是最后一列
     *
     * @param parent
     * @param pos
     * @param spanCount
     * @return
     */
    protected boolean isLastColumn(RecyclerView parent, int pos, int spanCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if ((pos + 1) % spanCount == 0) {// 如果是最后一列，则不需要绘制右边
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 0) {// 最后一列
                    return true;
                }
            } else {

            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            if (((LinearLayoutManager) layoutManager).getOrientation() == RecyclerView.HORIZONTAL) {
                if (pos == parent.getAdapter().getItemCount() - 1) {
                    return true;
                } else {
                    return false;
                }
            } else
                return true;
        }
        return false;
    }

    /**
     * 判读是否是最后一行
     *
     * @param pos
     * @param childCount
     * @return
     */
    protected boolean isLastRow(RecyclerView parent, int pos, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int lines = childCount % (int) mSpanCount == 0 ? childCount / (int) mSpanCount : childCount / (int) mSpanCount + 1;
            return lines == pos / (int) mSpanCount + 1;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int lines = childCount % (int) mSpanCount == 0 ? childCount / (int) mSpanCount : childCount / (int) mSpanCount + 1;
            return lines == pos / (int) mSpanCount + 1;
        } else if (layoutManager instanceof LinearLayoutManager) {
            if (((LinearLayoutManager) layoutManager).getOrientation() == RecyclerView.HORIZONTAL) {
                return true;
            } else {
                int lines = childCount % (int) mSpanCount == 0 ? childCount / (int) mSpanCount : childCount / (int) mSpanCount + 1;
                return lines == pos / (int) mSpanCount + 1;
            }
        }
        return false;
    }

    /**
     * 判断是否是第一行
     *
     * @param pos
     * @return
     */
    protected boolean isFirstRow(RecyclerView parent, int pos) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return (pos / (int) mSpanCount + 1) == 1;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            return (pos / (int) mSpanCount + 1) == 1;
        } else if (layoutManager instanceof LinearLayoutManager) {
            if (((LinearLayoutManager) layoutManager).getOrientation() == RecyclerView.HORIZONTAL) {
                return true;
            } else {
                return (pos / (int) mSpanCount + 1) == 1;
            }
        }
        return false;
    }

    /**
     * 获取列数
     *
     * @param parent
     * @return
     */
    protected float getSpanCount(RecyclerView parent) {
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof LinearLayoutManager) {
            if (((LinearLayoutManager) layoutManager).getOrientation() == RecyclerView.HORIZONTAL) {
                return mSpanCount;
            } else {
                spanCount = 1;
            }
        }
        return spanCount;
    }

}
