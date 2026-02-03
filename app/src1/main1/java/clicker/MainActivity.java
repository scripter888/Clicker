package clicker;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView currencyText;
    private TextView incomeText;
    private TextView clickText;
    private Button buyBtn;
    private GameManager gameManager;
    private RelativeLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameManager = GameManager.getInstance();
        SaveManager.loadGame(this);
        
        mainLayout = findViewById(R.id.main_layout);
        currencyText = findViewById(R.id.currency_text);
        incomeText = findViewById(R.id.income_per_second_text);
        clickText = findViewById(R.id.click_power_text);
        buyBtn = findViewById(R.id.buy_upgrade_btn);
        ImageButton clickButton = findViewById(R.id.click_button);

        clickButton.setOnClickListener(v -> {
            gameManager.addCurrency(gameManager.getClickPower());
            showClickAnimation(v);
            updateUI();
        });

        buyBtn.setOnClickListener(v -> {
            Upgrade cursor = gameManager.autoClickerUpgrade;
            if (gameManager.tryBuyUpgrade(cursor)) {
                updateUI();
            }
        });

        new AutoClicker().start(this::updateUI);
        updateUI();
    }

    private void showClickAnimation(View view) {
        TextView popupText = new TextView(this);
        popupText.setText("+" + gameManager.getClickPower());
        popupText.setTextColor(getResources().getColor(R.color.android_green));
        popupText.setTextSize(20);
        
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT, 
            RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        popupText.setLayoutParams(params);
        mainLayout.addView(popupText);

        AnimationSet animSet = new AnimationSet(true);
        animSet.addAnimation(new TranslateAnimation(0, 0, 0, -200));
        animSet.addAnimation(new AlphaAnimation(1.0f, 0.0f));
        animSet.setDuration(600);

        animSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                mainLayout.removeView(popupText);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        popupText.startAnimation(animSet);
    }

    private void updateUI() {
        runOnUiThread(() -> {
            long currentCurrency = gameManager.getCurrency();
            long income = gameManager.autoClickerUpgrade.getTotalPower();
            long cost = gameManager.autoClickerUpgrade.getCurrentCost();

            currencyText.setText(Utils.formatNumber(currentCurrency));
            incomeText.setText(getString(R.string.income_label, Utils.formatNumber(income)));
            clickText.setText(getString(R.string.click_label, String.valueOf(gameManager.getClickPower())));
            buyBtn.setText(getString(R.string.upgrade_buy, Utils.formatNumber(cost)));
            
            buyBtn.setEnabled(currentCurrency >= cost);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SaveManager.saveGame(this);
    }
}
