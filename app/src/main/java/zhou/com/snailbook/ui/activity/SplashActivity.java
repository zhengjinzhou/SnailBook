package zhou.com.snailbook.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import me.wangyuwei.particleview.ParticleView;
import zhou.com.snailbook.R;
import zhou.com.snailbook.component.AppComponent;

public class SplashActivity extends AppCompatActivity {

    private boolean flag = false;
    private Runnable runnable;
    private TextView tvSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ParticleView pv_logo = (ParticleView) findViewById(R.id.pv_logo);
        pv_logo.setOnParticleAnimListener(new ParticleView.ParticleAnimListener() {
            @Override
            public void onAnimationEnd() {
                goHome();
            }
        });
        pv_logo.startAnim();

        tvSkip = findViewById(R.id.tvSkip);

        runnable = new Runnable() {
            @Override
            public void run() {
                goHome();
            }
        };

        tvSkip.postDelayed(runnable, 3000);

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });

    }

    private synchronized void goHome() {
        if (!flag) {
            flag = true;
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        flag = true;
        tvSkip.removeCallbacks(runnable);
    }
}
