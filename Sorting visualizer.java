import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class SortingVisualizer extends JPanel {
    private int[] array;
    private final int SIZE = 50;
    private final int DELAY = 50;
    private JLabel complexityLabel;

    public SortingVisualizer(JLabel label) {
        array = new int[SIZE];
        complexityLabel = label;
        generateArray();
    }

    private void generateArray() {
        Random rand = new Random();
        for (int i = 0; i < SIZE; i++) {
            array[i] = rand.nextInt(400) + 50;
        }
        complexityLabel.setText("Algorithm: None | Time: - | Space: -");
        repaint();
    }

    public void bubbleSort() {
        complexityLabel.setText("Algorithm: Bubble Sort | Time: O(n^2) | Space: O(1)");
        new Thread(() -> {
            for (int i = 0; i < SIZE - 1; i++) {
                for (int j = 0; j < SIZE - i - 1; j++) {
                    if (array[j] > array[j + 1]) {
                        swap(j, j + 1);
                    }
                    repaint();
                    sleep();
                }
            }
        }).start();
    }

    public void selectionSort() {
        complexityLabel.setText("Algorithm: Selection Sort | Time: O(n^2) | Space: O(1)");
        new Thread(() -> {
            for (int i = 0; i < SIZE - 1; i++) {
                int minIndex = i;
                for (int j = i + 1; j < SIZE; j++) {
                    if (array[j] < array[minIndex]) {
                        minIndex = j;
                    }
                }
                swap(i, minIndex);
                repaint();
                sleep();
            }
        }).start();
    }

    public void quickSort() {
        complexityLabel.setText("Algorithm: Quick Sort | Time: O(n log n) | Space: O(log n)");
        new Thread(() -> quickSortHelper(0, SIZE - 1)).start();
    }

    private void quickSortHelper(int low, int high) {
        if (low < high) {
            int pivotIndex = partition(low, high);
            quickSortHelper(low, pivotIndex - 1);
            quickSortHelper(pivotIndex + 1, high);
        }
    }

    private int partition(int low, int high) {
        int pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;
                swap(i, j);
                repaint();
                sleep();
            }
        }
        swap(i + 1, high);
        repaint();
        sleep();
        return i + 1;
    }

    public void insertionSort() {
        complexityLabel.setText("Algorithm: Insertion Sort | Time: O(n^2) | Space: O(1)");
        new Thread(() -> {
            for (int i = 1; i < SIZE; i++) {
                int key = array[i];
                int j = i - 1;
                while (j >= 0 && array[j] > key) {
                    array[j + 1] = array[j];
                    j--;
                    repaint();
                    sleep();
                }
                array[j + 1] = key;
                repaint();
                sleep();
            }
        }).start();
    }

    public void mergeSort() {
        complexityLabel.setText("Algorithm: Merge Sort | Time: O(n log n) | Space: O(n)");
        new Thread(() -> mergeSortHelper(0, SIZE - 1)).start();
    }

    private void mergeSortHelper(int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortHelper(left, mid);
            mergeSortHelper(mid + 1, right);
            merge(left, mid, right);
        }
    }

    private void merge(int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;
        while (i <= mid && j <= right) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }
        while (i <= mid) {
            temp[k++] = array[i++];
        }
        while (j <= right) {
            temp[k++] = array[j++];
        }
        System.arraycopy(temp, 0, array, left, temp.length);
        repaint();
        sleep();
    }

    private void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private void sleep() {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        for (int i = 0; i < SIZE; i++) {
            g.fillRect(i * 12, 500 - array[i], 10, array[i]);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sorting Visualizer");
        JLabel complexityLabel = new JLabel("Algorithm: None | Time: - | Space: -");
        SortingVisualizer panel = new SortingVisualizer(complexityLabel);

        JButton bubbleSortBtn = new JButton("Bubble Sort");
        bubbleSortBtn.addActionListener(e -> panel.bubbleSort());
        
        JButton selectionSortBtn = new JButton("Selection Sort");
        selectionSortBtn.addActionListener(e -> panel.selectionSort());
        
        JButton quickSortBtn = new JButton("Quick Sort");
        quickSortBtn.addActionListener(e -> panel.quickSort());
        
        JButton insertionSortBtn = new JButton("Insertion Sort");
        insertionSortBtn.addActionListener(e -> panel.insertionSort());
        
        JButton mergeSortBtn = new JButton("Merge Sort");
        mergeSortBtn.addActionListener(e -> panel.mergeSort());
        
        JButton resetBtn = new JButton("Reset");
        resetBtn.addActionListener(e -> panel.generateArray());

        JPanel controlPanel = new JPanel();
        controlPanel.add(bubbleSortBtn);
        controlPanel.add(selectionSortBtn);
        controlPanel.add(quickSortBtn);
        controlPanel.add(insertionSortBtn);
        controlPanel.add(mergeSortBtn);
        controlPanel.add(resetBtn);

        frame.setLayout(new BorderLayout());
        frame.add(complexityLabel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
