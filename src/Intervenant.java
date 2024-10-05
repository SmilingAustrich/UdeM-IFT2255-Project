public class Intervenant implements User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String cityIdCode;
    private int entrepreneurType;

    public Intervenant(String firstName, String lastName, String email, String password, String cityIdCode, int entrepreneurType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.entrepreneurType = entrepreneurType;
        this.cityIdCode = cityIdCode;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public int getEntrepreneurType() {
        return entrepreneurType;
    }

    public String getCityIdCode() {
        return cityIdCode;
    }
}
