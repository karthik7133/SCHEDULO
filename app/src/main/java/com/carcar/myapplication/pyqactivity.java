package com.carcar.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class pyqactivity extends AppCompatActivity {

    TextView schedulo;
    CardView maths,cse1005,database,coa,cse2009,bckground;
    Dialog dialog;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pyqactivity);
        Window window=getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.neon_violate));






        schedulo = findViewById(R.id.schedulo);
        Shader gradient = new LinearGradient(
                0, 0, 0, schedulo.getTextSize(),
                new int[]{Color.parseColor("#C0C0C0"), Color.parseColor("#4B0082")},
                null, Shader.TileMode.CLAMP
        );

        schedulo.getPaint().setShader(gradient);
        schedulo.invalidate();  // Refresh the view to apply the gradient



        dialog = new Dialog(this);
        dialog.setContentView(R.layout.pyqtypes);
        CardView cat1=dialog.findViewById(R.id.cat1);
        CardView cat2=dialog.findViewById(R.id.cat2);
        CardView fat=dialog.findViewById(R.id.fat);

// Ensure dialog window is not null before modifying it
        if (dialog.getWindow() != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }



        maths=findViewById(R.id.mathcard);
        cse1005=findViewById(R.id.cse1005);
        cse2009=findViewById(R.id.cse2009);
        database=findViewById(R.id.DataBasecard);
        coa =findViewById(R.id.COAcard);
        maths.setOnClickListener(view -> {
            dialog.show();
            cat1.setOnClickListener(v -> {
                openPdf("matcat1.pdf");
                dialog.dismiss();
            });
            cat2.setOnClickListener(v -> {
                openPdf("matcat2.pdf");
                dialog.dismiss();
            });
            fat.setOnClickListener(v -> {
                openPdf("matfat.pdf");
                dialog.dismiss();
            });
        });

        cse2009.setOnClickListener(view -> {
            dialog.show();
            cat1.setOnClickListener(v -> {
                openPdf("cse2009_cat.pdf");
                dialog.dismiss();
            });
            cat2.setOnClickListener(v -> {
                openPdf("CSE2009_CAT2.pdf");
                dialog.dismiss();
            });
            fat.setOnClickListener(v -> {
                openPdf("CSE2009_FAT.pdf");
                dialog.dismiss();
            });
        });

        cse1005.setOnClickListener(view -> {
            dialog.show();
            cat1.setOnClickListener(v -> {
                openPdf("CSE1005_CAT1.pdf");
                dialog.dismiss();
            });
            cat2.setOnClickListener(v -> {
                openPdf("CSE1005_CAT2.pdf");
                dialog.dismiss();
            });
            fat.setOnClickListener(v -> {
                openPdf("CSE1005_FAT.pdf");
                dialog.dismiss();
            });
        });

        database.setOnClickListener(view -> {
            dialog.show();
            cat1.setOnClickListener(v -> {
                openPdf("CSE2007_CAT1.pdf");
                dialog.dismiss();
            });
            cat2.setOnClickListener(v -> {
                openPdf("CSE2007_CAT2.pdf");
                dialog.dismiss();
            });
            fat.setOnClickListener(v -> {
                openPdf("CSE2007_FAT.pdf");
                dialog.dismiss();
            });
        });

        coa.setOnClickListener(view -> {
            dialog.show();
            cat1.setOnClickListener(v -> {
                openPdf("ECE2002_CAT1.pdf");
                dialog.dismiss();
            });
            cat2.setOnClickListener(v -> {
                openPdf("ECE2002_cat2.pdf");
                dialog.dismiss();
            });
            fat.setOnClickListener(v -> {
                openPdf("ECE2002_FAT.pdf");
                dialog.dismiss();
            });
        });

    }








    private void openPdf(String fileName) {
        try {
            // Copy PDF from assets to cache
            File file = new File(getCacheDir(), fileName);
            if (!file.exists()) {
                InputStream inputStream = getAssets().open(fileName);
                FileOutputStream outputStream = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.close();
                inputStream.close();
            }

            // Open with Intent
            Uri pdfUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(pdfUri, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}