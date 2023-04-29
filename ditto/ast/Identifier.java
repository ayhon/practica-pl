package ditto.ast;

public class Identifier {
    private final String module;
    private final String name;

    public Identifier(String module, String name) {
        this.module = module;
        this.name = name;
    }

    public Identifier(String name) {
        this.module = null;
        this.name = name;
    }

    public boolean hasModule() {
        return this.module != null;
    }

    public String getModule() {
        return module;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Identifier) {
            Identifier other = (Identifier) obj;
            /// Dos identificadores son iguales si tienen el mismo modulo (si tienen) y el
            /// mismo nombre
            return (module == null || module.equals(other.module)) && name.equals(other.name);
        } else {
            return false;
        }
    }
}
