package Git;

public class Branch {
    private String name;
    public Branch() {
    }

    public Branch(String name){
        setName(name);
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
