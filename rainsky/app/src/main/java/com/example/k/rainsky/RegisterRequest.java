package com.example.k.rainsky;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    final static private String URL = "http://multiple.iptime.org/web/Register.php";
    private Map<String, String> parameters;

    public RegisterRequest(String id, String name,String pw,Response.Listener<String>listener) {
        super(Method.POST,URL,listener,null);
        parameters=new HashMap<>();
        parameters.put("id",id);
        parameters.put("name",name);
        parameters.put("pw",pw);

    }

    public Map<String, String> getParams() {
        return parameters;
    }
}
