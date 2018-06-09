package com.milind.mymandirsample.Home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.milind.mymandirsample.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    Context context;
    List<HomeModel> homeModelList;


    public HomeAdapter(Context context, List<HomeModel> homeModelList) {
        this.context = context;
        this.homeModelList = homeModelList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_home, parent, false);
        return new ViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.imageViewUploaded.setVisibility(View.GONE);
        HomeModel homeModel = homeModelList.get(position);
        HomeModel.Sender sender = homeModel.getSender();
        HomeModel.Attachments attachments;
        if (homeModel.getAttachments() != null) {
            attachments = homeModel.getAttachments().get(0);
            Picasso.get().load(attachments.getThumbnail_url()).into(holder.imageViewUploaded);
            holder.imageViewUploaded.setVisibility(View.VISIBLE);
            RxView.clicks(holder.imageViewUploaded)
                    .subscribe(aVoid ->{

                        onMediaClick.onMediaclick(attachments.getType(),attachments.getUrl(),sender.getName());

                    } );
        }
        holder.textViewTitle.setText(homeModel.getTitle());
        holder.textViewUsername.setText(sender.getName());
        Picasso.get().load(sender.getImageUrl()).into(holder.circleImageViewUser);

    }

    @Override
    public int getItemCount() {
        return homeModelList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_view_uploaded)
        ImageView imageViewUploaded;
        @BindView(R.id.circle_image_view_user)
        CircleImageView circleImageViewUser;
        @BindView(R.id.text_view_username)
        TextView textViewUsername;
        @BindView(R.id.text_view_title)
        TextView textViewTitle;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        }


  private onMediaClick onMediaClick;

    void setPresenter(onMediaClick onMediaClick) {
        this.onMediaClick = onMediaClick;

    }
}


