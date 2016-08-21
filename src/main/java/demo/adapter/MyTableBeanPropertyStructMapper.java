package demo.adapter;

import demo.model.MyTable;
import org.springframework.beans.BeanWrapper;
import org.springframework.data.jdbc.support.oracle.BeanPropertyStructMapper;

/**
 * Created by nhtzr on 8/20/2016.
 */
public class MyTableBeanPropertyStructMapper extends BeanPropertyStructMapper<MyTable> {

    public static final String PROPERTY_PATH = "column2";

    public MyTableBeanPropertyStructMapper() {
        super(MyTable.class);
    }

    @Override
    protected void initBeanWrapper(BeanWrapper bw) {
        super.initBeanWrapper(bw);
        bw.registerCustomEditor(String.class, PROPERTY_PATH, new ClobToStringEditor());
    }

}
