package io.spaco.wallet.widget;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.spaco.wallet.R;

/**
 * 数字键盘的适配器
 * Created by kimi on 2018/1/23.
 */

public class NumberKeyboardAdapter extends RecyclerView.Adapter<NumberKeyboardAdapter.NumberKeyboardViewHolder> {

    private int NUMBER_SIZI = 12;
    private OnNumberKeyboardDownListener onNumberKeyboardDownListener;

    @Override
    public NumberKeyboardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NumberKeyboardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.number_keyboard_item,parent,false));
    }

    @Override
    public void onBindViewHolder(NumberKeyboardViewHolder holder, int position) {
        holder.number.setEnabled(true);
        if(position == 9){
            holder.number.setEnabled(false);
            holder.number.setText("");
        }else if(position == 10){
            holder.number.setText("0");
        }else if(position == NUMBER_SIZI - 1){
            holder.number.setText("");
        }else{
            holder.number.setText((position + 1) + "");
        }
        holder.number.setTag(position);
        holder.number.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return NUMBER_SIZI;
    }

    private View.OnClickListener clickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            if(onNumberKeyboardDownListener != null && position != 9){
                if(position == NUMBER_SIZI -1){
                    onNumberKeyboardDownListener.onNumberKeyboardDelete();
                }else if(position == 10){
                    onNumberKeyboardDownListener.onNumberKeyboardDown(0);
                }else{
                    onNumberKeyboardDownListener.onNumberKeyboardDown(position+1);
                }
            }
        }
    };

    /**
     * 设置监听器
     * @param onNumberKeyboardDownListener
     */
    public void setOnNumberKeyboardDownListener(OnNumberKeyboardDownListener onNumberKeyboardDownListener){
        this.onNumberKeyboardDownListener = onNumberKeyboardDownListener;
    }


    /**
     * numberKeyboard viewholder
     */
    public static class NumberKeyboardViewHolder extends RecyclerView.ViewHolder{

        public TextView number;

        public NumberKeyboardViewHolder(View itemView) {
            super(itemView);
            number = (TextView) itemView;
        }
    }


    /**
     * 数字键盘监听器
     */
    public interface OnNumberKeyboardDownListener{
        /**
         * @param number 数字键盘选中的数字
         */
        void onNumberKeyboardDown(int number);

        /**
         * 数字键盘删除按键
         */
        void onNumberKeyboardDelete();
    }

}
