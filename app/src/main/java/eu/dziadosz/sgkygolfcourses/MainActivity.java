package eu.dziadosz.sgkygolfcourses;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public List<Course> mCourseList;

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FetchDataTask task = new FetchDataTask();
        task.execute("http://ptm.fi/jamk/android/golfcourses/golf_courses.json");
    }


    class FetchDataTask extends AsyncTask<String, Void, JSONObject> {

        private JSONArray jsonData;

        @Override
        protected JSONObject doInBackground(String... urls) {
            HttpURLConnection urlConnection = null;
            JSONObject json = null;
            try {
                URL url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                json = new JSONObject(stringBuilder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) urlConnection.disconnect();
            }
            return json;
        }

        protected void onPostExecute(JSONObject json) {
            try {
                jsonData = json.getJSONArray("kentat");
                mCourseList = new ArrayList<>();

                for (int i = 0; i < jsonData.length(); i++) {
                    JSONObject course = jsonData.getJSONObject(i);
                    mCourseList.add(new Course(Html.fromHtml(course.getString("Kentta")).toString(), course.getString("Osoite"), course.getString("Sahkoposti"), course.getString("Puhelin"), course.getString("Kuva"), course.getDouble("lat"), course.getDouble("lng"), course.getString("Webbi"), course.getString("Kuvaus")));
                }

                // connect recycler view
                mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                // create layoutmanager
                mLayoutManager = new LinearLayoutManager(context);
                // set manager to recycler view
                mRecyclerView.setLayoutManager(mLayoutManager);
                // create adapter
                mAdapter = new CoursesAdapter(mCourseList);
                // set adapter to recycler view
                mRecyclerView.setAdapter(mAdapter);
            } catch (JSONException e) {
                Log.e("JSON", "Error getting data.");
            }
        }


    }
}
