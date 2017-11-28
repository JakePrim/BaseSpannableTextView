package spannable.base.linksu.com.basespannablelibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：11/27 0027
 * 描    述：BaseSpannableTextView 实现各种文本特效:
 * 改变部分字体的颜色、大小、字体、部分关键字有下划线、以及下划线的颜色设置
 * 修订历史：
 * ================================================
 */
public class BaseSpannableTextView extends AppCompatTextView {

    //总的文本
    private String text;

    //自定义属性
    final TypedArray typedArray;

    private SpannableString spannableString;

    //需要做出变化的关键字
    private String keys;

    //多个关键字列表
    private String[] keyList;
    //多个关键字的颜色
    private
    @ColorInt
    int[] keyColorList;

    //多个关键字下划线的颜色
    private
    @ColorInt
    int[] keyUnderLineColorList;

    //设置多个关键字是否显示下划线
    private boolean[] keyLines;

    //多个关键字的字体大小
    private int[] keySizeList;

    private float baseKeysSize;

    private boolean isSerachAll = true;

    private
    @ColorInt
    int baseKeysColor;

    private
    @ColorInt
    int baseKeyUnderLineColor;

    private boolean baseKeyUnderLine;

    private static final String TAG = "BaseSpannableTextView";

    private Context context;

    public BaseSpannableTextView(Context context) {
        this(context, null);
    }

