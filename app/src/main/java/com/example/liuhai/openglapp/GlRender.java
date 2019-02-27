package com.example.liuhai.openglapp;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.file.FileAlreadyExistsException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import static android.opengl.GLES20.*;

/**
 * 作者：liuhai
 * 时间：2019/2/25:14:16
 * 邮箱：185587041@qq.com
 * 说明： 1 2 3必写是最简单的OPENGL的一种实现
 */
public class GlRender implements GLSurfaceView.Renderer {

    private static final int POSITION_COMPONMENT_COUNT = 2;//每个顶点2个向量 x y

    private static final int POSITION_COMPOINENT_COLOR_COUNT=3;//颜色的三个值
    private static String U_COLOR = "uColor"; //着色器的uniform的名字
    private int uColorLocation;//opengl程序对象中的位置，程序链接成功后查询可以得到这个位置，没有链接得不到

    private static String ATTRIBUTE = "vPosition";//顶点属性的名字

    private static String ATTRIBUTECOLOR="aColor";//顶点着色器新增的颜色属性

    private int aPositionLocation;//链接后得到顶点属性的位置

    //在openglse中 对于手机屏幕的坐标 XY 最大最小值是 1 和 -1 所以对于各个顶点的坐标要让其显示在屏幕中要让他的XY在其-1 1之间
    private float[] tabVertex = {
            //顶点一定要逆时针写
//            0.0f,   0.5f,
//            -0.5f, -0.5f,
//            0.5f,  -0.5f,
            //三角形扇
            0f, 0f,1f,1f,1f,
            -0.5f, -0.5f,0.7f,0.7f,0.7f,
            0.5f, -0.5f,0.7f,0.7f,0.7f,
            0.5f, 0.5f,0.7f,0.7f,0.7f,
            -0.5f, 0.5f,0.7f,0.7f,0.7f,
            -0.5f,- 0.5f,0.7f,0.7f,0.7f,

            //中间线

            -0.5f, 0f,1.0f,0.0f,0.0f,
            0.5f, 0f,1.0f,0.0f,0.0f,

            //两个木拍点
            0f, -0.25f,0.0f,1.0f,0.0f,
            0f, 0.25f,0.0f,0.0f,1.0f,





    };
    private FloatBuffer floatBuffer;
    private int progrogramid;
    private int aColorLocation;


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //创建floatbuffer

        //分配内存
        floatBuffer = ByteBuffer.allocateDirect(tabVertex.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(tabVertex);
        //1 设置GLE创建的时候的颜色画布 必写 （清空屏幕的颜色）
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0);
        //得到顶点着色器的代码
        String vertexShardSource = verticesShader;
        //得到片段着色器的代码
        String fragmentShardSource = fragmentShader;
        /**
         * 先得到 着色器的代码，然后要对代码进行编译
         * 成功后返回具体的着色器的ID
         */
        int vertexsharadid = ShardHelper.CompileVertexShard(vertexShardSource);
        int fragmentsharadid = ShardHelper.CompileFragmentShare(fragmentShardSource);
        /**
         * 得到两个着色器的ID=之后要把这两个着色器ID链接进OPENGL中
         * 链接着色器到程序中 返回程序的ID
         */
        progrogramid = ShardHelper.linkProgram(vertexsharadid, fragmentsharadid);
        /**
         * 对程序进行验证，opengles自带验证方法，对这个程序的性能 速度进行验证
         *判断是否可以运行
         *
         */
        if (ShardHelper.valideteProgram(progrogramid)) {
            GLES20.glUseProgram(progrogramid);
            //得到uniform ucolor的位置 以后更新uniform值的时候会用到这个值
            uColorLocation = GLES20.glGetUniformLocation(progrogramid, U_COLOR);
            aPositionLocation = GLES20.glGetAttribLocation(progrogramid, ATTRIBUTE);

            aColorLocation = GLES20.glGetAttribLocation(progrogramid,ATTRIBUTECOLOR);
            //这两个位置有什么用？获得这两个位置之后，传递给OPENGL，让OPENGL可以知道在哪可以得到这两个属性对应的数据了



            //告诉OPENGL从缓冲区的头开始读取数据
            floatBuffer.position(0);//从最开始开始读取
            //这个函数非常重要，告诉opengl从顶底位置开始读取数据，读取向量2 就是读取X Y ,读取类型是float
            //第一个参数 就是顶点属性的位置，第二个参数 对于这个顶点，有几个向量我们这个2个，在OPENGL中有四个，第三个参数数据类型，第四个参数 使用整型参数才需要设置
            //第五个参数 当数组存储多于一个属性的时候才有意义，这个后来再说
            //第六个参数 告诉OPENGL从哪里读取数据

            GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONMENT_COUNT, GLES20.GL_FLOAT, false, (POSITION_COMPOINENT_COLOR_COUNT+POSITION_COMPONMENT_COUNT)*4, floatBuffer);
            //顶点属性和数据关联之后调用下面方法
            GLES20.glEnableVertexAttribArray(aPositionLocation);//通过这个调用，OPENGL现在知道去哪里寻找它所需要的数据了，上面一个方法只是数据和属性的关联
            //之后就可以开始绘制顶点了在ondrawframe中进行绘制
            //颜色的值要从x,y之后开始读取，所以要跳到2的位置开始读取
            floatBuffer.position(POSITION_COMPONMENT_COUNT);
            //设置读取颜色的顶点着色器的规范  stride的参数说明 我们在float数组中加入了颜色的值，每两个相邻的颜色值之间相差5个POSTIION，换算城byte大小就是5*4，stride就是间隔的意思
            GLES20.glVertexAttribPointer(aColorLocation,POSITION_COMPOINENT_COLOR_COUNT,GLES20.GL_FLOAT,false,(POSITION_COMPOINENT_COLOR_COUNT+POSITION_COMPONMENT_COUNT)*4,floatBuffer);
            //顶点属性和数据关联之后调用下面方法
            GLES20.glEnableVertexAttribArray(aColorLocation);//通过这个调用，OPENGL现在知道去哪里寻找它所需要的数据了，上面一个方法只是数据和属性的关联
            //之后就可以开始绘制顶点了在ondrawframe中进行绘制


        } else {

            Log.d("openglse", "程序有问题");
        }


    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        //2 设置更新VIEW的宽高 必写 设置视图的尺寸，这就告诉了OpenGL可以用来渲染surface的大小。
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //3 必写   清空屏幕，清空屏幕后调用glClearColor(）中设置的颜色填充屏幕；
        Log.d("openglse","onDrawFrame");
          GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);//情况实现原本oncreate的颜





        //在此处绘制 点 线 三角形 注释掉的原因，我们在float中已经加过了颜色信息，并且GLSL中片段着色器的颜色属性也得到了更新，所以不需要再通过uniform属性来设置颜色了
     //   GLES20.glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);//更新着色器ucolor的值 台子白色
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);//画三角形，读取floatbuffer前六个的数据绘制
 //       GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 3, 6);//画三角形，读取floatbuffer前六个的数据绘制


