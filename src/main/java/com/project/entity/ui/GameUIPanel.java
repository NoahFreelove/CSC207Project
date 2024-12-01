package com.project.entity.ui;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameUIPanel extends JPanel implements GameUIObject {

    public void setAlpha(int a) {
        Color c = getBackground();
        setBackground(new Color(c.getRed(), c.getGreen(), c.getBlue(), a));
    }

    @Override
    public JSONObject serialize() {
        JSONObject output = new JSONObject();
        output.put("x", getX());
        output.put("y", getY());
        output.put("width", getWidth());
        output.put("height", getHeight());
        String hexOutput = "#";
        Color color = getBackground();
        hexOutput += Integer.toHexString(color.getRed());
        hexOutput += Integer.toHexString(color.getGreen());
        hexOutput += Integer.toHexString(color.getBlue());
        hexOutput += Integer.toHexString(color.getAlpha());
        output.put("color_hex", hexOutput);
        output.put("transparent", !isOpaque());


        JSONArray childrenArray = new JSONArray();
        ArrayList<Component> children = new ArrayList<>(List.of(getComponents()));
        children.forEach(component -> {
            if (component instanceof GameUIObject) {
                JSONObject outputChild = ((GameUIObject)component).serialize();
                outputChild.put("class", ((GameUIObject)component).attachedClass().getName());
                childrenArray.put(outputChild);
            }
        });

        output.put("children", childrenArray);

        return output;
    }

    @Override
    public JComponent getComponent() {
        return null;
    }
}
