package tuanz1.puzzle.controller;

import javafx.scene.image.Image;

public class TestCtrl {
    public static void main(String[] args){
       Test t = new Test();
    }
    static class Test{
        public Test(){
            Image test = new Image(getClass().getResourceAsStream("/blank120.jpg"));
        }
    }
}
