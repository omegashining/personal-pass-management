package mx.gob.org.ipn.cic.cyber.pmh.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import mx.gob.org.ipn.cic.cyber.du.interfaces.InterfaceComboInfo;

import static mx.gob.org.ipn.cic.cyber.pmh.constants.DataBase.SCHEMA;

/**
 *
 * @author Gabriel
 */
@Entity
@Table(name = "roles", schema = SCHEMA)
public class Roles implements java.io.Serializable, InterfaceComboInfo {

    @Id
    @Column(name = "id_role", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRole;
    @Column(name = "role_name", nullable = false, length = 45)
    private String roleName;
    @Column(name = "enable", nullable = false, columnDefinition = "boolean default TRUE")
    private boolean enable;
    @Column(name = "trash", nullable = false, columnDefinition = "boolean default FALSE")
    private boolean trash;
    @Column(name = "eliminable", nullable = false, columnDefinition = "boolean default TRUE")
    private boolean eliminable;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "roles_permissions", schema = SCHEMA,
            joinColumns = {
                @JoinColumn(name = "role_id_role", nullable = false, updatable = false)},
            inverseJoinColumns = {
                @JoinColumn(name = "permission_id_permission", nullable = false, updatable = false)})
    private List<Permission> permissions;

    public Roles() {
    }

    public Roles(int idRole, String roleName, boolean enable, boolean trash, boolean eliminable) {
        this.idRole = idRole;
        this.roleName = roleName;
        this.enable = enable;
        this.trash = trash;
        this.eliminable = eliminable;
        this.permissions = new ArrayList();
    }

    public Roles(int idRole, String roleName, boolean enable, boolean trash, boolean eliminable, List<Permission> permissions) {
        this.idRole = idRole;
        this.roleName = roleName;
        this.enable = enable;
        this.trash = trash;
        this.eliminable = eliminable;
        this.permissions = permissions;
    }

    public int getIdRole() {
        return this.idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public boolean areEmptyFields() {
        return this.roleName.isEmpty();
    }

    @Override
    public int getId() {
        return this.idRole;
    }

    @Override
    public String getName() {
        return this.roleName;
    }

    @Override
    public String toString() {
        return "Roles{" + "idRole=" + idRole + ", roleName=" + roleName + ", enable=" + enable + ", trash=" + trash + ", eliminable=" + eliminable + '}';
    }

}
