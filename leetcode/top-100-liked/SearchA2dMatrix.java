public class SearchA2dMatrix {

    public boolean searchMatrix(int[][] matrix, int target) {
        int n = matrix.length;
        int m = matrix[0].length;
        int low = 0, high = m * n - 1;
        while (low < high) {
            int mid = (high - low) / 2 + low;
            int x = matrix[mid / m][mid % m];
            if (x < target) {
                low = mid + 1;
            } else if (x > target) {
                high = mid - 1;
            } else {
                return true;
            }
        }
        return false;
    }

}
