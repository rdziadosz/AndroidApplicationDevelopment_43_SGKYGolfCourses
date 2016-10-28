package eu.dziadosz.sgkygolfcourses;

/**
 * Created by Radosław on 27.10.2016.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;


public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.ViewHolder> {
    // adapter data
    private List<Course> courseList;
    Context context;


    // adapter constructor, get data from activity
    public CoursesAdapter(List<Course> courseList) {
        this.courseList = courseList;
    }

    // return the size of courseList (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return courseList.size();
    }

    // create a view for this card
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_card, parent, false);
        return new ViewHolder(view);
    }

    // replace the contents of a view (invoked by the layout manager)
    // - get element from courselist at this position
    // - replace the contents of the view with that element
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Course course = courseList.get(position);
        //viewHolder.courseImageView.setImageResource(course.photoId);
        Picasso
                .with(context)
                .load("http://ptm.fi/jamk/android/golfcourses/" + course.photo)
                //.fit() // will explain later
                .into((ImageView) viewHolder.courseImageView);
        viewHolder.courseNameTextView.setText(course.name);
        viewHolder.coursePositionTextView.setText(course.position);
        viewHolder.courseEmailTextView.setText(course.email);
        viewHolder.coursePhoneTextView.setText(course.phone);
    }

    // view holder class to specify card UI objects
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView courseImageView;
        public TextView courseNameTextView;
        public TextView coursePositionTextView;
        public TextView courseEmailTextView;
        public TextView coursePhoneTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            // get layout ids
            courseImageView = (ImageView) itemView.findViewById(R.id.imageView);
            courseNameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            coursePositionTextView = (TextView) itemView.findViewById(R.id.positionTextView);
            courseEmailTextView = (TextView) itemView.findViewById(R.id.emailTextView);
            coursePhoneTextView = (TextView) itemView.findViewById(R.id.phoneTextView);
            // add click listner for a card
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    String name = courseList.get(position).name;
                    //Toast.makeText(view.getContext(), name, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                    intent.putExtra("course", (Serializable) courseList.get(position));
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

}