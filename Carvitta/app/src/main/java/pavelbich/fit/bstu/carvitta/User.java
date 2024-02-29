package pavelbich.fit.bstu.carvitta;

public class User {
    public Integer id;
    public String login;
    public String name;
    public String password;
    public Integer isAuth;

    public User(Integer Usid, String Login, String Name, String Password, Integer IsAuth){
        this.id = Usid;
        this.login = Login;
        this.name = Name;
        this.password = Password;
        this.isAuth = IsAuth;
    }

    public Integer getUsid(){
        return id;
    }
    public String getLogin(){
        return login;
    }
    public String getName(){
        return name;
    }
    public String getPassword(){
        return password;
    }
    public Integer getIsAuth(){
        return isAuth;
    }
}
