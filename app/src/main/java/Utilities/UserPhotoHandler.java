package Utilities;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
// User photo handler class to handle user photo actions
public class UserPhotoHandler {
    private static final int PERM_CODE_CAMERA = 101;
    private static final int CAMERA_REQUEST = 102;
    private static final int GALLERY_PERM = 103;
    private static final int GALLERY_REQ = 104;
    private static final int ZERO = 0;

    private static final int PHOTOQ = 100;

    private ImageView imageView;
    private Activity activity;
     Uri uri;

    // Constructor
    public UserPhotoHandler(ImageView imageView, Activity activity) {
        this.imageView = imageView;
        this.activity = activity;
    }
    // Check for permissions and open the gallery
    public void checkPermissionAndOpenGallery() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]
                    {Manifest.permission.READ_EXTERNAL_STORAGE}, GALLERY_REQ);
        } else {
            openGallery();
        }
    }
    // Ask for camera permissions
    public void askCameraPermissions(){
        if (ContextCompat.checkSelfPermission
                (activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions
                    (activity, new String[] {Manifest.permission.CAMERA}, PERM_CODE_CAMERA);
        } else {
            openCamera();
        }
    }
    // Handle the result of the permission request
    public void onRequestPermissionsResult
    (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERM_CODE_CAMERA) {
            if (grantResults.length > ZERO
                    && grantResults[ZERO] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(activity, "Camera Permission is Required to Use camera."
                        , Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == GALLERY_REQ) {
            if (grantResults.length > ZERO &&
                    grantResults[ZERO] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(activity, "Permission denied to read your External storage"
                        , Toast.LENGTH_SHORT).show();
            }
        }
    }
    // Open the camera
    public void openCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(camera, CAMERA_REQUEST);
    }
    // Open the gallery
    public void openGallery() {
        Intent gallery = new Intent
                (Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(gallery, GALLERY_PERM);
    }
    // Handle the result of the camera or gallery
    public Uri onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Uri photoHolderUri = null;
        if (requestCode == CAMERA_REQUEST) {
            Bitmap photoHolderBitmap = (Bitmap) data.getExtras().get("data");
            photoHolderUri = bitmapToUri(photoHolderBitmap, activity);
            imageView.setImageURI(photoHolderUri);
            return photoHolderUri;

        }
        if (resultCode == RESULT_OK && requestCode == GALLERY_PERM) {
            // Get the image URI
            photoHolderUri = data.getData();
            imageView.setImageURI(photoHolderUri);
            return photoHolderUri;
        }
        return photoHolderUri;
    }
    // Convert a Bitmap to a Uri
    public static Uri bitmapToUri(Bitmap image, Context context) {
        File imagesFolder = new File(context.getCacheDir(), "images");
        Uri uri = null;
        try {
            imagesFolder.mkdirs();
            File file = new File(imagesFolder, "captured_image.jpg");
            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, PHOTOQ, stream);
            stream.flush();
            stream.close();
            uri = FileProvider.getUriForFile(context.getApplicationContext(),
                    "com.example.myapplication"+".provider", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uri;
    }
    // Convert a URI to a Bitmap
    public static Bitmap uriToBitmap(Context context, Uri imageUri) {
        Bitmap bitmap = null;
        try {
            // Obtain an InputStream from the URI using the ContentResolver
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            // Decode the InputStream into a Bitmap
            bitmap = BitmapFactory.decodeStream(inputStream);
            // Make sure to close the InputStream
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // Handle the situation where the URI does not point to an accessible file
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IO exceptions
        }
        return bitmap;
    }
    // Convert a URI to a base64 string
    public static String uriToBase64(Uri uri, Context context) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, PHOTOQ, outputStream); // Compress Bitmap
            byte[] byteArray = outputStream.toByteArray();
            String base64Image = "";
            if (byteArray != null && byteArray.length > 0) {
                String base64EncodedImage = Base64.encodeToString(byteArray, Base64.NO_WRAP);
                base64Image = "data:image/jpeg;base64," + base64EncodedImage;
            }
            return base64Image;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // Convert a bitmap to a base64 string
    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, PHOTOQ, outputStream); // Compress Bitmap
        byte[] byteArray = outputStream.toByteArray();
        String base64Image = "";
        if (byteArray != null && byteArray.length > 0) {
            String base64EncodedImage = Base64.encodeToString(byteArray, Base64.NO_WRAP);
            base64Image = "data:image/jpeg;base64," + base64EncodedImage;
        }
        return base64Image;



    }
    // Compress a bitmap to a specified quality
    public static Bitmap compressBitmap(Bitmap originalBitmap, int quality) {
        // Compress the original bitmap to a byte array
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        originalBitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);

        // Decode the byte array back into a bitmap
        byte[] bitmapData = out.toByteArray();
        return BitmapFactory.decodeByteArray(bitmapData, ZERO, bitmapData.length);
    }
    // Resize a bitmap to a specified width and height
    public static Bitmap resizeBitmap(Bitmap bitmap, float maxWidth, float maxHeight) {
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        float scaleWidth = maxWidth / originalWidth;
        float scaleHeight = maxHeight / originalHeight;
        float scaleFactor = Math.min(scaleWidth, scaleHeight); // Maintains the aspect ratio

        return Bitmap.createScaledBitmap(bitmap, (int)(originalWidth * scaleFactor)
                , (int)(originalHeight * scaleFactor), true);
    }
    // Convert a base64 string to a Bitmap
    public static Bitmap base64ToBitmap(String base64Str) {
        // Check for and remove data URI scheme if present
        if (base64Str.startsWith("data:image/png;base64,")) {
            base64Str = base64Str.substring("data:image/png;base64,".length());
        } else if (base64Str.startsWith("data:image/jpeg;base64,")) {
            base64Str = base64Str.substring("data:image/jpeg;base64,".length());
        }
        // Decode base64 string
        byte[] decodedBytes = Base64.decode(base64Str, Base64.DEFAULT);

        // Convert the byte array to Bitmap
        return BitmapFactory.decodeByteArray(decodedBytes, ZERO, decodedBytes.length);
    }





}



