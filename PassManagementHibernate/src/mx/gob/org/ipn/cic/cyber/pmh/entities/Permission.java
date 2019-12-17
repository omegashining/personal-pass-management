package mx.gob.org.ipn.cic.cyber.pmh.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import static mx.gob.org.ipn.cic.cyber.pmh.constants.DataBase.SCHEMA;

/**
 *
 * @author Gabriel
 */
@Entity
@Table(name = "permissions", schema = SCHEMA)
public class Permission implements java.io.Serializable {

    @Id
    @Column(name = "id_permission", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPermission;
    @Column(name = "permission_name", nullable = false, length = 45)
    private String permissionName;
    @Column(name = "enable", nullable = false, columnDefinition = "boolean default TRUE")
    private boolean enable;
    @Column(name = "trash", nullable = false, columnDefinition = "boolean default FALSE")
    private boolean trash;

    public Permission() {
    }

    public Permission(int idPermission, String permissionName, boolean enable, boolean trash) {
        this.idPermission = idPermission;
        this.permissionName = permissionName;
        this.enable = enable;
        this.trash = trash;
    }

    public int getIdPermission() {
        return this.idPermission;
    }

    public void setIdPermission(int idPermission) {
        this.idPermission = idPermission;
    }

    public String getPermissionName() {
        return this.permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
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

    public boolean areEmptyFields() {
        return this.permissionName.isEmpty();
    }

    @Override
    public String toString() {
        return "Permission{" + "idPermission=" + idPermission + ", permissionName=" + permissionName + ", enable=" + enable + ", trash=" + trash + '}';
    }

}
