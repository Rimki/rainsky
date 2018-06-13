package com.example.k.rainsky;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class NoticeRequest extends StringRequest {

    final static private String URL = "http://183.101.242.171/web/NoticeList.php";
    private Map<String, String> parameters;

    public NoticeRequest(String id, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("id", id);


    }

    public Map<String, String> getParams() {
        return parameters;
    }
}