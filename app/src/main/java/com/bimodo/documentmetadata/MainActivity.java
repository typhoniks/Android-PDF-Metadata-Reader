 package com.bimodo.documentmetadata;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bimodo.documentmetadata.adapters.MyRecyclerAdapter;

import java.io.IOException;
import java.util.logging.Logger;


public class MainActivity extends AppCompatActivity {

    int ACTIVITY_CHOOSE_FILE = 192;
    static int PERMISSION_WRITE = 152;
    Button btnSelectFile;
    RecyclerView mRecyclerView;
    ReadPdfMetadata pdf;

    String[] lastMetadata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSelectFile = findViewById(R.id.btnSelectFile);
        btnSelectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile(v);
                requestWritePermission();
            }
        });

        mRecyclerView = findViewById(R.id.recyclerView);

    }

    //After Selecting File
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        pdf = new ReadPdfMetadata(data);
        if (requestCode == ACTIVITY_CHOOSE_FILE) {
            try {
                String[] metadata = pdf.getPdfMetadata();
                activateRecyclerView(metadata);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String[] restoredList = savedInstanceState.getStringArray("save");
        activateRecyclerView(restoredList);
        
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArray("save",lastMetadata);
    }

    //Request Write Permission
    private void requestWritePermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSION_WRITE);
    }


    //Open File Chooser Intent for Choosing TXT Files
    public void chooseFile(View view) {
        Intent chooseFile;
        Intent intent;
        chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
        chooseFile.setType("application/pdf");
        intent = Intent.createChooser(chooseFile, "Choose a file");
        startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);
    }

    private void activateRecyclerView(String[] metadataInfo) {

        lastMetadata = metadataInfo;
        MyRecyclerAdapter adapter = new MyRecyclerAdapter(this, metadataInfo);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }



}