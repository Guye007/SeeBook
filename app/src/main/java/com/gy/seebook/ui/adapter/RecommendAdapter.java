package com.gy.seebook.ui.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Observable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.anarchy.classify.adapter.BaseMainAdapter;
import com.anarchy.classify.adapter.BaseSubAdapter;
import com.anarchy.classify.simple.PrimitiveSimpleAdapter;
import com.anarchy.classify.simple.widget.InsertAbleGridView;
import com.gy.seebook.Constants;
import com.gy.seebook.R;
import com.gy.seebook.bean.RecommendBookData;
import com.gy.seebook.bean.RecommendBookGroup;
import com.gy.seebook.view.RecommendFolder;
import com.gy.seebook.view.RecommendGridLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.kit.KnifeKit;
import cn.droidlover.xdroidmvp.log.LogFormat;
import cn.droidlover.xdroidmvp.log.XLog;

/**
 * @name SeeBook
 * @class name：com.gy.seebook.ui.adapter
 * Author: apple   QQ:954401985
 * Email: guyework@163.com
 * Comment: //TODO
 * Date: 2017-08-22 10:01
 */

public class RecommendAdapter extends PrimitiveSimpleAdapter<RecommendBookGroup, RecommendAdapter.ViewHolder> {

    private final static String RecommendAdapterLog = "RecommendAdapterLog";

