package com.afzaal.twittifyIt;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainScreenRecyclerAdapter extends RecyclerView.Adapter<MainScreenRecyclerAdapter.PersonViewHolder> {
    List<Person> persons;
    Context context;

    MainScreenRecyclerAdapter(List<Person> persons, Context context){
        this.persons = persons;
        this.context=context;
    }
    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_screen_recycler_adapter, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);

        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder personViewHolder, int i) {

        personViewHolder.personName.setText(persons.get(i).name);
        personViewHolder.personAge.setText(persons.get(i).age);
        personViewHolder.personPhoto.setImageResource(persons.get(i).photoId);

    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    public void deleteItem(List update) {
        persons=update;
        notifyDataSetChanged();


//        notifyItemInserted(0);


    }
    public void addItem(List update) {
        persons=update;
        notifyDataSetChanged();


//        notifyItemInserted(0);


    }
    public class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView personAge;
        CircleImageView personPhoto;
        Button follow;
        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);

            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
            follow=itemView.findViewById(R.id.follow);
            follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String url = "https://twitter.com/#!/" ;
                    Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent1);
                }
            });
            personPhoto = (CircleImageView)itemView.findViewById(R.id.person_photo);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    Toast.makeText(context, ""+pos, Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(context,MainActivity.class);
                    intent.putExtra("pos",pos);
                    intent.putExtra("name",personName.getText());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });


        }


    }
}
