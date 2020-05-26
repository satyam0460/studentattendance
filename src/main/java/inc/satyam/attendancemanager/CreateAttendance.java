package inc.satyam.attendancemanager;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CreateAttendance extends AppCompatActivity {
    DatabaseReference dbreff,gbreff;
    String branch, roll, sem, year, subject, totalAttendane;
    TextView text,sub,myattd,totclass,code,professor;
    String status="off";
    Button btn1;
    int tot,yourTotalAttend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_attendance);
        dbreff = FirebaseDatabase.getInstance().getReference().child("Subjects");
        Intent i = getIntent();
        SubjectModel model = i.getParcelableExtra("data");
        branch = model.getBranch();
        btn1=findViewById(R.id.button);
        roll = model.getRoll();
        sub=findViewById(R.id.subject2);
        myattd=findViewById(R.id.attendance2);
        totclass=findViewById(R.id.totalClass2);
        code=findViewById(R.id.code2);
        professor=findViewById(R.id.professor2);
        sub.setText(model.getSubject());
        professor.setText(model.getProfessor());
        totclass.setText(model.getTotalClasses());
        code.setText(model.getCode());
        text=findViewById(R.id.status2);
        sem = model.getSem();
        year = model.getYear();
        subject=model.getSubject();
        dbreff.child(branch).child(year).child(sem).child(subject).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                status=dataSnapshot.child("Attendance").getValue().toString();
                totalAttendane=dataSnapshot.child("Total Classes").getValue().toString();
                tot=Integer.parseInt(totalAttendane);
                if(dataSnapshot.hasChild(roll)){
                    String att=dataSnapshot.child(roll).child("Your Attendance").getValue().toString();
                    yourTotalAttend=Integer.parseInt(dataSnapshot.child(roll).child("YourTotalAttendance").getValue().toString());
                    myattd.setText(att);
                }
                else {
                    myattd.setText("0");
                    yourTotalAttend=0;
                }
                totclass.setText(totalAttendane);
                text.setText(status);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status=text.getText().toString();
                if(status.equalsIgnoreCase("off")){
                    Toast.makeText(CreateAttendance.this,"Teacher didnt open the attendance portal",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(tot>yourTotalAttend){
                        Map<String,String> nemmap=new HashMap<>();
                        gbreff=FirebaseDatabase.getInstance().getReference().child("Subjects").child(branch).child(year).child(sem).child(subject).child(roll);
                        nemmap.put("Your Attendance",Integer.parseInt(myattd.getText().toString())+1+"");
                        nemmap.put("YourTotalAttendance",tot+"");
                        gbreff.setValue(nemmap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(CreateAttendance.this,"Attendance Successful",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        Toast.makeText(CreateAttendance.this,"You've marked your attendance for today",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}

