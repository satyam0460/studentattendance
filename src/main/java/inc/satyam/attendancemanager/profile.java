package inc.satyam.attendancemanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class profile extends Fragment {
    TextView txt1,txt2,txt3,txt4;
    DatabaseReference dbreff;
    FirebaseAuth mAuth;
    String uid;
    String s1,s2,s3,s4;
    ImageView imgv;
    ProgressDialog prg;
    Button btn1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.profile,container,false);
        txt1=v.findViewById(R.id.name);
        txt2=v.findViewById(R.id.branch);
        txt3=v.findViewById(R.id.sem);
        txt4=v.findViewById(R.id.roll);
        mAuth=FirebaseAuth.getInstance();
        uid=mAuth.getCurrentUser().getUid();
        prg=new ProgressDialog(getContext());
        prg.setTitle("Fetching Your Details...");
        btn1=v.findViewById(R.id.btn1);
        prg.setMessage("Please Wait");
        prg.setCanceledOnTouchOutside(false);
        prg.show();
        imgv=v.findViewById(R.id.imgv);
        dbreff= FirebaseDatabase.getInstance().getReference().child("Student").child(uid);
        dbreff.keepSynced(true);
        dbreff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                s1=dataSnapshot.child("Name").getValue().toString();
                s2=dataSnapshot.child("Branch").getValue().toString();
                s3=dataSnapshot.child("Sem").getValue().toString();
                s4=dataSnapshot.child("Roll").getValue().toString();
                txt1.setText("Name: "+s1);
                txt2.setText("Branch: "+s2);
                txt3.setText("Sem: "+s3);
                txt4.setText("Roll: "+s4);
                String img=dataSnapshot.child("Image").getValue().toString();
                if(!img.isEmpty())
                    Picasso.with(getActivity()).load(img).placeholder(R.drawable.ic_person).into(imgv);
                prg.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),UpdateProfile.class);
                intent.putExtra("name",s1);
                intent.putExtra("branch",s2);
                intent.putExtra("sem",s3);
                intent.putExtra("roll",s4);
                startActivity(intent);

            }
        });
        return v;
    }
}
