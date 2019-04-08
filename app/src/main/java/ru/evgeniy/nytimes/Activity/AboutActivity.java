package ru.evgeniy.nytimes.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ru.evgeniy.nytimes.R;

public class AboutActivity extends AppCompatActivity {

    private LinearLayout mLinearLayout;
    private ImageButton mImageButtonTelegram, mImageButtonVk, mImageButtonGit;
    private Button mButtonSendComment;
    private EditText mEditTextMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        init();

        mImageButtonTelegram.setOnClickListener(view -> openLink(getString(R.string.telegram)));
        mImageButtonVk.setOnClickListener(view -> openLink(getString(R.string.vk)));
        mImageButtonGit.setOnClickListener(view -> openLink(getString(R.string.github)));
        mButtonSendComment.setOnClickListener(view -> { if (isEmpty()) sendMessage();});
        createTextView();
    }

    private void init() {
        mImageButtonTelegram = findViewById(R.id.ib_telegram);
        mImageButtonVk = findViewById(R.id.ib_vk);
        mImageButtonGit = findViewById(R.id.ib_github);
        mButtonSendComment = findViewById(R.id.btn_send_comment);
        mLinearLayout = findViewById(R.id.my_linear);
        mEditTextMessage = findViewById(R.id.et_message);
    }


    private boolean isEmpty() {
        return !mEditTextMessage.getText().toString().isEmpty();
    }

    private void openLink(@NonNull String link) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(link));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            showMessage(getString(R.string.no_apps_browser));
        }
    }

    private void sendMessage() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.email_send)});
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.Subject));
        intent.putExtra(Intent.EXTRA_TEXT, mEditTextMessage.getText().toString());
        if (isEmailInstalled(getApplicationContext())) {
            startActivity(Intent.createChooser(intent, getString(R.string.are_you_want)));
        } else {
            showMessage(getString(R.string.no_apps_email));
        }
    }

    private void showMessage(@NonNull String msg) {
        Snackbar.make(this.mLinearLayout, msg, Snackbar.LENGTH_LONG).show();
    }

    private boolean isEmailInstalled(@NonNull Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        final PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, 0);

        if (list.size() == 0)
            return false;
        else
            return true;
    }

    private void createTextView() {
        TextView tv = new TextView(this);
        tv.setText(R.string.disclaimer);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        tv.setLayoutParams(layoutParams);
        tv.setGravity(Gravity.CENTER);
        mLinearLayout.addView(tv);
    }
}
