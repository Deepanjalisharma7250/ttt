// Parent class
class Animal {
    public void makeSound() {
        System.out.println("Some generic animal sound");
    }
}

class Dog extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Bark");
    }
}

class Cat extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Meow");
    }
}

public class Main {
    public static void main(String[] args) {
        Animal myDog = new Dog();  
        Animal myCat = new Cat();  
        
        myDog.makeSound();  
        myCat.makeSound(); 
        
        Animal myAnimal = new Animal();
        myAnimal.makeSound(); 
    }
}
