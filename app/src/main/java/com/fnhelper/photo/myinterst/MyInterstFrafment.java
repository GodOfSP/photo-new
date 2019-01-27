package com.fnhelper.photo.myinterst;


import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fnhelper.photo.R;
import com.fnhelper.photo.base.recyclerviewadapter.BaseAdapterHelper;
import com.fnhelper.photo.base.recyclerviewadapter.QuickAdapter;
import com.fnhelper.photo.beans.FollowListBean;
import com.fnhelper.photo.diyviews.ClearEditText;
import com.fnhelper.photo.interfaces.RetrofitService;
import com.fnhelper.photo.mine.PersonalCenterAc;
import com.fnhelper.photo.utils.STokenUtil;
import com.fnhelper.photo.utils.TwinklingRefreshLayoutUtil;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zyyoona7.popup.EasyPopup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fnhelper.photo.base.BaseActivity.showBottom;
import static com.fnhelper.photo.interfaces.Constants.CODE_ERROR;
import static com.fnhelper.photo.interfaces.Constants.CODE_SERIVCE_LOSE;
import static com.fnhelper.photo.interfaces.Constants.CODE_SUCCESS;
import static com.fnhelper.photo.interfaces.Constants.CODE_TOKEN;
import static com.fnhelper.photo.interfaces.Constants.pageSize;


/**
 * 我的关注
 */
public class MyInterstFrafment extends Fragment {


    @BindView(R.id.search_et)
    ClearEditText searchEt;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    TwinklingRefreshLayout refresh;
    @BindView(R.id.viewGroup)
    ConstraintLayout viewGroup;
    Unbinder unbinder;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.empty_page)
    RelativeLayout emptyPage;


    private QuickAdapter<FollowListBean.DataBean.RowsBean> adapter;
    private boolean canLoadMore = false;
    private int pageNum = 1;
    private EasyPopup mCirclePop;
    private String keyWord = "";


    private String mParam1;
    private String mParam2;

    public MyInterstFrafment() {
        // Required empty public constructor
    }


    public static MyInterstFrafment newInstance(String param1, String param2) {
        MyInterstFrafment fragment = new MyInterstFrafment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_interst, container, false);
        unbinder = ButterKnife.bind(this, view);
        initSearch();
        initTklRefreshLayout();
        initRecyclerView();
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        refresh.startRefresh();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }


    private void initSearch() {

        //搜索框回车键监听
        searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!TextUtils.isEmpty(keyWord)) {
                        keyWord = searchEt.getText().toString().trim();
                        if (!TextUtils.isEmpty(keyWord)) {
                            getList(false);
                        }
                    }

                    return true;
                }
                return false;
            }
        });
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())){
                    keyWord = "";
                    getList(false);
                }
            }
        });
    }

    private void initRecyclerView() {


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        adapter = new QuickAdapter<FollowListBean.DataBean.RowsBean>(getContext(), R.layout.item_my_interst) {
            @Override
            protected void convert(BaseAdapterHelper helper, final FollowListBean.DataBean.RowsBean item, int position) {

                helper.setFrescoImageResource(R.id.head_pic, item.getSHeadImg());
                helper.setVisible(R.id.vip_logo, item.isBIsVip());

                helper.setText(R.id.expiry_date_title, "关注时间:");
                helper.setText(R.id.expiry_date, item.getDConcernTime());
                helper.setText(R.id.num, item.getNumber() + "");
                if (item.getSRemarkName() != null) {
                    helper.setText(R.id.user_name, item.getSNickName() + "(" + item.getSRemarkName() + ")");
                } else {
                    helper.setText(R.id.user_name, item.getSNickName());
                }

                 /*
                 * 跳转到个人信息
                 */

                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), PersonalCenterAc.class);
                        intent.putExtra("concernId", item.getSConcernId());
                        intent.putExtra("nickName", item.getSNickName());
                        intent.putExtra("where", 100);
                        startActivity(intent);
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);


    }


    private void initTklRefreshLayout() {

        new TwinklingRefreshLayoutUtil().getUpdateAndLoadMoreTwinkling(getActivity(), refresh);
        refresh.setOnRefreshListener(new RefreshListenerAdapter() {


            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                pageNum = 1;
                getList(false);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (canLoadMore) {
                    pageNum++;
                    getList(true);
                } else {
                    refreshLayout.finishLoadmore();
                    showBottom(getContext(), "没有更多了 ~");
                }
            }
        });
    }

    private void getList(final boolean isLoadMore) {


        Call<FollowListBean> call = RetrofitService.createMyAPI().GetConcernListByPage(keyWord, pageSize, pageNum);
        call.enqueue(new Callback<FollowListBean>() {
            @Override
            public void onResponse(Call<FollowListBean> call, Response<FollowListBean> response) {
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body().getCode() == CODE_SUCCESS) {
                            //成功
                            if (response.body().getData() != null) {
                                if (response.body().getData().getRows() != null && response.body().getData().getRows().size() != 0) {
                                    if (isLoadMore) {
                                        adapter.addAll(response.body().getData().getRows());
                                    } else {
                                        adapter.replaceAll(response.body().getData().getRows());
                                    }
                                    if (response.body().getData().getTotal() > adapter.getData().size()) {
                                        canLoadMore = true;
                                    } else {
                                        canLoadMore = false;
                                    }
                                    emptyPage.setVisibility(View.GONE);
                                }else {
                                    if (!isLoadMore){
                                        adapter.clear();
                                        emptyPage.setVisibility(View.VISIBLE);
                                    }
                                }
                            }

                            refresh.finishRefreshing();
                            refresh.finishLoadmore();
                        } else if (response.body().getCode() == CODE_ERROR) {
                            //失败
                            refresh.finishRefreshing();
                            refresh.finishLoadmore();
                            showBottom(getContext(), response.body().getInfo());
                        } else if (response.body().getCode() == CODE_SERIVCE_LOSE) {
                            //服务错误
                            refresh.finishRefreshing();
                            refresh.finishLoadmore();
                            showBottom(getContext(), response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //登录过期
                            refresh.finishRefreshing();
                            refresh.finishLoadmore();
                            STokenUtil.check(getActivity());
                            showBottom(getContext(), response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //账号冻结
                            refresh.finishRefreshing();
                            refresh.finishLoadmore();
                            showBottom(getContext(), response.body().getInfo());
                        }
                    } else {
                        refresh.finishRefreshing();
                        refresh.finishLoadmore();
                        showBottom(getContext(), "网络异常！");
                    }
                }
            }

            @Override
            public void onFailure(Call<FollowListBean> call, Throwable t) {
                refresh.finishRefreshing();
                refresh.finishLoadmore();
            }
        });
    }




}
