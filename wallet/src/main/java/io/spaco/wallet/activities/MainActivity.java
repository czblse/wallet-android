package io.spaco.wallet.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import io.spaco.wallet.R;
import io.spaco.wallet.activities.Main.MainTransactionFragment;
import io.spaco.wallet.activities.Main.MainWalletFragment;
import io.spaco.wallet.base.BaseActivity;
import io.spaco.wallet.widget.SendKeyDialog;

/**
 * 主界面，一级界面
 */
public class MainActivity extends BaseActivity {

    /**
     * 底部导航容器
     */
    LinearLayout bottomContainer;
    int[] normalDrawableIds = new int[]{R.drawable.main_wallet_normal,R.drawable.main_send_bg,R.drawable.main_transation_normal};
    int[] drawableIds = new int[]{R.drawable.main_wallet,R.drawable.main_send_bg,R.drawable.main_transation};
    /**
     * 底部导航选中位置
     */
    int bottomSelectedIndex = 0;

    MainWalletFragment mainWalletFragment;
    MainTransactionFragment mainTransactionFragment;


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        bottomContainer = findViewById(R.id.main_bottom_container);
        initBottomContainer();

        mainWalletFragment = MainWalletFragment.newInstance(null);
        mainTransactionFragment = MainTransactionFragment.newInstance(null);
        switchFragment(mainWalletFragment);
    }

    /**
     * 切换碎片视图
     * @param fragment
     */
    private void switchFragment(Fragment fragment){
        String tag = fragment.getClass().getSimpleName();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment instance = getSupportFragmentManager().findFragmentByTag(tag);
        if(instance == null){
            fragmentTransaction.add(R.id.main_container,fragment,tag);
        }
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if(fragments != null){
            for(Fragment item : fragments){
                if(item.equals(fragment)){
                    fragmentTransaction.show(item);
                }else{
                    fragmentTransaction.hide(item);
                }
            }
        }
        fragmentTransaction.commit();
    }

    /**
     * 初始化底部容器
     */
    private void initBottomContainer() {
        for(int i = 0 ; i < normalDrawableIds.length; i ++ ){
            FrameLayout parent = (FrameLayout) bottomContainer.getChildAt(i);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
            layoutParams.gravity = Gravity.CENTER;
            ImageView imageView = new ImageView(this);
            if(i == bottomSelectedIndex){
                imageView.setImageResource(drawableIds[i]);
            }else{
                imageView.setImageResource(normalDrawableIds[i]);
            }
            //旋转第二张图片
            if(i == 1){
                imageView.setRotation(180);
            }
            parent.addView(imageView,layoutParams);
            imageView.setTag(i);
            imageView.setOnClickListener(bottomClickListener);
        }
    }

    /**
     * 碎片视图切换回掉
     * @param index
     */
    public void onBottomContainerSelected(int index){
        if(index == 0){
            switchFragment(mainWalletFragment);
        }else if(index == 1){
            new SendKeyDialog(this).show();
        }else if(index == 2){
            switchFragment(mainTransactionFragment);
        }
    }

    View.OnClickListener bottomClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            bottomSelectedIndex= (int) v.getTag();
            if(bottomSelectedIndex != 1){
                updateSelectedBottomContainer();
            }
            onBottomContainerSelected(bottomSelectedIndex);
        }
    };

    /**
     * 刷新底部容器视图
     */
    public void updateSelectedBottomContainer(){
        for(int i = 0; i < bottomContainer.getChildCount(); i ++){
            ImageView imageView = (ImageView) ((ViewGroup) bottomContainer.getChildAt(i)).getChildAt(0);
            if(i == bottomSelectedIndex){
                imageView.setImageResource(drawableIds[i]);
            }else{
                imageView.setImageResource(normalDrawableIds[i]);
            }
        }
    }


    @Override
    protected void initData() {

    }


}
