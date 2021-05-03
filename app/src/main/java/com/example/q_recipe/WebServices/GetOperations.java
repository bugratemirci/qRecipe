package com.example.q_recipe.WebServices;

import com.example.q_recipe.ENV.GlobalVariables;
import com.example.q_recipe.Models.Recipe;
import com.example.q_recipe.Models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

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

    /*public List<User> getUsers(){
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
    }*/

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
}
