package com.project.hellonepal.diary.content;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.project.hellonepal.R;
import com.project.hellonepal.diary.model.Diary;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;


public class CreateDiaryItem extends Activity implements View.OnClickListener, OnBMClickListener {


    private Realm realm;
    private TextView currentDate;
    private SimpleDateFormat format;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_diary);

        currentDate = ((TextView) findViewById(R.id.currentDate));
        format = new SimpleDateFormat("MMM d, yyyy h:mm a");

        String date = format.format(new Date());
        currentDate.setText(date);

        imageView = (ImageView) findViewById(R.id.imageView2);


        Button save = ((Button) findViewById(R.id.saveDiary));

        save.setOnClickListener(this);

        BoomMenuButton bmb = (BoomMenuButton) findViewById(R.id.bmb);

        for (int i = 0; i < bmb.getButtonPlaceEnum().buttonNumber(); i++) {
            bmb.addBuilder(new SimpleCircleButton.Builder()
                    .normalImageRes(R.drawable.ic_photo_camera_black_18dp).listener(this));
        }

    }

    @Override
    public void onClick(View v) {

        if (realm == null) {
            Realm.init(CreateDiaryItem.this);
            realm = Realm.getDefaultInstance();
        }
        realm.beginTransaction();


        final EditText title = ((EditText) findViewById(R.id.diaryTitleText));

        final EditText content = ((EditText) findViewById(R.id.diaryContentText));


        Diary diary = realm.createObject(Diary.class);
        diary.setTitle(title.getText().toString());

        diary.setContent(content.getText().toString());
        try {
            diary.setDate(format.parse(currentDate.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (imageView.getDrawable() != null) {
            Bitmap imageBitMap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitMap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            diary.setPhoto(stream.toByteArray());

        }

        realm.commitTransaction();

        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();

        finish();

        setContentView(R.layout.diaries_list);
    }

    @Override
    public void onBoomButtonClick(int index) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", 0);
        cameraIntent.putExtra("android.intent.extras.LENS_FACING_FRONT", 0);
        startActivityForResult(cameraIntent, 1888);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1888 && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }
}
