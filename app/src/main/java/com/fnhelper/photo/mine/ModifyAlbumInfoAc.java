package com.fnhelper.photo.mine;

import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fnhelper.photo.R;
import com.fnhelper.photo.base.BaseActivity;
import com.fnhelper.photo.beans.CheckCodeBean;
import com.fnhelper.photo.diyviews.ClearEditText;
import com.fnhelper.photo.interfaces.Constants;
import com.fnhelper.photo.interfaces.RetrofitService;
import com.fnhelper.photo.utils.DensityUtil;
import com.fnhelper.photo.utils.STokenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fnhelper.photo.interfaces.Constants.CODE_ERROR;
import static com.fnhelper.photo.interfaces.Constants.CODE_SERIVCE_LOSE;
import static com.fnhelper.photo.interfaces.Constants.CODE_SUCCESS;
import static com.fnhelper.photo.interfaces.Constants.CODE_TOKEN;

/**
 * 更改相册信息  通用
 */
public class ModifyAlbumInfoAc extends BaseActivity {


    @BindView(R.id.tv_com_back)
    ImageView tvComBack;
    @BindView(R.id.com_title)
    TextView comTitle;
    @BindView(R.id.com_right)
    TextView comRight;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.content)
    ClearEditText content;
    @BindView(R.id.word_num)
    TextView wordNum;
    @BindView(R.id.cl)
    ConstraintLayout cl;

    public static final int M_ALBUM_NAME = 1; //相册名
    public static final int M_CONNECT_TEL = 2; //联系电话
    public static final int M_WX_NUM = 3; //微信号
    public static final int M_ALBUM_INTRODUCE = 4; //相册介绍

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_modify_album_info);
        ButterKnife.bind(this);
    }

    @Override
    protected void initUI() {

        tvComBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        comRight.setText("保存");

        content.selectAll();

        switch (getIntent().getIntExtra("which", 1)) {
            case M_ALBUM_NAME:
                comTitle.setText("更改相册名");
                title.setText("相册名");
                content.setText(Constants.album_name);
                break;
            case M_CONNECT_TEL:
                comTitle.setText("更改联系电话");
                title.setText("联系电话");
                content.setText(Constants.sLinkPhone);
                break;
            case M_WX_NUM:
                comTitle.setText("更改微信号");
                title.setText("微信号");
                content.setText(Constants.wx_num);
                break;
            case M_ALBUM_INTRODUCE:
                comTitle.setText("更改相册介绍");
                content.setText(Constants.album_introduce);
                title.setVisibility(View.GONE);
                wordNum.setVisibility(View.VISIBLE);
                wordNum.setText(content.getText().toString().length() + "");

                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(ModifyAlbumInfoAc.this, 100));

                params.bottomToBottom = ((ConstraintLayout.LayoutParams) cl.getLayoutParams()).bottomToBottom;
                params.topToBottom = ((ConstraintLayout.LayoutParams) cl.getLayoutParams()).topToBottom;
                params.topToTop = ((ConstraintLayout.LayoutParams) cl.getLayoutParams()).topToTop;
                params.leftToLeft = ((ConstraintLayout.LayoutParams) cl.getLayoutParams()).leftToLeft;
                params.rightToRight = ((ConstraintLayout.LayoutParams) cl.getLayoutParams()).rightToRight;
                cl.setLayoutParams(params);
                break;
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

        comRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(content.getText().toString().trim())) {
                    showCenter(ModifyAlbumInfoAc.this, "内容为空！");
                } else {
                    save();
                }
            }
        });

        if (getIntent().getIntExtra("which", 1) == M_ALBUM_INTRODUCE) { //相册介绍

            content.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {


                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (s.toString().length() > 50) {
                        content.setText(s.toString().substring(0,50));
                        showBottom(ModifyAlbumInfoAc.this,"相册介绍不能超过50字");
                    }
                    wordNum.setText(content.getText().toString().length() + "");
                }
            });

        }else if (getIntent().getIntExtra("which", 1) == M_CONNECT_TEL){// = 2; //联系电话
            content.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {


                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (s.toString().length() > 11) {
                        content.setText(s.toString().substring(0,11));
                        showBottom(ModifyAlbumInfoAc.this,"联系电话不能超过11位");
                    }
                }
            });
        }else if (getIntent().getIntExtra("which", 1) == M_WX_NUM){// M_WX_NUM = 3; //微信号
            content.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {


                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (s.toString().length() > 10) {
                        content.setText(s.toString().substring(0,10));
                        showBottom(ModifyAlbumInfoAc.this,"微信号不能超过10位");
                    }
                }
            });
        }else if (getIntent().getIntExtra("which", 1) == M_ALBUM_NAME){// M_ALBUM_NAME = 3; //相册名
            content.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {


                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (s.toString().length() > 10) {
                        content.setText(s.toString().substring(0,10));
                        showBottom(ModifyAlbumInfoAc.this,"相册名不能超过10位");
                    }
                }
            });
        }
    }


    String s1 = "", s2 = "", s3 = "", s4 = "";

    private void save() {


        switch (getIntent().getIntExtra("which", 1)) {
            case M_ALBUM_NAME:
                s1 = content.getText().toString().trim();
                break;
            case M_CONNECT_TEL:
                s3 = content.getText().toString().trim();
                break;
            case M_WX_NUM:
                s2 = content.getText().toString().trim();
                break;
            case M_ALBUM_INTRODUCE:
                s4 = content.getText().toString().trim();
                break;
        }


        Call<CheckCodeBean> call = RetrofitService.createMyAPI().UpdatePersonalInfo(s1, s2, s3, s4);
        call.enqueue(new Callback<CheckCodeBean>() {
            @Override
            public void onResponse(Call<CheckCodeBean> call, Response<CheckCodeBean> response) {
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body().getCode() == CODE_SUCCESS) {
                            //成功

                            switch (getIntent().getIntExtra("which", 1)) {
                                case M_ALBUM_NAME:
                                    Constants.album_name = s1;
                                    editor.putString("album_name",Constants.album_name);
                                    break;
                                case M_CONNECT_TEL:
                                    Constants.sLinkPhone = s3;
                                    editor.putString("sLinkPhone",Constants.sLinkPhone);
                                    break;
                                case M_WX_NUM:
                                    Constants.wx_num = s2;
                                    editor.putString("wx_num",Constants.wx_num);
                                    break;
                                case M_ALBUM_INTRODUCE:
                                    Constants.album_introduce = s4;
                                    editor.putString("album_introduce",Constants.album_introduce);
                                    break;
                            }
                            editor.commit();
                            showBottom(ModifyAlbumInfoAc.this, response.body().getInfo());
                            finish();
                        } else if (response.body().getCode() == CODE_ERROR) {
                            //失败
                            showBottom(ModifyAlbumInfoAc.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_SERIVCE_LOSE) {
                            //服务错误
                            showBottom(ModifyAlbumInfoAc.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //登录过期
                            STokenUtil.check(ModifyAlbumInfoAc.this);
                            showBottom(ModifyAlbumInfoAc.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //账号冻结
                            showBottom(ModifyAlbumInfoAc.this, response.body().getInfo());
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<CheckCodeBean> call, Throwable t) {

            }
        });
    }

}
