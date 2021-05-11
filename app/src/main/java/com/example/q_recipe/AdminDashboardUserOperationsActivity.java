package com.example.q_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.q_recipe.Models.User;
import com.example.q_recipe.WebServices.GetOperations;

import java.io.Serializable;
import java.util.List;

public class AdminDashboardUserOperationsActivity extends AppCompatActivity {
    private ListView listviewUserOperationsAdminDashboard;
    private GetOperations getOperations = new GetOperations();
    private String[] ids;
    private String[] names;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard_user_operations);
        getSupportActionBar().hide();

        listviewUserOperationsAdminDashboard = findViewById(R.id.listviewUserOperationsAdminDashboard);

        List<User> userList = getOperations.getUsers();
        names = new String[userList.size()];
        ids = new String[userList.size()];
        for(int i = 0; i< userList.size(); i++){
            names[i] = userList.get(i).getName();
            ids[i] = userList.get(i).getId();
        }
        ArrayAdapter<String> recipeNamesAdapter = new  ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, names);
        listviewUserOperationsAdminDashboard.setAdapter(recipeNamesAdapter);

        listviewUserOperationsAdminDashboard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                user = userList.get(position);
                Intent intent = new Intent(AdminDashboardUserOperationsActivity.this, EditUserActivity.class);
                intent.putExtra("selectedUser", (Serializable) user);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.fade_out);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.ltor, R.anim.fade_out);
    }
}