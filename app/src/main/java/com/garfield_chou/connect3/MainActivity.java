package com.garfield_chou.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // 0 = yellow, 1 = red
    int activePlayer = 0;

    boolean gameIsActive = true;

    // 2 means unplayed
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    public void dropIn (View view) {
        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        String winner = "Red";
        boolean gameIsOver = false;

        if (2 == gameState[tappedCounter] && gameIsActive) {

            gameState[tappedCounter] = activePlayer;

            counter.setTranslationY(-1500f);

            if (0 == activePlayer) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1500f).rotation(3600f).setDuration(300);
        }

        for (int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 2 ) {

                // Someone has won
                gameIsActive = false;

                if (0 == gameState[winningPosition[0]]) {
                    winner = "Yellow";
                }
                /*
                TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                winnerMessage.setText(winner + " has won!");

                LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
                layout.setVisibility(View.VISIBLE);
                */
            } else {
                gameIsOver = true;
                for (int counterState : gameState) {
                    if (2 == counterState) gameIsOver = false;
                }
                /*
                if (gameIsOver) {
                    TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                    winnerMessage.setText("It's a draw");

                    LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);
                }
                */
            }
        }
        Log.i("winner is ", winner);
        if (gameIsOver || !gameIsActive) {
            TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
            if(!gameIsActive){
                winnerMessage.setText(winner + " has won!");                
            } else {
                winnerMessage.setText("It's a draw");
            }
            LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
            layout.setVisibility(View.VISIBLE);
        } else {
            Log.i("Debug", "Neither over nor active");
        }

    }

    public void playAgain(View view) {
        gameIsActive = true;
        LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);

        activePlayer = 0;
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }

        GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ((ImageView)gridLayout.getChildAt(i)).setImageResource(0);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
