package com.example.q_recipe.WebServices;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class PostOperations {
    public void registerOperations(Context context, String name, String email, String password, TextView labelRegisterWarning, String notification_token) {
        JSONObject postDataParams = new JSONObject();
        try {

            postDataParams.put("name", name);
            postDataParams.put("password", password);
            postDataParams.put("email", email);
            postDataParams.put("role", "user");
            postDataParams.put("notification_token", notification_token);
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


    public void loginOperations(Context context, String email, String password, TextView labelWarning) {
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

                        labelWarning.setText("Giriş başarılı");

                        try {
                            /*JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectData = new JSONObject(jsonObject.getString("data"));
                            Intent intent = new Intent(context, AddRecipeActivity.class);
                            intent.putExtra("access_token", "Bearer: " + jsonObject.getString("access_token"));
                            intent.putExtra("user_id", jsonObjectData.getString("id"));
                            context.startActivity(intent);*/
                            LoggedInUser loggedInUser = new LoggedInUser();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectData = new JSONObject(jsonObject.getString("data"));

                            loggedInUser.setAccess_token("Bearer: " + jsonObject.getString("access_token"));
                            loggedInUser.setName(jsonObjectData.getString("name"));
                            loggedInUser.setEmail(jsonObjectData.getString("email"));
                            loggedInUser.setId(jsonObjectData.getString("id"));

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
                            labelWarning.setText("Giriş başarısız");
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

    public void addRecipe(Context context, String name, String description, String[] ingredients, TextView labelWarningAddRecipe, String access_token, String id, LoggedInUser loggedInUser){
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
                        pushNotification(context,
                                name,
                                user.getName() + " tarafından yeni tarif eklendi",
                                "fYFAVtm5QLWE6SM4hhT-F2:APA91bFJk1JIU-WkFRkaKbMifrTSRACnZj9zPz5JF_XfmNxexkiMZv9zyu7FR8nVYb7U6IPjcp5mF-sCMfgFPKwDADD58er9OuPX-20RJan-yg7AB9Yuv7W0XRBlIp5UB2QThgkchq42",
                                "AAAAWHco7YE:APA91bGt9bFWiRSsL1DeV8NAf7B5-PiqpmxeWIoBzXUDOPS5x46VhgWoCPk4VlvOEQa6DKr1tj5_LgrptukHbIo1OYpf9Ho5LtlkLsz7kTFte6u9ytpSRVvy9xGgve-MY6q3f3Q-V1mw");
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
