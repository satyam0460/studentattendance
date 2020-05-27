package inc.satyam.attendancemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UpdateProfile extends AppCompatActivity {
EditText edt1,edt2;
TextView txt1,txt2;
Button btn1;
DatabaseReference dbreff;
FirebaseAuth mAuth;
String s1,s2,s3,s4,semester;
ProgressDialog prg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        edt1=findViewById(R.id.name);
        edt2=findViewById(R.id.sem);
        txt1=findViewById(R.id.branch);
        txt2=findViewById(R.id.roll);
        btn1=findViewById(R.id.btn1);
        Intent i=getIntent();
        prg=new ProgressDialog(this);
        prg.setMessage("Please Wait");
        prg.setCanceledOnTouchOutside(false);
        s1=i.getStringExtra("name");
        s2=i.getStringExtra("sem");
        s3=i.getStringExtra("branch");
        s4=i.getStringExtra("roll");
        mAuth=FirebaseAuth.getInstance();
        edt1.setText(s1);
        edt2.setText(s2);
        txt1.setText(s3);
        txt2.setText(s4);
        dbreff= FirebaseDatabase.getInstance().getReference().child("Student").child(mAuth.getCurrentUser().getUid());
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s1=edt1.getText().toString().trim();
                s2=edt2.getText().toString().trim();
                if(s1.isEmpty()||s2.isEmpty()){
                    Toast.makeText(UpdateProfile.this,"Please enter All Details to Continue",Toast.LENGTH_SHORT).show();
                }
                else {
                    prg.show();
                    int sem1=Integer.parseInt(s2);
                    if(sem1%2==0){
                        semester="Even Semester";
                    }
                    else {
                        semester="Odd Semester";
                    }
                    dbreff.child("Name").setValue(s1).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            dbreff.child("Sem").setValue(s2+"").addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                dbreff.child("Semester").setValue(semester).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                       Toast.makeText(UpdateProfile.this,"Successfully Updated",Toast.LENGTH_SHORT).show();
                                       prg.dismiss();
                                       finish();
                                    }
                                })    ;
                                }
                            });
                        }
                    });
                }
            }
        });
    }
}
