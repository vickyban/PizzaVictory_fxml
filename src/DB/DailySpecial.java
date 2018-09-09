package DB;

public enum DailySpecial {
    SUNDAY{
        @Override
        public PizzaRecipe getFirstRecipe() {
            return PizzaRecipe.SUPREME;
        }

        @Override
        public PizzaRecipe getSecondRecipe() {
            return PizzaRecipe.VEGGIE;
        }
    },MONDAY{
        @Override
        public PizzaRecipe getFirstRecipe() {
            return PizzaRecipe.HAWAII;
        }
        @Override
        public PizzaRecipe getSecondRecipe() {
            return PizzaRecipe.MEAT;
        }
    },TUESDAY{
        @Override
        public PizzaRecipe getFirstRecipe() {
            return PizzaRecipe.SUPREME;
        }

        @Override
        public PizzaRecipe getSecondRecipe() {
            return PizzaRecipe.CHEESE;
        }
    },WEDNESDAY{
        @Override
        public PizzaRecipe getFirstRecipe() {
            return PizzaRecipe.CHICKEN;
        }

        @Override
        public PizzaRecipe getSecondRecipe() {
            return PizzaRecipe.VEGGIE;
        }
    },THURSDAY{
        @Override
        public PizzaRecipe getFirstRecipe() {
            return PizzaRecipe.CHEESE;
        }

        @Override
        public PizzaRecipe getSecondRecipe() {
            return PizzaRecipe.CHICKEN;
        }
    },FRIDAY{
        @Override
        public PizzaRecipe getFirstRecipe() {
            return PizzaRecipe.HAWAII;
        }

        @Override
        public PizzaRecipe getSecondRecipe() {
            return PizzaRecipe.VEGGIE;
        }
    },SATURDAY{
        @Override
        public PizzaRecipe getFirstRecipe() {
            return PizzaRecipe.MEAT;
        }

        @Override
        public PizzaRecipe getSecondRecipe() {
            return PizzaRecipe.CHICKEN;
        }
    };
    public abstract PizzaRecipe getFirstRecipe();
    public abstract PizzaRecipe getSecondRecipe();
}
