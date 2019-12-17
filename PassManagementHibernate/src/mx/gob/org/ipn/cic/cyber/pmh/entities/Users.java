package mx.gob.org.ipn.cic.cyber.pmh.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static mx.gob.org.ipn.cic.cyber.pmh.constants.DataBase.CATALOG;

/**
 *
 * @author Gabriel
 */
@Entity
@Table(name = "users", catalog = CATALOG)
public class Users implements java.io.Serializable {

    @Id
    @Column(name = "id_user", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;
    @Column(name = "name", nullable = false, length = 30)
    private String name;
    @Column(name = "paternal", nullable = false, length = 20)
    private String paternal;
    @Column(name = "maternal", nullable = false, length = 20)
    private String maternal;
    @Column(name = "username", unique = true, nullable = false, length = 45)
    private String username;
    @Column(name = "password", nullable = false, length = 45)
    private String password;
    @Column(name = "enable", nullable = false, columnDefinition = "boolean default TRUE")
    private boolean enable;
    @Column(name = "trash", nullable = false, columnDefinition = "boolean default FALSE")
    private boolean trash;
    @Column(name = "eliminable", nullable = false, columnDefinition = "boolean default TRUE")
    private boolean eliminable;
    @ManyToOne
    @JoinColumn(name = "role_id_role")
    private Roles role;

    public Users() {
        this.role = new Roles();
    }

    public Users(int idUser, String name, String paternal, String maternal, String username, String password, boolean enable, boolean trash, boolean eliminable) {
        this.idUser = idUser;
        this.name = name;
        this.paternal = paternal;
        this.maternal = maternal;
        this.username = username;
        this.password = password;
        this.enable = enable;
        this.trash = trash;
        this.eliminable = eliminable;
        this.role = new Roles();
    }

    public Users(int idUser, String name, String paternal, String maternal, String username, String password, boolean enable, boolean trash, boolean eliminable, Roles role) {
        this.idUser = idUser;
        this.name = name;
        this.paternal = paternal;
        this.maternal = maternal;
        this.username = username;
        this.password = password;
        this.enable = enable;
        this.trash = trash;
        this.eliminable = eliminable;
        this.role = role;
    }

    public int getIdUser() {
        return this.idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPaternal() {
        return this.paternal;
    }

    public void setPaternal(String paternal) {
        this.paternal = paternal;
    }

    public String getMaternal() {
        return this.maternal;
    }

    public void setMaternal(String maternal) {
        this.maternal = maternal;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isTrash() {
        return trash;
    }

    public void setTrash(boolean trash) {
        this.trash = trash;
    }

    public boolean isEliminable() {
        return this.eliminable;
    }

    public void setEliminable(boolean eliminable) {
        this.eliminable = eliminable;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public boolean areEmptyFields() {
        return this.name.isEmpty() || this.paternal.isEmpty() || this.maternal.isEmpty() || this.username.isEmpty() || this.password.isEmpty() || this.role.getIdRole() == 0;
    }

    @Override
    public String toString() {
        return "Users{" + "idUser=" + idUser + ", name=" + name + ", paternal=" + paternal + ", maternal=" + maternal + ", username=" + username + ", password=" + password + ", enable=" + enable + ", trash=" + trash + ", eliminable=" + eliminable + '}';
    }

}
