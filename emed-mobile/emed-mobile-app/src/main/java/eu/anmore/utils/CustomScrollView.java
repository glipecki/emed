package eu.anmore.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class CustomScrollView extends ScrollView {

	public CustomScrollView(final Context context) {
		super(context);
	}

	public CustomScrollView(final Context context, final AttributeSet attrs, final int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustomScrollView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(final MotionEvent motionEvent) {
		if (isTouchInScrollZone(motionEvent, leftMargin)) {
			return false;
		} else {
			return super.onInterceptTouchEvent(motionEvent);
		}
	}

	private boolean isTouchInScrollZone(final MotionEvent motionEvent, final int leftMargin) {
		return motionEvent.getX() >= leftMargin;
	}

	private final int leftMargin = 150;

}
