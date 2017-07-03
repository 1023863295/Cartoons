package cn.pear.cartoon.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

/**
 * Created by liuliang on 2017/6/28.
 */

public class EditTextPreIme extends EditText {
    private OnKeyBackListener onKeyBackListener;

    public EditTextPreIme(Context context) {
        super(context);
    }

    public EditTextPreIme(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextPreIme(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        //因为按一次back键会传过来action_up/down两次keyevent 此方法会调用两次,都拦截但只处理actiondown
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            if(onKeyBackListener != null && event.getAction() == KeyEvent.ACTION_DOWN){
                onKeyBackListener.onKeyBack();//调用在edittext在browsersearchview中的实现
                return  true;
            }
        }
        return super.dispatchKeyEventPreIme(event);

    }

    public void setOnKeyBackListener(OnKeyBackListener listener) {
        this.onKeyBackListener = listener;
    }


    public interface OnKeyBackListener{
        void onKeyBack();
    }
}
