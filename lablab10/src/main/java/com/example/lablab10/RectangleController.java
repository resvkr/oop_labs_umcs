package com.example.lablab10;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RectangleController {
    @GetMapping("/rectangle")
    public Rectangle getRectangle() {
        Rectangle rectangle = new Rectangle(10, 20, 100, 50, "blue");
        return rectangle;
    }


    private List<Rectangle> rectangleList = new ArrayList<>();

    // Metoda dodająca prostokąt o określonych parametrach
    @PostMapping("/rectanlge/add")
    public String addRectangle(@RequestParam int x, @RequestParam int y, @RequestParam int width, @RequestParam int height, @RequestParam String color) {
        Rectangle rectangle = new Rectangle();
        rectangleList.add(rectangle);
        return "Rectangle added";
    }

    // Metoda zwracająca listę prostokątów zmapowaną na JSON
    @GetMapping("/rectangle/list")
    public List<Rectangle> getRectangleList() {
        return rectangleList;
    }

    // Metoda generująca napis zawierający kod SVG z prostokątami
    @GetMapping("/rectangle/svg")
    public String generateSvg() {
        StringBuilder svgBuilder = new StringBuilder();
        svgBuilder.append("<svg width=\"500\" height=\"500\">");
        for (Rectangle rectangle : rectangleList) {
            svgBuilder.append("<rect x=\"").append(rectangle.getX()).append("\" y=\"").append(rectangle.getY())
                    .append("\" width=\"").append(rectangle.getWidth()).append("\" height=\"").append(rectangle.getHeight())
                    .append("\" fill=\"").append(rectangle.getColor()).append("\"/>");
        }
        svgBuilder.append("</svg>");
        return svgBuilder.toString();
    }

    // Metoda GET z argumentem typu int, zwracająca prostokąt w liście o podanym indeksie
    @GetMapping("/rectangle/{index}")
    public Rectangle getRectangleAtIndex(@PathVariable int index) {
        return rectangleList.get(index);
    }

    // Metoda PUT z argumentem typu int i argumentem typu Rectangle, modyfikująca istniejący na liście pod tym indeksem prostokąt
    @PostMapping("/rectangle/{index}")
    public String updateRectangleAtIndex(@PathVariable int index, Rectangle rectangle) {
        rectangleList.set(index, rectangle);
        return "Rectangle updated";
    }

    // Metoda DELETE z argumentem typu int, usuwająca prostokąt z listy z miejsca o podanym indeksie
    @DeleteMapping("/rectangle/{index}")
    public String deleteRectangleAtIndex(@PathVariable int index) {
        if (index >= 0 && index < rectangleList.size()) {
            rectangleList.remove(index);
            return "Rectangle deleted at index " + index;
        } else {
            throw new IllegalArgumentException("Invalid index");
        }
    }

}
