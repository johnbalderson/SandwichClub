package com.udacity.sandwichclub.utils;


import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {


     // This utility converts the JSON data from a string array into a Sandwich JSONObject

    public static Sandwich parseSandwichJson(String json) {

        String TAG = Sandwich.class.getSimpleName();

        // Array list of AKA (other names)
        ArrayList<String> akaList = new ArrayList<>();

        // Array list of ingredients
        ArrayList<String> ingrList = new ArrayList<>();

        if (json != null) {
            // make sure data exists
            try {
                JSONObject jsonObject = new JSONObject(json);

                // get name from Sandwich array
                JSONObject sandwichName = jsonObject.getJSONObject("name");
                String mainName = sandwichName.getString("mainName");

               // get AKA from Sandwich array
                JSONArray sandwichAKA = sandwichName.getJSONArray("alsoKnownAs");
                // if there's data, add it to the akaList array
                if (sandwichAKA.length() > 0) {
                    for (int i = 0; i < sandwichAKA.length(); i++) {
                        akaList.add(sandwichAKA.getString(i));
                    }
                }else {
                    akaList.add("Also Known As (AKA) data not available");
                }

                // get sandwich origin from Sandwich array
                String origin = jsonObject.getString("placeOfOrigin");
                if (origin.isEmpty())
                    origin = "Sandwich origin not available";

                // get description from Sandwich array
                String description = jsonObject.getString("description");
                if (description.isEmpty())
                     description= "Description not available";

                // get image from Sandwich array
                String sandwichImage = jsonObject.getString("image");

                // get ingredients from Sandwich array
                JSONArray ingredients = jsonObject.getJSONArray("ingredients");
                // if there's data add it to the ingredients array
                if (ingredients.length() > 0) {
                    for (int i = 0; i < ingredients.length(); i++) {
                        ingrList.add(ingredients.getString(i));
                    }
                }else {
                    ingrList.add("Ingredients not available");
                }

                // parse the results into the Sandwich JSONObject
                Sandwich sandwichObject = new Sandwich(mainName, akaList,
                        origin, description, sandwichImage, ingrList);

                return sandwichObject;

            } catch (JSONException e) {
                Log.e(TAG,"JSON Parsing Error : " + e.getMessage());
                e.printStackTrace();
            }
        }else{
            Log.e(TAG,"JSON response is null");
        }

        return null;
    }
}

