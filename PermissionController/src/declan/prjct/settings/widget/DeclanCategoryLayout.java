/*
 * Copyright (C) 2024 AfterLife Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package declan.prjct.settings.widget;

import android.content.*;
import android.content.Context;
import android.database.*;
import android.graphics.Typeface;
import android.graphics.drawable.*;
import android.os.*;
import android.provider.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.declan.prjct.utils.DeclanUtils;

public class DeclanCategoryLayout extends LinearLayout {
	
	private boolean backgroundEnabled, iconEnabled, allCapsEnabled, boldEnabled;
	private int backgroundStyle, iconStyle, cornerTopLeft, cornerTopRight, cornerBotRight, cornerBotLeft, paddingTop, paddingBot, paddingLeft, paddingRight;
	
	private ImageView iconView;
	private View iconView1;
	private TextView titleView;
	private Context mContext;

	public DeclanCategoryLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext= context;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		titleView = findViewById(android.R.id.title);
		iconView = findViewById(android.R.id.icon);
		iconView1 = findViewById(android.R.id.icon1);
		new SettingsObserver(new Handler(Looper.getMainLooper())).observe();
		updateSettings();
	}
	
	private void updateSettings() {
		ContentResolver cr = mContext.getContentResolver();
		backgroundEnabled = Settings.System.getInt(cr, "declan_category_background_enabled", 0) == 1;
		backgroundStyle = Settings.System.getInt(cr, "declan_category_background_style", 0);
		iconEnabled = Settings.System.getInt(cr, "declan_category_icon_enabled", 0) == 1;
		iconStyle = Settings.System.getInt(cr, "declan_category_icon_style", 0);
		allCapsEnabled = Settings.System.getInt(cr, "declan_category_allcaps_enabled", 0) == 1;
		boldEnabled = Settings.System.getInt(cr, "declan_category_bold_enabled", 0) == 1;
		cornerTopLeft = DeclanUtils.getValueInDp(Settings.System.getInt(cr, "declan_category_corner_topL", 8));
		cornerTopRight = DeclanUtils.getValueInDp(Settings.System.getInt(cr, "declan_category_corner_topR", 8));
		cornerBotLeft = DeclanUtils.getValueInDp(Settings.System.getInt(cr, "declan_category_corner_botL", 8));
		cornerBotRight = DeclanUtils.getValueInDp(Settings.System.getInt(cr, "declan_category_corner_botR", 8));
		paddingLeft = DeclanUtils.getValueInDp(Settings.System.getInt(cr, "declan_category_padding_left", 4));
		paddingRight = DeclanUtils.getValueInDp(Settings.System.getInt(cr, "declan_category_padding_right", 4));
		paddingTop = DeclanUtils.getValueInDp(Settings.System.getInt(cr, "declan_category_padding_top", 4));
		paddingBot = DeclanUtils.getValueInDp(Settings.System.getInt(cr, "declan_category_padding_bot", 4));
		updateBackgroundTitle();
		updateIconView();
		updateTitleView();
	}
	
	private void updateBackgroundTitle() {
		int bgColor, textColor;
		GradientDrawable backgroundDrawable = new GradientDrawable();
		backgroundDrawable.setCornerRadii(new float[]{cornerTopLeft, cornerTopLeft, cornerTopRight, cornerTopRight, cornerBotRight, cornerBotRight, cornerBotLeft, cornerBotLeft});
		if (backgroundStyle == 1) {
			bgColor = DeclanUtils.getColorAttr(mContext, android.R.color.transparent);
			textColor = DeclanUtils.getColorAttr(mContext, android.R.color.transparent);
		} else if (backgroundStyle == 2) {
			bgColor = DeclanUtils.getColorAttr(mContext, android.R.color.transparent);
			textColor = DeclanUtils.getColorAttr(mContext, android.R.color.transparent);
		} else if (backgroundStyle == 3) {
			bgColor = DeclanUtils.getColorAttr(mContext, android.R.color.transparent);
			textColor = DeclanUtils.getColorAttr(mContext, android.R.color.transparent);
		} else {
			bgColor = DeclanUtils.getColorAttr(mContext, android.R.color.transparent);
			textColor = DeclanUtils.getColorAttr(mContext, android.R.color.transparent);
		}
		if (backgroundEnabled) {
			backgroundDrawable.setColor(bgColor);
			titleView.setBackground(backgroundDrawable);
			titleView.setTextColor(textColor);
			titleView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBot);
		} else {
			titleView.setBackgroundResource(android.R.color.transparent);
			titleView.setPadding(0, 0, 0, 0);
		}
	}
	
	private void updateIconView() {
		if (iconEnabled) {
			if (iconStyle == 1) {
				iconView.setVisibility(View.GONE);
				iconView1.setVisibility(View.VISIBLE);
			} else {
				iconView.setVisibility(View.VISIBLE);
				iconView1.setVisibility(View.GONE);
			}
			LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			titleParams.setMargins(10, 0, 0, 0);
			titleView.setLayoutParams(titleParams);
		} else {
			iconView.setVisibility(View.GONE);
			iconView1.setVisibility(View.GONE);
		}
	}
	
	private void updateTitleView() {
		if (allCapsEnabled) {
			titleView.setAllCaps(true);
		} else {
			titleView.setAllCaps(false);
		}
		if (boldEnabled) {
			titleView.setTypeface(Typeface.DEFAULT_BOLD);
		} else {
			titleView.setTypeface(Typeface.DEFAULT);
		}
	}
	
	class SettingsObserver extends ContentObserver {
		public SettingsObserver(Handler handler) {
			super(handler);
		}
		
		public void observe() {
			ContentResolver cr = mContext.getContentResolver();
			cr.registerContentObserver(Settings.System.getUriFor("declan_category_background_enabled"), false, this);
			cr.registerContentObserver(Settings.System.getUriFor("declan_category_background_style"), false, this);
			cr.registerContentObserver(Settings.System.getUriFor("declan_category_icon_enabled"), false, this);
			cr.registerContentObserver(Settings.System.getUriFor("declan_category_icon_style"), false, this);
			cr.registerContentObserver(Settings.System.getUriFor("declan_category_allcaps_enabled"), false, this);
			cr.registerContentObserver(Settings.System.getUriFor("declan_category_bold_enabled"), false, this);
			cr.registerContentObserver(Settings.System.getUriFor("declan_category_corner_topL"), false, this);
			cr.registerContentObserver(Settings.System.getUriFor("declan_category_corner_topR"), false, this);
			cr.registerContentObserver(Settings.System.getUriFor("declan_category_corner_botL"), false, this);
			cr.registerContentObserver(Settings.System.getUriFor("declan_category_corner_botR"), false, this);
			cr.registerContentObserver(Settings.System.getUriFor("declan_category_padding_left"), false, this);
			cr.registerContentObserver(Settings.System.getUriFor("declan_category_padding_right"), false, this);
			cr.registerContentObserver(Settings.System.getUriFor("declan_category_padding_top"), false, this);
			cr.registerContentObserver(Settings.System.getUriFor("declan_category_padding_bot"), false, this);
		}
		
		@Override
		public void onChange(boolean selfChange) {
			updateSettings();
		}
    }
    
    protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		updateBackgroundTitle();
		updateIconView();
		updateTitleView();
    }
	
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
    }
    
}
