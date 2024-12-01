package com.project.entity.ui;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameUIPanel extends JPanel implements GameUIObject {

    public GameUIPanel() {
        super();
        //setLayout(null);
        setOpaque(true);
    }

    public GameUIPanel(int x, int y, int width, int height) {
        super();
        this.setBounds(x, y, width, height);
        //setLayout(null);
        setOpaque(true);
    }

    @Override
    public void deserialize(JSONObject data) {
        int x = data.getInt("x");
        int y = data.getInt("y");
        int width = data.getInt("width");
        int height = data.getInt("height");
        this.setBounds(x, y, width, height);

        setBackground(Color.decode(data.getString("color_hex")));
        setOpaque(!data.getBoolean("transparent"));

        JSONArray arr = data.getJSONArray("children");

        for (Object o : arr){
            if (!(o instanceof JSONObject)) {
                System.err.println("GamePanel child Object not instance of JSON Object:" + o.toString() + " <" + o.getClass() + ">");
                continue;
            }

            JSONObject obj = (JSONObject) o;
            String classNameString = obj.getString("class");
            Class<?> classInst = null;

            try {
                classInst = Class.forName(classNameString);
            } catch (ClassNotFoundException e) {
                System.err.println("Error deserializing UI Child:  (" + e.getClass().getCanonicalName() + ")" + e.getMessage());
                continue;
            }

            // instantiate the class default constructor with reflection
            try {
                Component childUI = (Component) classInst.getDeclaredConstructor().newInstance();
                ((GameUIObject)childUI).deserialize(obj);
                add(childUI);
            } catch (Exception f) {
                System.err.println("Error deserializing scriptable: (" + f.getClass().getCanonicalName() + ")" + f.getMessage());
                continue;
            }
        }
    }

    public GameUIPanel setBackground(String hex) {
        super.setBackground(Color.decode(hex));
        return this;
    }

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
