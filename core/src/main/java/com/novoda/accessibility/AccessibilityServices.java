package com.novoda.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.view.accessibility.AccessibilityManager;

import com.novoda.accessibility.captioning.CaptionManager;
import com.novoda.accessibility.captioning.CaptionManagerFactory;

import java.util.List;

public final class AccessibilityServices {

    private final AccessibilityManager accessibilityManager;
    private final CaptionManager captionManager;

    public static AccessibilityServices newInstance(Context context) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        CaptionManager captionManager = CaptionManagerFactory.newInstance().getCaptionManager(context);
        return new AccessibilityServices(accessibilityManager, captionManager);
    }

    private AccessibilityServices(AccessibilityManager accessibilityManager, CaptionManager captionManager) {
        this.accessibilityManager = accessibilityManager;
        this.captionManager = captionManager;
    }

    /**
     * Reports if any services offering spoken feedback are enabled.
     *
     * Be aware it will return true when TalkBack is enabled, even if it's suspended.
     */
    public boolean isSpokenFeedbackEnabled() {
        List<AccessibilityServiceInfo> enabledServices = getEnabledServicesFor(AccessibilityServiceInfo.FEEDBACK_SPOKEN);
        return !enabledServices.isEmpty();
    }

    private List<AccessibilityServiceInfo> getEnabledServicesFor(int feedbackTypeFlags) {
        return accessibilityManager.getEnabledAccessibilityServiceList(feedbackTypeFlags);
    }

    public boolean isClosedCaptioningEnabled() {
        return captionManager.isClosedCaptioningEnabled();
    }
}
