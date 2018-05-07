package com.example.adity.scholarquiz;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.adity.scholarquiz.BroadcastReceiver.AlarmReceiver;
import com.example.adity.scholarquiz.Common.Common;
import com.example.adity.scholarquiz.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {
    MaterialEditText editYourSlackId, editNewPassword, editNewEmail;  // sign up
    MaterialEditText editSlackId, editPassword; //sign in

    Button buttonSignUp, buttonSignIn;

    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerAlarm();


        //Firebase
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");
        editSlackId = (MaterialEditText) findViewById(R.id.slackId);
        editPassword = (MaterialEditText) findViewById(R.id.password);

        buttonSignIn = (Button) findViewById(R.id.button_signIn);
        buttonSignUp = (Button) findViewById(R.id.button_signUp);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignUpDialog();
            }
        });
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(editSlackId.getText().toString(), editPassword.getText().toString());
            }
        });
    }

    @TargetApi(24)


        private void registerAlarm () {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);  // the time you want to send notification hour
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }


    private void signIn(final String user, final String pwd) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(user).exists()) {
                    if (!user.isEmpty()) {

                        User login = dataSnapshot.child(user).getValue(User.class);

                        if (login.getPassword().equals(pwd)) {
                            Intent homeActivity = new Intent(MainActivity.this, Home.class);
                            Common.currentUser = login;

                            startActivity(homeActivity);
                            finish();
                        } else
                            Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Please Enter Your User Name", Toast.LENGTH_SHORT).show();
                    }

                } else
                    Toast.makeText(MainActivity.this, "User is not exits", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    private void showSignUpDialog() {
        AlertDialog.Builder alertDailog = new AlertDialog.Builder(MainActivity.this);
        alertDailog.setTitle("Sign Up");
        alertDailog.setMessage("Please fill full information");

        LayoutInflater inflater = this.getLayoutInflater();
        View signup_layout = inflater.inflate(R.layout.signup_layout, null);

        editYourSlackId = (MaterialEditText) signup_layout.findViewById(R.id.edit_yourslackId);
        editNewEmail = (MaterialEditText) signup_layout.findViewById(R.id.edit_youremailId);
        editNewPassword = (MaterialEditText) signup_layout.findViewById(R.id.edit_yourpassword);

        alertDailog.setView(signup_layout);
        alertDailog.setIcon(R.drawable.ic_person_black_24dp);
        alertDailog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDailog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final User user = new User(editYourSlackId.getText().toString(),
                        editNewPassword.getText().toString(),
                        editNewEmail.getText().toString());

                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(user.getSlackId()).exists())
                            Toast.makeText(MainActivity.this, "User already exits !", Toast.LENGTH_SHORT).show();
                        else {
                            users.child(user.getSlackId())
                                    .setValue(user);
                            Toast.makeText(MainActivity.this, "User registration success!", Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                dialogInterface.dismiss();
            }
        });
        alertDailog.show();
    }

}
