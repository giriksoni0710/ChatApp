package com.crazy4web.chatapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class userlistAdapter extends RecyclerView.Adapter<userlistAdapter.UserListViewHolder> {


    ArrayList<userObject> userlist;

    public userlistAdapter(ArrayList<userObject> userlist){


        this.userlist = userlist;

    }

    @NonNull
    @Override
    public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

      View LayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,null,false);
      RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
      LayoutView.setLayoutParams(lp);

      UserListViewHolder rcv = new UserListViewHolder(LayoutView);


        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull UserListViewHolder holder, int position) {

        holder.mname.setText(userlist.get(position).getName());

        holder.mphone.setText(userlist.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public class UserListViewHolder extends RecyclerView.ViewHolder {

        TextView mname, mphone;


        public UserListViewHolder(@NonNull View itemView) {
            super(itemView);

            mname = itemView.findViewById(R.id.name);
            mphone = itemView.findViewById(R.id.phone);

        }
    }
}
