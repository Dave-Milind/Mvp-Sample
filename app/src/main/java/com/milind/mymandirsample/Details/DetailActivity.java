package com.milind.mymandirsample.Details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.milind.mymandirsample.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {


    @BindView(R.id.image_view_details)
    ImageView imageViewDetails;

    DetailPresenter detailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        detailPresenter = new DetailPresenter();
        detailPresenter.attachView(this);
        detailPresenter.start();

    }

    @Override
    public void setImage(String url) {
        Picasso.get().load(url).into(imageViewDetails);

    }

    @Override
    public String getIntentData() {

        return getIntent().getStringExtra("url");
    }
}
