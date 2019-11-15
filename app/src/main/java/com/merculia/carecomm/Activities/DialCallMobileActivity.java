package com.merculia.carecomm.Activities;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.merculia.carecomm.BaseActivity.BaseFragmentActivity;
import com.merculia.carecomm.Logics.ApiService;
import com.merculia.carecomm.Logics.CallData;
import com.merculia.carecomm.Logics.Chat.ReceiveConnectResponseModel;
import com.merculia.carecomm.Logics.Data;
import com.merculia.carecomm.R;

import androidx.annotation.NonNull;

import android.util.Log;

import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.OpentokError;
import com.opentok.android.Subscriber;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.opengl.GLSurfaceView;

public class DialCallMobileActivity extends BaseFragmentActivity implements View.OnClickListener, Session.SessionListener, PublisherKit.PublisherListener {

    private static String LOG_TAG = DialVideoCallMobileActivity.class.getSimpleName();
    private static final int RC_VIDEO_APP_PERM = 124;
    private static final int RC_SETTINGS_SCREEN_PERM = 123;

    private Session mSession;

    // Calling Container
    private FrameLayout callingContainer;
    private FrameLayout cSubscriber;
    private Publisher mPublisher;
    private FrameLayout cPublisher;
    private Subscriber mSubscriber;
    private CircleImageView ivPartnerPhoto;
    private TextView tvPartnerRelationship;
    private LinearLayout layoutMute;
    private ImageView ivMute;
    private TextView tvMute;
    private LinearLayout layoutSpeaker;
    private ImageView ivSpeaker;
    private TextView tvSpeaker;
    private LinearLayout layoutVolume;
    private ImageView ivVolume;
    private TextView tvVolume;
    private LinearLayout layoutMinimize;
    private ImageView ivMinimize;
    private TextView tvMinimize;
    private Button btnClose;

    // Requesting Container
    private FrameLayout requestingContainer;
    private CircleImageView ivRequestingResponderPhoto;
    private TextView tvRequestingResponderRelationship;
    private Button btnCancelRequesting;

    // Responding Container
    private FrameLayout respondingContainer;
    private CircleImageView ivRespondingRequesterPhoto;
    private TextView tvRespondingRequesterRelationship;
    private Button btnRefuseResponding;
    private Button btnAcceptResponding;

    // Cancelling Container
    private FrameLayout cancellingContainer;
    private CircleImageView ivCancellingResponderPhoto;
    private TextView tvCancellingResponderRelationship;

    // Refusing Container
    private FrameLayout refusingContainer;
    private CircleImageView ivRefusingRequesterPhoto;
    private TextView tvRefusingRequesterRelationship;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBottomNavigationBar();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setTheme();
        if (isTablet(context)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setContentView(R.layout.activity_dial_call_mobile);
        setView();
        init();
        setEvents();
        applyStatus();
    }

    @Override
    protected void setView() {
        // Calling Container
        callingContainer = findViewById(R.id.calling_container);
        cSubscriber = findViewById(R.id.camera_subscriber);
        cPublisher = findViewById(R.id.camera_publisher);
        ivPartnerPhoto = findViewById(R.id.iv_partner_photo);
        tvPartnerRelationship = findViewById(R.id.tv_partner_relationship);
        layoutMute = findViewById(R.id.layout_mute);
        ivMute = findViewById(R.id.iv_mute);
        tvMute = findViewById(R.id.tv_mute);
        layoutSpeaker = findViewById(R.id.layout_speaker);
        ivSpeaker = findViewById(R.id.iv_speaker);
        tvSpeaker = findViewById(R.id.tv_speaker);
        layoutVolume = findViewById(R.id.layout_volume);
        ivVolume = findViewById(R.id.iv_volume);
        tvVolume = findViewById(R.id.tv_volume);
        layoutMinimize = findViewById(R.id.layout_minimize);
        ivMinimize = findViewById(R.id.iv_minimize);
        tvMinimize = findViewById(R.id.tv_minimize);
        btnClose = findViewById(R.id.btn_close);

        // Requesting Container
        requestingContainer = findViewById(R.id.requesting_container);
        ivRequestingResponderPhoto = findViewById(R.id.iv_requesting_responder_photo);
        tvRequestingResponderRelationship = findViewById(R.id.tv_requesting_responder_relationship);
        btnCancelRequesting = findViewById(R.id.btn_cancel_requesting);

        // Responding Container
        respondingContainer = findViewById(R.id.responding_container);
        ivRespondingRequesterPhoto = findViewById(R.id.iv_responding_requester_photo);
        tvRespondingRequesterRelationship = findViewById(R.id.tv_responding_requester_relationship);
        btnRefuseResponding = findViewById(R.id.btn_refuse_responding);
        btnAcceptResponding = findViewById(R.id.btn_accept_responding);

        // Cancelling Container
        cancellingContainer = findViewById(R.id.cancelling_container);
        ivCancellingResponderPhoto = findViewById(R.id.iv_cancelling_responder_photo);
        tvCancellingResponderRelationship = findViewById(R.id.tv_cancelling_responder_relationship);

        // Refusing Container
        refusingContainer = findViewById(R.id.refusing_container);
        ivRefusingRequesterPhoto = findViewById(R.id.iv_refusing_requester_photo);
        tvRefusingRequesterRelationship = findViewById(R.id.tv_refusing_requester_relationship);
    }

