package model;

public class Actor extends Persona {
    private boolean personajePrincipal;

    public Actor(String nombre, String email, boolean personajePrincipal) {
        super(nombre, email);
        this.personajePrincipal = personajePrincipal;
    }

    public boolean isPersonajePrincipal() {
        return personajePrincipal;
    }

    public void setPersonajePrincipal(boolean personajePrincipal) {
        this.personajePrincipal = personajePrincipal;
    }
}