    public BaseSpannableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("UseSparseArrays")
    public BaseSpannableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseSpannableTextView, defStyleAttr, 0);
        keys = typedArray.getString(R.styleable.BaseSpannableTextView_baseKeys);
        baseKeysSize = typedArray.getDimension(R.styleable.BaseSpannableTextView_baseKeysSize, 30);
        baseKeysColor = typedArray.getColor(R.styleable.BaseSpannableTextView_baseKeysColor, Color.BLACK);
        baseKeyUnderLine = typedArray.getBoolean(R.styleable.BaseSpannableTextView_baseKeyUnderLine, false);
        baseKeyUnderLineColor = typedArray.getColor(R.styleable.BaseSpannableTextView_baseKeyUnderLineColor, Color.BLACK);
        typedArray.recycle();
        this.text = getText().toString().trim();
        refreshView();
    }

    /**
     * 更新view
     */
    public void refreshView() {
        if (!TextUtils.isEmpty(text)) {
            Log.e(TAG, "refreshView: " + text);
            setText(searchKeys(baseKeysColor, text, keys, (int) baseKeysSize, baseKeyUnderLineColor, baseKeyUnderLine));
        } else {
            Log.e(TAG, "This is not xml set text,please setText(String text)");
        }
    }


    /**
     * 搜索关键词
     *
     * @param color
     * @param text
     * @param keys
     *
     * @return
     */
    public SpannableString searchKeys(@ColorInt int color, String text, String keys, int keySize, @ColorInt int lineColor, boolean isLine) {
        spannableString = new SpannableString(text);
        if (keyList != null && keyList.length > 0) {
            for (int i = 0; i < keyList.length; i++) {
                String key = keyList[i];
                if (keyColorList != null && keyColorList.length > 0) {
                    color = keyColorList[i];
                }
                if (keySizeList != null && keySizeList.length > 0) {
                    keySize = keySizeList[i];
                }
                if (keyUnderLineColorList != null && keyUnderLineColorList.length > 0) {
                    lineColor = keyUnderLineColorList[i];
                }
                if (keyLines != null && keyLines.length > 0) {
                    isLine = keyLines[i];
                }
                if (isSerachAll) {
                    matcherSearchTitle(color, spannableString, text, key, keySize, lineColor, isLine);
                } else {
                    matcherSingleSearchText(color, spannableString, text, key, keySize, lineColor, isLine);
                }
            }
        } else {
            if (isSerachAll) {
                matcherSearchTitle(color, spannableString, text, keys, keySize, lineColor, isLine);
            } else {
                matcherSingleSearchText(color, spannableString, text, keys, keySize, lineColor, isLine);
            }
        }
        return spannableString;
    }

    /**
     * 设置关键词列表
     *
     * @param keyList
     */
    public void setKeyList(String[] keyList) {
        this.keyList = keyList;
    }

    /**
     * 设置关键词大小列表
     *
     * @param keyList
     */
    public void setKeySizeList(int[] keyList) {
        this.keySizeList = keyList;
    }

    /**
     * 设置关键词颜色列表
     *
     * @param keyList
     */
    public void setKeyColorList(int[] keyList) {
        this.keyColorList = keyList;
    }

    /**
     * 查找字符串中的全部关键字
     *
     * @param color
     * @param text
     * @param keyword
     *
     * @return
     */
    public void matcherSearchTitle(int color, SpannableString ss, String text, String keyword, int baseKeysSize, int baseKeyUnderLineColor, boolean baseKeyUnderLine) {
        if (!TextUtils.isEmpty(keyword)) {
            Log.e(TAG, "matcherSearchTitle: " + keyword + " baseKeysSize:" + baseKeysSize);
            String string = text.toLowerCase();
            String key = keyword.toLowerCase();

            Pattern pattern = Pattern.compile(key);
            Matcher matcher = pattern.matcher(string);

            while (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();
                ss.setSpan(new ForegroundColorSpan(color), start, end,
                        SPAN_EXCLUSIVE_EXCLUSIVE);
                ss.setSpan(new AbsoluteSizeSpan(baseKeysSize), start, end,
                        SPAN_EXCLUSIVE_EXCLUSIVE);
                if (baseKeyUnderLine) {
                    ss.setSpan(new ColoredUnderlineSpan(baseKeyUnderLineColor), start, end,
                            SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                ss.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 5, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    /***
     * 查找字符串中的单个关键字
     * @param color
     * @param ss
     * @param text
     * @param keyword
     * @param baseKeysSize
     * @param baseKeyUnderLineColor
     */
    public void matcherSingleSearchText(int color, SpannableString ss, String text, String keyword, int baseKeysSize, int baseKeyUnderLineColor, boolean baseKeyUnderLine) {
        int indexOf = text.indexOf(keyword);
        if (indexOf != -1) {
            ss.setSpan(new ForegroundColorSpan(color), indexOf, indexOf + keyword.length(),
                    SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new AbsoluteSizeSpan(baseKeysSize), indexOf, indexOf + keyword.length(),
                    SPAN_EXCLUSIVE_EXCLUSIVE);
            if (baseKeyUnderLine) {
                ss.setSpan(new ColoredUnderlineSpan(baseKeyUnderLineColor), indexOf, indexOf + keyword.length(),
                        SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    /**
     * 设置单个关键字
     *
     * @param keys
     */
    public void setKeys(String keys) {
        this.keys = keys;
    }

    /**
     * 设置当个关键字的颜色
     *
     * @param color
     */
    public void setKeyColor(@ColorInt int color) {
        this.baseKeysColor = color;
    }

    /**
     * 设置单个关键字的字体大小
     *
     * @param size
     */
    public void setKeySize(int size) {
        this.baseKeysSize = size;
    }

    /**
     * 是否查找全部关键字
     *
     * @param isSerachAll
     *         默认为true
     */
    public void setisSerachAll(boolean isSerachAll) {
        this.isSerachAll = isSerachAll;
    }

    /**
     * 设置时候显示下划线
     *
     * @param baseKeyUnderLine
     */
    public void setUnderLine(boolean baseKeyUnderLine) {
        this.baseKeyUnderLine = baseKeyUnderLine;
    }

    /**
     * 设置下划线的颜色
     *
     * @param baseKeyUnderLineColor
     */
    public void setUnderLineColor(@ColorInt int baseKeyUnderLineColor) {
        this.baseKeyUnderLineColor = baseKeyUnderLineColor;
    }

    /**
     * 设置多个关键字是否显示下划线
     *
     * @param keyLines
     */
    public void setKeyLines(boolean[] keyLines) {
        this.keyLines = keyLines;
    }

    /**
     * 设置多个关键字下划线的颜色
     *
     * @param keyUnderLineColorList
     */
    public void setKeyLineColors(int[] keyUnderLineColorList) {
        this.keyUnderLineColorList = keyUnderLineColorList;
    }

    /**
     * 设置总的字符串
     *
     * @param text
     */
    public void setBaseText(String text) {
        this.text = text;
    }
}
