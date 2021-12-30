package com.techov8.engineerguys.ui.Ask_Expert;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.database.annotations.NotNull;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.techov8.engineerguys.R;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.RECORD_AUDIO;

public class AskQuestionFragment extends Fragment {

    private BottomSheetBehavior bottomSheetBehavior;

    private EditText AskQuestionText;

    private Button AskExpertbutton, AskCommunitybutton;

    private RecyclerView CommunityQuestionrecylerview;

    QuestionAdapter questionAdapter;

    private List<Question> postList;

    ActivityResultLauncher<Intent> intentActivityResultLauncher;
    InputImage inputImage;
    TextRecognizer textRecognizer;


    private SpeechRecognizer speechRecognizer;

    private Intent speechRecognizerIntent;
    private String Voicestore;
    String temp;

    private ImageView Voicecommandbtn, Camerabtn,folderbtn;
    private int x=0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_askquestion, container, false);

        LinearLayout bottomsheetlinearLayout = view.findViewById(R.id.bottom_sheet_id);
        AskQuestionText = view.findViewById(R.id.editTextqustion);
        AskExpertbutton = view.findViewById(R.id.AskExpertbutton);
        AskCommunitybutton = view.findViewById(R.id.AskCommunitybutton);
        Voicecommandbtn = view.findViewById(R.id.voicequestion);
        Camerabtn = view.findViewById(R.id.scanquestion);
        folderbtn = view.findViewById(R.id.folderquestion);
        CommunityQuestionrecylerview = view.findViewById(R.id.communityrecylerview);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomsheetlinearLayout);



        textRecognizer= TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

               // if(x==0) {

                    ///handle picture here
                    Intent data = result.getData();
                    Uri imageUri = data.getData();

                    convetImagetoText(imageUri, null);
               // }






            }
        }

        );


        /////for photo scan using ml

       Camerabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                temp =AskQuestionText.getText().toString();


                Intent intent = new Intent();
                intent.setType("image/*");
               intent.setAction(Intent.ACTION_GET_CONTENT);
                /// startActivityForResult();

                intentActivityResultLauncher.launch(intent);


            }
        });

        folderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                x=1;


                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                //intent.setType("image/*");
              //intent.setAction(Intent.ACTION_CHOOSER);
                /// startActivityForResult();

                intentActivityResultLauncher.launch(intent);


            }
        });




        ///for speech reocognitation input and converting it to text

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());

        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        CommunityQuestionrecylerview.setLayoutManager(linearLayoutManager);


        postList = new ArrayList<>();
        questionAdapter = new QuestionAdapter(getContext(), postList);
        CommunityQuestionrecylerview.setAdapter(questionAdapter);


        voiceCommandPermission();


        PostQues();

        //RetriveMyQuestion();


        RetriveAllQuestion();

/////////////for voice command
        Voicecommandbtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                         temp =AskQuestionText.getText().toString();

                        speechRecognizer.startListening(speechRecognizerIntent);
                        Voicestore = "";
                        Toast.makeText(getContext(), "Listening...", Toast.LENGTH_SHORT).show();
                        break;


                    case MotionEvent.ACTION_UP:

                        speechRecognizer.stopListening();
                        break;
                }


                return false;
            }
        });


        /////

        /////////////for voice command


        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> storespeech = results.getStringArrayList(speechRecognizer.RESULTS_RECOGNITION);


                if (storespeech != null) {


                    Voicestore = storespeech.get(0);

                    AskQuestionText.setText(temp +" "+Voicestore);
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });


        ///////////////////////for voice command




        return view;
    }

    private void convetImagetoText(Uri imageUri,Bitmap bitmap) {
        //prepare the input image

        try{


                inputImage=InputImage.fromFilePath(getContext(),imageUri);


            Task<Text> result=textRecognizer.process(inputImage)
                    .addOnSuccessListener(new OnSuccessListener<Text>() {
                        @Override
                        public void onSuccess(@NonNull @NotNull Text text) {




                            AskQuestionText.setText( temp +text.getText());

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {

                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();

                        }
                    });

        }catch(Exception e){}


    }

    private void RetriveAllQuestion() {


       /* FirebaseDatabase.getInstance().getReference().child("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Question question = snapshot.getValue(Question.class);


                    if (question.getIspublicquestion().equals("yes")) {

                        postList.add(question);
                    }
                }
                questionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        */


    }



    private void PostQues() {


        AskExpertbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
                String postId = ref.push().getKey();

                HashMap<String, Object> map = new HashMap<>();
                map.put("postid", postId);
                map.put("Ispublicquestion", "no");
                map.put("questions", AskQuestionText.getText().toString());
                map.put("publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());

                ref.child(postId).setValue(map);
                // ref.child(postId).setValue();

            }
        });


        AskCommunitybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
                String postId = ref.push().getKey();

                HashMap<String, Object> map = new HashMap<>();
                map.put("postid", postId);
                map.put("Ispublicquestion", "yes");
                map.put("questions", AskQuestionText.getText().toString());
                map.put("publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());

                ref.child(postId).setValue(map);

            }
        });


    }


    ///for checking runtime permission of microphone

    private void voiceCommandPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(getContext(), RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getContext().getPackageName()));
                startActivity(intent);

                getActivity().finish();
            }

        }
    }


}