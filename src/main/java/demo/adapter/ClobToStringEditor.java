package demo.adapter;

import oracle.sql.CLOB;

import java.beans.PropertyEditorSupport;
import java.io.StringWriter;

import static org.apache.commons.io.IOUtils.copy;

/**
 * Created by nhtzr on 8/20/2016.
 */
public class ClobToStringEditor extends PropertyEditorSupport {

    @Override
    public Object getValue() {
        try {
            StringWriter writer = new StringWriter();
            CLOB value = (CLOB) super.getValue();
            copy(value.binaryStreamValue(), writer);
            return writer.toString();
        } catch (Exception e) {
            return "";
        }
    }

}
