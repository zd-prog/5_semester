package by.ziziko.fitboard.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "User")
public class User {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "login")
    private String login;
    @NonNull
    @ColumnInfo(name = "password")
    private String password;
    @NonNull
    @ColumnInfo(name = "isAdmin")
    private boolean isAdmin;
    @NonNull
    @ColumnInfo(name = "isInBlacklist")
    private boolean isInBlacklist;

    public User(String login, String password, boolean isAdmin, boolean isInBlacklist) {
        this.login = login;
        this.password = password;
        this.isAdmin = isAdmin;
        this.isInBlacklist = isInBlacklist;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isInBlacklist() {
        return isInBlacklist;
    }

    public void setInBlacklist(boolean inBlacklist) {
        isInBlacklist = inBlacklist;
    }
}
