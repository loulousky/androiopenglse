package com.example.liuhai.openglapp;
import static android.opengl.GLES20.*;
/**
 * 作者：liuhai
 * 时间：2019/3/4:14:19
 * 邮箱：185587041@qq.com
 * 说明：
 */
public class ColorShaderProgram extends ShaderProgram{
    private int uMartx;
    private final int aPoint;
    private final int aColorl;

    public ColorShaderProgram(String shader, String frament) {
        super(shader, frament);
        uMartx=glGetUniformLocation(programe,"vMatrix");
        aPoint=glGetAttribLocation(programe,"vPosition");

        aColorl=glGetAttribLocation(programe,"aColor");



    }

    public void setUniform(float m[],int textureid){

        glUniformMatrix4fv(uMartx,1,false,m,0);




    }

    public int getaPoint() {
        return aPoint;
    }

    public int getaColorl() {
        return aColorl;
    }
}
