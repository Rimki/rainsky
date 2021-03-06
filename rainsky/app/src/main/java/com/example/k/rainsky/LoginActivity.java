package com.example.k.rainsky;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

import static java.lang.Thread.sleep;

public class LoginActivity extends AppCompatActivity {

    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText idText= (EditText) findViewById(R.id.idText);
        final EditText pwText= (EditText) findViewById(R.id.pwText);

        final Button loginButton= (Button) findViewById(R.id.loginButton);
        final Button registerButton= (Button) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id=idText.getText().toString();
                final String pw=pwText.getText().toString();

                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean sucess=jsonResponse.getBoolean("success");
                            if(sucess){
                                AlertDialog.Builder builder =new AlertDialog.Builder(LoginActivity.this);
                                dialog=builder.setMessage("로그인에 성공하였습니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();

                                Intent intent= new Intent(LoginActivity.this,MainActivity.class);
                                intent.putExtra("id",id);
                                LoginActivity.this.startActivity(intent);
                                finish();
                            }
                            else{
                                AlertDialog.Builder builder =new AlertDialog.Builder(LoginActivity.this);
                                dialog=builder.setMessage("계정을 다시 확인하세요.")
                                        .setNegativeButton("다시 시도",null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest=new LoginRequest(id,pw,responseListener);
                RequestQueue queue= Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);

            }
        });
        TextView information=(TextView)findViewById(R.id.information);
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,Pop.class));
            }
        });
    }
    protected void onStop(){
        super.onStop();
        if(dialog!=null){
            dialog.dismiss();
            dialog=null;
        }
    }
}
