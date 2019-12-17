package mx.gob.org.ipn.cic.cyber.pm.util;

import mx.gob.org.ipn.cic.cyber.mu.security.enums.AlgorithmE;
import mx.gob.org.ipn.cic.cyber.mu.security.util.SecurityUtil;
import mx.gob.org.ipn.cic.cyber.pm.constants.ProjectC;

/**
 *
 * @author Gabriel
 */
public class SecurityU {
    
    public static String encrypt(String text) {
        return SecurityUtil.encryptTextJasypt(text, ProjectC.KEY, AlgorithmE.PBEWithMD5AndTripleDES);
    }
    
    public static String decrypt(String cyphertext) {
        return SecurityUtil.decryptTextJasypt(cyphertext, ProjectC.KEY, AlgorithmE.PBEWithMD5AndTripleDES);
    }
    
}
