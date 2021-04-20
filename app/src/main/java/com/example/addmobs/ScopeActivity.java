package com.example.addmobs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Objects;

public class ScopeActivity extends AppCompatActivity {

    ImageView imageView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scope);

        imageView=findViewById(R.id.image2);
        button=findViewById(R.id.save_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable bitmapDrawable= (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap=bitmapDrawable.getBitmap();
                saveImgToGallery(bitmap);

            }
        });

    }

    private void saveImgToGallery(Bitmap bitmap) {

        OutputStream fos;
        try {
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q)
            {
                ContentResolver contentResolver=getContentResolver();
                ContentValues contentValues=new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME,"image_"+".jpg");
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE,"image/jpg");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES+ File.separator+"Test Folder");
                Uri imageUri=contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);

                fos= (FileOutputStream) contentResolver.openOutputStream(Objects.requireNonNull(imageUri));

                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
                Objects.requireNonNull(fos);


                Toast.makeText(this, "Image Saved", Toast.LENGTH_SHORT).show();

            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Failed"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

        }
    }
}