/*
 * Copyright (C) 2001 - 2015 Marko Salmela, http://robopupu.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.robopupu.api.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.XmlResourceParser;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.util.Xml;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.robopupu.api.dependency.D;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UIToolkit {

    private static Application application = null;
    private static DisplayMetrics displayMetrics = null;
    private static Class<? extends Activity> settingsActivityClass = null;

    private static final String PREFIX_EDIT = "edit_";
    private static final int PROPERTY_NAME_BEGIN_INDEX = PREFIX_EDIT.length();

    private static int density = 0;

    // px = dp * (dpi / 160) 480 x 800 pixels and 218 dpi => 1.3625


    public static Display getDisplay() {
        final WindowManager manager = D.get(WindowManager.class);
        return manager.getDefaultDisplay();
    }

    public static DisplayMetrics getDisplayMetrics() {
        if (displayMetrics == null) {
            final Resources resources = application.getResources();
            displayMetrics = resources.getDisplayMetrics();
        }
        return displayMetrics;
    }

    public static void setApplication(final Application application) {
        UIToolkit.application = application;
    }

    private static View setContentView(final Activity activity, final int layoutResId) {
        activity.setContentView(layoutResId);
        return activity.findViewById(layoutResId);
    }

    public static Point getDisplaySize() {
        final WindowManager manager = (WindowManager) application
                .getSystemService(Context.WINDOW_SERVICE);
        final Display display = manager.getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        return size;
    }

    public static int getDensity() {
        if (density == 0) {
            return displayMetrics.densityDpi;
        }
        return density;
    }

    public static float dp2px(final int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
    }

    public static float sp2px(final int sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, displayMetrics);
    }

    public static int getNumberOfBottomActions() {
        return getNumberOfTopActions() + 3;
    }

    public static int getNumberOfTopActions() {
        final int density = getDensity();

        if (density >= 600) {
            return 5;
        } else if (density >= 500) {
            return 4;
        } else if (density >= 360) {
            return 3;
        } else {
            return 2;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> void collectTaggedViewsOfType(final Class<T> type, final ViewGroup viewGroup, final List<T> taggedViews) {
        final ArrayList<T> collectedViews = new ArrayList<>();
        collectViewsOfType(type, viewGroup, collectedViews);
        final int count = collectedViews.size();

        for (int i = count - 1; i >= 0; i--) {
            final View view = collectedViews.get(i);

            if (view.getTag() != null) {
                taggedViews.add((T)view);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> void collectViewsOfType(final Class<T> type, final ViewGroup viewGroup, final List<T> collectedViews) {
        final int count = viewGroup.getChildCount();

        for (int i = 0; i < count; i++) {
            final View child = viewGroup.getChildAt(i);

            if (type.isAssignableFrom(child.getClass())) {
                collectedViews.add((T) child);

                if (child instanceof ViewGroup) {
                    collectViewsOfType(type, (ViewGroup) child, collectedViews);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> T getChildOfType(final Class<T> type, final ViewGroup viewGroup) {
        final int count = viewGroup.getChildCount();

        for (int i = 0; i < count; i++) {
            final View child = viewGroup.getChildAt(i);

            if (type.isAssignableFrom(child.getClass())) {
                return ((T) child);
            }
        }

        for (int i = 0; i < count; i++) {
            final View child = viewGroup.getChildAt(i);

            if (child instanceof ViewGroup) {
                final View foundChild = getChildOfType(type, (ViewGroup) child);

                if (foundChild != null) {
                    return (T)foundChild;
                }
            }
        }
        return null;
    }

    public static void hideSoftInput(final View view) {
        final InputMethodManager manager = D.get(InputMethodManager.class);
        manager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    public static void showSoftInput(final View view) {
        final InputMethodManager manager = D.get(InputMethodManager.class);
        manager.showSoftInput(view, 0);
    }

    public static void showToast(final Context context, final String text, final boolean isLong) {
        // context.runOnUiThread(new Runnable() {
        // public void run() {
        final Toast toast = Toast.makeText(context, text, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        toast.show();
        // }
        // });
    }

    public static void showToast(final Context context, final String text, final boolean isLong,
                                 final int gravity, final int xOffset, final int yOffset) {
        final Toast toast = Toast.makeText(context, text, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        toast.setGravity(gravity, xOffset, yOffset);
        toast.show();
    }

    public Activity getActivity(final View view) {
        final Context context = view.getContext();
        Activity activity = null;

        if (context instanceof Activity) {
            activity = (Activity) context;
        }
        return activity;
    }

    public static String getStringResource(final int resId) {
        return application.getString(resId);
    }

    public static String getPropertyName(final View view) {
        final Resources resources = view.getResources();
        final String resourceName = resources.getResourceEntryName(view.getId());

        if (resourceName != null && resourceName.startsWith(PREFIX_EDIT)) {
            return resourceName.substring(PROPERTY_NAME_BEGIN_INDEX);
        }
        return null;
    }

    public static void doGoSettings(final Context context) {
        final Intent intent = new Intent(context, settingsActivityClass);
        context.startActivity(intent);
    }

    public static void startActivity(final Class<? extends Activity> activityClass) {
        final Intent intent = new Intent(application, activityClass);
        application.startActivity(intent);
    }

    public final boolean getBoolean(final int resId) {
        return application.getResources().getBoolean(resId);
    }

    public final int getColor(final int resId) {
        return application.getResources().getColor(resId);
    }

    public final int getInteger(final int resId) {
        return application.getResources().getInteger(resId);
    }


    public Animation getAnimation(final int resId) {
        XmlResourceParser pParser = null;

        try {
            pParser = application.getResources().getAnimation(resId);
            return createAnimationFromXml(pParser);
        } catch (final XmlPullParserException e) {
            final NotFoundException rnf = new NotFoundException(
                    "Can't load animation resource ID #0x" + Integer.toHexString(resId));
            rnf.initCause(e);
            throw rnf;
        } catch (final IOException e) {
            final NotFoundException nfe = new NotFoundException(
                    "Can't load animation resource ID #0x" + Integer.toHexString(resId));
            nfe.initCause(e);
            throw nfe;
        } finally {
            if (pParser != null) {
                pParser.close();
            }
        }
    }

    private Animation createAnimationFromXml(final XmlPullParser parser)
            throws XmlPullParserException, IOException {

        return createAnimationFromXml(parser, null, Xml.asAttributeSet(parser));
    }

    private Animation createAnimationFromXml(final XmlPullParser parser, final AnimationSet parent, final AttributeSet attrs) throws XmlPullParserException,
            IOException {

        Animation anim = null;

        // Make sure we are on a start tag.
        int type;
        int depth = parser.getDepth();

        while (((type = parser.next()) != XmlPullParser.END_TAG || parser.getDepth() > depth)
                && type != XmlPullParser.END_DOCUMENT) {

            if (type != XmlPullParser.START_TAG) {
                continue;
            }

            final String name = parser.getName();
            final Context context = application.getApplicationContext();

            if (name.equals("set")) {
                anim = new AnimationSet(context, attrs);
                createAnimationFromXml(parser, (AnimationSet) anim, attrs);
            } else if (name.equals("alpha")) {
                anim = new AlphaAnimation(context, attrs);
            } else if (name.equals("scale")) {
                anim = new ScaleAnimation(context, attrs);
            } else if (name.equals("rotate")) {
                anim = new RotateAnimation(context, attrs);
            } else if (name.equals("translate")) {
                anim = new TranslateAnimation(context, attrs);
            } else {
                throw new RuntimeException("Unknown animation name: " + parser.getName());
            }

            if (parent != null) {
                parent.addAnimation(anim);
            }
        }

        return anim;

    }

    public final Drawable getDrawable(final int resId) {
        return application.getResources().getDrawable(resId);
    }

    public final String getString(final int resId) {
        return application.getString(resId);
    }

    public String getString(final int resId, final Object... formatArgs) {
        return application.getString(resId, formatArgs);
    }

    public String[] getStringArray(final int resId) {
        return application.getResources().getStringArray(resId);
    }

    public static int getResourceId(final String variableName, final String resourceName, final String packageName) {
        try {
            return getResources().getIdentifier(variableName, resourceName, packageName);
        } catch (final Exception pException) {
            pException.printStackTrace(); // LOG
            return -1;
        }
    }

    public static Resources getResources() {
        return application.getResources();
    }

    public static boolean isPortrait() {
        final int rotation = getDisplay().getRotation();
        return (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180);
    }

    public static int getRotation() {
        return getDisplay().getRotation();
    }
}
