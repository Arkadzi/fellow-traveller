package us.fellowtraveller.presentation.activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import us.fellowtraveller.R;
import us.fellowtraveller.presentation.utils.LocationUtils;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addPolyline(new PolylineOptions()
                .addAll(LocationUtils.decodePolyline("}i|rHcvzxD@GBG@EDCBAFCD?D?L@XFPB@@"))
//                .addAll(LocationUtils.decodePolyline("{r{rHc}yxDyBcNoAuIEy@AsBNoD|@aQfAyRJgCFqDC_DIwBIkAa@_FcAkK}@{HaAkHwA_Mc@oEOeCG}BGqDD}Fd@kHf@iGFa@z@uKL_BTmBd@mCb@_ER}BLSHGLCNBHHHPFV@`@?ViBxMQ^Q`@MLWDy@W{Bm@iGkBaA[GUEa@@[HURWRB^H^XZZJTD^@V]pEi@pFc@tEuCp[sFzm@aB`QiC`Ym@tHyBxUqAxM{Cn\\\\WdDg@^M@K?s@MQ?ODMJKNOp@ERIJQJy@IK@u@SUMg@Mm@KM@oAa@qB_@iBc@wAQ_AKk@AaBDo@@kAD_CJo@By@CkDF_ABgEDaCFwAB}ABmFRyNb@sDBmAa@oEmAyD{@eFmA{@UoDmA_Ag@QS[K{@YeAk@yBwAgB}AgAiAe@i@wC_DwBkC_AqAwAaCo@sAeA}Bm@}AuAiEcA}Do@eDg@}Cw@_GeAeG{@iFe@gDYiDUsE[{IUeFQ{A_@gCYsA[gAi@yA_AmBe@w@{@gAiB}Ao@k@o@s@eBiCeAaBs@yA]y@m@iCUyAcAyLUgCUoB]_FWuCsAwNw@{NScGGgEIiJA}B@aI@_IAyE?yCDoGA_DDqE`@o]HsCJkJH}IDmBHaGBgCVcNXuMNaQLiIXsT@wAEgEWcJIkBI{@UyBe@iFYwBWyAS_AoCmLqA{F{C}NiEmTiAmHkBcKeAcG]mC_AkJo@iHsBmXmAgQ]oFq@iMY{RKoK?gGGoR?aLFuEJoKAsICqMA_EBuEBoMHyCLmIBuEA{EH_QeAN{@^oBF{@?W?yBE?yL@eJ?mBaA?CuACKUIsBGe@N")
//                .addAll(LocationUtils.decodePolyline("iwnrHock{D?g@AKAKAI@MA[?q@TqBX_Cp@eGd@iEb@{D`@sDn@{Ff@kE`@{D`@eDl@wFXcCToBLiAJ}@Fm@@O@KAI?KCKAGCEAEECCCEEGE[OyAo@sAm@mBcAyDiB_CeAcBy@qBaAmCmAaF_CGCcBw@u@_@e@UuBgAa@QYMSImFoBqI_DeE_BeBo@}HqCiBq@mAg@iC_AgBq@iAg@_@Q}CgAuCeAqBu@o@UcBu@iBk@aC}@{B{@oCaAcBm@i@UWI_@OMGWKQCG?E?G?UD"))
//                .addAll(LocationUtils.decodePolyline("")
//                .addAll(LocationUtils.decodePolyline("")
                );

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        mMap.addPolyline(PolylineOptions.)
    }
}
