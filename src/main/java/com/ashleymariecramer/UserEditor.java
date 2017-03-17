package com.ashleymariecramer;

import java.beans.PropertyEditorSupport;

public class UserEditor extends PropertyEditorSupport {

    //we will not show Employee as java object in UI,
    // rather when user submit a property bound to employee field,
    // only a string value will come in HTTP POST.
    // We need to have some mechanism to convert that string value back
    // to Employee instance and inject into Booking instance.
    @Override
    public void setAsText(String id)
    {
        Employee e;
        switch(Integer.parseInt(id))
        {
            case 1: e = new Employee("Tom", "Smith", "T", "111"); break;
            case 2: e = new Employee("Dick", "Smith", "D", "222"); break;
            case 3: e = new Employee("Harry", "Smith", "H", "333"); break;
            default: e = null;
        }
        this.setValue(e);
    }

}
