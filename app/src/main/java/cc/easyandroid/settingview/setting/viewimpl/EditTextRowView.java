package cc.easyandroid.settingview.setting.viewimpl;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cc.easyandroid.settingview.setting.DialogRowView;
import cc.easyandroid.settingview.util.Para;


public class EditTextRowView extends DialogRowView {
    private ImageView child_ImageView;
    private TextView value_TextView;

    /**
     * The edit text shown in the dialog.
     */
    private EditText mEditText;

    public EditText getEditText() {
        return mEditText;
    }

    private String mText;

    public EditTextRowView(Context context) {
        super(context);
        mEditText = new EditText(context);

        // Give it an ID so it can be saved/restored
        mEditText.setId(android.R.id.edit);

		/*
         * The preference framework and view framework both have an 'enabled' attribute. Most likely, the 'enabled' specified in this XML is for the preference framework, but it was also given to the view framework. We reset the enabled state.
		 */
        mEditText.setEnabled(true);
        // int viewlayout_id = getResources().getIdentifier("edittext_container", "layout", getContext().getPackageName());
        // setDialogLayoutResource(R.layout.edittext_container);
    }

    ViewGroup container;

    @Override
    protected View onCreateDialogView() {
        container = new LinearLayout(getContext());
        return container;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        showDialog();
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult) {
            String value = mEditText.getText().toString();
            setText(value);
        }
    }

    private void initValueData() {
        value_TextView.setText(getText());
    }

    /**
     * Saves the text to the {@link SharedPreferences}.
     *
     * @param text The text to save
     */
    public void setText(String text) {
        final boolean wasBlocking = shouldDisableDependents();
        mText = text;
        persistString(text);//保存值  赋值
        initValueData();
        if (para != null) {
            para.value = mText;
        }
        final boolean isBlocking = shouldDisableDependents();
        if (isBlocking != wasBlocking) {
            // notifyDependencyChange(isBlocking);
        }
    }

    public boolean shouldDisableDependents() {
        return TextUtils.isEmpty(mText);
    }

    public String getText() {
        return mText;
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        EditText editText = mEditText;
        editText.setText(getText());

        ViewParent oldParent = editText.getParent();
        if (oldParent != view) {
            if (oldParent != null) {
                ((ViewGroup) oldParent).removeView(editText);
            }
            onAddEditTextToDialogView(view, editText);
        }
    }

    protected void onAddEditTextToDialogView(View dialogView, EditText editText) {
        if (container != null) {
            container.addView(editText, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    /**
     * 初始化set的值
     */
    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        setText(restoreValue ? getPersistedString(mText) : defaultValue.toString());
    }

    /**
     * 在这里初始化view显示的数据
     */
    @Override
    protected void onInitViewData() {
        super.onInitViewData();
        initValueData();
    }

    private Para<String> para;

    public void setPara(Para<String> para) {
        this.para = para;
    }

    @Override
    protected boolean needInputMethod() {
        // We want the input method to show, if possible, when dialog is displayed
        return true;
    }

    // --------------------------------------------------------------------------- view

    /**
     * 初始化View
     */
    @Override
    public View initWidget() {
        ininImageView();
        initTextView();
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
        layout.addView(value_TextView);
        layout.addView(child_ImageView);
        return layout;
    }

    private void ininImageView() {
        child_ImageView = new ImageView(getContext());
        child_ImageView.setPadding(1, 0, 0, 0);
    }

    private void initTextView() {
        value_TextView = new TextView(getContext());
        value_TextView.setPadding(10, 0, 10, 0);
        value_TextView.setSingleLine(true);
        value_TextView.setTextColor(getResources().getColor(android.R.color.darker_gray));
        // TypedValue.applyDimension(TypedValue.TYPE_DIMENSION, value, get);
        value_TextView.setTextSize(17);
    }

    // 右边的图片资源
    @Override
    public void addWidgetResource(int resId) {
        child_ImageView.setImageResource(resId);
    }

    // ---------------------------------------------------------------------------view
}