    @Override
    protected void init() {
    }

    @Override
    protected void setEvents() {
        // Calling Container
        btnClose.setOnClickListener(this);
        layoutMinimize.setOnClickListener(this);
        layoutSpeaker.setOnClickListener(this);
        layoutMute.setOnClickListener(this);

        // cancelling container
        btnCancelRequesting.setOnClickListener(this);

        // responding container
        btnAcceptResponding.setOnClickListener(this);
        btnRefuseResponding.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // calling container
        if (view == btnClose){
            stopCall();
        }
        if (view == layoutMinimize){
            // openActivity(context,MainActivity.class);
        }
        if (view == layoutMute){
            CallData.isMute = !CallData.isMute;
            if (CallData.isMute){
                ivMute.setImageResource(R.mipmap.group_406);
            }else {
                ivMute.setImageResource(R.mipmap.group_403);
            }
            mSubscriber.setSubscribeToAudio(CallData.isMute);
        }
        if (view == layoutSpeaker){
            CallData.isSpeaker = !CallData.isSpeaker;
            if (CallData.isSpeaker){
                ivSpeaker.setImageResource(R.mipmap.group_420);
            }else {
                ivSpeaker.setImageResource(R.mipmap.speaker_white_border);
            }
            mPublisher.setPublishAudio(CallData.isSpeaker);
        }

        // requesting container
        if (view == btnCancelRequesting) {
            cancelRequesting();
        }

        // responding container
        if (view == btnAcceptResponding) {
            acceptCall();
        }

        if (view== btnRefuseResponding) {
            refuseCall();
        }
    }

    private void applyStatus() {
        String status = CallData.callingStatus;
        if (status.contentEquals("requesting")) {
            callingContainer.setVisibility(View.GONE);
            requestingContainer.setVisibility(View.VISIBLE);
            respondingContainer.setVisibility(View.GONE);
            cancellingContainer.setVisibility(View.GONE);
            refusingContainer.setVisibility(View.GONE);

            if (CallData.partnerData.photo.contentEquals(""))
                Picasso.get().load("https://static.productionready.io/images/smiley-cyrus.jpg").into(ivRequestingResponderPhoto);
            else
                Picasso.get().load(CallData.partnerData.photo).into(ivRequestingResponderPhoto);
            tvRequestingResponderRelationship.setText(CallData.partnerData.relationship);

        } else if (status.contentEquals("calling")) {
            callingContainer.setVisibility(View.VISIBLE);
            requestingContainer.setVisibility(View.GONE);
            respondingContainer.setVisibility(View.GONE);
            cancellingContainer.setVisibility(View.GONE);
            refusingContainer.setVisibility(View.GONE);

            startShowAudioFrames();

            if (CallData.partnerData.photo.contentEquals(""))
                Picasso.get().load("https://static.productionready.io/images/smiley-cyrus.jpg").into(ivPartnerPhoto);
            else
                Picasso.get().load(CallData.partnerData.photo).into(ivPartnerPhoto);
            tvPartnerRelationship.setText(CallData.partnerData.relationship);

        } else if (status.contentEquals("responding")) {
            callingContainer.setVisibility(View.GONE);
            requestingContainer.setVisibility(View.GONE);
            respondingContainer.setVisibility(View.VISIBLE);
            cancellingContainer.setVisibility(View.GONE);
            refusingContainer.setVisibility(View.GONE);

            if (CallData.partnerData.photo.contentEquals(""))
                Picasso.get().load("https://static.productionready.io/images/smiley-cyrus.jpg").into(ivRespondingRequesterPhoto);
            else
                Picasso.get().load(CallData.partnerData.photo).into(ivRespondingRequesterPhoto);
            tvRespondingRequesterRelationship.setText(CallData.partnerData.relationship);
        } else if (status.contentEquals("cancelling")) {
            callingContainer.setVisibility(View.GONE);
            requestingContainer.setVisibility(View.GONE);
            respondingContainer.setVisibility(View.GONE);
            cancellingContainer.setVisibility(View.VISIBLE);
            refusingContainer.setVisibility(View.GONE);

            if (CallData.partnerData.photo.contentEquals(""))
                Picasso.get().load("https://static.productionready.io/images/smiley-cyrus.jpg").into(ivCancellingResponderPhoto);
            else
                Picasso.get().load(CallData.partnerData.photo).into(ivCancellingResponderPhoto);
            tvCancellingResponderRelationship.setText(CallData.partnerData.relationship);

        } else if (status.contentEquals("refusing")) {
            callingContainer.setVisibility(View.GONE);
            requestingContainer.setVisibility(View.GONE);
            respondingContainer.setVisibility(View.GONE);
            cancellingContainer.setVisibility(View.GONE);
            refusingContainer.setVisibility(View.VISIBLE);

            if (CallData.partnerData.photo.contentEquals(""))
                Picasso.get().load("https://static.productionready.io/images/smiley-cyrus.jpg").into(ivRefusingRequesterPhoto);
            else
                Picasso.get().load(CallData.partnerData.photo).into(ivRefusingRequesterPhoto);
            tvRefusingRequesterRelationship.setText(CallData.partnerData.relationship);
        }
    }

