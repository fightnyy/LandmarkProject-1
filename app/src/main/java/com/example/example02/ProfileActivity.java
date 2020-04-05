package com.example.example02;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("users").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                     if(document != null) {
                        if (document.exists()) {
                            TextView tv_name = (TextView)findViewById(R.id.tv_name);
                            TextView tv_address = (TextView)findViewById(R.id.tv_address);
                            TextView tv_email = (TextView)findViewById(R.id.tv_email);
                            tv_name.setText(document.getData().get("name").toString());
                            tv_address.setText(document.getData().get("address").toString());
                            tv_email.setText(user.getEmail());
                        } else {
                            Log.d(TAG, "No such document");
                            profileSetting();
                        }
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        findViewById(R.id.setting).setOnClickListener(onClickListener);
        findViewById(R.id.Profileimage).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.setting:
                    profileSetting();
                    break;
                case R.id.Profileimage:
                    break;
            }
        }
    };

    private void profileSetting() {
        Intent intent = new Intent(this, ProfileSettingActivity.class);
        startActivity(intent);
    }

}
