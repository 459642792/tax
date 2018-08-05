package com.blueteam.base.util.component.editor;

import org.springframework.beans.propertyeditors.CustomBooleanEditor;

/**
 * 对Controller布尔入参进行格式验证。当格式不匹配时，赋默认值为null
 *
 * @author Eric Lee ,2017-02-20 09:20
 */
public class MyBooleanEditor extends CustomBooleanEditor {
    public MyBooleanEditor(boolean allowEmpty) {
        super(allowEmpty);
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
