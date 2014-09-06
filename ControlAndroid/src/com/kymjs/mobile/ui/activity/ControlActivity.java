package com.kymjs.mobile.ui.activity;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.ThreadPoolExecutor;

import org.kymjs.aframe.core.KJThreadExecutors;
import org.kymjs.aframe.ui.ViewInject;
import org.kymjs.aframe.ui.activity.BaseActivity;

import android.view.KeyEvent;
import android.view.View.OnLongClickListener;

import com.kymjs.mobile.AppContext;

public abstract class ControlActivity extends BaseActivity implements
        OnLongClickListener {
    public static int PORT = 8899;

    protected AppContext application;
    private DatagramSocket socket;
    private ThreadPoolExecutor threadPool;

    @Override
    protected void initData() {
        super.initData();
        threadPool = (ThreadPoolExecutor) KJThreadExecutors
                .newCachedThreadPool();
        application = (AppContext) getApplication();
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            ViewInject.toast("网络连接失败");
            skipActivity(aty, Main.class);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        handleKeyBoardEvent(keyCode, event);
        return true;
    }

    /**
     * 处理按键事件
     */
    private void handleKeyBoardEvent(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            sendMessage("keyboard:" + application.keyBack);
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {
            sendMessage("keyboard:" + application.keyMenu);
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            sendMessage("keyboard:" + application.keyVolumeUp);
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            sendMessage("keyboard:" + application.keyVolumeDown);
        }
    }

    /**
     * net request
     */
    protected void sendMessage(String datas) {
        threadPool.submit(new SendCommandThread(datas.getBytes()));
    }

    /**
     * 向电脑发送命令的线程
     */
    private class SendCommandThread implements Runnable {
        private byte[] datas;

        public SendCommandThread(byte[] datas) {
            this.datas = datas;
        }

        @Override
        public void run() {
            try {
                InetAddress pcAddress = InetAddress
                        .getByName(application.ip);
                DatagramPacket packet = new DatagramPacket(datas,
                        datas.length, pcAddress, PORT);
                socket.send(packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
