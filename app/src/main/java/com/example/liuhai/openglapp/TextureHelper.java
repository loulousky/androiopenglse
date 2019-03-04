package com.example.liuhai.openglapp;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import static android.opengl.GLES20.*;

/**
 * 作者：liuhai
 * 时间：2019/3/4:9:56
 * 邮箱：185587041@qq.com
 * 说明：纹理加载类
 */
public class TextureHelper {

    //加载材质
    public static int loadTexture(Context context,int res){

        final  int result[]=new int[1];
        //创建一个材质的ID，存到数组中
        glGenTextures(1,result,0);
        if(result[0]==0){
            //创建失败
            return 0;
        }
        //创建位图
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inScaled=false;
        Bitmap bitmap=BitmapFactory.decodeResource(context.getResources(),R.drawable.air_hockey_surface,options);
        if (bitmap==null){
            glDeleteTextures(1,result,0);//删除创建的材质ID
            return 0;
        }

        //使用纹理之前要绑定纹理告诉OPENGL后面要用这个纹理,绑定的是二维的纹理
        glBindTexture(GL_TEXTURE_2D,result[0]);
        //设置图片的纹理过滤，放大的纹理过滤，缩小的纹理过滤 分为（邻近过滤，双线性 ，三线性）
        //缩小用三线性，放大用双线性
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_LINEAR_MIPMAP_LINEAR);//三线性缩小
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_LINEAR);//放大的话用双线性
        //加载图片到OPENGL里面
        GLUtils.texImage2D(GL_TEXTURE_2D,0,bitmap,0);
        //加载成功后OPENGL拿到位图，android的位图可以去掉了
        bitmap.recycle();
        //生成材质过滤的MIP贴图,用于线性过滤的
        glGenerateMipmap(GL_TEXTURE_2D);
        //纹理加载完毕之后，解除绑定。0是解除 绑定过程中，对纹理进行操作，操作完成后解除绑定
        glBindTexture(GL_TEXTURE_2D,0);

        //返回纹理ID 接下来就是创建新的着色器把纹理带进去
        return result[0];








    }


}
