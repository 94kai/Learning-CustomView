package com.xk.customview.custom.behavior;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.xk.customview.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by xuekai on 2019/4/1.
 */
public class MCoordinatorLayout extends FrameLayout implements NestedScrollingParent {
    public MCoordinatorLayout(@NonNull Context context) {
        super(context);
    }

    public MCoordinatorLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MCoordinatorLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getLayoutParams() instanceof CustomLayoutParams) {
                CustomBehavior behavior = ((CustomLayoutParams) childAt.getLayoutParams()).behavior;
                if (behavior != null) {
                    behavior.onNestedScroll(childAt,dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
                }
            }
        }
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new CustomLayoutParams(getContext(), attrs);
    }


    class CustomLayoutParams extends LayoutParams {
        CustomBehavior behavior;

        public CustomLayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MCoordinatorLayout);
            String behaviorName = typedArray.getString(R.styleable.MCoordinatorLayout_behavior);
            if (!TextUtils.isEmpty(behaviorName)) {
                try {
                    Class<?> behaviorClass = Class.forName(behaviorName);
                    Constructor<?> constructor = behaviorClass.getConstructor(Context.class, AttributeSet.class);
                    behavior = (CustomBehavior) constructor.newInstance(context, attrs);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

            typedArray.recycle();
        }
    }
}
