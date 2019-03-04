package com.example.liuhai.opengldata;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.file.FileAlreadyExistsException;

import static android.opengl.GLES20.*;
/**
 * 作者：liuhai
 * 时间：2019/3/4:11:13
 * 邮箱：185587041@qq.com
 * 说明：顶点的数组数据
 */
public class VertexArray {
    private FloatBuffer floatBuffer;


    public VertexArray(float[] vertexData){
        floatBuffer=ByteBuffer.allocateDirect(vertexData.length*4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData);
    }

    public void setVertexAttributePointer(int dataoffset,int attributeLocation,int component,int stride){
        floatBuffer.position(dataoffset);//设置偏移量
        glVertexAttribPointer(attributeLocation,component,GL_FLOAT,false,stride,floatBuffer);
        glEnableVertexAttribArray(attributeLocation);//
        floatBuffer.position(0);


    }


}
