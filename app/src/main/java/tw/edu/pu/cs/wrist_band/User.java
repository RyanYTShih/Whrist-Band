package tw.edu.pu.cs.wrist_band;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class User {

    @PrimaryKey
    @NonNull
    private String PersonalID;

    @NonNull
    private String Name;

    @NonNull
    private String Password;

    private int Role;

    private String Gender;

    private float Height;

    private float Weight;

    public User(@NonNull String PersonalID,
                @NonNull String Name,
                @NonNull String Password,
                int Role,
                String Gender,
                float Height,
                float Weight) {
        this.PersonalID = PersonalID;
        this.Name = Name;
        this.Password = Password;
        this.Role = Role;
        this.Gender = Gender;
        this.Height = Height;
        this.Weight = Weight;
    }

    @NonNull
    public String getPersonalID() {
        return PersonalID;
    }

    @NonNull
    public String getName() {
        return Name;
    }

    @NonNull
    public String getPassword() {
        return Password;
    }

    public int getRole() {
        return Role;
    }

    public String getGender() {
        return Gender;
    }

    public float getHeight() {
        return Height;
    }

    public float getWeight() {
        return Weight;
    }

    @Override
    public String toString() {
        return "id: " + PersonalID + ", name: " + Name + ", passwd: " + Password + ", role: " + Role;
    }
}
