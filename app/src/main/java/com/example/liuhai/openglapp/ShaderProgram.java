package com.example.liuhai.openglapp;
import static android.opengl.GLES20.*;

/**
 * 作者：liuhai
 * 时间：2019/3/4:11:59
 * 邮箱：185587041@qq.com
 * 说明：加载着色器的基类
 */
public class ShaderProgram {

    public final int programe;

    public    ShaderProgram(String shader, String frament){
        programe = ShardHelper.buildProgram(shader,frament);

    }

    public void UserProgram(){
    glUseProgram(programe);


    }


}
