package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String TAG = "JSON_UTILS";

    public static Sandwich parseSandwichJson(String json) {

        // Validate the param
        if (json == null || json.equals(""))
            return null;

        Log.d(TAG, json);

        try {
            JSONObject sandwichData = new JSONObject(json);

            // Get the object "name" from the json file
            JSONObject name = sandwichData.getJSONObject("name");

            String mainName = name.getString("mainName");

            List<String> alsoKnownAsList = new ArrayList<String>();
            JSONArray othersNames = name.getJSONArray("alsoKnownAs");
            for (int i = 0; i < othersNames.length(); i++) {
                alsoKnownAsList.add(othersNames.getString(i));
            }

            String placeOfOrigin = sandwichData.getString("placeOfOrigin");
            String description = sandwichData.getString("description");
            String image = sandwichData.getString("image");

            List<String> ingredientsList = new ArrayList<String>();
            JSONArray sandwichIngredients = sandwichData.getJSONArray("ingredients");
            for (int i = 0; i < sandwichIngredients.length(); i++) {
                ingredientsList.add(sandwichIngredients.getString(i));
            }

            Sandwich myDeliciousSandwich = new Sandwich(mainName, alsoKnownAsList, placeOfOrigin,
                    description, image, ingredientsList);

            return myDeliciousSandwich;

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
            return null;
        }
    }
}
