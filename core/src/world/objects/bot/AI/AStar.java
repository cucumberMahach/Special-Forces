package world.objects.bot.AI;
import java.util.LinkedList;
class Cell {
    /**
     * Создает клетку с координатами x, y.
     * @param blocked является ли клетка непроходимой
     */
    public Cell(int x, int y, boolean blocked) {
        this.x = x;
        this.y = y;
        this.blocked = blocked;
    }

    /**
     * Функция вычисления манхеттенского расстояния от текущей
     * клетки до finish
     * @param finish конечная клетка
     * @return расстояние
     */
    public int mandist(Cell finish) {
        //return 10 * (Math.abs(this.x - finish.x) + Math.abs(this.y - finish.y));
        int x = this.x - finish.x;
        int y = this.y - finish.y;
        return x * x + y * y;
    }

    /**
     * Вычисление стоимости пути до соседней клетки finish
     * @param finish соседняя клетка
     * @return 10, если клетка по горизонтали или вертикали от текущей, 14, если по диагонали
     * (это типа 1 и sqrt(2) ~ 1.44)
     */
    public int price(Cell finish) {
        if (this.x == finish.x || this.y == finish.y) {
            return 10;
        } else {
            return 14;
        }
    }

    /**
     * Устанавливает текущую клетку как стартовую
     */
    public void setAsStart() {
        this.start = true;
    }

    /**
     * Устанавливает текущую клетку как конечную
     */
    public void setAsFinish() {
        this.finish = true;
    }

    /**
     * Сравнение клеток
     * @param second вторая клетка
     * @return true, если координаты клеток равны, иначе - false
     */
    public boolean equals(Cell second) {
        return (this.x == second.x) && (this.y == second.y);
    }
    
    public String toString() {
        if (this.road) {
            return "*";
        }
        if (this.start || this.finish) {
            return "+";
        }
        if (this.blocked) {
            return "#";
        }
        return ".";
    }

    public int x = -1;
    public int y = -1;
    public Cell parent = this;
    public boolean blocked = false;
    public boolean start = false;
    public boolean finish = false;
    public boolean road = false;
    public int F = 0;
    public int G = 0;
    public int H = 0;
}

class Table<T extends Cell> {
	 public int width;
	 public int height;
	 private Cell[][] table;
	 
    /**
     * Создаем карту игры с размерами width и height
     */
    public Table(int width, int height) {
        this.width = width;
        this.height = height;
        this.table = new Cell[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                table[y][x] = new Cell(0, 0, false);
            }
        }
    }

    /**
     * Добавить клетку на карту
     */
    public void add(Cell cell) {
        table[cell.y][cell.x] = cell;
    }

    /**
     * Получить клетку по координатам x, y
     * @return клетка, либо фейковая клетка, которая всегда блокирована (чтобы избежать выхода за границы)
     */
    @SuppressWarnings("unchecked")
    public T get(int x, int y) {
        if (x < width && x >= 0 && y < height && y >= 0) {
            return (T)table[y][x];
        }
        // а разве так можно делать в Java? оО но работает оО
        return (T)(new Cell(0, 0, true));
    }

    /**
     * Печать всех клеток поля. Красиво.
     */
    public void printp() {
        for (int y = 0; y < AStar.HEIGHT; y++) {
            for (int x = 0; x < AStar.WIDTH; x++) {
                System.out.print(this.get(x, y));
            }
            System.out.println();
        }
        System.out.println();
    }
}

public class AStar {
    public static int WIDTH = 50;
    public static int HEIGHT = 10;

