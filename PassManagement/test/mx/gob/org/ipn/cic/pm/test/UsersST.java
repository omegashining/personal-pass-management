package mx.gob.org.ipn.cic.pm.test;

import java.util.List;

import mx.gob.org.ipn.cic.cyber.mu.model.message.Notification;
import mx.gob.org.ipn.cic.cyber.pm.model.service.UsersS;
import mx.gob.org.ipn.cic.cyber.pmh.entities.Users;

import org.junit.Test;

/**
 *
 * @author Gabriel
 */
public class UsersST {
    
    @Test
    public void getByUsername() {
        UsersS usersS = new UsersS();
        Users user = usersS.getByUsername("gabriel");
        
        System.out.println("getByUsername");
        System.out.println(user);
        System.out.println(user.getRole());
        System.out.println("");
    }
    
    @Test
    public void getById() {
        UsersS usersS = new UsersS();
        Users user = usersS.getById(1);
        
        System.out.println("getById");
        System.out.println(user);
        System.out.println(user.getRole());
        System.out.println("");
    }
    
    @Test
    public void getAll() {
        UsersS usersS = new UsersS();
        List<Users> users = usersS.getAll(null);
        
        System.out.println("getAll");
        for (Users u : users) {
            System.out.println(u);
            System.out.println(u.getRole());
        }
        System.out.println("");
    }
    
    @Test
    public void getByPage() {
        UsersS usersS = new UsersS();
        List<Users> users = usersS.getByPage(1, 10, null);
        
        System.out.println("getByPage");
        for (Users u : users) {
            System.out.println(u);
            System.out.println(u.getRole());
        }
        System.out.println("");
    }
    
    @Test
    public void create() {
        UsersS usersS = new UsersS();
        Users user = usersS.getByUsername("gabriel");
        user.setIdUser(0);
        user.setUsername("test");
        System.out.println(user);
        
        Notification notification = usersS.create(user);
        
        System.out.println("create");
        System.out.println(notification);
        System.out.println("");
    }
    
    @Test
    public void update() {
        UsersS usersS = new UsersS();
        Users user = usersS.getByUsername("gabriel");
        
        Notification notification = usersS.update(user);
        
        System.out.println("update");
        System.out.println(notification);
        System.out.println("");
    }
    
    @Test
    public void deleteById() {
        UsersS usersS = new UsersS();
        
        Notification notification = usersS.deleteById(3);
        
        System.out.println("deleteById");
        System.out.println(notification);
        System.out.println("");
    }
    
    @Test
    public void count() {
        UsersS usersS = new UsersS();
        
        System.out.println("count");
        System.out.println(usersS.count(null));
        System.out.println("");
    }
    
}
