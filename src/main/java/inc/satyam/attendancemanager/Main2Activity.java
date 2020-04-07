package inc.satyam.attendancemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Main2Activity extends AppCompatActivity {
    FirebaseAuth mAuth;
private BottomNavigationView.OnNavigationItemSelectedListener navlistener=new BottomNavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment selectedFrag=null;
        switch(menuItem.getItemId())
        {
            case R.id.profile:selectedFrag=new profile();
            break;
            case R.id.Attendance:selectedFrag=new attendance();
            break;
            case R.id.time:selectedFrag=new TimeTable();
            break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,selectedFrag).commit();
        return true;
    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth=FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        BottomNavigationView bottomnav=findViewById(R.id.bottom_navigation);
        bottomnav.setOnNavigationItemSelectedListener(navlistener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,new profile()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.logout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout: mAuth.signOut();
                Intent intent=new Intent(Main2Activity.this,MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }
}
