package com.ampme.challenge.utils.view;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ampme.challenge.R;

public class MessageView extends RelativeLayout {

    private MessageViewInterface mMessageViewInterface;
    private ProgressBar mProgressBar;
    private TextView mTextViewErrorMessage;
    private TextView mTextViewInformation;
    private Button mBtRetry;
    private RelativeLayout mBox;
    private boolean mMessageShow = false;
    private boolean mProgressIsShow = false;

    private
    @ColorRes
    int infoBoxColor;
    private
    @ColorRes
    int backgroundColor;
    private
    @ColorRes
    int textColor;
    private
    @ColorRes
    int textErrorColor;

    public interface MessageViewInterface {
        void retry();
    }

    public MessageView(Context context) {
        super(context);
        init(context);
    }

    public MessageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MessageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.message_view, this, true);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBarHorizontal);
        mTextViewErrorMessage = (TextView) findViewById(R.id.tvError);
        mBtRetry = (Button) findViewById(R.id.btRetry);
        mTextViewErrorMessage.setVisibility(View.GONE);
        mBox = (RelativeLayout) findViewById(R.id.box);
        mTextViewInformation = (TextView) findViewById(R.id.tvInformation);
        backgroundColor = R.color.greyTrans;
        textColor = R.color.white;
        textErrorColor = R.color.white;
    }

    public void setProperties(@ColorRes int infoBoxColor, @ColorRes int backgroundColor, @ColorRes int textColor, @ColorRes int textErrorColor) {
        this.infoBoxColor = infoBoxColor;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.textErrorColor = textErrorColor;

        mTextViewInformation.setTextColor(getResources().getColor(textColor));
        mTextViewErrorMessage.setTextColor(getResources().getColor(textErrorColor));
        setBackgroundColor(getResources().getColor(backgroundColor));
        mBox.setBackgroundColor(getResources().getColor(infoBoxColor));
    }

    public void setMessageViewInterface(final MessageViewInterface mMessageViewInterface) {
        this.mMessageViewInterface = mMessageViewInterface;
        mBtRetry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessageViewInterface.retry();
            }
        });
    }

    public void setMessageInformation(String text) {
        this.setVisibility(View.VISIBLE);
        this.setBackgroundColor(getResources().getColor(backgroundColor));
        mBox.setBackgroundColor(getResources().getColor(infoBoxColor));
        mTextViewInformation.setVisibility(View.VISIBLE);
        mTextViewInformation.setText(text);
    }

    public void setMessageError(String text, boolean asToast) {
        this.setVisibility(View.VISIBLE);
        hideProgessBar();
        if (asToast && !mMessageShow) {
            Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
        } else if (!mMessageShow) {
            this.mTextViewInformation.setVisibility(View.GONE);
            this.setBackgroundColor(getResources().getColor(R.color.greyTrans));
            mBox.setBackgroundColor(getResources().getColor(R.color.transparent));
            mTextViewErrorMessage.setVisibility(View.VISIBLE);
            mTextViewErrorMessage.setText(text);
            if (mMessageViewInterface != null) {
                mBtRetry.setVisibility(View.VISIBLE);
            }
        }
        mMessageShow = true;
    }

    public void showProgressBar() {
        hideMessageError();
        if (!mProgressIsShow) {
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.animate();
        }
        mProgressIsShow = true;
    }

    public void hideMessageError() {
        mMessageShow = false;
        mTextViewErrorMessage.setVisibility(View.GONE);
        this.setBackgroundColor(getResources().getColor(R.color.transparent));
        mBtRetry.setVisibility(View.GONE);
    }

    public static void hideLoadingView(MessageView messageView) {
        if (messageView != null) {
            messageView.hideProgessBar();
            messageView.setVisibility(View.GONE);
        }
    }

    public void hideProgessBar() {
        mProgressIsShow = false;
        mProgressBar.setVisibility(View.GONE);
        mProgressBar.clearAnimation();
    }
}
