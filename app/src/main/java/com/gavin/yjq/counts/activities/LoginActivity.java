package com.gavin.yjq.counts.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.gavin.yjq.counts.R;
import com.gavin.yjq.counts.gen.UserDao;
import com.gavin.yjq.counts.model.*;
import com.gavin.yjq.counts.services.TestService;
import com.gavin.yjq.counts.util.GreenDaoUtils;
import com.gavin.yjq.counts.util.OkHttpClientManager;

import static com.gavin.yjq.counts.PublicData.*;

public class LoginActivity extends AppCompatActivity{


    private UserLoginTask mAuthTask = null;

    private EditText mAccountView;
    private EditText mPasswordView;
    private View mProgressView;
    private TextView mRegView;
    private Button mEmailSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mAccountView = (EditText) findViewById(R.id.account);
        mAccountView.setHintTextColor(Color.WHITE);
//        populateAutoComplete();

        mRegView = (TextView) findViewById(R.id.reg);
        mRegView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setHintTextColor(Color.WHITE);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mProgressView = findViewById(R.id.login_progress);
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        mAccountView.setError(null);
        mPasswordView.setError(null);

        String email = mAccountView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mAccountView.setError(getString(R.string.error_field_required));
            focusView = mAccountView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 6;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mEmailSignInButton.setVisibility(show ? View.GONE : View.VISIBLE);
            mEmailSignInButton.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mEmailSignInButton.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mEmailSignInButton.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mAccount;
        private final String mPassword;

        UserLoginTask(String account, String password) {
            mAccount = account;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against LoginActivity network service.

            try {
                String result = OkHttpClientManager.postAsString("http://115.159.118.111/user/login",
                        new OkHttpClientManager.Param("account",mAccount),
                        new OkHttpClientManager.Param("password",mPassword));
                System.out.println("LoginActivity:"+result);
                http re = JSON.parseObject(result,http.class);
                if (re.getStatus()==200) {
                    UserDao dao= GreenDaoUtils.getSingleTon().getmDaoSession(LoginActivity.this).getUserDao();
                    User user = new User(1,mAccount,mPassword);
                    dao.insert(user);
                    String userStr = re.getData();
                    userInfo = JSON.parseObject(userStr,usr.class);
                    System.out.println(result);
                    appsInfo = new ArrayList<>();
                    if (!userInfo.getAppCare().equals("[]")&&!userInfo.getAppCare().equals("")) {
                        List<careList> list = JSON.parseArray(userInfo.getAppCare(), careList.class);
                        for (int i = 0; i < list.size(); i++) {
                            result = OkHttpClientManager.getAsString("http://115.159.118.111/app/appinfo?id=" + list.get(i).getKey());
                            System.out.println("LoginActivity:" + result);
                            re = JSON.parseObject(result, http.class);
                            String appStr = re.getData();
                            app app=JSON.parseObject(appStr, app.class);
                            if (app!=null) {
                                appsInfo.add(app);
                            }
                        }
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(LoginActivity.this, TestService.class);
                            startService(intent);
                        }
                    }).start();
                }else{
                    return false;
                }
                // Simulate network access.
            } catch (IOException e) {
                return false;
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                Intent intent = new Intent(LoginActivity.this,TestActivity_.class);
                startActivity(intent);
                finish();
            } else {
                showProgress(false);
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}