    private List<RecommendBookData> mMockSource;
    private boolean mMockSourceChanged;
    private List<RecommendBookGroup> mLastMockGroup;
    private List<RecommendBookData> mCheckedData = new ArrayList<>();
    private boolean mEditMode;
    private boolean mSubEditMode;
    private int[] mDragPosition = new int[2];
    private RecommendObservable mObservable = new RecommendObservable();
    private SubObserver mSubObserver = new SubObserver(mObservable);
    private DialogInterface.OnDismissListener mDismissListener = new DialogInterface.OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialog) {
            if (mObservable.isRegister(mSubObserver)) mObservable.unregisterObserver(mSubObserver);
            mSubEditMode = false;
        }
    };


    public void registerObserver(RecommendObserver observer) {
        mObservable.registerObserver(observer);
    }


    public List<RecommendBookData> getMockSource() {
        return mMockSource;
    }

    public void setMockSource(List<RecommendBookData> mockSource) {
        mMockSource = mockSource;
        notifyDataSetChanged();
    }


    @Override
    protected void onDragStart(ViewHolder viewHolder, int parentIndex, int index) {
        if (!mEditMode) {
            //如果当前不为可编辑状态
            RecommendBookData mockData = index == -1 ? mMockSource.get(parentIndex) : ((RecommendBookGroup) mMockSource.get(parentIndex)).getChild(index);
            if (mockData != null) {
                mockData.setChecked(true);
                mCheckedData.add(mockData);
                mObservable.notifyItemCheckChanged(true);
                viewHolder.recommendFolderCheckBox.setVisibility(View.VISIBLE);
                viewHolder.recommendFolderCheckBox.setBackgroundResource(R.drawable.ic_checked);
            }
        }
    }

    @Override
    protected void onDragAnimationEnd(ViewHolder viewHolder, int parentIndex, int index) {
        if (!mEditMode) {
            setEditMode(true);
        }
    }

    @Override
    protected void onSubDialogShow(Dialog dialog, int parentPosition) {
        dialog.setOnDismissListener(mDismissListener);
        //当次级窗口显示时需要修改标题
        final ViewGroup contentView = (ViewGroup) dialog.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        final TextView selectAll = (TextView) contentView.findViewById(R.id.text_select_all);
        TextView title = (TextView) contentView.findViewById(R.id.text_title);
        final EditText editText = (EditText) contentView.findViewById(R.id.edit_title);
        FrameLayout subContainer = (FrameLayout) contentView.findViewById(R.id.sub_container);
        final RecommendBookGroup mockDataGroup = (RecommendBookGroup) mMockSource.get(parentPosition);
        mSubObserver.setBindResource(mockDataGroup, selectAll, getMainAdapter(), getSubAdapter(), parentPosition);
        if (!mObservable.isRegister(mSubObserver)) mObservable.registerObserver(mSubObserver);
        selectAll.setVisibility(mEditMode ? mSubEditMode ? View.GONE : View.VISIBLE : View.GONE);
        title.setText(String.valueOf(mockDataGroup.getCategory()));
        /*if(Build.VERSION.SDK_INT >= 19) {
            title.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    mSubEditMode = true;
                    selectAll.setVisibility(View.GONE);
                    editText.setText(String.valueOf(mockDataGroup.getCategory()));
                    editText.setSelection(0,editText.getText().toString().length());
                    int originWidth = editText.getWidth();
                    editText.setWidth(0);
                    TransitionManager.beginDelayedTransition(contentView);
                    editText.setWidth(originWidth);
                }
            });
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    switch (actionId){
                        case KeyEvent.KEYCODE_ENTER:
                            break;
                    }
                    return false;
                }
            });
        }*/

    }

    static class SubObserver extends RecommendObserver {
        final RecommendObservable mObservable;
        RecommendBookGroup mGroup;
        TextView selectAll;
        BaseSubAdapter mSubAdapter;
        BaseMainAdapter mMainAdapter;
        int parentPosition;
        boolean mLastIsAllSelect;

        SubObserver(@NonNull RecommendObservable observable) {
            mObservable = observable;
        }

        View.OnClickListener allSelectListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int childCount = mGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    RecommendBookData child = mGroup.getChild(i);
                    if (!child.isChecked()) {
                        child.setChecked(true);
                        mObservable.notifyItemCheckChanged(true);
                    }
                }
                mSubAdapter.notifyDataSetChanged();
                mMainAdapter.notifyItemChanged(parentPosition);
            }
        };
        View.OnClickListener cancelSelectListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int childCount = mGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    RecommendBookData child = mGroup.getChild(i);
                    if (child.isChecked()) {
                        child.setChecked(false);
                        mObservable.notifyItemCheckChanged(false);
                    }
                }
                mSubAdapter.notifyDataSetChanged();
                mMainAdapter.notifyItemChanged(parentPosition);
            }
        };

        void setBindResource(RecommendBookGroup source, TextView bindView, BaseMainAdapter mainAdapter, BaseSubAdapter subAdapter, int parentPosition) {
            mGroup = source;
            selectAll = bindView;
            mSubAdapter = subAdapter;
            mMainAdapter = mainAdapter;
            this.parentPosition = parentPosition;
            updateBind(true);
        }


        @Override
        public void onChecked(boolean isChecked) {
            updateBind(false);
        }

        private void updateBind(boolean force) {
            boolean isAllSelect = mGroup.getChildCount() == mGroup.getCheckedCount();
            if (force) {
                updateBindInternal(isAllSelect);
                return;
            }
            if (mLastIsAllSelect != isAllSelect) {
                updateBindInternal(isAllSelect);
            }
        }

        private void updateBindInternal(boolean isAllSelect) {
            mLastIsAllSelect = isAllSelect;
            selectAll.setText(isAllSelect ? "取消" : "全选");
            selectAll.setOnClickListener(isAllSelect ? cancelSelectListener : allSelectListener);
        }

    }

    /**
     * 返回当前拖拽的view 在adapter中的位置
     *
     * @return 返回int[0] 主层级位置 如果为 -1 则当前没有拖拽的item
     * int[1] 副层级位置 如果为 -1 则当前没有拖拽副层级的item
     */
    @NonNull
    public int[] getCurrentDragAdapterPosition() {
        mDragPosition[0] = getMainAdapter().getDragPosition();
        mDragPosition[1] = getSubAdapter().getDragPosition();
        return mDragPosition;
    }

    /**
     * @return 如果当前拖拽的为单个书籍 则返回 其他情况返回null
     */
    @Nullable
    RecommendBookData getCurrentSingleDragData() {
        int[] position = getCurrentDragAdapterPosition();
        if (position[0] == -1) return null;
        if (position[1] == -1) {
            RecommendBookData mockData = mMockSource.get(position[0]);
            if (mockData instanceof RecommendBookGroup) return null;
            return mockData;
        } else {
            return ((RecommendBookGroup) mMockSource.get(position[0])).getChild(position[1]);
        }
    }

    public void removeAllCheckedBook() {
        if (mCheckedData.size() == 0) return;
        XLog.e("size===",mCheckedData.size()+"");
        for (RecommendBookData data : mCheckedData) {

            if (data.getParent() != null) {
                RecommendBookGroup parent = data.getParent();
                parent.removeChild(data);
                if (parent.getChildCount() == 0) {
                    mMockSource.remove(parent);
                }
            } else {
                mMockSource.remove(data);
            }
        }
        notifyDataSetChanged();
        getSubAdapter().notifyDataSetChanged();
        mObservable.notifyItemRestore();
        mObservable.notifyItemHideSubDialog();
    }

    /**
     * 添加数据
     *
     * @param mockData
     */
    public void addBook(RecommendBookData mockData) {
        if (mMockSource == null) mMockSource = new ArrayList<>();
        mMockSource.add(0, mockData);
        notifyItemInsert(0);
    }

    /**
     * 设置是否在可编辑状态下
     *
     * @param editMode
     */
    public void setEditMode(boolean editMode) {
        mEditMode = editMode;
        if (!editMode) {
            if (mCheckedData.size() > 0) {
                for (RecommendBookData data : mCheckedData) {
                    data.setChecked(false);
                }
                mCheckedData.clear();
            }
            mObservable.notifyItemRestore();
        }
        notifyDataSetChanged();
        getSubAdapter().notifyDataSetChanged();
        mObservable.notifyItemEditModeChanged(editMode);
    }

    public boolean isEditMode() {
        return mEditMode;
    }

    public List<RecommendBookGroup> getMockGroup() {
        if (mMockSource == null) return null;
        if (mLastMockGroup != null && !mMockSourceChanged) {
            return mLastMockGroup;
        } else {
            List<RecommendBookGroup> result = new ArrayList<>();
            for (RecommendBookData mockData : mMockSource) {
                if (mockData instanceof RecommendBookGroup) {
                    result.add((RecommendBookGroup) mockData);
                }
            }
            mMockSourceChanged = false;
            mLastMockGroup = result;
            return result;
        }
    }


    /**
     * 创建view holder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    protected ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend_book, parent, false);
        return new ViewHolder(view);
    }

    /**
     * 用于显示{@link InsertAbleGridView} 的item布局
     *
     * @param parent       父View
     * @param convertView  缓存的View 可能为null
     * @param mainPosition 主层级位置
     * @param subPosition  副层级位置
     * @return
     */
    @Override
    public View getView(ViewGroup parent, View convertView, int mainPosition, int subPosition) {
        View result;
        if (convertView != null) {
            result = convertView;
        } else {
            result = new View(parent.getContext());
        }
        try {
            int color = ((RecommendBookGroup) mMockSource.get(mainPosition)).getChild(subPosition).getColor();
            result.setBackgroundColor(color);
        } catch (Exception e) {
            //something wrong
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 返回主层级数量
     *
     * @return
     */
    @Override
    protected int getItemCount() {
        return mMockSource == null ? 0 : mMockSource.size();
    }

    /**
     * 副层级的数量，用于主层级上的显示效果
     *
     * @param parentPosition
     * @return
     */
    @Override
    protected int getSubItemCount(int parentPosition) {
        if (parentPosition < mMockSource.size()) {
            RecommendBookData mockData = mMockSource.get(parentPosition);
            if (mockData instanceof RecommendBookGroup) {
                int subCount = ((RecommendBookGroup) mockData).getChildCount();
                return subCount;
            }
        }
        return 0;
    }

    /**
     * 返回副层级的数据源
     *
     * @param parentPosition
     * @return
     */
    @Override
    protected RecommendBookGroup getSubSource(int parentPosition) {
        RecommendBookData mockData = mMockSource.get(parentPosition);
        if (mockData instanceof RecommendBookGroup) return (RecommendBookGroup) mockData;
        return null;
    }

    /**
     * 能否弹出次级窗口
     *
     * @param position    主层级点击的位置
     * @param pressedView 点击的view
     * @return
     */
    @Override
    protected boolean canExplodeItem(int position, View pressedView) {
        return mMockSource.get(position) instanceof RecommendBookGroup;
    }

    /**
     * 在主层级触发move事件 在这里进行数据改变
     *
     * @param selectedPosition 当前选择的item位置
     * @param targetPosition   要移动到的位置
     */
    @Override
    protected void onMove(int selectedPosition, int targetPosition) {
        mMockSource.add(targetPosition, mMockSource.remove(selectedPosition));
        mMockSourceChanged = true;
    }

    /**
     * 副层级数据移动处理
     *
     * @param recommendBookGroup 副层级数据源
     * @param selectedPosition   当前选择的item位置
     * @param targetPosition     要移动到的位置
     */
    @Override
    protected void onSubMove(RecommendBookGroup recommendBookGroup, int selectedPosition, int targetPosition) {
        recommendBookGroup.addChild(targetPosition, recommendBookGroup.removeChild(selectedPosition));
    }

    /**
     * 两个选项能否合并
     *
     * @param selectPosition
     * @param targetPosition
     * @return
     */
    @Override
    protected boolean canMergeItem(int selectPosition, int targetPosition) {
        RecommendBookData select = mMockSource.get(selectPosition);
        return !(select instanceof RecommendBookGroup);
    }

    /**
     * 合并数据处理
     *
     * @param selectedPosition
     * @param targetPosition
     */
    @Override
    protected void onMerged(int selectedPosition, int targetPosition) {
        RecommendBookData target = mMockSource.get(targetPosition);
        RecommendBookData select = mMockSource.remove(selectedPosition);
        if (target instanceof RecommendBookGroup) {
            ((RecommendBookGroup) target).addChild(0, select);
        } else {
            //合并成为文件夹状态
            RecommendBookGroup group = new RecommendBookGroup();
            group.addChild(select);
            group.addChild(target);
            group.setCategory(generateNewCategoryTag());
            targetPosition = mMockSource.indexOf(target);
            mMockSource.remove(targetPosition);
            mMockSource.add(targetPosition, group);
        }
        mMockSourceChanged = true;
    }

    /**
     * 生成新的分类标签
     *
     * @return 新的分类标签
     */
    private String generateNewCategoryTag() {
        //生成默认分类标签
        List<RecommendBookGroup> mockDataGroups = getMockGroup();
        if (mockDataGroups.size() > 0) {
            int serialNumber = 1;
            int[] mHoldNumber = null;
            for (RecommendBookGroup temp : mockDataGroups) {
                if (temp.getCategory().startsWith("分类")) {
                    //可能是自动生成的标签
                    String pendingStr = temp.getCategory().substring(2);
                    if (!TextUtils.isEmpty(pendingStr) && TextUtils.isDigitsOnly(pendingStr)) {
                        //尝试转换为整数
                        try {
                            int serialCategory = Integer.parseInt(pendingStr);
                            if (mHoldNumber == null) {
                                mHoldNumber = new int[1];
                                mHoldNumber[0] = serialCategory;
                            } else {
                                mHoldNumber = Arrays.copyOf(mHoldNumber, mHoldNumber.length + 1);
                                mHoldNumber[mHoldNumber.length - 1] = serialCategory;
                            }
                        } catch (NumberFormatException e) {
                            //nope
                        }
                    }
                }
            }
            if (mHoldNumber != null) {
                //有自动生成的标签
                Arrays.sort(mHoldNumber);
                for (int serial : mHoldNumber) {
                    if (serial < serialNumber) continue;
                    if (serial == serialNumber) {
                        //已经被占用 自增1
                        serialNumber++;
                    } else {
                        break;
                    }
                }
            }
            return "分类" + serialNumber;
        } else {
            return "分类1";
        }
    }

    /**
     * 从副层级移除的元素
     *
     * @param recommendBookGroup 副层级数据源
     * @param selectedPosition   将要冲副层级移除的数据
     * @return 返回的数为添加到主层级的位置
     */
    @Override
    protected int onLeaveSubRegion(int parentPosition, RecommendBookGroup recommendBookGroup, int selectedPosition) {
        if (mObservable.isRegister(mSubObserver)) mObservable.unregisterObserver(mSubObserver);
        //从副层级移除并添加到主层级第一个位置上
        RecommendBookData mockData = recommendBookGroup.removeChild(selectedPosition);
        mMockSource.add(0, mockData);
        if (recommendBookGroup.getChildCount() == 0) {
            int p = mMockSource.indexOf(recommendBookGroup);
            mMockSource.remove(p);
        }
        mMockSourceChanged = true;
        return 0;
    }

    /**
     * 主层级数据绑定
     *
     * @param holder
     * @param position
     */
    @Override
    protected void onBindMainViewHolder(ViewHolder holder, int position) {
        holder.bind(mMockSource.get(position), mEditMode);
    }

    /**
     * 副层级数据绑定
     *
     * @param holder
     * @param mainPosition
     * @param subPosition
     */
    @Override
    protected void onBindSubViewHolder(ViewHolder holder, int mainPosition, int subPosition) {
        holder.bind(((RecommendBookGroup) mMockSource.get(mainPosition)).getChild(subPosition), mEditMode);
    }


    @Override
    protected void onItemClick(final ViewHolder viewHolder, int parentIndex, int index) {
        if (mEditMode) {
            final RecommendBookData mockData = index == -1 ? mMockSource.get(parentIndex) : ((RecommendBookGroup) mMockSource.get(parentIndex)).getChild(index);
            if (!(mockData instanceof RecommendBookGroup)) {
                //执行check动画
                mockData.setChecked(!mockData.isChecked());
                if (mockData.isChecked()){
                    mCheckedData.add(mockData);
                }else {
                    mCheckedData.remove(mockData);
                }

                //通知
                mObservable.notifyItemCheckChanged(mockData.isChecked());
                if (index != -1) {
                    notifyItemChanged(parentIndex);
                }
                viewHolder.recommendFolderCheckBox.setScaleX(0f);
                viewHolder.recommendFolderCheckBox.setScaleY(0f);
                viewHolder.recommendFolderCheckBox.animate().scaleX(1f).scaleY(1f).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        viewHolder.recommendFolderCheckBox.setScaleX(1f);
                        viewHolder.recommendFolderCheckBox.setScaleY(1f);
                        viewHolder.recommendFolderCheckBox.animate().setListener(null);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        viewHolder.recommendFolderCheckBox.setScaleX(1f);
                        viewHolder.recommendFolderCheckBox.setScaleY(1f);
                        viewHolder.recommendFolderCheckBox.animate().setListener(null);
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        viewHolder.recommendFolderCheckBox.setBackgroundResource(mockData.isChecked() ? R.drawable.ic_checked : R.drawable.ic_unchecked);
                    }
                });
            }
        }
    }

    static class ViewHolder extends PrimitiveSimpleAdapter.ViewHolder {

        @BindView(R.id.recommend_folder_bg)
        View recommendFolderBg;
        @BindView(R.id.recommend_folder_grid)
        RecommendGridLayout recommendFolderGrid;
        @BindView(R.id.recommend_folder_content)
        FrameLayout recommendFolderContent;
        @BindView(R.id.recommend_folder_check_box)
        TextView recommendFolderCheckBox;
        @BindView(R.id.recommend_folder_tag)
        TextView recommendFolderTag;
        @BindView(R.id.recommend_folder)
        RecommendFolder recommendFolder;

        ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }

        void bind(RecommendBookData recommendBookData, boolean inEditMode) {
            if (inEditMode) {
                if (recommendBookData instanceof RecommendBookGroup) {
                    Log.i(Constants.CLASSIFY_VIEW_INIT, "RecommendBookGroup");
                    int count = ((RecommendBookGroup) recommendBookData).getCheckedCount();
                    if (count > 0) {
                        recommendFolderCheckBox.setVisibility(View.VISIBLE);
                        recommendFolderCheckBox.setText(count + "");
                        recommendFolderCheckBox.setBackgroundDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_number_bg));
                    } else {
                        recommendFolderCheckBox.setVisibility(View.GONE);
                    }
                } else {
                    Drawable drawable = ContextCompat.getDrawable(itemView.getContext(), recommendBookData.isChecked() ? R.drawable.ic_checked : R.drawable.ic_unchecked);
                    recommendFolderCheckBox.setText("");
                    recommendFolderCheckBox.setVisibility(View.VISIBLE);
                    recommendFolderCheckBox.setBackgroundDrawable(drawable);
                }
            } else {
                recommendFolderCheckBox.setVisibility(View.GONE);
            }
            if (recommendBookData instanceof RecommendBookGroup) {
                recommendFolderGrid.setVisibility(View.VISIBLE);
                recommendFolderTag.setVisibility(View.VISIBLE);
                recommendFolderTag.setText(((RecommendBookGroup) recommendBookData).getCategory());
                recommendFolderContent.setVisibility(View.GONE);
            } else {
                recommendFolderGrid.setVisibility(View.INVISIBLE);
                recommendFolderTag.setVisibility(View.GONE);
                recommendFolderContent.setBackgroundColor(recommendBookData.getColor());
                recommendFolderContent.setVisibility(View.VISIBLE);
            }
        }
    }

    static class RecommendObservable extends Observable<RecommendObserver> {

        public boolean isRegister(RecommendObserver observer) {
            return mObservers.contains(observer);
        }


        public void notifyItemCheckChanged(boolean isChecked) {
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onChecked(isChecked);
            }
        }

        public void notifyItemEditModeChanged(boolean editMode) {
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onEditChanged(editMode);
            }
        }

        public void notifyItemRestore() {
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onRestore();
            }
        }

        public void notifyItemHideSubDialog() {
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onHideSubDialog();
            }
        }
    }

    public static abstract class RecommendObserver {
        public void onChecked(boolean isChecked) {

        }


        public void onEditChanged(boolean inEdit) {

        }

        public void onRestore() {

        }

        public void onHideSubDialog() {

        }
    }
}
