package us.fellowtraveller.domain.model;

/**
 * Created by arkadii on 3/6/17.
 */

public class User {
    public static final String GENDER_MALE = "male";
    public static final String GENDER_FEMALE = "female";
    private String id;
    private String ssoId;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String token;


    public User(String ssoId, String password, String firstName, String lastName, String email, String gender) {
        this.ssoId = ssoId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
    }

    public User() {
    }

    public User(String ssoId, String password) {
        this.ssoId = ssoId;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSsoId() {
        return ssoId;
    }

    public void setSsoId(String ssoId) {
        this.ssoId = ssoId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", ssoId='" + ssoId + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
