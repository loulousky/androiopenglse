package com.example.liuhai.openglapp;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 作者：liuhai
 * 时间：2019/3/4:11:10
 * 邮箱：185587041@qq.com
 * 说明：纹理着色器
 */
public class TextureRender implements GLSurfaceView.Renderer {
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }


    /**
     * 顶点着色器新增属性 color
     */
    private static final String verticesShader
            = "attribute vec4 vPosition;            \n" // 顶点位置属性vPosition
            +"attribute vec4 aColor;            \n"//新增的颜色属性 属性只有顶点着色器有
            +"varying vec4 vColor;            \n"//新增的颜色属性 varying 修饰符让顶点着色器和片段着色器值能够共享
            +"uniform mat4 vMatrix;            \n"//正交投影的矩阵
            +"attribute vec2 aTexture;            \n"//纹理的属性
            +"varying vec2 vTexture;            \n"//纹理的属性共享给片段着色器
            + "void main(){                         \n"
            + "   gl_Position = vMatrix*vPosition;\n" // 确定顶点位置 与矩阵相乘修复画面的比例
            + "   vColor = aColor;\n" // 设置颜色的值 共享
            + "   vTexture = aTexture;\n" // 设置纹理的值 共享
            + "gl_PointSize=10.0;                     \n"
            + "}";

    // 片元着色器的脚本 片源着色器简单来说就是在顶点确定后，对其确定的顶点间的区域进行渲染 填充
    //代码使用的是GLSL语言为OPENGL定义的规范
    private static final String fragmentShader
            = "precision mediump float;         \n" // 声明float类型的精度为中等(精度越高越耗资源)
            +"varying vec4 vColor;            \n"//新增的颜色属性 varying 修饰符让顶点着色器和片段着色器值能够共享
            +"varying vec2 vTexture;            \n"//新增的纹理
            +"uniform simpler2D uTexture;            \n"//OPENGL用来接收实际的纹理数据
            //   + "uniform vec4 uColor;             \n" // uniform的属性uColor 去掉uniform不需要再手动为GL设置颜色值了，共享了顶点着色器读到的值
            + "void main(){                     \n"
            //      + "   gl_FragColor = vColor;        \n" // 给此片元的填充色 有材质了，不要颜色了
            + "   gl_FragColor = texture2D(uTexture,vTexture);        \n" // 调用texture2D 的函数读入纹理中特定坐标的颜色值，然后给gl_fragcolor
            + "}";





}
