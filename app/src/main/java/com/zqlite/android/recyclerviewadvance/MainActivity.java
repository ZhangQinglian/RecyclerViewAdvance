package com.zqlite.android.recyclerviewadvance;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.Image;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private Handler handler = new Handler();

    public static final int ADD_DATA_DURATION = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        final RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(itemAnimator);

        final MyItemDecoration myItemDecoration = new MyItemDecoration();
        recyclerView.addItemDecoration(myItemDecoration);

        final MyAdapter adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);


        FloatingActionButton addDataBtn = (FloatingActionButton) findViewById(R.id.add_data_button);
        addDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addData();
            }
        });

        FloatingActionButton subtractDataBtn = (FloatingActionButton) findViewById(R.id.subtract_data_button);
        subtractDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.subtractData();
            }
        });
    }

    private class MyAdapter extends RecyclerView.Adapter{

        private final String[] imageUrlArray = {
                "http://o6p4e1uhv.bkt.clouddn.com/tu24967_22.jpg",
                "http://o6p4e1uhv.bkt.clouddn.com/cce4b48f8c5494ee7008eb9528f5e0fe98257eca.jpg",
                "http://o6p4e1uhv.bkt.clouddn.com/tu25422_4.jpg",
                "http://o6p4e1uhv.bkt.clouddn.com/ad7fd688d43f879424026deed01b0ef41ad53a2e.jpg",
                "http://o6p4e1uhv.bkt.clouddn.com/tu25422_6.jpg",
                "http://o6p4e1uhv.bkt.clouddn.com/refsdg.jpg",
                "http://o6p4e1uhv.bkt.clouddn.com/ggfnvbn.jpg",
                "http://o6p4e1uhv.bkt.clouddn.com/sghgfsh.jpg",
                "http://o6p4e1uhv.bkt.clouddn.com/asfasdf.jpg",
                "http://o6p4e1uhv.bkt.clouddn.com/fsbgbsfg.jpg",
                "http://o6p4e1uhv.bkt.clouddn.com/gsfdtrysrtsfdg.jpg",
                "http://o6p4e1uhv.bkt.clouddn.com/sdfgsrters.jpg"};

        private List<String> datas = new ArrayList<>();

        public MyAdapter(){
        }

        public void addData(){
            Random random = new Random();
            datas.add(imageUrlArray[random.nextInt(imageUrlArray.length)]);
            notifyItemInserted(datas.size());
        }

        public void subtractData(){
            if(datas.size() <1){
                return ;
            }
            Random random = new Random();
            int removeIndex = random.nextInt(datas.size());
            datas.remove(removeIndex);
            notifyItemRemoved(removeIndex);
        }

        public int getSize(){
            return datas.size();
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            RecyclerView.LayoutParams reLP = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT);
            reLP.setMargins(20, 20, 20, 20);
            CardView cardView = new CardView(parent.getContext());
            cardView.setLayoutParams(reLP);


            CardView.LayoutParams cLP = new CardView.LayoutParams(parent.getMeasuredWidth()/3,(int)(parent.getMeasuredWidth()/3*1.3));
            ImageView imageView = new ImageView(parent.getContext());
            imageView.setLayoutParams(cLP);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            cardView.addView(imageView);

            MyHolder holder = new MyHolder(cardView);

            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyHolder myHolder = (MyHolder)holder;
            ImageView imageView = myHolder.imageView;
            Picasso.with(imageView.getContext()).load(datas.get(position)).into(imageView);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        private class MyHolder extends RecyclerView.ViewHolder{

            public ImageView imageView ;
            public MyHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) ((CardView)itemView).getChildAt(0);
            }
        }
    }

    private class MyItemDecoration extends RecyclerView.ItemDecoration{

        Paint paint = new Paint();

        public MyItemDecoration(){
            paint.setStyle(Paint.Style.STROKE);
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(10);
        }
        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            for(int i = 0 ; i<parent.getChildCount();i++){
                if( (i-1)%3 == 0 ){
                    View child = parent.getChildAt(i);
                    RecyclerView.LayoutParams rLP = (RecyclerView.LayoutParams) child.getLayoutParams();
                    int left = child.getLeft() ;
                    int top = child.getTop();
                    int right = child.getRight();
                    int bottom = child.getBottom() ;
                    c.drawRect(left,top,right,bottom,paint);
                }
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            if( (position + 1)%3==0 ){
                outRect.set(0,0,-20,0);
            }
            if(position % 3 == 0){
                outRect.set(-20,0,0,0);
            }
        }
    }
}
