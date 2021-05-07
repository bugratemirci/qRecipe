package com.example.q_recipe.WebServices;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.q_recipe.ENV.GlobalVariables;
import com.example.q_recipe.Models.Ingredient;
import com.example.q_recipe.Models.Recipe;
import com.example.q_recipe.Models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetOperations {
    private String jsonData = "";

    //Json datasını indirip okur, String olarak geri döner.
    private String GetJsonData(String _url) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        URL uri = new URL(_url);
        HttpURLConnection urlConnection = (HttpURLConnection) uri.openConnection();

        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bin = new BufferedReader(new InputStreamReader(in));
            String inputLine;

            while ((inputLine = bin.readLine())!= null)
            {
                sb.append(inputLine);
            }
        }
        finally
        {
            urlConnection.disconnect();
        }

        return sb.toString();
    }

    public List<User> getUsers(){
        List<User> userModelList = null;
        String url = GlobalVariables.API_URL + "/api/users";
        try{
            // + Json verisi çekiliyor.
            jsonData = GetJsonData(url);
            // -

            // + Gelen veri liste şeklinde olacağı için
            // Modelimizin Liste tipinde olacağını belirtiyoruz.
            Type userModelListType = new TypeToken<List<User>>(){}.getType();
            // -

            // + Gson ile Json verisi okunur ve Listeye alınır.
            Gson gson = new Gson();
            userModelList  = gson.fromJson(jsonData, userModelListType);
            // -


        }
        catch (Exception exception){
            exception.printStackTrace();
        }
        finally {
            return userModelList;
        }
    }

    public List<Recipe> getRecipes(){
        List<Recipe> recipeModelList = null;
        String url = GlobalVariables.API_URL + "/api/recipes";
        try{
            // + Json verisi çekiliyor.
            jsonData = GetJsonData(url);
            // -

            // + Gelen veri liste şeklinde olacağı için
            // Modelimizin Liste tipinde olacağını belirtiyoruz.
            Type recipeModelListType = new TypeToken<List<Recipe>>(){}.getType();
            // -

            // + Gson ile Json verisi okunur ve Listeye alınır.
            Gson gson = new Gson();
            recipeModelList  = gson.fromJson(jsonData, recipeModelListType);
            // -


        }
        catch (Exception exception){
            exception.printStackTrace();
        }
        finally {
            return recipeModelList;
        }
    }

    public String getProfilePhoto(String id){
        String profilePhoto = null;
        String url = GlobalVariables.API_URL + "/uploads/profile_images/" + id + "_profile_photo.jpeg";
        try{
            // + Json verisi çekiliyor.
            jsonData = GetJsonData(url);
            // -

            // + Gelen veri liste şeklinde olacağı için
            // Modelimizin Liste tipinde olacağını belirtiyoruz.
            Type userType = new TypeToken<String>(){}.getType();
            // -

            // + Gson ile Json verisi okunur ve Listeye alınır.
            Gson gson = new Gson();
            profilePhoto  = gson.fromJson(jsonData, userType);
            // -


        }
        catch (Exception exception){
            exception.printStackTrace();
        }
        finally {
            return profilePhoto;
        }
    }

    public List<Recipe> getRecipeByName(String nameKey){
        List<Recipe> recipeModelList = null;
        String url = GlobalVariables.API_URL + "/api/recipes/getRecipeByName/" + nameKey;
        try{
            // + Json verisi çekiliyor.
            jsonData = GetJsonData(url);
            // -

            // + Gelen veri liste şeklinde olacağı için
            // Modelimizin Liste tipinde olacağını belirtiyoruz.
            Type recipeModelListType = new TypeToken<List<Recipe>>(){}.getType();
            // -

            // + Gson ile Json verisi okunur ve Listeye alınır.
            Gson gson = new Gson();
            recipeModelList  = gson.fromJson(jsonData, recipeModelListType);
            // -


        }
        catch (Exception exception){
            exception.printStackTrace();
        }
        finally {
            return recipeModelList;
        }
    }
    public User getUser(String id){
        User user = null;
        String url = GlobalVariables.API_URL + "/api/userInfo/" + id;
        try{
            // + Json verisi çekiliyor.
            jsonData = GetJsonData(url);
            // -

            // + Gelen veri liste şeklinde olacağı için
            // Modelimizin Liste tipinde olacağını belirtiyoruz.
            Type userType = new TypeToken<User>(){}.getType();
            // -

            // + Gson ile Json verisi okunur ve Listeye alınır.
            Gson gson = new Gson();
            user  = gson.fromJson(jsonData, userType);
            // -


        }
        catch (Exception exception){
            exception.printStackTrace();
        }
        finally {
            return user;
        }
    }

    public void logOut(Context context){

        String url = GlobalVariables.API_URL + "/api/users/logout";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // As of f605da3 the following should work
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset((response).headers, "utf-8"));
                                // Now you can use any deserializer to make sense of data
                                JSONObject obj = new JSONObject(res);
                            } catch (UnsupportedEncodingException e1) {
                                // Couldn't properly decode data to string
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                // returned data is not JSONObject?
                                e2.printStackTrace();
                            }

                        }
                    }
                }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

    public List<Ingredient> getIngredients(){
        List<Ingredient> recipeModelList = null;
        String url = GlobalVariables.API_URL + "/api/ingredients";
        try{
            // + Json verisi çekiliyor.
            jsonData = GetJsonData(url);
            // -

            // + Gelen veri liste şeklinde olacağı için
            // Modelimizin Liste tipinde olacağını belirtiyoruz.
            Type ingredienteModelListType = new TypeToken<List<Ingredient>>(){}.getType();
            // -

            // + Gson ile Json verisi okunur ve Listeye alınır.
            Gson gson = new Gson();
            recipeModelList  = gson.fromJson(jsonData, ingredienteModelListType);
            // -


        }
        catch (Exception exception){
            exception.printStackTrace();
        }
        finally {
            return recipeModelList;
        }
    }


}
