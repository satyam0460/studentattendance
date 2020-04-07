package inc.satyam.attendancemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    String spinVal, nme, yr, rol, sem, password,getEmail;
    FirebaseAuth mAuth;
    EditText name, year, rollno, pass, mail;
    Button btn1;
    RadioGroup rgp;
    RadioButton rb;
    ProgressDialog prg;
    String email;
    DatabaseReference dbreff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        spinner=findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> arradat=ArrayAdapter.createFromResource(this,R.array.branches,android.R.layout.simple_spinner_item);
        arradat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arradat);
        spinner.setOnItemSelectedListener(this);
        mAuth=FirebaseAuth.getInstance();
        name=findViewById(R.id.edt1);
        year=findViewById(R.id.edt5);
        rollno=findViewById(R.id.edt6);
        btn1=findViewById(R.id.btn1);
        rgp=findViewById(R.id.rg1);
        prg=new ProgressDialog(this);
        prg.setTitle("Please Wait");
        prg.setMessage("Setting You Up");
        prg.setCanceledOnTouchOutside(false);
        pass=findViewById(R.id.edt7);
        mail=findViewById(R.id.edt8);
        dbreff= FirebaseDatabase.getInstance().getReference().child("Student");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nme=name.getText().toString();
                yr=year.getText().toString();
                rol=rollno.getText().toString();
                int radioId=rgp.getCheckedRadioButtonId();
                rb=findViewById(radioId);
                if(rb!=null)
                    sem=rb.getText().toString();
//                else {
//                    Toast.makeText(SignUp.this, "Please Fill in All The Details", Toast.LENGTH_SHORT).show();
//                }
                password=pass.getText().toString();
                getEmail=mail.getText().toString();
                if(nme==null||yr==null||rol==null||sem==null||spinVal==null||password==null||getEmail==null||rb==null)
                {
                    Toast.makeText(SignUp.this,"Please Fill in All The Details",Toast.LENGTH_SHORT).show();
                }
                else if(nme.isEmpty()||yr.isEmpty()||rol.isEmpty()||sem.isEmpty()||spinVal.isEmpty()||password.isEmpty()||getEmail.isEmpty())
                {
                    Toast.makeText(SignUp.this,"Please Fill in All The Details",Toast.LENGTH_SHORT).show();
                }
                else
                {
//                    getEmail=getEmail.substring(0,getEmail.lastIndexOf('@')).toUpperCase();
                    rol=rol.toUpperCase();
                    int se=2020-Integer.parseInt(yr);
                    if(sem.equalsIgnoreCase("Even Semester"))
                    {
                        se=se*2;
                    }
                    else
                    {
                        se=se*2-1;
                    }
                    final String k=se+"";
                    email=getEmail;
                    prg.show();
                    mAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            mAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    String uid=mAuth.getCurrentUser().getUid();
                                    HashMap<String,String> map=new HashMap<>();
                                    map.put("Name",nme);
                                    map.put("Year",yr);
                                    map.put("Roll",rol);
                                    map.put("Semester",sem);
                                    map.put("Branch",spinVal);
                                    map.put("Email",email);
                                    map.put("Sem",k);
                                    map.put("Image","");
                                    dbreff.child(uid).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(SignUp.this,"Success! A verificstion Email has been sent to your mail Account. \n Verify Your Email And Sign In!",Toast.LENGTH_LONG).show();
                                            prg.dismiss();
                                            finish();
                                        }
                                    });
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUp.this,"Some Unexpected Error Occured. Please try after Sometime",Toast.LENGTH_SHORT).show();
                            prg.dismiss();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        spinVal=adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
