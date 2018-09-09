package DB;

public enum PizzaRecipe {
    OWN {
        @Override
        public String getName(){
            return "Create your own";
        }
        @Override
        public String[] getImg(){
            return new String[]{"App/Img/createOwn2.png","App/Img/createOwn3.png"};
        }

        @Override
        public String[] getToppings() {
            return new String[]{};
        }
    },
    MEAT {
        @Override
        public String getName(){
            return "Meat Lover's Pizza";
        }
        @Override
        public String[] getImg(){
            return new String[]{"App/Img/meatLover2.png","App/Img/meatLover3.png"};
        }

        @Override
        public String[] getToppings() {
            return new String[]{"Pepperoni","Bacon Crumble","Ham","Beef","Italian Sausage", "Mild Sausage"};
        }
    },
    HAWAII {
        @Override
        public String getName(){
            return "Hawaiian Pizza";
        }
        @Override
        public String[] getImg(){
            return new String[]{"App/Img/hawaiin2.png","App/Img/hawaiin3.png"};
        }

        @Override
        public String[] getToppings() {
            return new String[]{"Ham","Pineapples"};
        }
    },CHICKEN {
        @Override
        public String getName() {
            return "Chicken Lover's Pizza";
        }

        @Override
        public String[] getImg() {
            return new String[]{"App/Img/chickenLover2.png","App/Img/chickenLover3.png"};
        }

        @Override
        public String[] getToppings() {
            return new String[]{"Grilled Chicken", "Red Onions","Mushroom","Green Peppers"};
        }
    },CHEESE{
        @Override
        public String getName() {
            return "Cheese Lover's Pizza";
        }

        @Override
        public String[] getImg() {
            return new String[]{"App/Img/cheeseLover2.png","App/Img/cheeseLover3.png"};
        }

        @Override
        public String[] getToppings() {
            return new String[]{"Feta Cheese"};
        }
    },SUPREME{
        @Override
        public String getName() {
            return "Meat Supreme";
        }

        @Override
        public String[] getImg() {
            return new String[]{"App/Img/supreme2.png","App/Img/supreme3.png"};
        }

        @Override
        public String[] getToppings() {
            return new String[]{"Pepperoni","Ham","Italian Sausage","Mild Sausage","Beef","Green Peppers","Red Onions","Black Olives"};
        }
    },VEGGIE{
        @Override
        public String getName() {
            return "Veggie Lover's Pizza";
        }

        @Override
        public String[] getImg() {
            return new String[]{"App/Img/vegie2.png","App/Img/vegie3.png"};
        }

        @Override
        public String[] getToppings() {
            return new String[]{"Green Peppers","Mushroom","Red Onions","Marinated tomatoes"};
        }
    };
    public abstract String getName();
    public abstract String[] getImg();
    public abstract String[] getToppings();
}
