package com.example.k.rainsky;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    final static private String URL = "http://multipledestination.online/web/Login.php";
    private Map<String, String> parameters;

    public LoginRequest(String id, String pw, Response.Listener<String>listener) {
        super(Method.POST,URL,listener,null);
        parameters=new HashMap<>();
        parameters.put("id",id);
        parameters.put("pw",pw);

    }

    public Map<String, String> getParams() {
        return parameters;
    }
}
