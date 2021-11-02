package test.taylor.com.taylorcode.data_persistence;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import androidx.annotation.NonNull;

@Entity(tableName = "taylor")
@TypeConverters(DataConverter.class)
public class Activity {
    @ColumnInfo(name = "user_name")
    private String userName;
    @ColumnInfo(name = "user_avatar_url")
    private String userAvatarUrl;
    private String title;
    @TypeConverters(DataConverter.class)
    private String time;
    private String type;
    @PrimaryKey
    @NonNull
    private String id;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatrarUrl) {
        this.userAvatarUrl = userAvatrarUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
