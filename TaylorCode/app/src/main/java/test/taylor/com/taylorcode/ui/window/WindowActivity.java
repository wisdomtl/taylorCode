package test.taylor.com.taylorcode.ui.window;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import test.taylor.com.taylorcode.R;

public class WindowActivity extends Activity implements View.OnClickListener, CustomPopupWindow.OnItemClickListener {

    //    private PopupWindow popupWindow;
    private CustomPopupWindow popupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View content = LayoutInflater.from(this).inflate(R.layout.window_activity, null);
        setContentView(content);
        findViewById(R.id.btn_show_window).setOnClickListener(this);
        findViewById(R.id.btn_show_popup_window).setOnClickListener(this);
        findViewById(R.id.btn_outside).setOnClickListener(this);
    }

    private View getWindowView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.window_content, null);
        return view;
    }


    /**
     * window case1:show window
     * TBC: how to dismiss
     *
     * @param context
     */
    private void showWindow(Context context) {
        if (context == null) {
            Log.v("ttaylor", "WindowActivity.showWindow()" + "  context is null");
            return;
        }
        WindowManager windowManager = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        //this flag allow touching outside window
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        //android.permission.SYSTEM_ALERT_WINDOW is needed,or permission denied for window type 2003
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;
        windowManager.addView(getWindowView(context), layoutParams);
    }

    //transition is much more customizable than setAnimationStyle() but there is api level constraint
//    private Transition getTransition(int gravity){
//        Slide slide = new Slide(gravity) ;
//        slide.setDuration(800) ;
//        return slide ;
//    }


    /**
     * window case2:popup window shown at the bottom of screen
     * TBC:how to use interpolation
     *
     * @param context
     * @param rootView the root view of the hole activity
     */
    public void showBottomPopupWindow(Context context, View rootView) {
//        if (popupWindow == null) {
//            View windowView = getWindowView(context);
//            bindWindowClickListener(windowView);
//            popupWindow = new PopupWindow(windowView,
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
//
//            popupWindow.setBackgroundDrawable(new BitmapDrawable());
//            //TBC:interceptor touch event on popupwindow
//            popupWindow.setTouchInterceptor(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    return false;
//                }
//            });
//            //popup animation
////            popupWindow.setAnimationStyle(R.style.popup_window_anim);
//            //transition is much more customizable than setAnimationStyle() but there is api level constraint
////            popupWindow.setEnterTransition(getTransition(Gravity.BOTTOM));
////            popupWindow.setExitTransition(getTransition(Gravity.RIGHT));
//            //listen popup window dismiss event
//            PopupWindow.OnDismissListener onDismissListener = new PopupWindow.OnDismissListener() {
//                @Override
//                public void onDismiss() {
//                    Log.v("ttaylor", "WindowActivity.onDismiss()" + "  ");
//                    changeBackgroundAlpha(WindowActivity.this, 1.0f);
//                }
//            };
//            popupWindow.setOnDismissListener(onDismissListener);
//        }
//
//        if (popupWindow.isShowing()) {
//            popupWindow.dismiss();
//            changeBackgroundAlpha(this, 1.0f);
//        } else {
//            popupWindow.showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
//            changeBackgroundAlpha(this, 0.5f);
//        }

    }


    public void showBottomPopupWindow2(Context context) {
        if (popupWindow == null) {
            View windowView = getWindowView(context);
            popupWindow = new CustomPopupWindow((ViewGroup) windowView,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, this);

            popupWindow.setOnItemClickListener(this);
        }

        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }

    }

    private void bindWindowClickListener(View rootView) {
        rootView.findViewById(R.id.btn_sexual_content).setOnClickListener(this);
        rootView.findViewById(R.id.btn_violent).setOnClickListener(this);
        rootView.findViewById(R.id.btn_spam).setOnClickListener(this);
        rootView.findViewById(R.id.btn_cancel).setOnClickListener(this);

    }

    private void changeBackgroundAlpha(Activity activity, float alpha) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = alpha;
        window.setAttributes(layoutParams);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_show_window:
                showWindow(this);
                break;
            case R.id.btn_show_popup_window:
                showBottomPopupWindow2(this);
                break;
            case R.id.btn_outside:
                Toast.makeText(this, "btn out side of pop up window", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_violent:
                Log.v("ttaylor", "WindowActivity.onClick()" + "  violent");
                popupWindow.dismiss();
                break;
            case R.id.btn_sexual_content:
                Log.v("ttaylor", "WindowActivity.onClick()" + "  sexual");
                popupWindow.dismiss();
                break;
            case R.id.btn_spam:
                Log.v("ttaylor", "WindowActivity.onClick()" + "  spam");
                popupWindow.dismiss();
                break;
            case R.id.btn_cancel:
                Log.v("ttaylor", "WindowActivity.onClick()" + "  ");
                popupWindow.dismiss();
                break;
        }
    }

    @Override
    public void onWindowItemClick(View view) {
        Log.v("ttaylor", "WindowActivity.onWindowItemClick()" + "  ");
        int id = view.getId();
        switch (id) {
            case R.id.btn_violent:
                Toast.makeText(this, "btn_violent", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_sexual_content:
                Toast.makeText(this, "btn_sexual_content", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_spam:
                Toast.makeText(this, "btn_spam", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_cancel:
                Toast.makeText(this, "btn_cancel", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
