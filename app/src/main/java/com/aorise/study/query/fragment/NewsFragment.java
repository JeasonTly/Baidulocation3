package com.aorise.study.query.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aorise.study.BR;
import com.aorise.study.R;
import com.aorise.study.adapter.BaseViewHolder;
import com.aorise.study.base.LogT;
import com.aorise.study.databinding.ActivityQueryNewsBinding;
import com.aorise.study.query.fragment.bean.NewsTitle;
import com.aorise.study.query.fragment.bean.NewsTitleContent;
import com.aorise.study.query.listener.HttpRquestReturnListener;
import com.aorise.study.query.vm.NewsVm;
import com.aorise.study.query.vm.adapter.ContentAdapter;
import com.aorise.study.query.vm.adapter.TitleAdapter;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Tuliyuan.
 * Date: 2019/1/31.
 */
public class NewsFragment extends Fragment implements BaseRefreshListener, HttpRquestReturnListener {
    private ActivityQueryNewsBinding mDataBinding;
    private List<NewsTitle> datas = new ArrayList<NewsTitle>();
    private ContentAdapter mContentAdapter;
    private static int currentPosition = 0 ;
    private Context mContext;
    private NewsVm newsVm;
    private boolean loadmore = false;
    public NewsFragment (){
        super();
        List<NewsTitleContent> titleContents1 = new ArrayList<>();
        titleContents1.add(new NewsTitleContent("2018-11-21","xx" ,"xxx" , "xxxxxx"));
        titleContents1.add(new NewsTitleContent("2018-11-11","x1x" ,"xx2x" , "xxxx1xx"));
        titleContents1.add(new NewsTitleContent("2018-11-01","x11x" ,"xx23x" , "xxxx11xx"));
        List<NewsTitleContent> titleContents2 = new ArrayList<>();
        titleContents2.add(new NewsTitleContent("2011-10-21","xx" ,"x2xx" , "xxxxxx"));
        titleContents2.add(new NewsTitleContent("2011-10-11","x2x" ,"xx4x" , "xxxx1xx"));
        titleContents2.add(new NewsTitleContent("2011-09-01","x21x" ,"xx21x" , "xxxx31xx"));
        titleContents2.add(new NewsTitleContent("2011-09-01","x21x" ,"xx21x" , "xxxx31xx"));
        List<NewsTitleContent> titleContents3 = new ArrayList<>();
        titleContents3.add(new NewsTitleContent("2013-10-21","xx" ,"x2xx" , "xxxxxx"));
        List<NewsTitleContent> titleContents4 = new ArrayList<>();
        titleContents4.add(new NewsTitleContent("2014-10-21","xx" ,"x2xx" , "xxxxxx"));
        titleContents4.add(new NewsTitleContent("2014-10-11","x2x" ,"xx4x" , "xxxx1xx"));
        titleContents4.add(new NewsTitleContent("2014-09-01","x21x" ,"xx21x" , "xxxx31xx"));
        titleContents4.add(new NewsTitleContent("2014-09-01","x21x" ,"xx21x" , "xxxx31xx"));
        titleContents4.add(new NewsTitleContent("2014-09-01","x21x" ,"xx21x" , "xxxx31xx"));
        List<NewsTitleContent> titleContents5 = new ArrayList<>();
        titleContents5.add(new NewsTitleContent("2015-10-21","xx" ,"x2xx" , "xxxxxx"));
        titleContents5.add(new NewsTitleContent("2015-10-11","x2x" ,"xx4x" , "xxxx1xx"));
        titleContents5.add(new NewsTitleContent("2015-09-01","x21x" ,"xx21x" , "xxxx31xx"));
        titleContents4.add(new NewsTitleContent("2014-09-01","x21x" ,"xx21x" , "xxxx31xx"));
        titleContents4.add(new NewsTitleContent("2014-09-01","x21x" ,"xx21x" , "xxxx31xx"));
        titleContents4.add(new NewsTitleContent("2014-09-01","x21x" ,"xx21x" , "xxxx31xx"));
        titleContents4.add(new NewsTitleContent("2014-09-01","x21x" ,"xx21x" , "xxxx31xx"));

        datas.add(new NewsTitle(0, "Title1",titleContents1));
        datas.add(new NewsTitle(1, "Title2",titleContents2));
        datas.add(new NewsTitle(3, "Title3",titleContents3));
        datas.add(new NewsTitle(4, "Title4",titleContents4));
        datas.add(new NewsTitle(5, "Title5",titleContents5));
        newsVm = new NewsVm();

        LogT.d("datasize is "+datas.size());
    }
    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.activity_query_news,container,false);
        mDataBinding.newsPullRefresh.setRefreshListener(this);
        mContext = getContext();
        initContent();
        initTitle();
        return mDataBinding.getRoot();

    }
    private void initTitle(){
        if(datas != null && mDataBinding != null ){
            RecyclerView.LayoutManager manager = new LinearLayoutManager(mContext);
            ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.HORIZONTAL);
            mDataBinding.newsRecycleviewTitle.setLayoutManager(manager);
            mDataBinding.newsRecycleviewTitle.setAdapter(new TitleAdapter(mContext,R.layout.title,datas ,mContentAdapter));
        }
    }
    private void initContent(){
        if(datas != null && mDataBinding != null ){
            RecyclerView.LayoutManager manager = new LinearLayoutManager(mContext);
            ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
            LogT.d("ddddd " + datas.get(currentPosition).getDatas());
            mContentAdapter = new ContentAdapter(mContext,R.layout.title,datas.get(0).getDatas());
            mDataBinding.newsRecycleview.setLayoutManager(manager);
            mDataBinding.newsRecycleview.setAdapter(mContentAdapter);
        }
    }

    @Override
    public void refresh() {
        LogT.d("refresh data");
        newsVm.QueryTypeData(datas.get(currentPosition).getNewsType(),false);
        loadmore = false;
    }

    @Override
    public void loadMore() {
        LogT.d("loadMore data");
        newsVm.QueryTypeData(datas.get(currentPosition).getNewsType(),true);
        loadmore = true;
    }

    @Override
    public void loadSuccess() {
        if(loadmore){
            mDataBinding.newsPullRefresh.finishLoadMore();
        }else{
            mDataBinding.newsPullRefresh.finishRefresh();
        }
    }

    @Override
    public void loadFailuer(String e) {
        if(loadmore){
            mDataBinding.newsPullRefresh.finishLoadMore();
        }else{
            mDataBinding.newsPullRefresh.finishRefresh();
        }
    }


