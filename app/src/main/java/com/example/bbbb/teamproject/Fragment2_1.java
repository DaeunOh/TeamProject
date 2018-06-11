package com.example.bbbb.teamproject;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by SUNSOOMIN on 2018-05-19.
 */

public class Fragment2_1 extends Fragment implements OnMapReadyCallback {

    private MapView mapView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View layout = inflater.inflate(R.layout.fragment2_1, container, false);
        mapView = (MapView) layout.findViewById(R.id.map);
        mapView.getMapAsync(this);

        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //액티비티가 처음 생성될 때 실행되는 함수
        if(mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(final GoogleMap googleMap) {

        //위치 알아서 찍어주기
        LatLng ajouUniv = new LatLng(37.277516, 127.043829);

        LatLng index1 = new LatLng(37.279168, 127.043357);
        LatLng index2 = new LatLng(37.279036, 127.043540);
        LatLng index3 = new LatLng(37.279277, 127.043519);
        LatLng index4 = new LatLng(37.277236, 127.043667);
        LatLng index5 = new LatLng(37.276837, 127.043993);
        LatLng index6 = new LatLng(37.279568, 127.043306);
        LatLng index7 = new LatLng(37.279206, 127.044650);
        LatLng index8 = new LatLng(37.275825, 127.044264);
        LatLng index9 = new LatLng(37.279067, 127.043206);
        LatLng index10 = new LatLng(37.277837, 127.043936);
        LatLng index11 = new LatLng(37.277349, 127.044185);
        LatLng index12 = new LatLng(37.277048, 127.044029);
        LatLng index13 = new LatLng(37.275374, 127.044759);

        LatLng index15 = new LatLng(37.279443, 127.043093);
        LatLng index16 = new LatLng(37.278125, 127.045210);

        LatLng index18 = new LatLng(37.278918, 127.044169);
        LatLng index19 = new LatLng(37.278849, 127.045027);
        LatLng index20 = new LatLng(37.277839, 127.044101);
       ;
        LatLng index22 = new LatLng(37.279093, 127.042473);
        LatLng index23 = new LatLng(37.279302, 127.044683);
        LatLng index24 = new LatLng(37.278876, 127.044683);
        LatLng index25 = new LatLng(37.279234, 127.043384);

        LatLng index27 = new LatLng(37.276132, 127.044233);
        LatLng index28 = new LatLng(37.279094, 127.042901);

        LatLng index30 = new LatLng(37.278859, 127.043301);

        LatLng index32 = new LatLng(37.279097, 127.043172);
        LatLng index33 = new LatLng(37.279111, 127.043302);
        LatLng index34 = new LatLng(37.279302, 127.043015);
        LatLng index35 = new LatLng(37.276216, 127.044227);
        LatLng index36 = new LatLng(37.277859, 127.044067);
        LatLng index37 = new LatLng(37.277877, 127.044022);
        LatLng index38 = new LatLng(37.278415, 127.044246);
        LatLng index39 = new LatLng(37.276620, 127.044916);

        LatLng index40 = new LatLng(37.278708, 127.042929);
        LatLng index41 = new LatLng(37.278456, 127.043225);
        LatLng index42 = new LatLng(37.278272, 127.042574);
        LatLng index43 = new LatLng(37.278433, 127.042551);
        LatLng index44 = new LatLng(37.278052, 127.042399);
        LatLng index45 = new LatLng(37.278034, 127.042454);
        LatLng index46 = new LatLng(37.278174, 127.043521);
        LatLng index47 = new LatLng(37.278251, 127.043595);
        LatLng index48 = new LatLng(37.278456, 127.043691);
        LatLng index49 = new LatLng(37.278512, 127.043529);
        LatLng index50 = new LatLng(37.278593, 127.043483);
        LatLng index51 = new LatLng(37.276620, 127.044916);
        LatLng index52 = new LatLng(37.278550, 127.043553);
        LatLng index53 = new LatLng(37.278747, 127.043556);
        LatLng index54 = new LatLng(37.277095, 127.043118);
        LatLng index55 = new LatLng(37.277120, 127.043658);
        LatLng index56 = new LatLng(37.274540, 127.043576);









        // 구글맵에다가 마커찍기 한식빨간색 중식파란색 일식노란색 분식보라색 패스트푸드 연두색 기타분홍색

        googleMap.addMarker(new MarkerOptions().position(index1).title("천애부").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        googleMap.addMarker(new MarkerOptions().position(index2).title("아롤도그").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
        googleMap.addMarker(new MarkerOptions().position(index3).title("맘스터치").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
        googleMap.addMarker(new MarkerOptions().position(index4).title("광교반점").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        googleMap.addMarker(new MarkerOptions().position(index5).title("국수나무").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(index6).title("깐돌이네").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(index7).title("깡통집").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(index8).title("꼬꼬아찌숯불치킨").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        googleMap.addMarker(new MarkerOptions().position(index9).title("내가 찜한 닭").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(index10).title("노랑통닭").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        googleMap.addMarker(new MarkerOptions().position(index11).title("동대문엽기떡볶이").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
        googleMap.addMarker(new MarkerOptions().position(index12).title("동떡이").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
        googleMap.addMarker(new MarkerOptions().position(index13).title("만년닭강정").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

        googleMap.addMarker(new MarkerOptions().position(index15).title("멘야고이쿠치").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        googleMap.addMarker(new MarkerOptions().position(index16).title("멘야고코로").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

        googleMap.addMarker(new MarkerOptions().position(index18).title("무한통삼").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(index19).title("미성초밥").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        googleMap.addMarker(new MarkerOptions().position(index20).title("미쳐버린파닭").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

        googleMap.addMarker(new MarkerOptions().position(index22).title("봉구스밥버거").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(index23).title("봉추찜닭").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(index24).title("소고").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        googleMap.addMarker(new MarkerOptions().position(index25).title("스파게티스토리").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

        googleMap.addMarker(new MarkerOptions().position(index27).title("아웃닭").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        googleMap.addMarker(new MarkerOptions().position(index28).title("알촌").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        googleMap.addMarker(new MarkerOptions().position(index30).title("오닭꼬치").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

        googleMap.addMarker(new MarkerOptions().position(index32).title("유가네").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(index33).title("육쌈냉면").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(index34).title("일미 닭갈비").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(index35).title("짚신매운갈비찜").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(index36).title("콩나물떡볶이").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
        googleMap.addMarker(new MarkerOptions().position(index37).title("피자스쿨").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
        googleMap.addMarker(new MarkerOptions().position(index38).title("한방전주콩나물국밥").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(index39).title("할머니부대찌개").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        googleMap.addMarker(new MarkerOptions().position(index40).title("제일찌개백반").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(index41).title("의정부 부대찌개").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(index42).title("우만동족발집").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(index43).title("만고쿠").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        googleMap.addMarker(new MarkerOptions().position(index44).title("맛이짱").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(index45).title("푸드테라피").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(index46).title("KFC").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
        googleMap.addMarker(new MarkerOptions().position(index47).title("낭만 핫도그").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
        googleMap.addMarker(new MarkerOptions().position(index48).title("아오센").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        googleMap.addMarker(new MarkerOptions().position(index49).title("파스타앤그릴").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        googleMap.addMarker(new MarkerOptions().position(index50).title("한솥").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(index51).title("홍콩반점").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        googleMap.addMarker(new MarkerOptions().position(index52).title("더진국").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(index53).title("구뜰보쌈").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(index54).title("서울곱창").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(index55).title("도쿄라면").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        googleMap.addMarker(new MarkerOptions().position(index56).title("미스터피자").alpha(0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));










        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ajouUniv, 16));

        // 정보 눌렀을 때
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(getActivity(), Store.class);
                intent.putExtra("title", marker.getTitle());
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_left);
            }
        });

        // 마커 눌렀을 때
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });

    }
}
