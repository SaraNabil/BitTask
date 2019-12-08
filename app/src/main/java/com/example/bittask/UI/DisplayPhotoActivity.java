package com.example.bittask.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bittask.Model.HomeModel;
import com.example.bittask.R;
import com.example.bittask.Utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DisplayPhotoActivity extends AppCompatActivity {

    @BindView(R.id.titleTv)
    TextView titleTv;
    @BindView(R.id.homeIv)
    ImageView homeIv;

    HomeModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_photo);
        //ButterKnife
        ButterKnife.bind(this);

        model = (HomeModel) getIntent().getSerializableExtra(Constants.PHOTO_KEY);
        if (model != null) {
            titleTv.setText(model.getTitle());

            Glide.with(this).load(model.getImage()).apply(new RequestOptions()
                    .placeholder(R.drawable.ic_placeholder_48dp)
                    .centerInside())
                    .into(homeIv);
        }
    }

    @OnClick(R.id.backBtn)
    void backBtn() {
        finish();
    }
}
