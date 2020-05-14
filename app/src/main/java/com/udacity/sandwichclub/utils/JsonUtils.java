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

    // JSON constants
    private static final String NAME ="name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        // Validate the param
        if (json == null || json.equals(""))
            return null;

        Log.d(TAG, json);

        try {
            JSONObject sandwichData = new JSONObject(json);

            // Get the object "name" from the json file
            JSONObject name = sandwichData.optJSONObject(NAME);

            String mainName = name.optString(MAIN_NAME);

            List<String> alsoKnownAsList = new ArrayList<String>();
            JSONArray othersNames = name.optJSONArray(ALSO_KNOWN_AS);
            for (int i = 0; i < othersNames.length(); i++) {
                alsoKnownAsList.add(othersNames.optString(i));
            }

            String placeOfOrigin = sandwichData.optString(PLACE_OF_ORIGIN);
            String description = sandwichData.optString(DESCRIPTION);
            String image = sandwichData.optString(IMAGE);

            List<String> ingredientsList = new ArrayList<String>();
            JSONArray sandwichIngredients = sandwichData.optJSONArray(INGREDIENTS);
            for (int i = 0; i < sandwichIngredients.length(); i++) {
                ingredientsList.add(sandwichIngredients.optString(i));
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
