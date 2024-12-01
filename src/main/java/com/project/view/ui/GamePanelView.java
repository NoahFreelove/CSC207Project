package com.project.view.ui;

import com.project.entity.ui.GameUIObject;
import com.project.entity.ui.GameUIPanel;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;

public class GamePanelView extends JPanel{
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

}
