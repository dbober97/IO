package edu.io.player;

import edu.io.token.Tool;

import java.util.Stack;

public class Shed {
    private final Stack<Tool> tools = new Stack<>();

    public boolean isEmpty()
    {
        return tools.isEmpty();
    }

    public void add(Tool tool)
    {
        if(tool == null) throw new IllegalArgumentException("Tool cannot be null!");
        tools.add(tool);
    }

    public Tool getTool()
    {
        if(tools.isEmpty()) return new NoTool();
        else return tools.peek();
    }

    public void dropTool()
    {
        tools.removeLast();
    }


}
