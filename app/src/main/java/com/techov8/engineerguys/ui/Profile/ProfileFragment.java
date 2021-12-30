package com.techov8.engineerguys.ui.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techov8.engineerguys.EditProfileActivity;
import com.techov8.engineerguys.R;
import com.techov8.engineerguys.ui.Ask_Expert.Question;
import com.techov8.engineerguys.ui.Ask_Expert.QuestionAdapter;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private RecyclerView recyclerViewSaves;


    private RecyclerView recyclerView;


    private CircleImageView imageProfile;


    private TextView fullname;
    private TextView mobile;
    private TextView mail;

    private List<Question> myQuestionList;

    private TextView editProfile;

    private FirebaseUser fUser;

    QuestionAdapter questionAdapter;


    String profileId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        fUser = FirebaseAuth.getInstance().getCurrentUser();

        String data = getContext().getSharedPreferences("PROFILE", Context.MODE_PRIVATE).getString("profileId", "none");

        if (data.equals("none")) {
            profileId = fUser.getUid();
        } else {
            profileId = data;
            getContext().getSharedPreferences("PROFILE", Context.MODE_PRIVATE).edit().clear().apply();
        }

        imageProfile = view.findViewById(R.id.profileimage);

        fullname = view.findViewById(R.id.fullname);
        mobile = view.findViewById(R.id.mobile);
        mail = view.findViewById(R.id.email);
        editProfile = view.findViewById(R.id.editprofile);


        recyclerView = view.findViewById(R.id.recucler_view_myquestion);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myQuestionList = new ArrayList<>();
        questionAdapter = new QuestionAdapter(getContext(), myQuestionList);
        recyclerView.setAdapter(questionAdapter);


        userInfo();

        getMyQuestion();


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), EditProfileActivity.class));

            }
        });


        return view;
    }

    private void getMyQuestion() {



      /*  FirebaseDatabase.getInstance().getReference().child("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myQuestionList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Question post = snapshot.getValue(Question.class);

                    if (post.getPublisher().equals(profileId)) {
                        myQuestionList.add(post);
                    }
                }

                Collections.reverse(myQuestionList);
                questionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       */


    }


    private void userInfo() {

        FirebaseDatabase.getInstance().getReference().child("Users").child(profileId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                // Picasso.get().load(user.getImageurl()).into(imageProfile);
                mail.setText(user.getEmail());
                fullname.setText(user.getName());
                mobile.setText(user.getMobile());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
