import java.util.Arrays;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class ShortestPath {

    public static void main(String[] args) {
        /*
         *    (0)---3----(1)---1----(4) <==end
         *      \        / \        / \
         *       \      /   \      /   \
         *        1    7     5    2     8
         *         \  /       \  /       \
         *          \/         \/         \
         * start==> (2)---2----(3)---1----(5)
         */
        int[] shortestPath = dijkstra(2, 4, new int[][]{
                {0, 3, 1, 0, 0, 0},
                {3, 0, 7, 5, 1, 0},
                {1, 7, 0, 2, 0, 0},
                {0, 5, 2, 0, 2, 1},
                {0, 1, 0, 2, 0, 8},
                {0, 0, 0, 1, 8, 0},
        });

        System.out.println(Arrays.toString(shortestPath));
    }

    static public int[] dijkstra(int start, int end, int[][] graph) {
        final BitSet visited = new BitSet(graph.length);
        final int[] distances = new int[graph.length];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[start] = 0;

        final Queue<Integer> queue = new LinkedList<>();
        queue.add(start);

        int steps = 0;

        // distances to the graph nodes
        while (!queue.isEmpty()) {
            int curr = queue.remove();
            visited.set(curr);

            if (curr == end) {  // optimization
                continue;
            }

            // neighbours
            for (int i = 0; i < graph.length; i++) {
                if (!visited.get(i) && graph[curr][i] > 0 && graph[curr][i] + distances[curr] < distances[i]) {
                    distances[i] = graph[curr][i] + distances[curr];
                    queue.add(i);
                }
                steps++;
            }
        }
        System.out.println("steps: " + steps);
        System.out.println(Arrays.toString(distances));

        // get the shortest path
        int curr = end;
        final List<Integer> path = new LinkedList<>();
        path.add(end);

        while (curr != start) {
            int minDistanceNode = -1;
            for (int i = 0; i < graph.length; i++) {
                if (i != curr && graph[curr][i] > 0) {
                    if (minDistanceNode == -1 || distances[i] < distances[minDistanceNode]) {
                        minDistanceNode = i;
                    }
                }
            }
            curr = minDistanceNode;
            path.add(0, curr);
        }
        return path.stream().mapToInt(i -> i).toArray();
    }
}
