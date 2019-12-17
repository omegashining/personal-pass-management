package mx.gob.org.ipn.cic.cyber.pmh.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import mx.gob.org.ipn.cic.cyber.du.interfaces.InterfaceComboInfo;

import static mx.gob.org.ipn.cic.cyber.pmh.constants.DataBase.SCHEMA;

/**
 *
 * @author Gabriel
 */
@Entity
@Table(name = "types", schema = SCHEMA)
public class Types implements java.io.Serializable, InterfaceComboInfo {

    @Id
    @Column(name = "id_type", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idType;
    @Column(name = "type_name", nullable = false, length = 45)
    private String typeName;
    @Column(name = "enable", nullable = false, columnDefinition = "boolean default TRUE")
    private boolean enable;
    @Column(name = "trash", nullable = false, columnDefinition = "boolean default FALSE")
    private boolean trash;

    public Types() {
    }

    public Types(int idType, String typeName, boolean enable, boolean trash) {
        this.idType = idType;
        this.typeName = typeName;
        this.enable = enable;
        this.trash = trash;
    }

    public int getIdType() {
        return this.idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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
        return this.typeName.isEmpty();
    }

    @Override
    public int getId() {
        return this.idType;
    }

    @Override
    public String getName() {
        return this.typeName;
    }

    @Override
    public String toString() {
        return "Types{" + "idType=" + idType + ", typeName=" + typeName + ", enable=" + enable + ", trash=" + trash + '}';
    }

}
