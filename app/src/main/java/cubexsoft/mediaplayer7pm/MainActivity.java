package cubexsoft.mediaplayer7pm;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mPlayer;

    SeekBar sBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPlayer=MediaPlayer.create(this,R.raw.test);

        sBar=(SeekBar)findViewById(R.id.s1);
        sBar.setMax(mPlayer.getDuration());

        sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sBar.setProgress(mPlayer.getCurrentPosition());
                handler.postDelayed(this,5000);
                if(mPlayer.getCurrentPosition()>=mPlayer.getDuration()){
                    handler.removeCallbacks(this);
                }
            }
        }, 5000);


    }

    public void media(View v){
        switch (v.getId()){
            case R.id.backward :

         mPlayer.seekTo(mPlayer.getCurrentPosition()-mPlayer.getDuration()/10);

                break;
            case R.id.play :
                mPlayer.start();
                break;

            case R.id.pause:
                mPlayer.pause();
                break;

            case R.id.stop:
                mPlayer.stop();
                mPlayer=MediaPlayer.create(this,R.raw.test);
                break;

            case R.id.forward:
                mPlayer.seekTo(mPlayer.getCurrentPosition()+mPlayer.getDuration()/10);

                break;

            case R.id.list :

                Intent i=new Intent();
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.setType("audio/*");
                startActivityForResult(i,123);

                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mPlayer.stop();

        try {
            mPlayer = new MediaPlayer();
            mPlayer.setDataSource(this, data.getData());
            mPlayer.prepare();
            mPlayer.start();

            sBar.setMax(mPlayer.getDuration());
        }catch (Exception e){

        }

    }
}
