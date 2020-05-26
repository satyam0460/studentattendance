package inc.satyam.attendancemanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
List<SubjectModel> modelList;
Context mContext;

    public RecyclerAdapter(List<SubjectModel> modelList, Context mContext) {
        this.modelList = modelList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.single_subject,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txt1.setText(modelList.get(position).getSubject());
        holder.txt2.setText(modelList.get(position).getCode());
        holder.txt3.setText("Professor: "+modelList.get(position).getProfessor());
        holder.txt4.setText(modelList.get(position).getTotalClasses());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt1,txt2,txt3,txt4;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txt1=itemView.findViewById(R.id.txt1);
            txt2=itemView.findViewById(R.id.txt2);
            txt3=itemView.findViewById(R.id.txt3);
            txt4=itemView.findViewById(R.id.txt6);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int pos=getAdapterPosition();
            Intent intent=new Intent(mContext,CreateAttendance.class);
            intent.putExtra("data",modelList.get(pos));
//            Toast.makeText(mContext,modelList.get(pos).getProfessor(),Toast.LENGTH_SHORT).show();
            mContext.startActivity(intent);
        }
    }
}
