package com.aorise.study.common;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;

import aorise.com.food_safety_app.R;
import aorise.com.food_safety_app.query.BaseIntentExtra;
import aorise.com.food_safety_app.query.QueryListActivity;
import razerdp.basepopup.BasePopupWindow;

/**
 * Created by Tuliyuan.
 * Date: 2019/2/21.
 */
public class MenuPopup extends BasePopupWindow implements View.OnClickListener {
    private Context mContext;
    private int id ;
    public MenuPopup(Context context, int id) {
        super(context);
        this.mContext = context;
        this.id = id;
        findViewById(R.id.menu_1).setOnClickListener(this);
        findViewById(R.id.menu_2).setOnClickListener(this);
        setAlignBackground(false);
        setPopupGravity(Gravity.BOTTOM | Gravity.LEFT);
    }

    @Override
    protected Animation onCreateShowAnimation() {
        AnimationSet set = new AnimationSet(true);
        set.setInterpolator(new DecelerateInterpolator());
        set.addAnimation(getScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0));
        set.addAnimation(getDefaultAlphaAnimation());
        return set;
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        AnimationSet set = new AnimationSet(true);
        set.setInterpolator(new DecelerateInterpolator());
        set.addAnimation(getScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0));
        set.addAnimation(getDefaultAlphaAnimation(false));
        return set;
    }

    @Override
    public void showPopupWindow(View v) {
        setOffsetX(v.getWidth() / 2);
        super.showPopupWindow(v);
    }


    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.activity_firm_detail_menu);
    }

    @Override
    public void onClick(View v) {
        Intent mIntent = new Intent();
        switch (v.getId()) {
            case R.id.menu_1:
                setCheckResultIntent(mIntent);
                break;
            case R.id.menu_2:
                setJobHoldIntent(mIntent);
                break;
            default:
                break;
        }
        ComponentName componentName = new ComponentName(mContext, QueryListActivity.class);
        mIntent.setComponent(componentName);
        mIntent.putExtra(BaseIntentExtra.EXTRA_FIRM_ID,id);
        mContext.startActivity(mIntent);
        this.dismiss();
    }
    //atitle abackimg amenuimg
    private void setCheckResultIntent(Intent intent){
        String intentinfo = BaseIntentExtra.EXTRA_DailyCheck +"///" + id;
        intent.putExtra(BaseIntentExtra.EXTRA_INTENT_INFO,intentinfo);
        intent.putExtra(BaseIntentExtra.EXTRA_ABACK_IMG,R.drawable.ic_firm_back);
        intent.putExtra(BaseIntentExtra.EXTRA_ATITLE,R.string.check_title);
        intent.putExtra(BaseIntentExtra.EXTRA_AMENU_IMG,0);
    }
    private void setJobHoldIntent(Intent intent){
        String intentinfo = BaseIntentExtra.EXTRA_JobHolder_menu +"///" + id;
        intent.putExtra(BaseIntentExtra.EXTRA_INTENT_INFO,intentinfo);
        intent.putExtra(BaseIntentExtra.EXTRA_ABACK_IMG,R.drawable.ic_firm_back);
        intent.putExtra(BaseIntentExtra.EXTRA_ATITLE,R.string.jobhold_title);
        intent.putExtra(BaseIntentExtra.EXTRA_AMENU_IMG,0);
    }
}
