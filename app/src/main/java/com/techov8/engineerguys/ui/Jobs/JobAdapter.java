package com.techov8.engineerguys.ui.Jobs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.techov8.engineerguys.R;


import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.TeacherViewAdapter> {

    private List<JobData> list;
    private Context context;

    public JobAdapter(List<JobData> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public TeacherViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.job_item_layout, parent, false);

        return new TeacherViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewAdapter holder, int position) {

        final JobData item = list.get(position);
        holder.Jobname.setText(item.getJobtitle());
        holder.email.setText(item.getSalary());
        holder.post.setText(item.getPost());
        holder.additionalinfo.setText(item.getAdditionalinfo());

        holder.applybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(item.getLink()));
                holder.itemView.getContext().startActivity(i);

            }
        });

        try {
            Picasso.get().load(item.getImage()).placeholder(R.drawable.profile_image).into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TeacherViewAdapter extends RecyclerView.ViewHolder {

        private TextView Jobname, email, post,additionalinfo;
        private ImageView imageView;
        private Button applybutton;

        public TeacherViewAdapter(@NonNull View itemView) {
            super(itemView);
            Jobname = itemView.findViewById(R.id.teacherName);
            email = itemView.findViewById(R.id.teacherEmail);
            post = itemView.findViewById(R.id.teacherPost);
            imageView = itemView.findViewById(R.id.teacherImage);
            applybutton = itemView.findViewById(R.id.Applybutton);
            additionalinfo = itemView.findViewById(R.id.additionalinfo);
        }
    }

}
