package spannable.base.linksu.com.basespannablelibrary;

import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

import java.lang.reflect.Method;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：11/27 0027
 * 描    述：自定义下划线的颜色
 * 修订历史：
 * ================================================
 */
public class ColoredUnderlineSpan extends CharacterStyle implements UpdateAppearance {
    private final int mColor;

    public ColoredUnderlineSpan(final int color) {
        mColor = color;
    }

    @Override
    public void updateDrawState(final TextPaint tp) {
        try {
            final Method method = TextPaint.class.getMethod("setUnderlineText",
                    Integer.TYPE,
                    Float.TYPE);
            method.invoke(tp, mColor, 8.0f);
        } catch (final Exception e) {
            tp.setUnderlineText(true);
        }
    }

}
