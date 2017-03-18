package us.fellowtraveller.domain.model;

/**
 * Created by arkadii on 3/18/17.
 */
public class AccountUser extends User {

    private String password;
    private String token;


    public AccountUser() {
    }

    public AccountUser(String ssoId, String password) {
        setSsoId(ssoId);
        setPassword(password);
    }

    public AccountUser(String ssoId, String password, String firstName, String lastName, String email, String gender) {
        setSsoId(ssoId);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setGender(gender);
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
                "id='" + getId() + '\'' +
                ", ssoId='" + getSsoId() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", gender='" + getGender() + '\'' +
                ", token='" + getToken() + '\'' +
                '}';
    }
}
