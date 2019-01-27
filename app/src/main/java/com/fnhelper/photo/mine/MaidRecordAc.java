package com.fnhelper.photo.mine;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fnhelper.photo.R;
import com.fnhelper.photo.base.BaseActivity;
import com.fnhelper.photo.base.recyclerviewadapter.BaseAdapterHelper;
import com.fnhelper.photo.base.recyclerviewadapter.QuickAdapter;
import com.fnhelper.photo.beans.MadiRecordBean;
import com.fnhelper.photo.interfaces.RetrofitService;
import com.fnhelper.photo.utils.STokenUtil;
import com.fnhelper.photo.utils.TwinklingRefreshLayoutUtil;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fnhelper.photo.interfaces.Constants.CODE_ERROR;
import static com.fnhelper.photo.interfaces.Constants.CODE_SERIVCE_LOSE;
import static com.fnhelper.photo.interfaces.Constants.CODE_SUCCESS;
import static com.fnhelper.photo.interfaces.Constants.CODE_TOKEN;
import static com.fnhelper.photo.interfaces.Constants.pageSize;

public class MaidRecordAc extends BaseActivity {

    @BindView(R.id.tv_com_back)
    ImageView tvComBack;
    @BindView(R.id.com_title)
    TextView comTitle;
    @BindView(R.id.com_right)
    ImageView comRight;
    @BindView(R.id.com_code)
    ImageView comCode;
    @BindView(R.id.head_view)
    RelativeLayout headView;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.refresh)
    TwinklingRefreshLayout refresh;
    @BindView(R.id.empty_page)
    RelativeLayout emptyPage;

    private QuickAdapter<MadiRecordBean.DataBean.RowsBean> adapter = null;
    private boolean canLoadMore = false;
    private int pageNum = 1;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_maid_record);
        ButterKnife.bind(this);
    }

    @Override
    protected void initUI() {
        comTitle.setText("返佣记录");
        comRight.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        initRecyclerView();
        initTklRefreshLayout();
    }

    @Override
    protected void initListener() {
        tvComBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void initRecyclerView() {


        recycler.setLayoutManager(new LinearLayoutManager(MaidRecordAc.this, LinearLayoutManager.VERTICAL, false));


        adapter = new QuickAdapter<MadiRecordBean.DataBean.RowsBean>(MaidRecordAc.this, R.layout.item_maid_record) {
            @Override
            protected void convert(BaseAdapterHelper helper, final MadiRecordBean.DataBean.RowsBean item, int position) {

                helper.setText(R.id.user_name, item.getSNickName());
                helper.setText(R.id.vip_money, item.getDRechargePrice());
                helper.setText(R.id.maid_money, item.getDMoney());
                helper.setText(R.id.time, item.getDInsertTime());
                helper.setFrescoImageResource(R.id.head_pic, item.getSHeadImg());
                helper.setVisible(R.id.vip_logo, item.isBIsVip());

            }
        };

        recycler.setAdapter(adapter);


    }


    private void initTklRefreshLayout() {

        new TwinklingRefreshLayoutUtil().getUpdateAndLoadMoreTwinkling(MaidRecordAc.this, refresh);
        refresh.setOnRefreshListener(new RefreshListenerAdapter() {


            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                pageNum = 1;
                getListData(false);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (canLoadMore) {
                    pageNum++;
                    getListData(true);
                } else {
                    refreshLayout.finishLoadmore();
                    showBottom(MaidRecordAc.this, "没有更多了 ~");
                }
            }
        });
    }


    private void getListData(final boolean isLoadMore) {
        Call<MadiRecordBean> call = RetrofitService.createMyAPI().GetCommisionRecord(pageSize, pageNum);
        call.enqueue(new Callback<MadiRecordBean>() {
            @Override
            public void onResponse(Call<MadiRecordBean> call, Response<MadiRecordBean> response) {
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

                        } else if (response.body().getCode() == CODE_ERROR) {
                            //失败
                            showBottom(MaidRecordAc.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_SERIVCE_LOSE) {
                            //服务错误
                            showBottom(MaidRecordAc.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //登录过期
                            STokenUtil.check(MaidRecordAc.this);
                            showBottom(MaidRecordAc.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //账号冻结
                            showBottom(MaidRecordAc.this, response.body().getInfo());
                        }
                    }
                }
                refresh.finishRefreshing();
                refresh.finishLoadmore();

            }

            @Override
            public void onFailure(Call<MadiRecordBean> call, Throwable t) {
                refresh.finishRefreshing();
                refresh.finishLoadmore();
                showBottom(MaidRecordAc.this, "网络异常！");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        refresh.startRefresh();
    }

}
