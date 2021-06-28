package com.example.loadmorelinechart;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * @apiNote 加载对话框
 */
public class LoadingDialogFragment extends DialogFragment {

    private TextView mLoadingTv;

    public static LoadingDialogFragment newInstance(String loadingText) {
        LoadingDialogFragment f = new LoadingDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("data", loadingText);
        f.setArguments(bundle);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //去掉对话框头部标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getDialog().getWindow();
        if (window != null)
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       // setCancelable(false);

        return inflater.inflate(R.layout.fragment_loading_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        String loadingText = null;
        if (bundle != null) {
            loadingText = bundle.getString("data");
        }

        mLoadingTv = (TextView) view.findViewById(R.id.loading_tv);

        /*mLoadingTv.setVisibility(TextUtils.isEmpty(loadingText) ? View.GONE : View.VISIBLE);
        mLoadingTv.setText(loadingText);*/
        if (!TextUtils.isEmpty(loadingText)){
            mLoadingTv.setText(loadingText);
        }

    }
}
