package com.blueteam.base.util.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 格式加tree
 *
 * @author xiaojiang
 * @create 2017-07-26  17:23
 */
public class BuildTree {

    /**
     * 方法的功能描述:TODO 格式化
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/7/26 18:33
     * @since 1.4.0
     */
    public static <T> Tree<T> build(List<Tree<T>> nodes) {
        if (nodes == null) {
            return null;
        }
        List<Tree<T>> topNodes = new ArrayList<Tree<T>>();
        for (Tree<T> children : nodes) {
            String pid = children.getParentId();
            if (pid == null || "".equals(pid)) {
                topNodes.add(children);

                continue;
            }
            for (Tree<T> parent : nodes) {
                String id = parent.getId();
                if (id != null && id.equals(pid)) {
                    parent.getChildren().add(children);
                    children.setParent(true);
                    parent.setChildren(true);
                    continue;
                }
            }

        }
        Tree<T> root = new Tree<T>();
        if (topNodes.size() == 1) {
            root = topNodes.get(0);
        } else {
            root.setId("-1");
            root.setParentId("");
            root.setParent(false);
            root.setChildren(true);
            root.setChecked(true);
            root.setChildren(topNodes);
            root.setText("顶级节点");

        }
        return root;
    }
}
