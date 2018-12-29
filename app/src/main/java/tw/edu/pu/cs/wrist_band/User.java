package tw.edu.pu.cs.wrist_band;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class User {

    @PrimaryKey
    @NonNull
    private String id;

    @NonNull
    private String name;

    @NonNull
    private String passwd;

    private int role;

    public User(@NonNull String id, @NonNull String name, @NonNull String passwd, int role) {
        this.id = id;
        this.name = name;
        this.passwd = passwd;
        this.role = role;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getPasswd() {
        return passwd;
    }

    public int getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "id: " + id + ", name: " + name + ", passwd: " + passwd + ", role: " + role;
    }
}
