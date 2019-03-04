package com.example.liuhai.openglapp;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import javax.microedition.khronos.opengles.GL;

/**
 * 作者：liuhai
 * 时间：2019/2/26:10:18
 * 邮箱：185587041@qq.com
 * 说明：编译着色器，根据着色器的代码
 */
public class ShardHelper {

    /**
     * 编译顶点着色器
     *
     * @param shardcode
     * @return
     */
    public static int CompileVertexShard(String shardcode) {

        return CompileShard(GLES20.GL_VERTEX_SHADER, shardcode);
    }


    /**
     * 编译片段着色器
     */
    public static int CompileFragmentShare(String shardcode) {

        return CompileShard(GLES20.GL_FRAGMENT_SHADER, shardcode);

    }

    /**
     * androide 的opengl调用的是底层C的代码，
     * c和c plus对于成功的返回一般用0 或者非0 来表示
     *
     * @param type
     * @param code
     * @return
     */
    private static int CompileShard(int type, String code) {

        int shardId = GLES20.glCreateShader(type);//创建一个着色器，成功返回着色器的ID，以后对此着色器的操作都会通过着色器的ID来进行
        if (shardId == 0) {
            //为0表示创建失败
            Log.d("opengles", "创建着色器失败");//可以通过glerror来看具体的错误
            return 0;
        }
        //拿到着色器的ID，和CODE上传到OPENGL中
        GLES20.glShaderSource(shardId, code);//上传着色器的代码
        //上传过后，进行编译
        GLES20.glCompileShader(shardId);
        //编译以后要得到编译的结果 传int数组得到返回的结果码在opengl中很广泛的使用
        int[] compilestate = new int[1];
        //必须通过此方法来检查编译的返回值，结果放入int数组
        GLES20.glGetShaderiv(shardId, GLES20.GL_COMPILE_STATUS, compilestate, 0);
        //可以通过下面的方法来得到具体的编译结果的打印信息
        Log.d("opengles", GLES20.glGetShaderInfoLog(shardId));
        //判断返回的结果是否成功，不成功直接删除此shardid
        if (compilestate[0] == 0) {
            //编译失败
            Log.d("opengles", "编译失败");
            //删除此sharadid
            GLES20.glDeleteShader(shardId);

            return 0;
        }

        //成功得到shardid
        return shardId;
    }

    /**
     * 链接着色器ID到openGL中
     *
     * @return
     */
    public static int linkProgram(int Shardadid, int Sharadid2) {
        //先创建程序对象
        int progrogramid = GLES20.glCreateProgram();
        if (progrogramid == 0) {
            Log.d("opengles", "创建程序失败" + GLES20.glGetProgramInfoLog(progrogramid));
            return 0;
        }
        //创建成功链接着色器
        GLES20.glAttachShader(progrogramid, Shardadid);
        GLES20.glAttachShader(progrogramid, Sharadid2);
        GLES20.glLinkProgram(progrogramid);//链接程序
        //判断是否链接成功
        int[] linkstate = new int[1];
        GLES20.glGetProgramiv(progrogramid, GLES20.GL_LINK_STATUS, linkstate, 0);
        if (linkstate[0] == 0) {
            String reult = GLES20.glGetProgramInfoLog(progrogramid);
            Log.d("openglse", "链接失败" + reult);
            GLES20.glDeleteProgram(progrogramid);
            return 0;
        }
        return progrogramid;
    }


    /**
     * 对生成的程序ID进行验证，判断程序的性能，和是否可以运行
     *
     * @param programeid
     * @return
     */
    public static boolean valideteProgram(int programeid) {
        GLES20.glValidateProgram(programeid);
        int[] validateid = new int[1];
        GLES20.glGetProgramiv(programeid, GLES20.GL_VALIDATE_STATUS, validateid, 0);

        return validateid[0] != 0;


    }

    /**
     *
     * @param vertext
     * @param fragment
     * @return 编译着色器然后链接到程序
     */
    public static int  buildProgram(String vertext,String fragment){
        int program;
        int vertextshader=CompileVertexShard(vertext);
        int fragmentshader=CompileFragmentShare(fragment);

        program= linkProgram(vertextshader,fragmentshader);

        return program;



    }


}
