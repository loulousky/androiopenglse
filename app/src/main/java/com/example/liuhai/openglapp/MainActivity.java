package com.example.liuhai.openglapp;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;


/**
 *
 * opengl的使用，前期用法差不多
 * 一般创建GLSurfaceView 设置版本设置渲染类Render
 * render有三个待实现的方法
 * onSurfacecreate() 创建的时候调用，一般只调用一次，但是有时候会调用多次
 * onSurfacechange()surfaceView大小改变的时候调用，横竖屏切换时
 * onDrawFrame()渲染每一帧的时候调用，可以手动调用或者自动按照机器的刷新率调用
 */
public class MainActivity extends Activity {

//    // Used to load the 'native-lib' library on application startup.
//    static {
//        System.loadLibrary("native-lib");
//    }

    private GLSurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityManager manager = ContextCompat.getSystemService(this, ActivityManager.class);
        if (manager.getDeviceConfigurationInfo().reqGlEsVersion >= 0x20000) {
            //支持OPGL2.0
            surfaceView = findViewById(R.id.surfaceview);
            findViewById(R.id.up).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            findViewById(R.id.down).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            surfaceView.setEGLContextClientVersion(2);//必须制定版本
            surfaceView.setRenderer(new GlRender());//设置具体的渲染代码
            surfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
            //最简单的OPENGL的使用
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (surfaceView != null) {
            surfaceView.onResume();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (surfaceView != null) {
            surfaceView.onPause();
        }
    }


}
