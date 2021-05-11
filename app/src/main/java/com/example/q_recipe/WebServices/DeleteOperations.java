package com.example.q_recipe.WebServices;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.q_recipe.Business.LoggedInUser;
import com.example.q_recipe.ENV.GlobalVariables;
import com.example.q_recipe.EditRecipeByAdminActivity;
import com.example.q_recipe.Models.Recipe;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class DeleteOperations {
    private GetOperations getOperations = new GetOperations();
    private String[] recipeNames;
    private String[] ids;
    public void deleteRecipe(Context context, String id, ListView listView) {

        String urlUpload = GlobalVariables.API_URL + "/api/admin/deleteRecipe";


        JSONObject postDataParams = new JSONObject();
        try {

            postDataParams.put("id", id);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        String data = postDataParams.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpload, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<Recipe> recipeList = getOperations.getRecipes();
                recipeNames = new String[recipeList.size()];
                ids = new String[recipeList.size()];

                for(int i = 0; i<recipeList.size(); i++){
                    recipeNames[i] = recipeList.get(i).getName();
                    ids[i] = recipeList.get(i).getId();
                }
                ArrayAdapter<String> recipeNamesAdapter = new  ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, android.R.id.text1, recipeNames);
                listView.setAdapter(recipeNamesAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return data == null ? null : data.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {

                    return null;
                }
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}

