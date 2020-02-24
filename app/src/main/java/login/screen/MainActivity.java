package login.screen;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import login.screen.login.LoginButton;
import login.screen.login.LoginFragment;
import login.screen.login.SignUpFragment;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private boolean isLogin = true;

    FrameLayout login;
    FrameLayout signup;
    LoginButton loginButton;
    CoordinatorLayout coordinatorLayout;
    FlexibleFrameLayout wrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginFragment loginFragment = new LoginFragment();
        SignUpFragment signUpFragment = new SignUpFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.login_fragment, loginFragment)
                .replace(R.id.sign_up_fragment, signUpFragment)
                .commit();
        login = findViewById(R.id.login_fragment);
        signup = findViewById(R.id.sign_up_fragment);
        loginButton = findViewById(R.id.button);
        coordinatorLayout = findViewById(R.id.main);
        wrapper = findViewById(R.id.wrapper);

        login.setRotation(-90);



        loginButton.setOnSignUpListener(signUpFragment);
        loginButton.setOnLoginListener(loginFragment);


        loginButton.setOnButtonSwitched(isLogin -> {

           coordinatorLayout.setBackgroundColor(ContextCompat.getColor(
                   this,
                   isLogin ? R.color.colorPrimary : R.color.secondPage));

        });

        login.setVisibility(INVISIBLE);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coordinatorLayout.getRootView()
                        .setBackgroundColor(ContextCompat.getColor(
                                MainActivity.this,
                                isLogin ? R.color.colorPrimary : R.color.secondPage));

                switchFragment(view);
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        login.setPivotX(login.getWidth() / 2);
        login.setPivotY(login.getHeight());
        signup.setPivotX(signup.getWidth() / 2);
        signup.setPivotY(signup.getHeight());
    }

    public void switchFragment(View v) {
        if (isLogin) {
            login.setVisibility(VISIBLE);
            login.animate().rotation(0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    signup.setVisibility(INVISIBLE);
                    signup.setRotation(90);
                    wrapper.setDrawOrder(FlexibleFrameLayout.ORDER_LOGIN_STATE);
                }
            });
        } else {
            signup.setVisibility(VISIBLE);
            signup.animate().rotation(0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    login.setVisibility(INVISIBLE);
                    login.setRotation(-90);
                    wrapper.setDrawOrder(FlexibleFrameLayout.ORDER_SIGN_UP_STATE);
                }
            });
        }

        isLogin = !isLogin;
        loginButton.startAnimation();
    }

}