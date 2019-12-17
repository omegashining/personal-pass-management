package mx.gob.org.ipn.cic.cyber.pmh.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static mx.gob.org.ipn.cic.cyber.pmh.constants.DataBase.SCHEMA;

/**
 *
 * @author Gabriel
 */
@Entity
@Table(name = "accounts", schema = SCHEMA)
public class Account implements java.io.Serializable {

    @Id
    @Column(name = "id_account", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAccount;
    @Column(name = "account_name", nullable = false, length = 45)
    private String accountName;
    @Column(name = "username", nullable = false, length = 45)
    private String username;
    @Column(name = "password", nullable = false, length = 45)
    private String password;
    @Column(name = "url", nullable = false, length = 255)
    private String url;
    @Column(name = "observations", nullable = true, length = 255)
    private String observations;
    @Column(name = "trash", nullable = false, columnDefinition = "boolean default FALSE")
    private boolean trash;
    @ManyToOne
    @JoinColumn(name = "type_id_type")
    private Types type;
    @ManyToOne
    @JoinColumn(name = "users_id_user")
    private Users user;

    public Account() {
        this.type = new Types();
        this.user = new Users();
    }

    public Account(int idAccount, String accountName, String username, String password, String url, boolean trash) {
        this.idAccount = idAccount;
        this.accountName = accountName;
        this.username = username;
        this.password = password;
        this.url = url;
        this.observations = "";
        this.trash = trash;
        this.type = new Types();
        this.user = new Users();
    }

    public Account(int idAccount, String accountName, String username, String password, String url, String observations, boolean trash) {
        this.idAccount = idAccount;
        this.accountName = accountName;
        this.username = username;
        this.password = password;
        this.url = url;
        this.observations = observations;
        this.trash = trash;
        this.type = new Types();
        this.user = new Users();
    }

    public Account(int idAccount, String accountName, String username, String password, String url, String observations, boolean trash, Types type, Users user) {
        this.idAccount = idAccount;
        this.accountName = accountName;
        this.username = username;
        this.password = password;
        this.url = url;
        this.trash = trash;
        this.observations = observations;
        this.type = type;
        this.user = user;
    }

    public int getIdAccount() {
        return this.idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
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

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getObservations() {
        return this.observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public boolean isTrash() {
        return this.trash;
    }

    public void setTrash(boolean trash) {
        this.trash = trash;
    }

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public boolean areEmptyFields() {
        return this.accountName.isEmpty() || this.username.isEmpty() || this.password.isEmpty() || this.observations.isEmpty() || this.type.getIdType() == 0;
    }

    @Override
    public String toString() {
        return "Account{" + "idAccount=" + idAccount + ", accountName=" + accountName + ", username=" + username + ", password=" + password + ", url=" + url + ", observations=" + observations + ", trash=" + trash + ", user=" + user + '}';
    }

}
