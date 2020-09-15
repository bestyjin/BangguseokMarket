package com.jalmaksa.bm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {  //View.OnClickListener,이거 추가해야됨
    private static final String TAG = MapsActivity.class.getSimpleName();
    Context mContext;

    //seoutdata activity로 가기위한 버튼
    Button mart_surround;

    private GoogleMap mMap;
    private Marker currentMarker = null;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient; // Deprecated된 FusedLocationApi를 대체
    private LocationRequest locationRequest;
    private Location mCurrentLocatiion;

    private final LatLng mDefaultLocation = new LatLng(37.56, 126.97);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int UPDATE_INTERVAL_MS = 1000 * 60 * 15;  // LOG 찍어보니 이걸 주기로 하지 않는듯
    private static final int FASTEST_UPDATE_INTERVAL_MS = 1000 * 30 ; // 30초 단위로 화면 갱신

    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    ImageView home;
    ImageButton myp;

    //성북구 시장, 마트 마커 변수
    LatLng market1 = new LatLng(37.6093342,127.0495657);  //장위골목시장
    LatLng market2 = new LatLng(37.5914935,127.0152754);  //돈암시장
    LatLng market3 = new LatLng(37.6107888,127.0293129);  //이마트미안
    LatLng market4 = new LatLng(37.608698,127.0286830);  //현대백화점 미아
    LatLng market5 = new LatLng(37.6066066,127.00872);  //세계로마트
    LatLng market6 = new LatLng(37.6041572,127.0388147);  //롯데슈퍼
    LatLng market7 = new LatLng(37.6017514,127.0403249);  //홈플러스
    LatLng market8 = new LatLng(37.5842723,127.0120712);  //보문시장
    LatLng market9 = new LatLng(37.6023812,127.0126817);  //길음시장
    LatLng market10 = new LatLng(37.6067271,127.0058505);  //정릉시장

    //이름, 번호 저장(마트)
    String[] infor = {"장위 골목시장","돈암 제일 시장", "이마트 미아점", "현대백화점 미아점", "세계로마트 정릉점","롯데슈퍼 하월곡점",
            "홈플러스 월곡점","보문 시장", "길음시장", "정릉 시장"};
    String[] tele = {"070-7799-9129", "번호 없음", "02-944-1234", "02-2117-1141","02-915-6683","02-918-5651",
            "02-3669-8000", "02-928-3461", "02-909-0926", "070-4126-0098"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (savedInstanceState != null) {
            mCurrentLocatiion = savedInstanceState.getParcelable(KEY_LOCATION);
            CameraPosition mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }
        setContentView(R.layout.activity_maps);
        mContext = MapsActivity.this;

        //액션바사리지기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        home = (ImageView)findViewById(R.id.home);
        myp = (ImageButton)findViewById(R.id.myp);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        myp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MyPage.class);
                startActivity(intent);
            }
        });

        locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY) // 정확도를 최우선적으로 고려
                .setInterval(UPDATE_INTERVAL_MS) // 위치가 Update 되는 주기
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS); // 위치 획득후 업데이트되는 주기

        LocationSettingsRequest.Builder builder =
                new LocationSettingsRequest.Builder();

        builder.addLocationRequest(locationRequest);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Build the map.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.realtimemap);
        mapFragment.getMapAsync(this);