    /**
     * Пример хуевой реализации алгоритма поиска пути А*
     * @param args нихуя
     */
    public static void main(String[] args) {
    	
    	Cell start, finish;
        Table<Cell> cellList = new Table<Cell>(AStar.WIDTH, AStar.HEIGHT);
        LinkedList<Cell> openList = new LinkedList<Cell>();
        LinkedList<Cell> closedList = new LinkedList<Cell>();
        LinkedList<Cell> tmpList = new LinkedList<Cell>();

        for (int y = 0; y < AStar.HEIGHT; y++) {
            for (int x = 0; x < AStar.WIDTH; x++) {
                cellList.add(new Cell(x, y, false));
            }
        }
        
        cellList.get(20, 0).blocked = true;
        cellList.get(20, 1).blocked = true;
        cellList.get(20, 2).blocked = true;
        cellList.get(20, 3).blocked = true;
        cellList.get(20, 4).blocked = true;
        cellList.get(20, 5).blocked = true;
        cellList.get(20, 6).blocked = true;
        cellList.get(20, 7).blocked = true;

        // Стартовая и конечная
        
        start = cellList.get(0, 0);
        start.setAsStart();
        finish = cellList.get(45, 0);
        finish.setAsFinish();

        cellList.printp();

        // Фух, начинаем
        boolean found = false;
        boolean noroute = false;
        int count = 0;

        //1) Добавляем стартовую клетку в открытый список.
        openList.push(start);

        //2) Повторяем следующее:
        while (!found && !noroute) {
            //a) Ищем в открытом списке клетку с наименьшей стоимостью F. Делаем ее текущей клеткой.
            Cell min = openList.getFirst();
            for (Cell cell : openList) {
                // тут я специально тестировал, при < или <= выбираются разные пути,
                // но суммарная стоимость G у них совершенно одинакова. Забавно, но так и должно быть.
                if (cell.F < min.F) min = cell;
            }

            //b) Помещаем ее в закрытый список. (И удаляем с открытого)
            closedList.push(min);
            openList.remove(min);

            //c) Для каждой из соседних 8-ми клеток ...
            tmpList.clear();
            tmpList.add(cellList.get(min.x - 1, min.y - 1));
            tmpList.add(cellList.get(min.x,     min.y - 1));
            tmpList.add(cellList.get(min.x + 1, min.y - 1));
            tmpList.add(cellList.get(min.x + 1, min.y));
            tmpList.add(cellList.get(min.x + 1, min.y + 1));
            tmpList.add(cellList.get(min.x,     min.y + 1));
            tmpList.add(cellList.get(min.x - 1, min.y + 1));
            tmpList.add(cellList.get(min.x - 1, min.y));

            for (Cell neightbour : tmpList) {
                //Если клетка непроходимая или она находится в закрытом списке, игнорируем ее. В противном случае делаем следующее.
                if (neightbour.blocked || closedList.contains(neightbour)) continue;

                //Если клетка еще не в открытом списке, то добавляем ее туда. Делаем текущую клетку родительской для это клетки. Расчитываем стоимости F, G и H клетки.
                if (!openList.contains(neightbour)) {
                    openList.add(neightbour);
                    neightbour.parent = min;
                    neightbour.H = neightbour.mandist(finish);
                    neightbour.G = start.price(min);
                    neightbour.F = neightbour.H + neightbour.G;
                    continue;
                }

                // Если клетка уже в открытом списке, то проверяем, не дешевле ли будет путь через эту клетку. Для сравнения используем стоимость G.
                if (neightbour.G + neightbour.price(min) < min.G) {
                    // Более низкая стоимость G указывает на то, что путь будет дешевле. Эсли это так, то меняем родителя клетки на текущую клетку и пересчитываем для нее стоимости G и F.
                    neightbour.parent = min; // вот тут я честно хз, надо ли min.parent или нет
                    neightbour.H = neightbour.mandist(finish);
                    neightbour.G = start.price(min);
                    neightbour.F = neightbour.H + neightbour.G;
                }

                // Если вы сортируете открытый список по стоимости F, то вам надо отсортировать свесь список в соответствии с изменениями.
            }

            //d) Останавливаемся если:
            //Добавили целевую клетку в открытый список, в этом случае путь найден.
            //Или открытый список пуст и мы не дошли до целевой клетки. В этом случае путь отсутствует.
            
            if (openList.contains(finish)) {
                found = true;
            }

            if (openList.isEmpty()) {
                noroute = true;
            }
            count++;
        }
        
        
        //3) Сохраняем путь. Двигаясь назад от целевой точки, проходя от каждой точки к ее родителю до тех пор, пока не дойдем до стартовой точки. Это и будет наш путь.
        if (!noroute) {
            Cell rd = finish.parent;
            while (!rd.equals(start)) {
                rd.road = true;
                rd = rd.parent;
                if (rd == null) break;
            }
            cellList.printp();
        } else {
            System.out.println("NO ROUTE");
        }
        System.out.println("Iterations: " + count);
    }
}