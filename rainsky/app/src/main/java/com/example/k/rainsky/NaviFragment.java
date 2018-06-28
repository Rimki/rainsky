package com.example.k.rainsky;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skt.Tmap.TMapTapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NaviFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NaviFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NaviFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Intent intent= getIntent();
    private OnFragmentInteractionListener mListener;
    ArrayList<Notice> noticeList=new ArrayList<Notice>();


    public NaviFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NaviFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NaviFragment newInstance(String param1, String param2) {
        NaviFragment fragment = new NaviFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    /*public static Location findGeoPoint(Context mcontext, String address) {
        Location loc = new Location("");
        Geocoder coder = new Geocoder(mcontext);
        List<Address> addr = null;

        try {
            addr = coder.getFromLocationName(address, 1);
        } catch (IOException e) {

            e.printStackTrace();
        }
        if (addr != null) {
            for (int i = 0; i < addr.size(); i++) {
                Address lating = addr.get(i);
                double lat = lating.getLatitude();
                double lon = lating.getLongitude();
                loc.setLatitude(lat);
                loc.setLongitude(lon);
            }
        }
        return loc;
    }*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

         Geocoder geocoder = new Geocoder(this.getContext());

         HashMap pathInfo = new HashMap();
        Address addr;
        TMapTapi tmap = new TMapTapi(this.getContext());
        tmap.setSKTMapAuthentication("69963709-a2eb-4e80-b851-72c6f352b6be");

      /*  for(int i=0;i<noticeList.size();i++) {
            try {
                List<Address> listAddress= geocoder.getFromLocationName( noticeList.get(i).getAddress(),1);
                if(listAddress.size()>0){
                    addr=listAddress.get(i);
                    int lat=(int)(addr.getLatitude()*1E6);
                    int lng=(int)(addr.getLongitude()*1E6);
                    if(i==listAddress.size()-1){
                        pathInfo.put("rGoName",addr);
                        pathInfo.put("rGoX",lng);
                        pathInfo.put("rGoY",lat);
                    }
                    pathInfo.put("rV"+i+1+"Name",addr);
                    pathInfo.put("rV"+i+1+"X",lng);

                    pathInfo.put("rV"+i+1+"Y",lat);
              }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        */
        pathInfo.put("rGoName","인천광역시 계양구 서운동 148-86");
        pathInfo.put("rGoX","126.75874190000002");
        pathInfo.put("rGoY","37.52945589999999");

       pathInfo.put("rV1Name","인천광역시 계양구 대보로 102-7");
        pathInfo.put("rV1X","126.7417974");
        pathInfo.put("rV1Y","37.52494360000001");
        pathInfo.put("rV2Name","인천광역시 계양구 서운동 55-45");
        pathInfo.put("rV2X","126.74885019999999");
        pathInfo.put("rV2Y","37.5264196");
      /*  pathInfo.put("rV3Name","인천광역시 계양구 아나지로 538-9");
        pathInfo.put("rV3X",);
        pathInfo.put("rV3Y",);
        pathInfo.put("rV4Name","인천광역시 계양구 서운동 142");
        pathInfo.put("rV4X",);
        pathInfo.put("rV4Y",);
        pathInfo.put("rV5Name","인천광역시 계양구 서운산업로 47번길 21");
        pathInfo.put("rV5X",);
        pathInfo.put("rV5Y",);
        pathInfo.put("rV6Name","인천광역시 계양구 서운동 148-59");
        pathInfo.put("rV6X",);
        pathInfo.put("rV6Y",);
        pathInfo.put("rV7Name","인천광역시 계양구 서운산업로41번길 5");
        pathInfo.put("rV7X",);
        pathInfo.put("rV7Y",);
        pathInfo.put("rV8Name","인천광역시 계양구 서운산업로 27");
        pathInfo.put("rV8X",);
        pathInfo.put("rV8Y",);
        pathInfo.put("rV9Name","인천광역시 계양구 서운동 148-86");
        pathInfo.put("rV9X",);
        pathInfo.put("rV9Y",);*/


        tmap.invokeRoute(pathInfo);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        noticeList=getArguments().getParcelableArrayList("noticeList");
        return inflater.inflate(R.layout.fragment_navi, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public Intent getIntent() {
        return intent;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