    ////////////////////////////////////////////////
    ////////////   OpenTok Audio Call   ////////////
    ////////////////////////////////////////////////

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onConnected(Session session) {
        Log.i(LOG_TAG, "Session connected.");

        mPublisher = new Publisher.Builder(this).build();
        mPublisher.setPublisherListener(this);

        cPublisher.addView(mPublisher.getView());

        if (mPublisher.getView() instanceof GLSurfaceView) {
            ((GLSurfaceView) mPublisher.getView()).setZOrderOnTop(true);
        }

        mSession.publish(mPublisher);
    }

    @Override
    public void onDisconnected(Session session) {
        Log.i(LOG_TAG, "Session disconnected.");

        if (mSubscriber != null) {
            mPublisher = null;
            cPublisher.removeAllViews();
            mSubscriber = null;
            cSubscriber.removeAllViews();
            CallData.currentScreen = "Audio Call Screen";
            CallData.callingStatus = "none";

            String token = Data.token;
            Call<Void> closeCall = ApiService.call.closeConnect(token);
            closeCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });

            finish();
        }
    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream received.");

        if (mSubscriber == null) {
            mSubscriber = new Subscriber.Builder(this, stream).build();
            mSession.subscribe(mSubscriber);
            cSubscriber.addView(mSubscriber.getView());
        }

    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream dropped.");
        if (mSubscriber != null) {
            mPublisher = null;
            cPublisher.removeAllViews();
            mSubscriber = null;
            cSubscriber.removeAllViews();
            CallData.currentScreen = "Audio Call Screen";
            CallData.callingStatus = "none";

            String token = Data.token;
            Call<Void> closeCall = ApiService.call.closeConnect(token);
            closeCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });

            openActivity(context, MainActivity.class);
        }
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {
        Log.i(LOG_TAG, "Session error: "+opentokError.getMessage());
        if (mSubscriber != null) {
            finish();
        }
    }

    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {

    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {
        if (mSubscriber != null) {
            finish();
        }
    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {
        if (mSubscriber != null) {
            finish();
        }
    }

    private void stopCall() {
        mPublisher = null;
        cPublisher.removeAllViews();
        mSubscriber = null;
        cSubscriber.removeAllViews();
        mSession.disconnect();

        String token = Data.token;
        Call<Void> closeCall = ApiService.call.closeConnect(token);
        closeCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                CallData.currentScreen = "Main Screen";
                CallData.callingStatus = "none";

                openActivity(context, MainActivity.class);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

    private void cancelRequesting() {
        CallData.callingStatus = "cancelling";
        applyStatus();

        String token = Data.token;
        Call<Void> cancelCall = ApiService.call.cancelRequesting(token);
        cancelCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    CallData.callingStatus = "none";
                    CallData.currentScreen = "Main Screen";
                    openActivity(context, MainActivity.class);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    @AfterPermissionGranted(RC_VIDEO_APP_PERM)
    private void startShowAudioFrames() {
        String[] perms = { Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO };
        if (EasyPermissions.hasPermissions(this, perms)) {
            // initialize view objects from your layout
            cPublisher = findViewById(R.id.camera_publisher);
            cSubscriber = findViewById(R.id.camera_subscriber);

            // initialize and connect to the session
            mSession = new Session.Builder(this, CallData.opentokApiKey, CallData.opentokSessionId).build();
            mSession.setSessionListener(this);
            mSession.connect(CallData.opentokToken);

        } else {
            EasyPermissions.requestPermissions(this, "This app needs access to your camera and mic to make audio calls", RC_VIDEO_APP_PERM, perms);
        }
    }

    private void acceptCall() {
        String token = Data.token;
        Call<ReceiveConnectResponseModel> acceptCall = ApiService.call.receiveConnect(token, CallData.partnerData._id);
        acceptCall.enqueue(new Callback<ReceiveConnectResponseModel>() {
            @Override
            public void onResponse(Call<ReceiveConnectResponseModel> call, Response<ReceiveConnectResponseModel> response) {
                if (response.isSuccessful()) {
                    CallData.opentokApiKey = response.body().apiKey;
                    CallData.opentokSessionId = response.body().sessionId;
                    CallData.opentokToken = response.body().token;
                    CallData.callingStatus = "calling";
                    CallData.currentScreen = "Audio Call Screen";
                    applyStatus();
                }
            }

            @Override
            public void onFailure(Call<ReceiveConnectResponseModel> call, Throwable t) {

            }
        });
    }

    private void refuseCall() {
        CallData.callingStatus = "refusing";
        String token = Data.token;
        Call<Void> refuseCall = ApiService.call.refuseCalling(token);
        refuseCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                CallData.callingStatus = "none";
                CallData.currentScreen = "Main Screen";
                openActivity(context, MainActivity.class);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

}
