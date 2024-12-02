package com.project.entity.ui;

import com.project.entity.serialization.ISerializable;

import javax.swing.*;

public interface GameUIObject extends ISerializable {
    public JComponent getComponent();
}