//        //마트가격버튼 파인드 뷰
//        mart_price = (Button) findViewById(R.id.mart_btn) ;
//        //xml intent mart버튼
//        mart_price.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), SeoulData.class);
//                startActivity(intent);
//            }
//        });
        //마트주변 파인드 뷰
        mart_surround = (Button) findViewById(R.id.surround_mart);

        mart_surround.setOnClickListener(new View.OnClickListener() {
            MarkerOptions markerOptions = new MarkerOptions();

            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext(), "표시 구역은 성북구입니다.", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder dlg=new AlertDialog.Builder(MapsActivity.this);
                dlg.setTitle("표시가능지역");
                dlg.setMessage("현재 베타버전에서는 성북구만 지원 가능합니다.");
                dlg.setPositiveButton("확인",null);
                dlg.show();

                //for문 시도하였지만 되지 않아 일일히 작성

                markerOptions.position(market1);
                markerOptions.title(infor[0]);
                markerOptions.snippet(tele[0]);
                mMap.addMarker(markerOptions);

                markerOptions.position(market2);
                markerOptions.title(infor[1]);
                markerOptions.snippet(tele[1]);
                mMap.addMarker(markerOptions);

                markerOptions.position(market3);
                markerOptions.title(infor[2]);
                markerOptions.snippet(tele[2]);
                mMap.addMarker(markerOptions);

                markerOptions.position(market4);
                markerOptions.title(infor[3]);
                markerOptions.snippet(tele[3]);
                mMap.addMarker(markerOptions);

                markerOptions.position(market5);
                markerOptions.title(infor[4]);
                markerOptions.snippet(tele[4]);
                mMap.addMarker(markerOptions);

                markerOptions.position(market6);
                markerOptions.title(infor[5]);
                markerOptions.snippet(tele[5]);
                mMap.addMarker(markerOptions);

                markerOptions.position(market7);
                markerOptions.title(infor[6]);
                markerOptions.snippet(tele[6]);
                mMap.addMarker(markerOptions);

                markerOptions.position(market8);
                markerOptions.title(infor[7]);
                markerOptions.snippet(tele[7]);
                mMap.addMarker(markerOptions);

                markerOptions.position(market9);
                markerOptions.title(infor[8]);
                markerOptions.snippet(tele[8]);
                mMap.addMarker(markerOptions);

                markerOptions.position(market10);
                markerOptions.title(infor[9]);
                markerOptions.snippet(tele[9]);
                mMap.addMarker(markerOptions);
            }
        });
    }

    /**
     * Saves the state of the map when the activity is paused.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mCurrentLocatiion);
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        Log.e(TAG, "onMapReady :");

        mMap = map;

        setDefaultLocation(); // GPS를 찾지 못하는 장소에 있을 경우 지도의 초기 위치가 필요함.
        getLocationPermission();
        updateLocationUI();
        getDeviceLocation();
        //카메라 이동 시작
        // 카메라 이동 중
        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {

            @Override
            public void onCameraMove() {
                Log.d("set>>","move");
            }
        });

        // 지도를 클릭하면 호출되는 이벤트
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {



            @Override
            public void onMapClick(LatLng latLng) {
                // 기존 마커 정리
                //mMap.clear();
                // 클릭한 위치로 지도 이동하기
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // 신규 마커 추가  //주석처리이유 : 무분별한 마커생성방지
                //MarkerOptions newMarker=new MarkerOptions();

            }
        });
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mCurrentLocatiion = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void setDefaultLocation() {
        if (currentMarker != null) currentMarker.remove();

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(mDefaultLocation);
        markerOptions.title("위치정보 가져올 수 없음");
        markerOptions.snippet("위치 퍼미션과 GPS 활성 여부 확인하세요");
        markerOptions.draggable(true);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        currentMarker = mMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mDefaultLocation, 15);
        mMap.moveCamera(cameraUpdate);
    }

    String getCurrentAddress(LatLng latlng) {
        // 위치 정보와 지역으로부터 주소 문자열을 구한다.
        List<Address> addressList = null ;
        Geocoder geocoder = new Geocoder( this, Locale.getDefault());

        // 지오코더를 이용하여 주소 리스트를 구한다.
        try {
            addressList = geocoder.getFromLocation(latlng.latitude,latlng.longitude,1);

        } catch (IOException e) {
            Toast. makeText( this, "위치로부터 주소를 인식할 수 없습니다. 네트워크가 연결되어 있는지 확인해 주세요.", Toast.LENGTH_SHORT ).show();
            e.printStackTrace();
            return "주소 인식 불가" ;
        }

        if (addressList.size() < 1) { // 주소 리스트가 비어있는지 비어 있으면
            return "해당 위치에 주소 없음" ;
        }

        // 주소를 담는 문자열을 생성하고 리턴
        Address address = addressList.get(0);
        StringBuilder addressStringBuilder = new StringBuilder();
        for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
            addressStringBuilder.append(address.getAddressLine(i));
            if (i < address.getMaxAddressLineIndex())
                addressStringBuilder.append("\n");
        }

        return addressStringBuilder.toString();
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            List<Location> locationList = locationResult.getLocations();

            if (locationList.size() > 0) {
                Location location = locationList.get(locationList.size() - 1);

                LatLng currentPosition
                        = new LatLng(location.getLatitude(), location.getLongitude());

                String markerTitle = getCurrentAddress(currentPosition);

                String markerSnippet = "                               내 위치                               " ;

                Log.d(TAG, "Time :" + CurrentTime() + " onLocationResult : " + markerSnippet);

                // Update 주기를 확인해보려고 시간을 찍어보았음.

                //현재 위치에 마커 생성하고 이동
                setCurrentLocation(location, markerTitle, markerSnippet);
                mCurrentLocatiion = location;
            }
        }

    };

    private String CurrentTime(){
        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss a");
        return time.format(today);
    }

    public void setCurrentLocation(Location location, String markerTitle, String markerSnippet) {
        if (currentMarker != null) currentMarker.remove();

        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLatLng);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);

        currentMarker = mMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng);
        mMap.moveCamera(cameraUpdate);
    }

    private void getDeviceLocation() {
        try {
            if (mLocationPermissionGranted) {
                mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mLocationPermissionGranted) {
            Log.d(TAG, "onStart : requestLocationUpdates");
            mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
            if (mMap!=null)
                mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mFusedLocationProviderClient != null) {
            Log.d(TAG, "onStop : removeLocationUpdates");
            mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFusedLocationProviderClient != null) {
            Log.d(TAG, "onDestroy : removeLocationUpdates");
            mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    }
}

