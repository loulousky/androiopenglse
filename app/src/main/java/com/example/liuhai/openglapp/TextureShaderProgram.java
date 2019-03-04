package com.example.liuhai.openglapp;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import static android.opengl.GLES20.*;
/**
 * 作者：liuhai
 * 时间：2019/3/4:13:53
 * 邮箱：185587041@qq.com
 * 说明：纹理着色器
 */
public class TextureShaderProgram extends ShaderProgram{

    private final int uMatraixL;
    private final int uTextureL;
    private final int aPointl;
    private final int aTextureL;


    private TextureShaderProgram(String v,String f){
super(v,f);
        uMatraixL=glGetUniformLocation(programe,"vMatrix");

        uTextureL=glGetUniformLocation(programe,"uTexture");

        aPointl=glGetAttribLocation(programe,"vPosition");

        aTextureL=glGetAttribLocation(programe,"aTexture");

    }

    public void setUniform(float m[],int textureid){

        glUniformMatrix4fv(uMatraixL,1,false,m,0);

        glActiveTexture(GL_TEXTURE0);

        glBindTexture(GL_TEXTURE_2D,textureid);

        glUniform1i(uTextureL,0);



    }

    public int getaPointl() {
        return aPointl;
    }

    public int getaTextureL() {
        return aTextureL;
    }
}
