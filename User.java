public abstract class User {
    //Define Variables
    private int user_id;
    private String name;
    private String email;
    private boolean notifyByEmail;

    //Constructor
    public User(int user_id, String name, String email) {
        this.user_id = user_id;
        this.name = name;
        setEmail(email);
        this.notifyByEmail = true;
    }   

    //Getters and setters
    public int getUserID() {
        return user_id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public void setUserID(int user_id) {
        this.user_id=user_id;
    }
    public void setName(String name) {
        this.name=name;
    }
    public void setEmail(String email) {
        if(email==null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address");
        }
        this.email=email;
    }
    public boolean isNotifyByEmail() {
        return notifyByEmail;
    }
    public void setNotifyByEmail(boolean notifyByEmail) {
        this.notifyByEmail = notifyByEmail;
    }
    
    //Methods

    //Authentication Method
    public boolean authenticate(int id, String email) {
        return this.user_id == id && this.email.equals(email);
    }

    //Notifcation Preference For Due Dates and Reservations Updates


}
