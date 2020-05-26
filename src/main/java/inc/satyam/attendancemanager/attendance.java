package inc.satyam.attendancemanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class attendance extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference dbreff,gbreff;
    FirebaseAuth mAuth;
    String uid;
    String branch,roll,sem,year, subject;
    List<SubjectModel> modelList;
    RecyclerAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.attendance,container,false);
        recyclerView=v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAuth=FirebaseAuth.getInstance();
        uid=mAuth.getCurrentUser().getUid();
        dbreff= FirebaseDatabase.getInstance().getReference().child("Student").child(uid);
        gbreff=FirebaseDatabase.getInstance().getReference().child("Subjects");
        modelList=new ArrayList<>();
        dbreff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                branch=dataSnapshot.child("Branch").getValue().toString();
                roll=dataSnapshot.child("Roll").getValue().toString();
                sem=dataSnapshot.child("Sem").getValue().toString();
                year=dataSnapshot.child("Year").getValue().toString();
                gbreff.child(branch).child(year).child(sem).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        modelList.clear();
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            String subject=snapshot.getKey();
                            String code=snapshot.child("Code").getValue().toString();
                            String name=snapshot.child("Name").getValue().toString();
                            String Tot=snapshot.child("Total Classes").getValue().toString();
                            SubjectModel model=new SubjectModel(subject,code,Tot,name,branch,roll,sem,year);
                            modelList.add(model);
                        }
                        mAdapter=new RecyclerAdapter(modelList,getActivity());
                        recyclerView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return v;
    }
}
