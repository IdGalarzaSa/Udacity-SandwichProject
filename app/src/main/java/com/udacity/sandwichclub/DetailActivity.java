package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private final String TAG = "DETAIL_ACTIVITY";

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    //Views
    private ImageView sandwichImageIv;
    private ProgressBar imageProgressBar;
    private TextView mainNameTv;
    private TextView alsoKnownAsLabelTv;
    private TextView alsoKnownAsTv;
    private TextView ingredientsTv;
    private TextView placeOfOriginLabelTv;
    private TextView placeOfOriginTv;
    private TextView descriptionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        /*  This method init all views, I init all views here because I want init the views
            just If this activity has data. */
        initViews();

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        /*
            I use a progressBar instead a placeholder because I want practice how handler
            the visibility into a frame layout.
         */
        imageProgressBar.setVisibility(View.VISIBLE);
        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(sandwichImageIv, new Callback() {

                    @Override
                    public void onSuccess() {
                        imageProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        closeOnError();
                    }
                });

        setTitle(sandwich.getMainName());
    }

    // Init all the views in this activity
    private void initViews() {
        sandwichImageIv = (ImageView) findViewById(R.id.image_iv);
        imageProgressBar = (ProgressBar) findViewById(R.id.image_progressBar_pb);
        mainNameTv = (TextView) findViewById(R.id.mainName_tv);
        alsoKnownAsLabelTv = (TextView) findViewById(R.id.also_known_as_label_tv);
        alsoKnownAsTv = (TextView) findViewById(R.id.alsoKnownAs_tv);
        ingredientsTv = (TextView) findViewById(R.id.ingredients_tv);
        placeOfOriginLabelTv = (TextView) findViewById(R.id.place_of_origin_label_tv);
        placeOfOriginTv = (TextView) findViewById(R.id.placeOfOrigin_tv);
        descriptionTv = (TextView) findViewById(R.id.description_tv);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        mainNameTv.setText(sandwich.getMainName());

        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        if (alsoKnownAsList.size() > 0) {
            showAlsoKnownInformation();
            // the "- " is for the first item of the list
            String alsoKnownString = "- " + TextUtils.join("\n- ", alsoKnownAsList);
            alsoKnownAsTv.setText(alsoKnownString);
        } else {
            hideAlsoKnownInformation();
        }

        // Ingredients TextView
        // the "- " is for the first item of the list
        String ingredientsString = "- " + TextUtils.join("\n - ", sandwich.getIngredients());
        ingredientsTv.setText(ingredientsString);

        // Origin TextView
        String placeOrigin = sandwich.getPlaceOfOrigin();
        if (placeOrigin != null && !placeOrigin.equals("")) {
            showPlaceOfOrigin();
            placeOfOriginTv.setText(placeOrigin);
        } else {
            hidePlaceOfOrigin();
        }

        descriptionTv.setText(sandwich.getDescription());
    }

    private void showAlsoKnownInformation() {
        alsoKnownAsLabelTv.setVisibility(View.VISIBLE);
        alsoKnownAsTv.setVisibility(View.VISIBLE);
    }

    private void showPlaceOfOrigin() {
        placeOfOriginLabelTv.setVisibility(View.VISIBLE);
        placeOfOriginTv.setVisibility(View.VISIBLE);
    }

    /*  I used GONE instead INVISIBLE because I don't want to keep the space of the text views
        when it's hasn't data.
     */
    private void hideAlsoKnownInformation() {
        alsoKnownAsLabelTv.setVisibility(View.GONE);
        alsoKnownAsTv.setVisibility(View.GONE);
    }

    private void hidePlaceOfOrigin() {
        placeOfOriginLabelTv.setVisibility(View.GONE);
        placeOfOriginTv.setVisibility(View.GONE);
    }

}
