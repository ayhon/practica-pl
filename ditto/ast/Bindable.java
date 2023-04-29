package ditto.ast;

import java.util.List;

public interface Bindable {
    public void bind(Module global, LocalContext localContext);

    public List<Bindable> getBindableChildren();
}
