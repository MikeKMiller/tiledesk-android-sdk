package tiledesk.android.sdk;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.chat21.android.core.ChatManager;
import org.chat21.android.core.users.models.IChatUser;
import org.chat21.android.ui.ChatUI;
import org.chat21.android.utils.IOUtils;

/**
 * Created by stefanodp91 on 03/05/18.
 */

public class AppContext extends Application {

    private static final String TAG = "WIDGET";

    private static AppContext instance;

    private ChatManager.Configuration configuration;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static AppContext getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initChatSDK();
    }

    public void initChatSDK() {

        //enable persistence must be made before any other usage of FirebaseDatabase instance.
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        // it creates the chat configurations
//        ChatManager.Configuration configuration =
        configuration = new ChatManager.Configuration.Builder(getString(R.string.chat_firebase_appId))
                .firebaseUrl(getString(R.string.chat_firebase_url))
                .storageBucket(getString(R.string.chat_firebase_storage_bucket))
                .build();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // assuming you have a login, check if the logged user (converted to IChatUser) is valid
        if (currentUser != null) {
            IChatUser iChatUser = (IChatUser) IOUtils.getObjectFromFile(instance,
                    ChatManager._SERIALIZED_CHAT_CONFIGURATION_LOGGED_USER);

            ChatManager.start(this, configuration, iChatUser);
            Log.i(TAG, "AppContext: chat has been initialized with success");

            ChatUI.getInstance().setContext(instance);
            ChatUI.getInstance().enableGroups(true);

            Log.i(TAG, "AppContext: ChatUI has been initialized with success");

        } else {
            Log.w(TAG, "AppContext: chat can't be initialized because chatUser is null");
        }
    }

    public ChatManager.Configuration getConfiguration() {
        return configuration;
    }
}
