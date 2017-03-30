package com.aajju.adexam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aajju.adexam.ads.AdsConfig;
import com.aajju.adexam.ads.SubAdlibAdViewAdmob;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mocoplex.adlib.AdlibConfig;
import com.mocoplex.adlib.AdlibManager;

public class MainActivity extends AppCompatActivity {

    private AdlibManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseInstanceId.getInstance().getToken();

        initAds();
    }

    private void initAds(){
        initAdlib();
        initViews();
    }

    private void initAdlib(){
        //파라미터는 애드립 인벤토리 아이디를 넣으면 됩니다.
        mManager = new AdlibManager(AdsConfig.ADLIB_ID);

        mManager.onCreate(this);


        //애드립 말고 다른 광고 플랫폼을 연결하기 위해 사용되는 코드
        AdlibConfig.getInstance().bindPlatform("ADMOB","com.aajju.adexam.ads.SubAdlibAdViewAdmob");
        AdlibConfig.getInstance().bindPlatform("ADMOB", SubAdlibAdViewAdmob.class.getCanonicalName());


        //애드립을 통해 출력되는 광고 모드 설정 true: 테스트
        mManager.setAdlibTestMode(true);
        //배너광고가 출력될 애드립 컨테이너 뷰 아이디
        mManager.setAdsContainer(R.id.main_ad);

        //애드립액티비티가 아니고 일반 액티비티(구글 순정) 사용할때 광고 출력하려면
        //애드립 매너저를 써야하는데, 애기비티 생명주기별로 관련메소드를 호출해야합니다.
    }

    private void initViews(){
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, SecondActivity.class), 1000);
            }
        });
    }

    @Override
    protected void onResume() {
        mManager.onResume(this);
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1000){
            mManager.loadFullInterstitialAd(this);
        }
    }

    @Override
    protected void onPause() {
        mManager.onPause(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mManager.onDestroy(this);
        super.onDestroy();
    }
}
