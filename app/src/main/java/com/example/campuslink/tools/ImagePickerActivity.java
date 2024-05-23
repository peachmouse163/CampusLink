package com.example.campuslink.tools;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.campuslink.R;
import com.example.campuslink.thin.CreateThinActivity;
import com.github.gzuliyujiang.imagepicker.ActivityBuilder;
import com.github.gzuliyujiang.imagepicker.CropImageView;
import com.github.gzuliyujiang.imagepicker.ImagePicker;
import com.github.gzuliyujiang.imagepicker.PickCallback;

public class ImagePickerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_picker);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImagePicker.getInstance().onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ImagePicker.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    public void onCamera(View view) {
        ImagePicker.getInstance().startCamera(this, true, new PickCallback() {
            @Override
            public void onPermissionDenied(String[] permissions, String message) {
                Toast.makeText(ImagePickerActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cropConfig(ActivityBuilder builder) {
                builder.setMultiTouchEnabled(true)
                        .setGuidelines(CropImageView.Guidelines.ON_TOUCH)
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .setRequestedSize(400, 400)
                        .setFixAspectRatio(true)
                        .setAspectRatio(1, 1);
            }

            @Override
            public void onCropImage(@Nullable Uri imageUri) {
                Toast.makeText(ImagePickerActivity.this, String.valueOf(imageUri), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onGallery(View view) {
        ImagePicker.getInstance().startGallery(this, false, new PickCallback() {
            @Override
            public void onPermissionDenied(String[] permissions, String message) {
                Toast.makeText(ImagePickerActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPickImage(@Nullable Uri imageUri) {
                CreateThinActivity.fileUri = imageUri;
                Toast.makeText(ImagePickerActivity.this, String.valueOf(imageUri), Toast.LENGTH_SHORT).show();
            }
        });
    }
}