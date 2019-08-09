package com.polimentes.utilitieskotlin.images;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @author LAdrian GrupoBECM
 * Description:
 * created on 30/12/2016
 */

/**
 * AsyncTask para transformación de una image
 * int inputType tipo de archivo de entrada valores definidos en ImageResource
 * InputStream or Bitmap dependiendo del input de entrada
 * IImageManager interfaz que comunica el resultado de la conversion
 * ***** PARAMETROS OPCIONALES estos se encuentran en las constantes ImageResource
 * int resizeWidth medida de ancho a redimensionar en pixeles
 * int resizeHeight medida de alto a redimensionar en pixeles
 * Sintaxis new ImageEncoder.execute(inputType,image,imageManager, resizeWidth,resizeHeight)
 */
public class ImageEncoder extends AsyncTask<Object, Void, String> {
    private static final int INPUT_TYPE = 0;
    private static final int IMAGE = 1;
    private static final int MANAGER = 2;
    private static final int RESIZE_WIDTH = 3;
    private static final int RESIZE_HEIGHT = 4;
    //Esta constante esta definida por un numero de elementos detras de resize_width
    private static final int OPTIONAL_PARAMS = 3;
    private BitmapFactory.Options options;
    private ByteArrayOutputStream imageToSend;
    private int resizeWidth;
    private int resizeHeight;
    private IImageManager imageManager;
    private int inputType;
    private byte[] imageToSendInBytes;
    private String imageToSendBase64;


    @Override
    protected void onPreExecute() {
        options = new BitmapFactory.Options();
        inputType = ImageResource.INPUT_TYPE_INPUT_STREAM;
        options.inSampleSize = ImageResource.SAMPLE_SIZE;
        imageToSend = new ByteArrayOutputStream();
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Object... params) {
        Bitmap imageResized;
        InputStream picture;
        inputType = (int) params[INPUT_TYPE];
        imageManager = (IImageManager) params[MANAGER];

        if (inputType == ImageResource.INPUT_TYPE_BITMAP) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Bitmap bitmap = (Bitmap) params[IMAGE];
            bitmap.compress(Bitmap.CompressFormat.JPEG, ImageResource.QUALITY_COMPRESS_MAX, outputStream);
            picture = new ByteArrayInputStream(outputStream.toByteArray());
        } else
            picture = (InputStream) params[IMAGE];

        Bitmap imageToUpload = BitmapFactory.decodeStream(picture, null, options);
        if (params.length > OPTIONAL_PARAMS) {
            resizeWidth = (int) params[RESIZE_WIDTH];
            resizeHeight = (int) params[RESIZE_HEIGHT];
        } else {
            resizeWidth = imageToUpload.getWidth();
            resizeHeight = imageToUpload.getHeight();
        }

        imageResized = Bitmap.createScaledBitmap(imageToUpload, resizeWidth, resizeHeight, true);
        imageResized.compress(Bitmap.CompressFormat.JPEG, ImageResource.QUALITY_COMPRESS_MAX, imageToSend);
        imageToSendInBytes = imageToSend.toByteArray();
        imageToSendBase64 = Base64.encodeToString(imageToSendInBytes, Base64.DEFAULT);

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        imageManager.onSuccesfulConvertion(imageToSendInBytes, imageToSendBase64);
        super.onPostExecute(s);
    }
}

/*
@Override
    public void sendPhoto(final Uri uri) {
        try {
            Log.d(App.TAG_LOG, "Codificando foto");
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            new ImageEncoder().execute(ImageResource.INPUT_TYPE_INPUT_STREAM, inputStream, new IImageManager() {
                @Override
                public void onSuccesfulConvertion(Bitmap image) {

                }

                @Override
                public void onSuccesfulConvertion(byte[] image) {

                }

                @Override
                public void onSuccesfulConvertion(byte[] image, String imageEncoded) {
                    imageCoded = imageEncoded.replace("\n", "");
                    photoRequest.createPhoto(imageCoded, ChallengeRegistrationInteractor.this, ChallengeRegistrationInteractor.this);
                    imageToDelete = uri;
                }
            });
        } catch (FileNotFoundException e) {
            Log.d(App.TAG_LOG, "No se encontró la foto");
            //listener.onError(R.string.error_connection_failed);
        }
    }
 */
