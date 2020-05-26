package inc.satyam.attendancemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    TextView txt1;
    FirebaseAuth mAuth;
    Button btn1;
    EditText edt1, edt2;
    String email, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt1=findViewById(R.id.txt2);
        mAuth=FirebaseAuth.getInstance();
        btn1=findViewById(R.id.btn1);
        edt1=findViewById(R.id.edt1);
        edt2=findViewById(R.id.edt2);
        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SignUp.class);
                startActivity(intent);
            }
        });
        if(mAuth.getCurrentUser()!=null&&mAuth.getCurrentUser().isEmailVerified())
        {
            Intent inte=new Intent(MainActivity.this,Main2Activity.class);
            startActivity(inte);
            finish();
        }
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Signing You In...", Toast.LENGTH_SHORT).show();
                email=edt1.getText().toString();
                pass=edt2.getText().toString();
                btn1.setEnabled(false);
                if(email.isEmpty()||pass.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Enter All the details to continue", Toast.LENGTH_SHORT).show();
                    btn1.setEnabled(true);
                }
                 else {
                     mAuth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                         @Override
                         public void onSuccess(AuthResult authResult) {
                             if(mAuth.getCurrentUser().isEmailVerified())
                             {
                                 Intent inten=new Intent(MainActivity.this,Main2Activity.class);
                                 startActivity(inten);
                                 finish();
                                 btn1.setEnabled(true);
                             }
                             else
                             {
                                 Toast.makeText(MainActivity.this,"Please Verify Your Email First",Toast.LENGTH_SHORT).show();
                                 btn1.setEnabled(true);
                             }
                         }
                     }).addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                             Toast.makeText(MainActivity.this,"Some Unexpected Error Occured",Toast.LENGTH_SHORT).show();
                             btn1.setEnabled(true);
                         }
                     });
                }
            }
        });
    }
}
