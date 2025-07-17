package com.example.smartcity.frontend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.smartcity.R;
import com.example.smartcity.backend.cache.UserCache;
import com.example.smartcity.backend.dao.UserDao;
import com.example.smartcity.backend.dao.UserDaoImpl;
import com.example.smartcity.backend.entity.User;
import com.example.smartcity.util.LoginCallback;

/**
 * This activity use to implement login function
 *
 */
public class LoginActivity extends AppCompatActivity {

    private EditText loginUsername;
    private EditText loginPwd;
    private Button btnLogin;
    private boolean showPwd = false;
    private ImageView btnShowPwd;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userDao = new UserDaoImpl();
        userDao.initializeUser();

        loginUsername = findViewById(R.id.login_user_name);
        loginPwd = findViewById(R.id.login_pwd);
        btnLogin = findViewById(R.id.login_btn);
        btnShowPwd = findViewById(R.id.login_show_btn);
        btnLogin.setOnClickListener(v -> {
            String username = loginUsername.getText().toString().trim();
            String pwd = loginPwd.getText().toString().trim();
            login(username, pwd);
        });

        /* the image from www.iconfont.cn */
        // set the state of show/hide pwd button
        btnShowPwd.setImageResource(R.mipmap.login_hide_pwd);
        btnShowPwd.setOnClickListener(v -> {
            showPassword();
        });
    }

    /**
     * used to implement user login function
     *
     * @param username user's account
     * @param pwd user's password
     */
    private void login(String username, String pwd) {
        // if the account or password is null, this user won't be allowed to login
        if (username.isEmpty()) {
            Toast.makeText(this, "Username can't be null", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pwd.isEmpty()) {
            Toast.makeText(this, "Password can't be null", Toast.LENGTH_SHORT).show();
            return;
        }

        // jump to main page
        checkUser(username,pwd);
    }

    /**
     * used to show/hide the password
     */
    public void showPassword() {
        if (!showPwd) {
            /* the image from www.iconfont.cn */
            btnShowPwd.setImageResource(R.mipmap.login_show_pwd);
            HideReturnsTransformationMethod show = HideReturnsTransformationMethod.getInstance();
            loginPwd.setTransformationMethod(show);
            showPwd = true;
        } else {
            /* the image from www.iconfont.cn */
            btnShowPwd.setImageResource(R.mipmap.login_hide_pwd);
            PasswordTransformationMethod hide = PasswordTransformationMethod.getInstance();
            loginPwd.setTransformationMethod(hide);
            showPwd = false;
        }
    }

    /**
     * used to check if the user exist
     * call the callback function to check if the user exist or not
     * The callback function will only be executed after the query is completed.
     * @param username user's name
     * @param pwd     user's password
     */
    private void checkUser(String username, String pwd) {
        User user = new User(username, pwd);
        // call the dao to check database
        UserDao userDao = new UserDaoImpl();
        userDao.checkUser(user, new LoginCallback() {
            // when running here, it will be blocked
            // until the onComplete() in userDaoImpl finished
            @Override
            public void onCallback(boolean isUserExists) {
                // thus the userExist update, and app can
                // judge the state of user correctly
                if (isUserExists) {
                    UserCache.getInstance().setCurrentUserName(username);
                    // update the state of login user
                    user.updateLoginUserState(username);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}