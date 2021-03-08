package fr.istic.tools.scanexam.view.fX

import javafx.scene.control.TextField

class NumberTextField extends TextField
{

    override void replaceText(int start, int end, String text)
    {
        if (validate(text))
        {
            super.replaceText(start, end, text);
        }
    }

    
    override void replaceSelection(String text)
    {
        if (validate(text))
        {
            super.replaceSelection(text);
        }
    }

    def  boolean validate(String text)
    {
        return text.matches("[0-9]*");
    }
}