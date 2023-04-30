package ditto.ast;

import java.util.Stack;

public class Delta {
    private final Stack<Integer> offsets = new Stack<Integer>();
    private int nextOffset = 0;
    private int offsetSize;

    public void enterBlock() {
        offsets.push(nextOffset);
    }

    public void exitBlock() {
        nextOffset = offsets.pop();
    }

    public int useNextOffset(int typeSize) {
        var currOffset = nextOffset;
        increaseOffset(typeSize);
        return currOffset;
    }

    private void increaseOffset(int typeSize) {
        nextOffset += typeSize;
        offsetSize = Math.max(offsetSize, nextOffset);
    }

    public int getOffsetSize() {
        return offsetSize;
    }
}