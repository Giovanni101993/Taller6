package com.example.doglife;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.MyHolder>{

    Context context;
    List<ModelUsers> usersList;

    public AdapterUsers(Context context, List<ModelUsers> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_users, viewGroup,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {

        String userImage = usersList.get(i).getImage();
        String userName = usersList.get(i).getName();
        String userEmail = usersList.get(i).getEmail();

        holder.mNameTv.setText(userName);
        holder.mEmailTv.setText(userEmail);

        try {
            Picasso.get().load(userImage)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(holder.mAvatarIv);
        }
        catch (Exception e){

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ""+userEmail, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        ImageView mAvatarIv;
        TextView mNameTv, mEmailTv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            mAvatarIv = itemView.findViewById(R.id.avatarIv);
            mNameTv = itemView.findViewById(R.id.nameTv);
            mEmailTv = itemView.findViewById(R.id.emailTv);

        }
    }
}
