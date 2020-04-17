package test.taylor.com.taylorcode.ui;

public class Person {
    private String name;
    private int gender;
    private int age;
    private int height;
    private int weight;

    private Person(Builder builder) {
        this.name = builder.name;
        this.gender = builder.gender;
        this.age = builder.age;
        this.height = builder.height;
        this.weight = builder.weight;
    }

    public static class Builder {
        private String name;
        private int gender;
        private int age;
        private int height;
        private int weight;


        public Builder(String name) {
            this.name = name;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder gender(int gender) {
            this.gender = gender;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder weight(int weight) {
            this.weight = weight;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }
}
