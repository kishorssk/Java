package inninc.nieplacementcellapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {


    private EditText inputName, inputEmail, inputPhone, inputUsn, inputDOB, inputCGPA, inputCGPAPercentage, inputPUC, inputSchool, inputBranch, inputPassword;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPhone, inputLayoutUSN, inputLayoutDOB, inputLayoutBranch, inputLayoutCGPA, inputLayoutCGPAPercent, inputLayoutPUC, inputLayoutSchool ;
    private Button register;

    Integer flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPhone = (TextInputLayout) findViewById(R.id.input_layout_phone);
        inputLayoutUSN = (TextInputLayout) findViewById(R.id.input_layout_usn);
        inputLayoutDOB = (TextInputLayout) findViewById(R.id.input_layout_dob);
        inputLayoutBranch = (TextInputLayout) findViewById(R.id.input_layout_branch);
        inputLayoutCGPA = (TextInputLayout) findViewById(R.id.input_layout_cgpa);
        inputLayoutCGPAPercent = (TextInputLayout) findViewById(R.id.input_layout_cgpapercent);
        inputLayoutPUC = (TextInputLayout) findViewById(R.id.input_layout_puc);
        inputLayoutSchool = (TextInputLayout) findViewById(R.id.input_layout_school);

        inputName = (EditText) findViewById(R.id.input_name);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPhone = (EditText) findViewById(R.id.input_phone);
        inputUsn = (EditText) findViewById(R.id.input_usn);
        inputDOB = (EditText) findViewById(R.id.input_dob);
        inputBranch = (EditText) findViewById(R.id.input_branch);
        inputCGPA = (EditText) findViewById(R.id.input_cgpa);
        inputCGPAPercentage = (EditText) findViewById(R.id.input_cgpapercent);
        inputPUC = (EditText) findViewById(R.id.input_puc);
        inputSchool = (EditText) findViewById(R.id.input_school);
        inputPassword = (EditText)findViewById(R.id.input_password);

        register = (Button)findViewById(R.id.btn_register);

        inputName.addTextChangedListener(new MyTextWatcher(inputName));
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPhone.addTextChangedListener(new MyTextWatcher(inputPhone));
        inputDOB.addTextChangedListener(new MyTextWatcher(inputDOB));
        inputUsn.addTextChangedListener(new MyTextWatcher(inputUsn));
        inputBranch.addTextChangedListener(new MyTextWatcher(inputBranch));
        inputCGPA.addTextChangedListener(new MyTextWatcher(inputCGPA));
        inputPUC.addTextChangedListener(new MyTextWatcher(inputPUC));
        inputSchool.addTextChangedListener(new MyTextWatcher(inputSchool));

        final StudentDBHandler db = new StudentDBHandler(Register.this);
        final String chk = db.getKeyRegistered();

        if(chk!=null){

            Student student = db.getProfile();

            inputName.setText(student.getName());
            inputEmail.setText(student.getEmail());
            inputPhone.setText(student.getPhone());
            inputUsn.setText(student.getUsn());
            inputDOB.setText(student.getDob());
            inputBranch.setText(student.getBranch());
            inputCGPA.setText(student.getCgpa());
            inputCGPAPercentage.setText(student.getCgpaPercent());
            inputPUC.setText(student.getPucMarks());
            inputSchool.setText(student.getSchoolMarks());
            inputPassword.setText(student.getPassword());

            register.setText("Update");

        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("registrations");

                Student student = new Student();

                student.setName(inputName.getText().toString().toUpperCase());
                student.setEmail(inputEmail.getText().toString());
                student.setUsn(inputUsn.getText().toString().toUpperCase());
                student.setPhone(inputPhone.getText().toString());
                student.setDob(inputDOB.getText().toString());
                student.setBranch(inputBranch.getText().toString().toUpperCase());

                student.setCgpa(inputCGPA.getText().toString());
                student.setCgpaPercent(inputCGPAPercentage.getText().toString());
                student.setPucMarks(inputPUC.getText().toString());
                student.setSchoolMarks(inputSchool.getText().toString());

                student.setPassword(inputPassword.getText().toString());
                student.setPlaced("Not Yet Placed");



                if (flag == 1) {
                    Toast.makeText(getApplicationContext(), "Please enter the required details", Toast.LENGTH_LONG).show();
                }
                else {


                    if(chk!=null){

                        Student deleteS = db.getProfile();


                        if(!inputUsn.getText().toString().equals(deleteS.getUsn())){

                            Context context = getApplicationContext();
                            CharSequence text = "You Can't Change Your USN!";
                            int duration = Toast.LENGTH_LONG;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                        }
                        else {

                            db.deleteProfile(deleteS.getUsn());

                            mDatabase.child(inputUsn.getText().toString().toUpperCase()).setValue(student);

                            Context context = getApplicationContext();
                            CharSequence text = "Your Details have been updated!";
                            int duration = Toast.LENGTH_LONG;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                            db.addProfile(student);
                        }


                    }
                    else {
                        //Storing values to firebase
                        //ref.child("feedback").setValue(person);

                        // Creating new user node, which returns the unique key value
                        String id = mDatabase.push().getKey();
                        // new user node would be /users/$userid/

                        // pushing user to 'users' node using the userId
                        mDatabase.child(inputUsn.getText().toString().toUpperCase()).setValue(student);

                        Context context = getApplicationContext();
                        CharSequence text = "Registration Successful. Good Luck!";
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        db.addProfile(student);

                        Intent intent = new Intent("inninc.nieplacementcellapp.MainActivity");
                        startActivity(intent);

                        finish();
                    }

                }

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    /**
     * Validating form
     */
    private void submitForm() {
        if (!validateName()) {
            flag=1;
        }

        if (!validateEmail()) {
            flag=1;
        }

        if (!validatePhone()) {
            flag=1;
        }

        if (!validateUSN()) {
            flag=1;
        }

        if (!validateDOB()) {
            flag=1;
        }

        if (!validateBranch()) {
            flag=1;
        }

        if (!validateCGPA()) {
            flag=1;
        }

        if (!validatePUC()) {
            flag=1;
        }

        if (!validateSchool()) {
            flag=1;
        }

    }

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError("Please Enter Your Full Name");
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError("Please enter a Valid email address");
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePhone() {

        int len = inputPhone.getText().toString().length();

        if (inputPhone.getText().toString().trim().isEmpty()) {
            inputLayoutPhone.setError("Please Enter Your Mobile No.");
            requestFocus(inputPhone);
            return false;
        } else if(len < 10){
            inputLayoutPhone.setError("Please Enter A Valid 10 Digit Mobile No.");
            requestFocus(inputPhone);
            return false;
        } else {
            inputLayoutPhone.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateUSN() {
        if (inputUsn.getText().toString().trim().isEmpty()) {
            inputLayoutUSN.setError("Please Enter Your USN");
            requestFocus(inputUsn);
            return false;
        } else {
            inputLayoutUSN.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateDOB() {
        if (inputDOB.getText().toString().trim().isEmpty()) {
            inputLayoutDOB.setError("Please Enter Your DOB");
            requestFocus(inputDOB);
            return false;
        } else {
            inputLayoutDOB.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateBranch() {
        if (inputBranch.getText().toString().trim().isEmpty()) {
            inputLayoutBranch.setError("Please Enter Your Branch");
            requestFocus(inputBranch);
            return false;
        } else {
            inputLayoutBranch.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateCGPA() {
        if (inputCGPA.getText().toString().trim().isEmpty()) {
            inputLayoutCGPA.setError("Please Enter Your CGPA");
            requestFocus(inputCGPA);
            return false;
        } else {
            inputLayoutCGPA.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePUC() {
        if (inputPUC.getText().toString().trim().isEmpty()) {
            inputLayoutPUC.setError("Please Enter Your 12th/2nd PU Percentage");
            requestFocus(inputPUC);
            return false;
        } else {
            inputLayoutPUC.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateSchool() {
        if (inputSchool.getText().toString().trim().isEmpty()) {
            inputLayoutSchool.setError("Please Enter Your 10th Percentage");
            requestFocus(inputSchool);
            return false;
        } else {
            inputLayoutSchool.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    validateName();
                    break;
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_phone:
                    validatePhone();
                    break;
                case R.id.input_usn:
                    validateUSN();
                    break;
                case R.id.input_dob:
                    validateDOB();
                    break;
                case R.id.input_branch:
                    validateBranch();
                    break;
                case R.id.input_cgpa:
                    validateCGPA();
                    break;
                case R.id.input_puc:
                    validatePUC();
                    break;
                case R.id.input_school:
                    validateSchool();
                    break;
            }
        }
    }


}
