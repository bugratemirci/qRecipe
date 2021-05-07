package com.example.q_recipe.WebServices;

import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.q_recipe.AddRecipeActivity;
import com.example.q_recipe.Business.LoggedInUser;
import com.example.q_recipe.ENV.GlobalVariables;
import com.example.q_recipe.HomepageActivity;
import com.example.q_recipe.MainPageActivity;
import com.example.q_recipe.Models.User;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostOperations {


    public void registerOperations(Context context, String name, String email, String password, TextView labelRegisterWarning, String notification_token, String phone, String about) {
        JSONObject postDataParams = new JSONObject();
        try {

            postDataParams.put("name", name);
            postDataParams.put("password", password);
            postDataParams.put("email", email);
            postDataParams.put("role", "user");
            postDataParams.put("notification_token", notification_token);
            postDataParams.put("phone", phone);
            postDataParams.put("about", about);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        String data = postDataParams.toString();
        String url = GlobalVariables.API_URL + "/api/users/register";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        labelRegisterWarning.setText("Kayıt başarılı");
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
                            labelRegisterWarning.setText("Kayıt başarısız");
                        }
                    }
                }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return data == null ? null : data.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    //Log.v("Unsupported Encoding while trying to get the bytes", data);
                    return null;
                }
            }

        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

    public void updateProfilePicture(Context context, Bitmap bitmap, String access_token, String id, LoggedInUser loggedInUser) {

        String urlUpload = GlobalVariables.API_URL + "/api/users/upload";
        BitmapFactory.Options options = new BitmapFactory.Options();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        loggedInUser.setProfile_image(imageString);

        JSONObject postDataParams = new JSONObject();
        try {

            postDataParams.put("profile_image", imageString);
            postDataParams.put("id", id);
            postDataParams.put("access_token", access_token);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        String data = postDataParams.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpload, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                    //Log.v("Unsupported Encoding while trying to get the bytes", data);
                    return null;
                }
            }
        };
    RequestQueue requestQueue = Volley.newRequestQueue(context);
    requestQueue.add(stringRequest);
    }

    public void updateRecipePicture(Context context, Bitmap bitmap, String access_token, String recipeId, String userId) {

        String urlUpload = GlobalVariables.API_URL + "/api/recipes/uploadImage";

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        JSONObject postDataParams = new JSONObject();
        try {
            postDataParams.put("recipe_image", imageString);
            postDataParams.put("id", userId);
            postDataParams.put("access_token", access_token);
            postDataParams.put("recipe_id", recipeId);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        String data = postDataParams.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpload, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                    //Log.v("Unsupported Encoding while trying to get the bytes", data);
                    return null;
                }
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


    public void loginOperations(Context context, String email, String password) {
        JSONObject postDataParams = new JSONObject();

        try {

            postDataParams.put("password", password);
            postDataParams.put("email", email);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        String data = postDataParams.toString();
        String url = GlobalVariables.API_URL + "/api/users/login";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);



                        try {
                            LoggedInUser loggedInUser = new LoggedInUser();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectData = new JSONObject(jsonObject.getString("data"));

                            loggedInUser.setAccess_token("Bearer: " + jsonObject.getString("access_token"));
                            loggedInUser.setName(jsonObjectData.getString("name"));
                            loggedInUser.setEmail(jsonObjectData.getString("email"));
                            loggedInUser.setId(jsonObjectData.getString("id"));
                            loggedInUser.setAbout(jsonObjectData.getString("about"));
                            loggedInUser.setPhone(jsonObjectData.getString("phone"));
                            loggedInUser.setRole(jsonObjectData.getString("role"));
                            loggedInUser.setProfile_image(jsonObjectData.getString("profile_image_string"));

                            Intent intent = new Intent(context, HomepageActivity.class);
                            intent.putExtra("user", loggedInUser);
                            context.startActivity(intent);


                        }
                        catch (Exception exception){
                            exception.printStackTrace();
                        }

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

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return data == null ? null : data.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    //Log.v("Unsupported Encoding while trying to get the bytes", data);
                    return null;
                }
            }

        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

    public void addRecipe(Context context, String name, String description, ArrayList<String> ingredients, TextView labelWarningAddRecipe, String access_token, String id, LoggedInUser loggedInUser){
        JSONObject postDataParams = new JSONObject();
        JSONArray ingredientList = new JSONArray();
        GetOperations getOperations = new GetOperations();
        User user = getOperations.getUser(id);

        for(String ingredient: ingredients){
            ingredientList.put(ingredient);
        }

        try {

            postDataParams.put("name", name);
            postDataParams.put("description", description);
            postDataParams.put("ingredients", ingredientList);
            postDataParams.put("access_token", access_token);
            postDataParams.put("id", id);



        } catch (Exception exception) {
            exception.printStackTrace();
        }
        String data = postDataParams.toString();
        String url = GlobalVariables.API_URL + "/api/recipes/enterARecipe";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        labelWarningAddRecipe.setText("Kayıt başarılı");

                        Intent intent = new Intent(context, MainPageActivity.class);
                        intent.putExtra("user", loggedInUser);
                        context.startActivity(intent);

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
                            labelWarningAddRecipe.setText("Kayıt başarısız");
                        }

                    }
                }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return data == null ? null : data.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    //Log.v("Unsupported Encoding while trying to get the bytes", data);
                    return null;
                }
            }

        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

    public void getRecipeByIngredients(Context context, ArrayList<String> ingredientsSlug, ListView listViewSearch){
        JSONObject postDataParams = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(String ingredients: ingredientsSlug){
            jsonArray.put(ingredients);
        }
        try {
            postDataParams.put("ingredients", jsonArray);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        String data = postDataParams.toString();
        String url = GlobalVariables.API_URL + "/api/recipes/getRecipeByIngredient";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String[] ids;

                        Log.d("Response", response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            ids = new String[jsonArray.length()];
                            for(int i = 0; i < jsonArray.length(); i++){
                                ids[i] = jsonArray.getJSONObject(i).getString("name");
                            }


                            ArrayAdapter<String> recipeNamesAdapter = new  ArrayAdapter<String>(context,
                                    android.R.layout.simple_list_item_1, android.R.id.text1, ids);
                            listViewSearch.setAdapter(recipeNamesAdapter);
                        }
                        catch (Exception exception){
                            exception.printStackTrace();
                        }




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

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return data == null ? null : data.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    //Log.v("Unsupported Encoding while trying to get the bytes", data);
                    return null;
                }
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

    public void pushNotification(Context context, String body, String title, String to, String token){
        JSONObject postDataParams = new JSONObject();
        JSONObject postNotification = new JSONObject();


        try {
            postNotification.put("body", body);
            postNotification.put("title", title);
            postDataParams.put("to",to);
            postDataParams.put("notification", postNotification);



        } catch (Exception exception) {
            exception.printStackTrace();
        }
        String data = postDataParams.toString();
        String url = "https://fcm.googleapis.com/fcm/send";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
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

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return data == null ? null : data.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    //Log.v("Unsupported Encoding while trying to get the bytes", data);
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "key=" + token);
                return headers;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }
}
