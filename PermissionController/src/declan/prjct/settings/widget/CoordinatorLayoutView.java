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
import android.database.ContentObserver;
import android.graphics.drawable.GradientDrawable;
import android.os.*;
import android.provider.Settings;
import android.util.AttributeSet;
import android.os.Handler;
import android.os.UserHandle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.*;

import com.android.settingslib.widget.R;

import com.declan.prjct.utils.DeclanUtils;

public class CoordinatorLayoutView extends CoordinatorLayout {

	private Context mContext;
	private AppBarLayout mAppBarLayout;
	private CollapsingToolbarLayout mCollapsingLayout;

	private boolean mDualToneMode;
	private int mCollapsedTitleMode, mTitleGravity;

	public CoordinatorLayoutView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mAppBarLayout = findViewById(R.id.app_bar);
		mCollapsingLayout = findViewById(R.id.collapsing_toolbar);
		new SettingsObserver(new Handler()).observe();
		updateSettings();
	}

	private void updateSettings() {
		ContentResolver cr = mContext.getContentResolver();
		mDualToneMode = Settings.System.getInt(cr, "declan_base_dual_tone_rounded", 0) == 1;
		mCollapsedTitleMode = Settings.System.getInt(cr, "declan_base_title_collapsed_mode", 0);
		mTitleGravity = Settings.System.getInt(cr, "declan_base_expanded_title_gravity", 0);
		updateDualTone();
		updateTitleView();
	}

	private void updateDualTone() {
		int radius = DeclanUtils.dpToPx(28);
		GradientDrawable appBarDrawable = new GradientDrawable();
		GradientDrawable scrimDrawable = new GradientDrawable();
		scrimDrawable.setColor(android.R.color.transparent);
		scrimDrawable.setCornerRadii(new float[]{0, 0, 0, 0, radius, radius, radius, radius});
		if (mDualToneMode) {
			appBarDrawable.setColor(android.R.color.transparent);
			appBarDrawable.setCornerRadii(new float[]{0, 0, 0, 0, radius, radius, radius, radius});
		} else {
			appBarDrawable.setColor(android.R.color.transparent);
			appBarDrawable.setCornerRadius(0);
		}
		mAppBarLayout.setBackground(appBarDrawable);
		mCollapsingLayout.setContentScrim(scrimDrawable);
	}

	private void updateTitleView() {
		if (mCollapsedTitleMode == 1) {
			if (mCollapsingLayout != null) {
				mCollapsingLayout.setTitleCollapseMode(CollapsingToolbarLayout.TITLE_COLLAPSE_MODE_SCALE);
			}
		} else if (mCollapsingLayout != null) {
			mCollapsingLayout.setTitleCollapseMode(CollapsingToolbarLayout.TITLE_COLLAPSE_MODE_FADE);
		}
		if (mTitleGravity == 1) {
			if (mCollapsingLayout != null) {
				mCollapsingLayout.setExpandedTitleGravity(81);
			}
		} else if (mCollapsingLayout != null) {
			mCollapsingLayout.setExpandedTitleGravity(83);
		}
	}

	public final class SettingsObserver extends ContentObserver {
		public SettingsObserver(Handler handler) {
			super(handler);
		}

		public final void observe() {
			ContentResolver contentResolver = mContext.getContentResolver();
			contentResolver.registerContentObserver(Settings.System.getUriFor("declan_base_dual_tone_rounded"), false, this);
			contentResolver.registerContentObserver(Settings.System.getUriFor("declan_base_title_collapsed_mode"), false, this);
			contentResolver.registerContentObserver(Settings.System.getUriFor("declan_base_expanded_title_gravity"), false, this);
		}

		@Override
		public void onChange(boolean selfChange) {
			updateSettings();
		}
    }

}