//
//        //画中间线 颜色红色
// //       GLES20.glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2);
//
//        //绘制点
//   //     GLES20.glUniform4f(uColorLocation, 0.0f, 1.0f, 0.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1);
////
//    //    GLES20.glUniform4f(uColorLocation, 0.0f, .0f, 1.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1);


    }


    // 顶点着色器 何为顶点，在opengl 中 对图形定义有三种方式 点 线 面 ，面只能定义三角形，所以的图形都是基于三角形而来
    //顶点着色器 顾名思义 用来在OPENgl中渲染 图形的顶点 如三角形的三个顶点的确定

    //代码使用的是GLSL语言为OPENGL定义的规范
//    private static final String verticesShader
//            = "attribute vec4 vPosition;            \n" // 顶点位置属性vPosition
//            + "void main(){                         \n"
//            + "   gl_Position = vPosition;\n" // 确定顶点位置
//            + "gl_PointSize=10.0;                     \n"
//            + "}";
//
//    // 片元着色器的脚本 片源着色器简单来说就是在顶点确定后，对其确定的顶点间的区域进行渲染 填充
//    //代码使用的是GLSL语言为OPENGL定义的规范
//    private static final String fragmentShader
//            = "precision mediump float;         \n" // 声明float类型的精度为中等(精度越高越耗资源)
//            + "uniform vec4 uColor;             \n" // uniform的属性uColor
//            + "void main(){                     \n"
//            + "   gl_FragColor = uColor;        \n" // 给此片元的填充色
//            + "}";


    /**
     * 顶点着色器新增属性 color
     */
    private static final String verticesShader
            = "attribute vec4 vPosition;            \n" // 顶点位置属性vPosition
            +"attribute vec4 aColor;            \n"//新增的颜色属性 属性只有顶点着色器有
            +"varying vec4 vColor;            \n"//新增的颜色属性 varying 修饰符让顶点着色器和片段着色器值能够共享
            + "void main(){                         \n"
            + "   gl_Position = vPosition;\n" // 确定顶点位置
            + "   vColor = aColor;\n" // 设置颜色的值 共享
            + "gl_PointSize=10.0;                     \n"
            + "}";

    // 片元着色器的脚本 片源着色器简单来说就是在顶点确定后，对其确定的顶点间的区域进行渲染 填充
    //代码使用的是GLSL语言为OPENGL定义的规范
    private static final String fragmentShader
            = "precision mediump float;         \n" // 声明float类型的精度为中等(精度越高越耗资源)
            +"varying vec4 vColor;            \n"//新增的颜色属性 varying 修饰符让顶点着色器和片段着色器值能够共享
         //   + "uniform vec4 uColor;             \n" // uniform的属性uColor 去掉uniform不需要再手动为GL设置颜色值了，共享了顶点着色器读到的值
            + "void main(){                     \n"
            + "   gl_FragColor = vColor;        \n" // 给此片元的填充色
            + "}";



}
