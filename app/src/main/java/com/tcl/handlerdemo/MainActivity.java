package com.tcl.handlerdemo;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "YJ===>"+MainActivity.class.getName();
    private TextView display;
    private HandlerTest1 mHandlerTest1;
    private HandlerTest2 mHandlerTest2;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = findViewById(R.id.display);

        mHandlerTest2 = new HandlerTest2(Looper.getMainLooper());

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: 1");
                Looper.prepare();
                mHandlerTest1 = new HandlerTest1(Looper.myLooper());
//                Message msg = new Message();
//                msg.obj = "子线程发送的消息Hi~Hi";
//                mHandlerTest1.sendMessage(msg);
//                Message msg2 = Message.obtain();
//                msg2.obj = "子线程发送的消息Hi~Hi";
//                mHandlerTest2.sendMessage(msg2);
                Looper.loop();
            }
        }).start();

        HandlerTest1 handler = new HandlerTest1(Looper.myLooper());
        Message msg2 = Message.obtain();
        msg2.obj = "主线程发送的消息Hi~Hi";
        handler.sendMessage(msg2);
    }
    private class HandlerTest1 extends Handler {
        private HandlerTest1(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "handleMessage: 2");
            super.handleMessage(msg);
            System.out.println("YJ===>子线程收到：" + msg.obj);
//            Message message = new Message();
//            message.obj = "O(∩_∩)O";
//            mHandlerTest2.sendMessage(message);
        }
    }

    private class HandlerTest2 extends Handler {
        private HandlerTest2(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG, "handleMessage: 3");
            display.setText("在主线程中,收到子线程发来消息:" + msg.obj);
//            if (counter == 0) {
//                mHandlerTest1 = new HandlerTest1(Looper.myLooper());
//                Message message = new Message();
//                message.obj = "主线程发送的消息Xi~Xi";
//                mHandlerTest1.sendMessage(message);
//                counter++;
//            }
        }
    }
}
