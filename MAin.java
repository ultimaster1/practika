
import java.util.Scanner;//библиотека для ввода и там ещё куча всякой фигни которой никто не пользуется
import java.io.PrintWriter;
import java.util.Arrays;

public class MAin {
    // public static final int MY_CONST_1 = 1000;
    //public static final int MY_CONST_2 = 1000;
    public static final double INF = 1000*1000*1000/*Math.pow(MY_CONST_1, MY_CONST_2)*/;//число олицетворяющее бесконечность
    public static void main(String[] args) {
        MAin solution = new MAin();
        solution.solve();
    }

    private void solve() {
        // Для считывания воспользуемся классом Scanner
        // Для вывода - классом PrintWriter
        Scanner scanner = new Scanner(System.in);
        PrintWriter printWriter = new PrintWriter(System.out);

        // Считываем число вершин и дуг графа
        System.out.print("введите количество вершин");
        int vertexCount = scanner.nextInt();
        System.out.print("введите количество дуг");
        int edgeCount = scanner.nextInt();

        // Дуги графа будем хранить массиве
        // экземпляров класса Edge
        Edge[] edges = new Edge[edgeCount];

        for (int i = 0; i < edgeCount; i++) {
            // Считываем начальную и конечную вершину
            // i-ой дуги, а также её вес
            System.out.print("введите откуда идет дуга");
            int from = scanner.nextInt();
            System.out.print("введите куда идет дуга");
            int to = scanner.nextInt();
            System.out.print("введите вес дуги");
            int weight = scanner.nextInt();

            // Так как нами используется 0-индексация,
            // то уменьшим индексы вершин на единицу
            from--;
            to--;

            // Кладем считанную дугу в массив дуг
            edges[i] = new Edge(from, to, weight);
        }

        // Создаем массив, i-ый элемент которого
        // будет хранить текущее расстояние от 1-ой
        // (или 0-ой в нашем случае 0-индексации)
        // до i-ой вершины графа
        int[] distance = new int[vertexCount];

        // До начала работы алгоритма все расстояния,
        // кроме 0-го, равны бесконечности (условной)
        //Arrays.fill(edges, 10000);
        for (int j = 0; j < edgeCount; j++) {
            distance [j] = 10000;
        }
        // 0-ое расстояние, очевидно равно нулю,
        // так как расстояние от 0-ой вершины
        // до самой себя равно нулю
        distance[0] = 0;

        // В соответствии с алгоритмом будем
        // обновлять массив расстояний
        for (int i = 0; i < vertexCount - 1 ; i++) {
            for (int j = 0; j < edgeCount; j++) {
                int from = edges[j].from;
                int to = edges[j].to;
                int weight = edges[j].weight;

                // Ясно, что если вершина from на
                // текущем шаге работы алгоритма
                // находится бесконечно далеко от
                // 0-ой, то она не изменит ответ
                if (distance[from] == 10000) {
                    continue;
                }

                // В противном случае обновим
                // расстояние до вершины to,
                // это называют релаксацией
                distance[to] = Math.min(distance[to],
                        distance[from] + weight);
            }
        }

        // Выводим расстояние от 0-ой вершины
        // до каждой отличной от нее
        for (int i = 1; i < vertexCount  ; i++) {
            if (distance[i] == 10000) {
                printWriter.println("NO");
            } else {
                printWriter.println(distance[i]);
            }
        }

        // После выполнения программы необходимо закрыть
        // потоки ввода и вывода
        scanner.close();
        printWriter.close();
    }

    // Для удобства хранения дуг графа создадим
    // класс, содержащий информацию о весе дуги,
    // начальной и конечной вершинах дуги
    public class Edge {
        int from;
        int to;
        int weight;

        public Edge(int u, int v, int w) {
            this.from = u;
            this.to = v;
            this.weight = w;
        }
    }
}




