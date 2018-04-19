package com.jc.flora.apps.component.request.trh;

public class RequestParameter implements java.io.Serializable, Comparable<Object> {

    private static final long serialVersionUID = 1274906854152052510L;

    public String name;

    public String value;

    public RequestParameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public int compareTo(Object another) {
        int compared;
        // 值比较
        RequestParameter parameter = (RequestParameter) another;
        compared = name.compareTo(parameter.name);
        if (compared == 0) {
            compared = value.compareTo(parameter.value);
        }
        return compared;
    }

    public boolean equals(Object o) {
        if (null == o) {
            return false;
        }

        if (this == o) {
            return true;
        }

        if (o instanceof RequestParameter) {
            RequestParameter parameter = (RequestParameter) o;
            return name.equals(parameter.name) && value.equals(parameter.value);
        }

        return false;
    }

}
