package tiledesk.android.sdk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.chat21.android.core.ChatManager;
import org.chat21.android.core.authentication.ChatAuthentication;
import org.chat21.android.core.chat_groups.models.ChatGroup;
import org.chat21.android.core.users.models.ChatUser;
import org.chat21.android.core.users.models.IChatUser;
import org.chat21.android.tilewidget.core.departments.DepartmentsManager;
import org.chat21.android.tilewidget.core.models.Department;
import org.chat21.android.tilewidget.ui.DepartmentsUIManager;
import org.chat21.android.tilewidget.ui.departments.activities.ContactFormActivity;
import org.chat21.android.tilewidget.ui.departments.listeners.OnDepartmentClickListener;
import org.chat21.android.tilewidget.ui.listeners.ContactFormListener;
import org.chat21.android.ui.ChatUI;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stefanodp91 on 04/05/18.
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "WIDGET";

    private static final String WIDGET_PROJECT_ID = "5ae1a39b86724100146e1e4c";
//    private static final String WIDGET_PROJECT_ID = "5ad4c101e774ac0014ae0d07";

    private IChatUser recipient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        // configure the departments manager
        DepartmentsManager.Configuration configuration =
                new DepartmentsManager.Configuration.Builder(WIDGET_PROJECT_ID).build();

        // init the departments manager
        DepartmentsManager.start(this, configuration);

        // init the ui manager
        DepartmentsUIManager.getInstance().setContext(this);
        DepartmentsUIManager.getInstance().setOnDepartmentClickListener(new OnDepartmentClickListener() {
            @Override
            public void onDepartmentsClicked(Department department, int position) {

                if (department != null) {

                    Log.d(TAG, "MainActivity.onCreate.onDepartmentsClicked.department: " + department.toString());

                    // if the department id is not valid it shows the department id
                    String departmentDisplayName = department.getName() != null &&
                            !department.getName().isEmpty() ? department.getName() : department.getId();

                    if (MainActivity.this.recipient != null) {
                        // update the recipient display name
                        MainActivity.this.recipient.setFullName(departmentDisplayName);

                        Log.d(TAG, "MainActivity.onCreate.onDepartmentsClicked.recipient: " + MainActivity.this.recipient.toString());


                        final ChatGroup chatGroup = new ChatGroup();
                        chatGroup.setGroupId(recipient.getId());
                        chatGroup.setName(recipient.getFullName());
                        Map<String, Integer> members = new HashMap<>();
                        members.put(ChatManager.getInstance().getLoggedUser().getId(), 1);
                        chatGroup.setMembers(members);
                        chatGroup.setOwner(ChatManager.getInstance().getLoggedUser().getId());
                        chatGroup.setTimestamp(new Date().getTime());

                        ChatManager.getInstance().getGroupsSyncronizer().saveOrUpdateGroupInMemory(chatGroup);

                        ChatUI.getInstance().openChatWithGroup(recipient);

                        Log.d(TAG, "MainActivity.prepareChat.onChatLoginSuccess: currentUser: " + ChatManager.getInstance().getLoggedUser().toString());
                        Log.d(TAG, "MainActivity.prepareChat.onChatLoginSuccess: recipient: " + recipient.toString());
                        Log.d(TAG, "MainActivity.prepareChat.onChatLoginSuccess: department: " + department.toString());

                    }
                }
            }
        });

        DepartmentsUIManager.getInstance().setContactFormListener(new ContactFormListener() {
            @Override
            public void onPreloadContactForm() {
                prepareChat();
            }

            @Override
            public void onContactFormLoaded(Fragment fragment) {

            }
        });

        // launch the contact form activity
        startActivity(new Intent(this, ContactFormActivity.class));
        this.finish();
    }

    private void prepareChat() {
        // start the chat anonymously
        ChatManager.startAnonymously(this, ChatManager.Configuration.appId,
                new ChatAuthentication.OnChatLoginCallback() {
                    @Override
                    public void onChatLoginSuccess(IChatUser currentUser) {
                        Log.d(TAG, "MainActivity.prepareChat.onChatLoginSuccess.currentUser: " + currentUser.toString());

                        ChatManager.start(MainActivity.this,
                                AppContext.getInstance().getConfiguration(), currentUser);
                        Log.i(TAG, "MainActivity.prepareChat.onChatLoginSuccess: chat has been initialized with success");

                        // get a custom group id
                        DatabaseReference node = FirebaseDatabase.getInstance().getReference();
                        String groupId = "support-group" + node.push().getKey();

                        recipient = new ChatUser(groupId, "");
                        MainActivity.this.recipient = recipient;

//                        Log.d(TAG, "MainActivity.prepareChat.onChatLoginSuccess: currentUser: " + currentUser.toString());
//                        Log.d(TAG, "MainActivity.prepareChat.onChatLoginSuccess: recipient: " + recipient.toString());
                    }

                    @Override
                    public void onChatLoginError(Exception e) {
                        Log.e(TAG, "MainActivity.prepareChat.onChatLoginError: " + e);
                    }
                });
    }
}