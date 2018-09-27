package com.hojong.meokgol.data_model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;

public abstract class BmpModel implements Serializable {
    protected byte[] mBmpByteArr;

    public void setBmp(InputStream bmpStream, String imgExtension)
    {
        Log.d(toString(), "imgExtension=" + imgExtension);
        Bitmap bmp = BitmapFactory.decodeStream(bmpStream);
//        bmp = Bitmap.createScaledBitmap(bmp, 200, 200, true);

        // compress
        Bitmap.CompressFormat fmt = null;
        switch (imgExtension.toLowerCase())
        {
            case "jpg":
            case "jpeg":
                fmt = Bitmap.CompressFormat.JPEG;
                break;
            case "png":
                fmt = Bitmap.CompressFormat.PNG;
                break;
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(fmt, 90, stream);
        mBmpByteArr = stream.toByteArray();
    }

    public Bitmap getBmp()
    {
        if (mBmpByteArr != null)
            return BitmapFactory.decodeByteArray(mBmpByteArr, 0, mBmpByteArr.length);
        else
            return null;
    }
}
