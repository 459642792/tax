package com.blueteam.base.util.component.editor;

import org.springframework.beans.propertyeditors.CustomNumberEditor;

/**
 * 对Controller数字入参进行格式验证。当格式不匹配时，赋默认值为null
 *
 * @author Eric Lee ,2017-02-20 09:20
 */
public class MyNumberEditor extends CustomNumberEditor {

    public MyNumberEditor(Class<? extends Number> numberClass, boolean allowEmpty) throws IllegalArgumentException {
        super(numberClass, allowEmpty);
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        try {
            super.setAsText(text);
        } catch (Exception e) {
            super.setAsText(null);
        }
    }
}
