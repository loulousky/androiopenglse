package com.example.liuhai.opengldata;

/**
 * 作者：liuhai
 * 时间：2019/3/4:11:29
 * 邮箱：185587041@qq.com
 * 说明：渲染桌子
 */
public class Table {
    private static final int  POSITION_COMPONENT_COUNT=2;
    private static final int  TEXTURE_COORDINATES_COMPONENT_COUNT=2;
    private static final int Stride=(POSITION_COMPONENT_COUNT+TEXTURE_COORDINATES_COMPONENT_COUNT)*4;
    private static final float[] vertexData={
            //前两顶点位置，后面两 材质的位置
        0f,0f,0.5f,0.5f,
            -0.5f,-0.8f,0f,0.9f,
            0.5f,-0.8f,1f,0.9f,
            0.5f,0.8f,1f,0.1f,
            -0.5f,0.8f,0f,0.1f,
            -0.5f,-0.8f,0f,0.9f
    };

    public void BindData(){



    }




}
