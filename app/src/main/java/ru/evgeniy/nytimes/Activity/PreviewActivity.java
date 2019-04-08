package ru.evgeniy.nytimes.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ru.evgeniy.nytimes.R;

public class PreviewActivity extends AppCompatActivity {
    public String EMAIL_KEY = "EMAIL_KEY";
    private TextView mTextView;
    private Button mButton;
    private String text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        text = getIntent().getStringExtra(EMAIL_KEY);

        mTextView = findViewById(R.id.tv_previewText);
        mTextView.setText(text);

        mButton = findViewById(R.id.btn_send);
        mButton.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setType("text/plain");
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] {getString(R.string.email_to)});
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.Subject));
            intent.putExtra(Intent.EXTRA_TEXT, text);
            if (isEmailInstalled(getApplicationContext())){
                startActivity(Intent.createChooser(intent, getString(R.string.are_you_want)));
            }
            else {
                showToast();
            }
        });
    }
    
    public void showToast(){
        Toast.makeText(this, R.string.no_apps_email, Toast.LENGTH_LONG).show();
    }

    public boolean isEmailInstalled(@NonNull Context context){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        final PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, 0);

        return list.size() != 0;
    }
}
