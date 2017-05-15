package com.example.asus.scheduling.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.scheduling.R;
import com.example.asus.scheduling.activity.User;

import java.util.List;

// adapter untuk Join Grup
public class JoinGrupAdapter extends RecyclerView.Adapter<JoinGrupAdapter.UserViewHolder> {

    private List<User> list;

    public JoinGrupAdapter(List<User> list){
        this.list = list;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_join_group,parent,false));
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, int position) {

        User usermodel = list.get(position);

        holder.textNama.setText(usermodel.name);
        holder.textEmail.setText(usermodel.email);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        TextView textNama, textEmail;

        public UserViewHolder(View itemView) {
            super(itemView);

            textNama = (TextView) itemView.findViewById(R.id.txtNama);
            textEmail = (TextView) itemView.findViewById(R.id.txtEmail);


        }
    }
}
