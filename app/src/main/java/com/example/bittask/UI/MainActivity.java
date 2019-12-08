package com.example.bittask.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bittask.Adapter.HomeAdapter;
import com.example.bittask.Model.HomeModel;
import com.example.bittask.R;
import com.example.bittask.Utils.Constants;
import com.example.bittask.Utils.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.profileIv)
    CircleImageView profileIv;
    @BindView(R.id.profileNameTv)
    TextView profileNameTv;
    @BindView(R.id.locationNameTv)
    TextView locationNameTv;
    @BindView(R.id.bioTv)
    TextView bioTv;
    @BindView(R.id.postsTv)
    TextView postsTv;
    @BindView(R.id.followersTv)
    TextView followersTv;
    @BindView(R.id.followingTv)
    TextView followingTv;
    @BindView(R.id.photosRv)
    RecyclerView photosRv;
    @BindView(R.id.loading)
    ProgressBar loading;

    ArrayList<HomeModel> homeList;
    HomeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ButterKnife
        ButterKnife.bind(this);

        //home
        homeList = new ArrayList<>();
        adapter = new HomeAdapter(this, homeList);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        photosRv.setLayoutManager(layoutManager);
        photosRv.setAdapter(adapter);

        getProfile();

        getHome();

    }

    /**
     * get profile data
     */
    private void getProfile() {
        String url = Constants.PROFILE_BASE_URL;
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                loading.setVisibility(View.GONE);
                if (response.getBoolean("status")) {
                    JSONObject data = response.getJSONObject("data");
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.placeholder(R.drawable.ic_placeholder_48dp);
                    Glide.with(MainActivity.this).load(data.getString("profile_picture")).apply(new RequestOptions()
                            .placeholder(R.drawable.ic_placeholder_48dp)
                            .centerCrop())
                            .into(profileIv);
                    profileNameTv.setText(data.getString("full_name"));
                    locationNameTv.setText(data.getString("location"));
                    bioTv.setText(data.getString("bio"));
                    JSONObject counts = data.getJSONObject("counts");
                    postsTv.setText(Integer.toString(counts.getInt("posts")));
                    followersTv.setText(Integer.toString(counts.getInt("followers")));
                    followingTv.setText(Integer.toString(counts.getInt("following")));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(MainActivity.this, getResources().getString(R.string.error_message), Toast.LENGTH_LONG).show());

        MySingleton.getInstance(MainActivity.this).addToRequestQueue(objectRequest);
    }

    /**
     * get home data
     */
    private void getHome() {
        String url = Constants.HOME_BASE_URL;
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                loading.setVisibility(View.GONE);
                if (response.getBoolean("status")) {
                    JSONArray data = response.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject homeData = data.getJSONObject(i);
                        HomeModel model = new HomeModel();
                        model.setId(homeData.getInt("id"));
                        model.setImage(homeData.getString("image"));
                        model.setTitle(homeData.getString("title"));

                        homeList.add(model);
                    }

                    adapter.notifyDataSetChanged();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            loading.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, getResources().getString(R.string.error_message), Toast.LENGTH_LONG).show();
        });

        MySingleton.getInstance(MainActivity.this).addToRequestQueue(objectRequest);
    }
}
