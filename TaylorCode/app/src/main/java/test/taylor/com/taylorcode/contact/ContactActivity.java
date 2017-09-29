package test.taylor.com.taylorcode.contact;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created on 17/9/13.
 */

public class ContactActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //case1:query all contacts
        queryContacts();
        Button btn = new Button(this);
        btn.setOnClickListener(this);
        setContentView(btn);
    }


    /**
     * case1:query all contacts
     */
    private void queryContacts() {
        final String[] PROJECTECTION = {
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,//联系人姓名
                ContactsContract.CommonDataKinds.Phone.NUMBER,//联系人号码
                //raw_contacts表对应phonetic_name，data表对应data9
                ContactsContract.Intents.Insert.PHONETIC_NAME,
                //raw_contact_id
                ContactsContract.Data.RAW_CONTACT_ID
        };
        final int DISPLAY_NAME_INDEX = 0;
        final int PHONE_NUMBER_INDEX = 1;
        final int DING_TAG = 2;
        final int CONTACT_ID_INDEX = 3;
        ContentResolver resolver = this.getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTECTION, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(DISPLAY_NAME_INDEX);
                String phone = cursor.getString(PHONE_NUMBER_INDEX);
                String tag = cursor.getString(DING_TAG);
                long contactId = cursor.getLong(CONTACT_ID_INDEX);
                Log.v("taylor ttcontact", "ContactActivity.queryContacts() " + "contactId=" + contactId + ",name=" + name + ",phone=" + phone);
            }
            cursor.close();
        }
    }

    /**
     * case2:update specified contact
     */
    private void updateContact(long contactId, String number) {
        ContentResolver resolver = this.getContentResolver();
        ContentValues values = new ContentValues();
        values.clear();
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, number);

        String Where = ContactsContract.Data.RAW_CONTACT_ID + " = ? and " + ContactsContract.Data.MIMETYPE + " = ?";
        String[] WhereParams = new String[]{"" + contactId,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE};
//        resolver.update(ContactsContract.Data.CONTENT_URI, values, Where, WhereParams);

//        //更新联系人姓名
//        values.clear();
//        values.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, item.getShowname());
//        values.put(ContactsContract.Data.DATA3, "");
//        Where = ContactsContract.Data.RAW_CONTACT_ID + " = ? and " + ContactsContract.Data.MIMETYPE + " = ?";
//        WhereParams = new String[]{"" + item.getContactId(),
//                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE};
        int result = resolver.update(ContactsContract.Data.CONTENT_URI, values, Where, WhereParams);
    }

    @Override
    public void onClick(View v) {
        Log.v("taylor ttcontact", "ContactActivity.onClick() " + " ");
        //case2:update specified contact
        updateContact(111,"+8623232222");
    }
}
