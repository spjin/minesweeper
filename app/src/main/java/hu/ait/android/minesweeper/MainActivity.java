package hu.ait.android.minesweeper;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.facebook.shimmer.ShimmerFrameLayout;

public class MainActivity extends AppCompatActivity {

    private TextView gameStatus;
    private LinearLayout layoutContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutContent = findViewById(R.id.layoutContent);

        gameStatus = (TextView) findViewById(R.id.gameStatus);

        final MineSweeperView mineSweeper = (MineSweeperView) findViewById(R.id.minesweep);

        final ToggleButton flagM = (ToggleButton) findViewById(R.id.flag);

        flagM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                MineSweeperModel.getInstance().setFlagMode(b);
            }
        });

        Button btnClear = (Button) findViewById(R.id.resetBtn);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mineSweeper.clear();
                gameStatus = (TextView) findViewById(R.id.gameStatus);
                showGameStatus(getString(R.string.text_in_session));
                flagM.setChecked(false);
                showReset();
            }
        });

        ShimmerFrameLayout shimmerView = findViewById(R.id.shimmer_view);
        shimmerView.startShimmerAnimation();
    }

    public void showReset(){
        Snackbar.make(layoutContent, getString(R.string.text_reset), Snackbar.LENGTH_LONG).show();
    }

    public void showGameStatus(String gameStat){
        gameStatus.setText(getString(R.string.text_game_status, gameStat));
    }
}
