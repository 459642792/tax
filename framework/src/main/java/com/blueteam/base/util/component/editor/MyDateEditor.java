package com.blueteam.base.util.component.editor;

import org.springframework.beans.propertyeditors.CustomDateEditor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MyDateEditor extends CustomDateEditor {
    /**
     * 构造方法
     *
     * @param dateFormat
     * @param formatString 当dateFormat为null时，此参数必须指定
     * @param allowEmpty
     */
    public MyDateEditor(DateFormat dateFormat, String formatString, boolean allowEmpty) {
        super(beforeConstructor(dateFormat, formatString), allowEmpty);
    }

    private static DateFormat beforeConstructor(DateFormat dateFormat, String formatString) {
        dateFormat = dateFormat == null ? new SimpleDateFormat(formatString) : dateFormat;
        dateFormat.setLenient(false);
        return dateFormat;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        try {
            super.setAsText(text);
        } catch (Exception e) {
            setValue(null);
        }
    }
}
