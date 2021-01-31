package com.example.neo.mymmorpg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;

/**
 * Created by neo on 2/26/2018.
 */

public class LoadImageTask extends AsyncTask<Context, Void, BitmapDrawable> {

    private BitmapDrawable[] sprite = new BitmapDrawable[2];
    private int imgRessource;
    private int w, h;

    public LoadImageTask(int ressource, int w, int h) {
        this.imgRessource = ressource;
        this.w = w;
        this.h = h;
    }

    public BitmapDrawable[] getBitmapDrawable() {
        return sprite;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    // w et h sont sa largeur et hauteur définis en pixels
    private BitmapDrawable setImage(final Context c, final int ressource, final int w, final int h) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(c.getResources(), ressource, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, w, h);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(c.getResources(), ressource, options);

        return new BitmapDrawable(c.getResources(), Bitmap.createScaledBitmap(bitmap, w, h, true));
    }

    @Override
    protected BitmapDrawable doInBackground(Context... contexts) {
        return setImage(contexts[0], imgRessource, w, h);
    }

    @Override
    protected void onPostExecute(BitmapDrawable result) {
        super.onPostExecute(result);
        sprite[0] = result;
        sprite[1] = Utility.flip(result);
    }
}
