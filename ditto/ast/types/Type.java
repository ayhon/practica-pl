package ditto.ast.types;

import java.util.ArrayList;
import java.util.List;

import ditto.ast.Bindable;
import ditto.ast.Module;
import ditto.ast.LocalContext;

public abstract class Type implements Bindable {
    /// Lo que ocuparía en memoria. Se usa para calcular delta.
    public abstract int size();

    @Override
    public List<Bindable> getBindableChildren() {
        return new ArrayList<>();
    }

    @Override
    public void bind(Module global, LocalContext localContext) {
        for (var children : this.getBindableChildren()) {
            children.bind(global, localContext);
        }
    }
}
