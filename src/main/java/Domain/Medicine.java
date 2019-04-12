package Domain;

public class Medicine extends Entity {
    private String name, producer;
    private double price;
    private boolean recipe;


    public Medicine(String id, String name, String producer, double price, boolean recipe) {
        super(id);
        this.name = name;
        this.producer = producer;
        this.price = price;
        this.recipe = recipe;
    }


    @Override
    public String toString() {
        return "Medicine{" +
                ", name='" + name + '\'' +
                ", producer='" + producer + '\'' +
                ", price=" + price +
                ", recipe=" + recipe +
                '}';
    }

    //public void setId(String id) {
    //this.id = id;
    //}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price= price;
    }

    public boolean isRecipe() {
        return recipe;
    }

    public void setRecipe(boolean recipe) {
        this.recipe = recipe;
    }
}

