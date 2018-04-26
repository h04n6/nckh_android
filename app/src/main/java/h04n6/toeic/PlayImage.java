package h04n6.toeic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by hoang on 3/24/2018.
 */

//TODO load image from internet
public class PlayImage extends AsyncTask<String, Void, Bitmap> {

    private Bitmap bitmapHinh = null;
    private ImageView imageView;

    public PlayImage(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {

        try {
            URL url = new URL(strings[0]);
            InputStream inputStream = url.openConnection().getInputStream();
            bitmapHinh = BitmapFactory.decodeStream(inputStream);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmapHinh;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        imageView.setImageBitmap(bitmap);
    }
}