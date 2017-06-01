package inninc.nieplacementcellapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {


    EditText uname, pass;
    Button signIn, register, forgotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        checkFirstRun();

        uname = (EditText)findViewById(R.id.uname);
        pass = (EditText)findViewById(R.id.password);

        signIn = (Button)findViewById(R.id.btnSignIn);
        register = (Button)findViewById(R.id.btnRegister);

        forgotPass = (Button)findViewById(R.id.btnForgotPassword);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent("inninc.nieplacementcellapp.Register");
                startActivity(intent);

            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        Login.this);

                // Setting Dialog Title
                alertDialog2.setTitle("Forgot Your Password?");

                // Setting Dialog Message
                alertDialog2.setMessage("Enter Your Birthdate in dd/mm/yyyy format");

                // Setting Icon to Dialog


                // Setting Positive "Yes" Btn
                alertDialog2.setPositiveButton("Done",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                            }
                        });

// Setting Negative "NO" Btn


// Showing Alert Dialog
                alertDialog2.show();
            }
        });

        final StudentDBHandler db = new StudentDBHandler(Login.this);
        final String chk = db.getKeyRegistered();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(chk!=null){

                    Student student = db.getProfile();

                    Log.d("uname",student.getUsn());
                    Log.d("pass:",student.getPassword());

                    if(uname.getText().toString().equals(student.getUsn()) && pass.getText().toString().equals(student.getPassword())){

                        Intent intent = new Intent("inninc.nieplacementcellapp.MainActivity");
                        startActivity(intent);

                        finish();

                    } else if(uname.getText().toString().equals(student.getUsn()) && pass.getText().toString().equals(student.getDob())){

                        Intent intent = new Intent("inninc.nieplacementcellapp.MainActivity");
                        startActivity(intent);

                        finish();

                    } else if(uname.getText().toString().equals("admin@nie") && pass.getText().toString().equals("nietpo")){

                        Intent intent = new Intent("inninc.nieplacementcellapp.MainActivity");
                        startActivity(intent);

                        finish();

                    } else {

                        if(!uname.getText().toString().equals(student.getUsn())){

                            Context context = getApplicationContext();
                            CharSequence text = "WRONG Username! Your USN in caps is the Username";
                            int duration = Toast.LENGTH_LONG;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                        }
                        else{

                            Context context = getApplicationContext();
                            CharSequence text = "WRONG Password!";
                            int duration = Toast.LENGTH_LONG;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                        }



                    }

                }

                else {

                    Context context = getApplicationContext();
                    CharSequence text = "Please Register!";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }

            }
        });




    }

    //Actions to do when the app is launched for the first time
    public void checkFirstRun() {

        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -1;


        // Get current version code
        int currentVersionCode = 0;
        try {
            currentVersionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            // handle exception
            e.printStackTrace();
            return;
        }

        // Get saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {

            // This is just a normal run
            return;

        } else if (savedVersionCode == DOESNT_EXIST) {

            // TODO This is a new install (or the user cleared the shared preferences)

            Intent intent = new Intent("inninc.nieplacementcellapp.Register");
            startActivity(intent);

            finish();

        } else if (currentVersionCode > savedVersionCode) {

            // TODO This is an upgrade

        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).commit();

    }

}
