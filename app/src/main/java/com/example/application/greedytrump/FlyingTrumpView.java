package com.example.application.greedytrump;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class FlyingTrumpView extends View
{

    private Bitmap trump[] = new Bitmap[2];
    private int trumpX= 10;
    private int trumpY;
    private int trumpSpeed;

    private int canvasWidth, canvasHeight;

    private int coinX, coinY,coinSpeed=16;
   private Bitmap coinImage;



    private int dollarX, dollarY, dollarSpeed=20;
    private Bitmap dollarImage;

    private int missileX, missileY, missileSpeed=22;
    private Bitmap missileImage;



   private int score,lifeCounterofTrump;


    private boolean touch = false;

    private Bitmap backgroundImage;

    private Paint scorePaint=new Paint();

    private Bitmap life[]= new Bitmap[2];


    public FlyingTrumpView(Context context)

    {
        super(context);

        trump[0]= BitmapFactory.decodeResource(getResources(), R.drawable.trumpeat);
        trump[1]= BitmapFactory.decodeResource(getResources(), R.drawable.trumpeattwo);

        backgroundImage=BitmapFactory.decodeResource(getResources(), R.drawable.citytwo);


        coinImage=BitmapFactory.decodeResource(getResources(), R.drawable.coin);
        dollarImage=BitmapFactory.decodeResource(getResources(), R.drawable.dollar);
        missileImage=BitmapFactory.decodeResource(getResources(), R.drawable.missile);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);



        life[0]=BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1]=BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);

        trumpY = 550;
        score = 0;
        lifeCounterofTrump=3;
    }



    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        canvasWidth=canvas.getWidth();
        canvasHeight=canvas.getHeight();

        canvas.drawBitmap(backgroundImage, 0, 0, null);


        int minTrumpY= trump[0].getHeight();
        int maxTrumpY=canvasHeight - trump[0].getHeight() * 3;
        trumpY= trumpY + trumpSpeed;


        if(trumpY < minTrumpY)
        {

            trumpY=minTrumpY;
        }
        if(trumpY > maxTrumpY)
        {

            trumpY= maxTrumpY;
        }

        trumpSpeed = trumpSpeed + 2;



        if(touch)
        {

            canvas.drawBitmap(trump[1], trumpX, trumpY, null);
            touch=false;

        }

        else
        {

            canvas.drawBitmap(trump[0], trumpX, trumpY, null);


        }



        coinX = coinX - coinSpeed;
        if(hitballchecker(coinX,coinY))
        {

            score= score + 10;
            coinX = -100;

        }
        if(coinX < 0)
        {


            coinX=canvasWidth + 21;
            coinY = (int) Math.floor(Math.random() * (maxTrumpY - minTrumpY)) + minTrumpY;

        }



        canvas.drawBitmap(coinImage, coinX, coinY, null);





        dollarX = dollarX - dollarSpeed;
        if(hitballchecker(dollarX,dollarY))
        {

            score= score + 20;
            dollarX = -100;

        }
        if(dollarX < 0)
        {


            dollarX=canvasWidth + 21;
            dollarY = (int) Math.floor(Math.random() * (maxTrumpY - minTrumpY)) + minTrumpY;

        }



        canvas.drawBitmap(dollarImage, dollarX, dollarY, null);







        missileX = missileX - missileSpeed;
        if(hitballchecker(missileX,missileY))
        {

            missileX = -100;
            lifeCounterofTrump--;

            if(lifeCounterofTrump ==0)
            {

                Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();

                Intent gameOverIntent= new Intent(getContext(), GameOverActivity.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                gameOverIntent.putExtra("score", score);
                getContext().startActivity(gameOverIntent);

            }

        }
        if(missileX < 0)
        {


            missileX=canvasWidth + 21;
            missileY = (int) Math.floor(Math.random() * (maxTrumpY - minTrumpY)) + minTrumpY;

        }



        canvas.drawBitmap(missileImage, missileX, missileY, null);



        canvas.drawText("Score :"+score , 20, 60, scorePaint );



        for(int i=0; i<3; i++)

        {
            int x = (int) (580 + life[0].getWidth() * 1.5 * i);
            int y =30;

            if(i < lifeCounterofTrump)
            {

                canvas.drawBitmap(life[0],x ,y , null);
            }
            else
            {

                canvas.drawBitmap(life[1],x ,y , null);


            }



        }







    }

    public boolean hitballchecker(int x, int y)
    {


        if(trumpX < x && x < (trumpX + trump[0].getWidth()) && trumpY < y && y < (trumpY + trump[0].getHeight()))
        {


            return true;
        }
        return false;
    }




    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

            if(event.getAction() == MotionEvent.ACTION_DOWN)
            {
                touch=true;

                trumpSpeed = -22;
            }
                return true;
    }
}
