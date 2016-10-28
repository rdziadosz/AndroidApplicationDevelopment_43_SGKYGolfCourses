package eu.dziadosz.sgkygolfcourses;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    public ImageView courseImageView;
    //public TextView courseNameTextView;
    public TextView coursePositionTextView;
    public TextView courseEmailTextView;
    public TextView coursePhoneTextView;
    public TextView courseDescriptionTextView;
    public TextView courseUrlTextView;
    Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        course = (Course) getIntent().getSerializableExtra("course");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.details_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(course.name);
        ActionBar ab = getSupportActionBar();

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        courseImageView = (ImageView) findViewById(R.id.imageView);
        //courseNameTextView = (TextView) findViewById(R.id.nameTextView);
        coursePositionTextView = (TextView) findViewById(R.id.positionTextView);
        courseEmailTextView = (TextView) findViewById(R.id.emailTextView);
        coursePhoneTextView = (TextView) findViewById(R.id.phoneTextView);
        courseDescriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        courseUrlTextView = (TextView) findViewById(R.id.urlTextView);

        //viewHolder.courseImageView.setImageResource(course.photoId);
        Picasso
                .with(this)
                .load("http://ptm.fi/jamk/android/golfcourses/" + course.photo)
                .into((ImageView) courseImageView);
        //courseNameTextView.setText(course.name);
        coursePositionTextView.setText(course.position);
        courseEmailTextView.setText(course.email);
        coursePhoneTextView.setText(course.phone);
        courseUrlTextView.setText(course.url);
        courseDescriptionTextView.setText(course.description);
    }

    public void openMap(View view) {
        // show map
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:<" + course.lat + ">,<" + course.lng + ">?q=<" + course.lat + ">,<" + course.lng + ">(" + course.name + ")"));
        startActivity(intent);
    }
}
