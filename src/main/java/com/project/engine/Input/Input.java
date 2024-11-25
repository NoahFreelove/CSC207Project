package com.project.engine.Input;

import com.project.engine.Core.Engine;
import com.project.engine.Core.Window.IGetActiveScene;
import com.project.engine.Core.Window.ISimpleMouseMoveEvent;

import javax.swing.*;
import java.awt.event.*;

public class Input {
    public static String extractKeyName(KeyEvent e) {
        String key = String.valueOf(e.getKeyChar());
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                key = "UP";
                break;
            case KeyEvent.VK_DOWN:
                key = "DOWN";
                break;
            case KeyEvent.VK_LEFT:
                key = "LEFT";
                break;
            case KeyEvent.VK_RIGHT:
                key = "RIGHT";
                break;
            case KeyEvent.VK_ESCAPE:
                key = "ESC";
                break;
            case KeyEvent.VK_BACK_SPACE:
                key = "BACKSPACE";
                break;
            case KeyEvent.VK_ENTER:
                key = "ENTER";
                break;
            case KeyEvent.VK_SPACE:
                key = "SPACE";
                break;
            case KeyEvent.VK_SHIFT:
                key = "SHIFT";
                break;
            case KeyEvent.VK_CONTROL:
                key = "CTRL";
                break;
            case KeyEvent.VK_ALT:
                key = "ALT";
                break;
            case KeyEvent.VK_TAB:
                key = "TAB";
                break;
            case KeyEvent.VK_CAPS_LOCK:
                key = "CAPS";
                break;
            case KeyEvent.VK_INSERT:
                key = "INSERT";
                break;
            case KeyEvent.VK_DELETE:
                key = "DELETE";
                break;
            case KeyEvent.VK_HOME:
                key = "HOME";
                break;
            case KeyEvent.VK_END:
                key = "END";
                break;
            case KeyEvent.VK_PAGE_UP:
                key = "PAGEUP";
                break;
            case KeyEvent.VK_PAGE_DOWN:
                key = "PAGEDOWN";
                break;
            case KeyEvent.VK_F1:
                key = "F1";
                break;
            case KeyEvent.VK_F2:
                key = "F2";
                break;
            case KeyEvent.VK_F3:
                key = "F3";
                break;
            case KeyEvent.VK_F4:
                key = "F4";
                break;
            case KeyEvent.VK_F5:
                key = "F5";
                break;
            case KeyEvent.VK_F6:
                key = "F6";
                break;
            case KeyEvent.VK_F7:
                key = "F7";
                break;
            case KeyEvent.VK_F8:
                key = "F8";
                break;
            case KeyEvent.VK_F9:
                key = "F9";
                break;
            case KeyEvent.VK_F10:
                key = "F10";
                break;
            case KeyEvent.VK_F11:
                key = "F11";
                break;
            case KeyEvent.VK_F12:
                key = "F12";
                break;
            default:
                break;
        }
        return key.toUpperCase();
    }
    public static String mouseNumToString(int button) {
        String result = "?MB";
        switch (button) {
            case MouseEvent.BUTTON1:
                result = "LMB";
                break;
            case MouseEvent.BUTTON2:
                result = "MMB";
                break;
            case MouseEvent.BUTTON3:
                result = "RMB";
                break;
            default:
                break;
        }
        return result;
    }

    public static int getMods(KeyEvent event) {
        return getModsGeneric(event.isShiftDown(), event.isControlDown(), event.isAltDown());
    }

    public static int getMouseMods(MouseEvent mouseEvent) {
        return getModsGeneric(mouseEvent.isShiftDown(), mouseEvent.isControlDown(), mouseEvent.isAltDown());
    }

    public static int getModsGeneric(boolean shiftDown, boolean controlDown, boolean altDown) {
        int mods = 0;
        if (shiftDown) {
            mods |= InputMods.SHIFT;
        }
        if (controlDown) {
            mods |= InputMods.CTRL;
        }
        if (altDown) {
            mods |= InputMods.ALT;
        }
        return mods;
    }
    public static void addKeyboardListeners(JFrame window, IGetActiveScene activeScene, java.util.Map<String, Boolean> keys) {
        window.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                Engine.getInstance().input(activeScene.getActiveScene(), extractKeyName(e), EInputType.TYPED, getMods(e));
            }

            @Override
            public void keyPressed(KeyEvent e) {
                String key = extractKeyName(e);
                Engine.getInstance().input(activeScene.getActiveScene(), key, EInputType.PRESS, getMods(e));
                keys.put(key, true);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String key = extractKeyName(e);
                Engine.getInstance().input(activeScene.getActiveScene(), key, EInputType.RELEASE, getMods(e));
                keys.put(key, false);
            }
        });
    }

    public static void addMouseMotionListeners(JPanel gamePanel, ISimpleMouseMoveEvent dragEvent, ISimpleMouseMoveEvent moveEvent) {
        gamePanel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                dragEvent.mouseMoved(e.getX(), e.getY());
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                moveEvent.mouseMoved(e.getX(), e.getY());
            }
        });
    }

    public static void addMouseListeners(JPanel gamePanel, IGetActiveScene activeScene, java.util.Map<String, Boolean> keys) {
        gamePanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Engine.getInstance().input(activeScene.getActiveScene(), mouseNumToString(e.getButton()), EInputType.TYPED, getMouseMods(e));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Engine.getInstance().input(activeScene.getActiveScene(), mouseNumToString(e.getButton()), EInputType.PRESS, getMouseMods(e));
                keys.put(mouseNumToString(e.getButton()), true);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Engine.getInstance().input(activeScene.getActiveScene(), mouseNumToString(e.getButton()), EInputType.RELEASE, getMouseMods(e));
                keys.put(mouseNumToString(e.getButton()), false);
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

}
