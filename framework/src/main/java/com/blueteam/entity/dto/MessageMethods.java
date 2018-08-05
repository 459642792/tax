package com.blueteam.entity.dto;

/**
 * Created by libra on 2017/5/25.
 */
public class MessageMethods {
    /**
     * 控制器名称
     */
    private String controllerName;

    /**
     * ActionName
     */
    private String actionName;

    public String getControllerName() {
        return controllerName;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
}
