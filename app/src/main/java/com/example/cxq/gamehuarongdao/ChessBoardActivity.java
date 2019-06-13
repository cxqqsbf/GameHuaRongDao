package com.example.cxq.gamehuarongdao;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChessBoardActivity extends AppCompatActivity implements OnClickListener {
    private int warId;
    private ChessBoard board;
    private ChessControl chessControl;
    //触摸的坐标
    float x1, x2, y1, y2;
    //步数记录
    private int step=0;
    //棋盘的单位长度
    private double chessSizeUnit = 72.0;
    private int windowWidth = 320, windowHeight = 480;
    private RelativeLayout layoutChessBoard;
    private LinearLayout layoutUpper, layoutDowner, layoutBody, layoutBottom;
    private RelativeLayout layoutHeader;
    private MusicService musicService;
    //步数显示
    TextView text1;
    //音乐播放
    private Button musicButton;
    private boolean  bmusicPlayed = true;
    private MediaPlayer moveMediaPlayer, goodjobMediaPlayer,backMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chessboard);
        //禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        moveMediaPlayer = MediaPlayer.create(this, R.raw.move);
        goodjobMediaPlayer=MediaPlayer.create(this, R.raw.good);
        backMediaPlayer=MediaPlayer.create(this,R.raw.bgm1);
        musicService=new MusicService();
        layoutUpper = (LinearLayout)findViewById(R.id.upper);
        layoutDowner = (LinearLayout)findViewById(R.id.downer);
        layoutHeader = (RelativeLayout)findViewById(R.id.header);
        layoutBody = (LinearLayout)findViewById(R.id.body);
        layoutBottom = (LinearLayout)findViewById(R.id.bottom);
        musicButton = (Button)findViewById(R.id.btn_backgruound_muisc);
        musicButton.setOnClickListener((OnClickListener) this);
        text1= (TextView) findViewById(R.id.step);
        text1.setText("步数：0");
        WindowManager wm = (WindowManager) this.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        try {
            Point size = new Point();
            display.getSize(size);
            windowWidth = size.x;
            windowHeight = size.y;
        } catch (NoSuchMethodError e) {
            windowWidth = display.getWidth();
            windowHeight = display.getHeight();
        }
        chessSizeUnit = windowWidth*0.925/4;

        // 如果手机屏幕的尺寸不是 320X480, 重新布局

        if(windowHeight > (int)(windowWidth * 480.0 / 320.0)) {

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)layoutUpper.getLayoutParams();
            params.height = (int)(windowWidth * 480.0 / 320.0);

            params = new LinearLayout.LayoutParams(windowWidth, windowHeight - (int)(windowWidth * 480.0 / 320.0));
            params.setMargins(0, (int)(windowWidth * 480.0 / 320.0), 0, 0);
            layoutDowner.setLayoutParams(params);

            params = new LinearLayout.LayoutParams(windowWidth, (int)(windowWidth * 480.0 / 320.0 * 65.0 / 480.0));
            params.setMargins(0, 0, 0, 0);
            layoutHeader.setLayoutParams(params);

            params = new LinearLayout.LayoutParams(windowWidth, (int)(windowWidth * 480.0 / 320.0 * 370.0 / 480.0));
            params.setMargins(0, 0, 0, 0);
            layoutBody.setLayoutParams(params);

            params = new LinearLayout.LayoutParams(windowWidth, (int)(windowWidth * 480.0 / 320.0 * 45.0 / 480.0));
            params.setMargins(0, 0, 0, 0);
            layoutBottom.setLayoutParams(params);
        } else {
            layoutUpper.setVisibility(View.GONE);
        }
        Intent intent = getIntent();
        this.warId = intent.getIntExtra("level", 0);
        String layout="";
        //以字符串的形式显示各个棋子的位置，一共有八关
        switch(warId)
        {
            case 0:
                layout="2, 0, 0S2, 1, 0S2, 2, 0S2, 3, 0S1, 2, 2S1, 3, 2S4, 0, 3S3, 2, 3S1, 2, 4S1, 3, 4";
                break;
            case 1:
                layout ="2, 0, 0S4, 1, 0S2, 3, 0S2, 0, 2S3, 1, 2S2, 3, 2S1, 1, 3S1, 2, 3S1, 0, 4S1, 3, 4";
                break;
            case 2:
                layout="2, 0, 0S4, 1, 0S2, 3, 0S1, 0, 2S1, 1, 2S1, 2, 2S1, 3, 2S2, 0, 3S3, 1, 3S2, 3, 3";
                break;
            case 3:
                layout="1, 0, 0S4, 1, 0S1, 3, 0S2, 0, 1S2, 3, 1S3, 1, 2S2, 0, 3S1, 1, 3S1, 2, 3S2, 3, 3";
                break;
            case 4:
                layout="4, 0, 0S2, 2, 0S2, 3, 0S3, 0, 2S1, 2, 2S1, 3, 2S2, 0, 3S2, 1, 3S1, 2, 3S1, 3, 3";
                break;
            case 5:
                layout="2, 0, 0S4, 1, 0S2, 3, 0S2, 1, 2S2, 2, 2S1, 0, 3S1, 3, 3S1, 0, 4S3, 1, 4S1, 3, 4";
                break;
            case 6:
                layout="4, 0, 0S3, 2, 0S2, 2, 1S1, 3, 1S1, 3, 2S2, 0, 2S2, 1, 2S1, 2, 3S1, 3, 3S3, 1, 4";
                break;
            case 7:
                layout="4, 0, 0S3, 2, 0S3, 2, 1S2, 0, 2S2, 1, 2S3, 2, 2S1, 2, 3S1, 3, 3S1, 0, 4S1, 3, 4";
                break;
        }
        //将布局转化为实际的棋子和它们的位置
        ArrayList<Chess> allChesses = StringToLayout.convertToChessLayout(layout);
        layoutChessBoard = (RelativeLayout) findViewById(R.id.chessboard_body);
        this.board = new ChessBoard(4 ,5);
        this.chessControl = new ChessControl(board);

        for(int i = 0; i< allChesses.size(); i++) {
            Chess piece = allChesses.get(i);
            this.chessControl.addPiece(piece);
            ImageView imageView = createImageView(layoutChessBoard.getContext(),
                    piece.getPosition().getPosx(),
                    piece.getPosition().getPosy(),
                    piece.getWidth(),
                    piece.getHeight(),
                    piece.getId());
            imageView.setOnTouchListener(new mTouch());
            layoutChessBoard.addView(imageView);
        }

        //redraw chess pieces
        layoutChessBoard.requestLayout();
        Intent intent1 = new Intent();
        intent1.setClass(this, MusicService.class);
        if(bmusicPlayed) {
            startService(intent1);
            Toast.makeText(this, "背景音乐开", Toast.LENGTH_SHORT).show();
        }
    }

   public class mTouch implements View.OnTouchListener{
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int id = v.getId();
            Chess piece =board.getPiece(v.getTag().toString());
            //当手指按下的时候
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                x1 = event.getRawX();
                y1 = event.getRawY();
                System.out.println("x1:"+(int)(x1/chessSizeUnit)+"y1:"+(int)(y1/chessSizeUnit));
            }
            //当手指抬起的时候
            if (event.getAction() == MotionEvent.ACTION_UP)
            {
                x2 = event.getRawX();
                y2 = event.getRawY();
                System.out.println("x2:"+x2+"y2:"+y2);
                TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f, 0.0f, 0.0f);
                animation.setDuration(1);
                v.startAnimation(animation);
                if (y1 - y2 > 50)//"向上滑:"
                {
                    int oldx=piece.getPosition().posx;
                    int oldy=piece.getPosition().posy;
                    Position pos=new Position(piece.getPosition().getPosx(),piece.getPosition().getPosy()-1);
                    piece.getPosition().posx=pos.posx;
                    piece.getPosition().posy=pos.posy;
                    if(board.setLayout()) {
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(v.getWidth(), v.getHeight());
                        params.setMargins((int) (pos.getPosx() * chessSizeUnit),
                                (int) (pos.getPosy() * chessSizeUnit), 0, 0);
                        v.setLayoutParams(params);
                        step++;
                        text1.setText("步数：" + step);
                        if(bmusicPlayed) {
                            moveMediaPlayer.start();
                        }
                    }
                    else
                        board.retrieve(oldx,oldy,piece);
                }
                else if (y2 - y1 > 50)//向下滑
                {
                    int oldx=piece.getPosition().posx;
                    int oldy=piece.getPosition().posy;
                    Position pos=new Position(piece.getPosition().getPosx(),piece.getPosition().getPosy()+1);
                    piece.getPosition().posx=pos.posx;
                    piece.getPosition().posy=pos.posy;
                    if(board.setLayout()) {
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(v.getWidth(), v.getHeight());
                        params.setMargins((int) (pos.getPosx() * chessSizeUnit),
                                (int) (pos.getPosy() * chessSizeUnit), 0, 0);
                        v.setLayoutParams(params);
                        step++;
                        text1.setText("步数：" + step );
                        if(bmusicPlayed) {
                            moveMediaPlayer.start();
                        }
                    }
                    else
                        board.retrieve(oldx,oldy,piece);
                }
                else if (x1 - x2 > 50)//向左滑
                {
                    int oldx=piece.getPosition().posx;
                    int oldy=piece.getPosition().posy;
                    Position pos=new Position(piece.getPosition().getPosx()-1,piece.getPosition().getPosy());
                    piece.getPosition().posx=pos.posx;
                    piece.getPosition().posy=pos.posy;
                    if(board.setLayout()) {
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(v.getWidth(), v.getHeight());
                        params.setMargins((int) (pos.getPosx() * chessSizeUnit),
                                (int) (pos.getPosy() * chessSizeUnit), 0, 0);
                        v.setLayoutParams(params);
                        step++;
                        text1.setText("步数：" + step);
                        if(bmusicPlayed) {
                            moveMediaPlayer.start();
                        }
                    }
                    else
                        board.retrieve(oldx,oldy,piece);
                }
                else if (x2 - x1 > 50)//向右滑
                {
                    int oldx=piece.getPosition().posx;
                    int oldy=piece.getPosition().posy;
                    Position pos=new Position(piece.getPosition().getPosx()+1,piece.getPosition().getPosy());
                    piece.getPosition().posx=pos.posx;
                    piece.getPosition().posy=pos.posy;
                    if(board.setLayout()) {
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(v.getWidth(), v.getHeight());
                        params.setMargins((int) (pos.getPosx() * chessSizeUnit),
                                (int) (pos.getPosy() * chessSizeUnit), 0, 0);
                        v.setLayoutParams(params);
                        step++;
                        text1.setText("步数：" + step);
                        if(bmusicPlayed) {
                            moveMediaPlayer.start();
                        }
                    }
                    else
                        board.retrieve(oldx,oldy,piece);
                }
                if(v.getTag() == "cao" && piece.getPosition().getPosx() == 1 && piece.getPosition().getPosy() == 3) {
                    if(bmusicPlayed) {
                        goodjobMediaPlayer.start();
                    }
                    LevelupDialog cdd = new LevelupDialog(ChessBoardActivity.this);
                    cdd.show();
                }
            }
            return true;
        }
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btn_backgruound_muisc) {
            bmusicPlayed = !bmusicPlayed;
            autoPlayBMusic();
        }
    }

    public void autoPlayBMusic() {
        Intent intent = new Intent();
        intent.setClass(this, MusicService.class);

        if(bmusicPlayed) {
            startService(intent);
            Toast.makeText(this, "背景音乐开", Toast.LENGTH_SHORT).show();
        } else {
            stopService(intent);
            Toast.makeText(this, "背景音乐关", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /*
     * 为每一个棋子创建图片并放在相应位置上
     */
    public ImageView createImageView(Context context, int x, int y, int w, int h, String drawName) {
        ImageView img = new ImageView (this);
        img.setVisibility(View.VISIBLE);
        int drawId = this.getResources().getIdentifier(drawName, "mipmap", this.getPackageName());
        Drawable drawable = getResources().getDrawable(drawId);
        img.setImageDrawable(drawable);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)(w*chessSizeUnit),
                (int)(h*chessSizeUnit));
        params.setMargins((int)(x*chessSizeUnit), (int)(y*chessSizeUnit), 0, 0);
        img.setLayoutParams(params);
        img.setTag(drawName);
        return img;
    }

    //过关成功，进入下一关
        public void reload() {
        Intent intent = getIntent();
        warId=warId+1;
        if(warId<8)
        {
            intent.putExtra("level", warId);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            finish();
            overridePendingTransition(0, 0);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        else
        {
            Toast.makeText(ChessBoardActivity.this,"您已完成所有关卡",Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
