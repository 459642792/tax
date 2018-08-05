package com.blueteam.base.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by  NastyNas on 2017/9/20.
 */
public class RList {


    public static <T> List<T> add(List<T> list, T l2) {
        list = list == null ? new ArrayList<T>() : list;
        list.add(l2);
        return list;
    }

    public static <T> List<T> asList(T... a) {
        List<T> list = new ArrayList<T>((a.length * 3) / 2 + 1);
        for (int i = 0; i < a.length; i++) {
            list.add(a[i]);
        }
        return list;
    }

    public static <T> List<T> asList(Collection<? extends T> c) {
        List<T> l = new ArrayList<T>();
        l.addAll(c);
        return l;
    }

    public static <T> boolean isEmpty(Collection<? extends T> c) {
        if (c == null || c.size() == 0) {
            return true;
        }
        return false;
    }

    public static <T> boolean isNotEmpty(Collection<? extends T> c) {
        return !isEmpty(c);
    }

}