//    class TitleRecycleAdapter extends RecyclerView.Adapter{
//        ViewDataBinding mTitleDataBinding;
//
//        @NonNull
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//            mTitleDataBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.title,viewGroup,false);
//            return new BaseViewHolder(mTitleDataBinding);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
//            LogT.d(" position "+ position + " title " + datas.get(position).getNewsType() + " currentposition is "+currentPosition);
//            mTitleDataBinding.setVariable(BR.titletext,datas.get(position).getNewsType());
//            mTitleDataBinding.executePendingBindings();
//
//           TextView textView = viewHolder.itemView.findViewById(R.id.news_type_title);
//            if(currentPosition == position){
//                textView.setTextColor(Color.GREEN);
//            }else{
//                textView.setTextColor(Color.BLUE);
//            }
//            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                   // LogT.d("点击了标题中的" + " 第 "+ (position+1) + "项" + " datas 为"+datas.get(position).getDatas());
//                    mContentAdapter.setDatas(datas.get(position).getDatas());
//                    mContentAdapter.notifyDataSetChanged();
//                    setSelectedIndex(position);
//                    notifyDataSetChanged();
//                }
//            });
//
//        }
//        public void setSelectedIndex(int position) {
//            LogT.d("do select " + position);
//            currentPosition = position;
//            notifyDataSetChanged();
//        }
//        @Override
//        public int getItemCount() {
//            if(datas != null){
//                return datas.size();
//            }
//            return 0;
//        }
//    }
//    class ContentAdapter<T> extends RecyclerView.Adapter{
//        private final int TYPE_NO_DATAS = 0;
//        private int Type = -1;
//        private List<T> contentDatas = new ArrayList<>();
//        ViewDataBinding mTitleDataBinding;
//
//        public ContentAdapter(List<T> datas) {
//            LogT.d(" 111");
//            contentDatas.clear();
//            contentDatas.addAll(datas);
//            if(datas == null){
//                LogT.d("no datas 111");
//                Type = TYPE_NO_DATAS;
//            }
//        }
//        public void setDatas(List<T> datas) {
//            contentDatas.clear();
//            contentDatas.addAll(datas);
//            if(datas == null){
//                Type = TYPE_NO_DATAS;
//            }
//        }
//        @NonNull
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//            if(Type == TYPE_NO_DATAS){
//                LogT.d("no datas");
//                mTitleDataBinding =  DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.title,viewGroup,false);
//                return new BaseViewHolder(mTitleDataBinding);
//            }
//            mTitleDataBinding =  DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.title,viewGroup,false);
//            return new BaseViewHolder(mTitleDataBinding);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
//            if(Type == TYPE_NO_DATAS){
//                mTitleDataBinding.setVariable(BR.titletext,"No Datas");
//            }else {
//                LogT.d("点击了标题中的 "+currentPosition + " 第 "+ (i) + "项" + " datas 为" + datas.get(currentPosition).getDatas() );
//                mTitleDataBinding.setVariable(BR.titletext,datas.get(currentPosition).getDatas().get(i).toString());
//                mTitleDataBinding.executePendingBindings();//必须加入这个更新数据
//                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        LogT.d("点击了内容中的" + datas.get(currentPosition).getNewsType() + "中的第" + i + "项目");
//                    }
//                });
//            }
//        }
//
//        @Override
//        public int getItemCount() {
//            if(datas != null){
//                if(datas.get(currentPosition).getDatas() !=null){
//                    return datas.get(currentPosition).getDatas().size();
//                }
//            }
//            return 0;
//        }
//    }

    @Nullable
    @Override
    public View getView() {
        return super.getView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
