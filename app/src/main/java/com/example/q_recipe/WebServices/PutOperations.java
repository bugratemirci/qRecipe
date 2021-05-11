package com.example.q_recipe.WebServices;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class PutOperations {

    public void editProfile(Context context, String id, String name, String email, String password, TextView labelRegisterWarning, String access_token, String about) {
        JSONObject postDataParams = new JSONObject();
        JSONObject editInformation = new JSONObject();
        try {
            editInformation.put("name", name);
            editInformation.put("email", email);
            editInformation.put("about", about);
            postDataParams.put("id", id);
            postDataParams.put("editInformation", editInformation);
            postDataParams.put("access_token", access_token);
            postDataParams.put("password", password);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        String data = postDataParams.toString();
        String url = GlobalVariables.API_URL + "/api/users/edit";
        StringRequest postRequest = new StringRequest(Request.Method.PUT, url,
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

    public void editProfileAdmin(Context context, String id, String name, String email, String password, String about, String phone) {
        JSONObject postDataParams = new JSONObject();
        JSONObject editInformation = new JSONObject();
        try {
            editInformation.put("name", name);
            editInformation.put("email", email);
            editInformation.put("phone", phone);
            editInformation.put("about", about);
            postDataParams.put("id", id);
            postDataParams.put("editInformation", editInformation);

            postDataParams.put("password", password);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        String data = postDataParams.toString();
        String url = GlobalVariables.API_URL + "/api/admin/editUser";
        StringRequest postRequest = new StringRequest(Request.Method.PUT, url,
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

        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }
}
