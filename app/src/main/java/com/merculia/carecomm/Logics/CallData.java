package com.merculia.carecomm.Logics;

import com.merculia.carecomm.Logics.Chat.PartnerModel;

public class CallData {
    public static String opentokApiKey = null;
    public static String opentokSessionId = null;
    public static String opentokToken = null;

    public static String callingType = "video";
    public static String callingStatus = "none";
    // none, requesting, responding, calling, cancelling, refusing
    public static String currentScreen = ""; // Video Call Screen, Audio Call Screen, Main Screen
    public static boolean isMute = true;
    public static boolean isSpeaker = true;

    public static PartnerModel partnerData = new PartnerModel();
    public static PartnerModel myUserData = new PartnerModel();
}
