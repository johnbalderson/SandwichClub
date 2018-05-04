package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    Sandwich sandwich = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final ImageView ingredientsIv = findViewById(R.id.image_iv);

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

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);
        populateUI();

        setTitle(sandwich.getMainName());
        // Support Up Navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message,
                Toast.LENGTH_SHORT).show();
    }

    /**
     * This method populates the UI with sandwich details
     */

    private void populateUI() {

        // save AKA and ingredients to build text in UI
        String stringAKA ="";
        String stringIngr = "";

        List<String> alsoKnownAs;
        List<String> ingredients;

       // set up TextViews findViewById for data in UI
        TextView mAKA = findViewById(R.id.also_known_tv);
        TextView mOrigin = findViewById(R.id.origin_tv);
        TextView mDescription = findViewById(R.id.description_tv);
        TextView mIngredients = findViewById(R.id.ingredients_tv);

       // get AKA list values
        alsoKnownAs = sandwich.getAlsoKnownAs();
        for(int i=0;i<alsoKnownAs.size();i++){
          stringAKA = stringAKA + "\n" + alsoKnownAs.get(i);
        }

        /* get the values of ingredients List */
        ingredients = sandwich.getIngredients();
        for(int i=0;i<ingredients.size();i++){
          stringIngr = stringIngr + "\n" + ingredients.get(i);
        }

       // put text in UI
        // also known as (AKA)
        mAKA.setText(stringAKA);
        // ingredients
        mIngredients.setText(stringIngr);
        // place of origin
        mOrigin.setText(sandwich.getPlaceOfOrigin().toString());
        // description
        mDescription.setText(sandwich.getDescription().toString());

    }
}
