package remake.webui;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

   // or import javax.enterprise.context.SessionScoped;

@ManagedBean(name="propriedades") // or @Named("user")
@SessionScoped
public class PropriedadesBean {
    
    protected static String name;
    protected static String login;
    protected static String password;
    protected static String confirmPassword;
    protected static String email;
    

    
    
   public String getLogin() { 
       return login; 
   }
   public void setLogin(String newValue) { 
       login = newValue; 
   }

   public static String login() { 
       return login; 
   }
   public static void login(String newValue) { 
       login = newValue; 
   }
   
   public String getPassword() {
       return password; 
   }
   public void setPassword(String newValue) {
       password = newValue; 
   }

   public static String password() {
       return password; 
   }
   public static void password(String newValue) {
       password = newValue; 
   }
   
   public String getName() {
       return name; 
   }
   public void setName(String newValue) {
       name = newValue; 
   }

   public static String name() {
       return name; 
   }
   public static void name(String newValue) {
       name = newValue; 
   }
   
   public String getConfirmPassword() {
       return confirmPassword; 
   }
   public void setConfirmPassword(String newValue) {
       confirmPassword = newValue; 
   }

   public static String confirmPassword() {
       return confirmPassword; 
   }
   public static void confirmPassword(String newValue) {
       confirmPassword = newValue; 
   }

   
   public String getEmail() {
       return email; 
   }
   public void setEmail(String newValue) {
       email = newValue; 
   }

   public static String email() {
       return email; 
   }
   public static void email(String newValue) {
   
       email = newValue;
   }
   
   
   
}
