package io.spaco.wallet.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.spaco.wallet.R;
import io.spaco.wallet.common.OnItemClickListener;

/**
 * Created by kimi on 2018/3/21.</br>
 */

public final class OptionsDialog extends Dialog implements AdapterView.OnItemClickListener {

    private Builder builder;
    private ListView listView;
    private TextView title;
    private View line;

    public OptionsDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.options_dialog_layout);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = Gravity.BOTTOM;
        attributes.windowAnimations = R.style.options_dialog_style;
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(attributes);
        title = findViewById(R.id.title);
        listView = findViewById(R.id.listview);
        line = findViewById(R.id.line);
        if (builder != null) {
            if (!TextUtils.isEmpty(builder.title))
                title.setText(builder.title);

            title.setTextColor(builder.titleColor);
            listView.setDivider(new ColorDrawable(builder.dividerColor));
            listView.setDividerHeight(1);
            line.setBackground(new ColorDrawable(builder.dividerColor));
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.options_dialog_items,android.R.id.text1 ,builder.items);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }
    }

    public void setBuilder(Builder builder) {
        this.builder = builder;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(builder != null && builder.onItemClickListener != null)
            builder.onItemClickListener.onClick(view.getId(),position,builder.items.get(position));
    }


    public static final class Builder {
        public Context mContext;
        public String title;
        public int titleColor = Color.GRAY, itemColor = Color.GRAY, dividerColor = Color.GRAY;
        public List<String> items = new ArrayList<>();
        public OnItemClickListener onItemClickListener;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setTitleColor(int titleColor) {
            this.titleColor = titleColor;
            return this;
        }

        public Builder addItem(String item) {
            this.items.add(item);
            return this;
        }

        public Builder addItems(List<String> list) {
            items.addAll(list);
            return this;
        }

        public Builder setItemColor(int itemColor) {
            this.itemColor = itemColor;
            return this;
        }

        public Builder setDividerColor(int dividerColor) {
            this.dividerColor = dividerColor;
            return this;
        }

        public <T> Builder setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
            return this;
        }

        public OptionsDialog build() {
            OptionsDialog optionsDialog = new OptionsDialog(mContext);
            optionsDialog.setBuilder(this);
            return optionsDialog;
        }
    }
}
