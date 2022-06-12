package Classes;

import Interfaces.ISort;
import classes.Stack;

public class Sort implements ISort{
	
	public static int sortCode;
	// assume code = 1 --> Newest first
	
	
	static Object[] Swap(Object[] data,int a,int b) {
		
		Object temp=data[a];
		data[a]=data[b];
		data[b]=temp;
		return data;
		
	}
	
    public static void quickSort(Object[] data, int code) {
        sortCode = code;
    	Stack sort = new Stack();
        sort.push(0);
        sort.push(data.length);

        while (!sort.isEmpty()) {
            int end = (int) sort.pop();
            int start = (int) sort.pop();
            if (end - start >= 2) {
                int pivot = start ;   
                pivot = divide(data, pivot, start, end);

                sort.push(pivot + 1);
                sort.push(end);

                sort.push(start);
                sort.push(pivot);
            }

            
        }
    }


	
    private static int divide(Object[] data, int position, int start, int end) {
        int lower = start;
        int upper = end - 2;
        Object piv = data[position];
        Swap(data, position, end - 1);

        while (lower < upper) {
            if (Filter.compare(data[lower], piv, sortCode)<=0) {
                lower++;
            } else if (Filter.compare(data[upper],piv, sortCode) >0) {
                upper--;
            } else {
                Swap(data, lower, upper);
            }
        }
        int index = upper;
        if (Filter.compare(data[upper],piv, sortCode) < 0) {
            index++;
        }
        Swap(data, end - 1, index);
        return index;
    }
    
    public static int chooseSortCode(String sortString) {
		if (sortString.compareTo("Newest first") == 0) {
			return 1;
		} else if (sortString.compareTo("Oldest first") == 0) {
			return 2;
		} else if (sortString.compareTo("Alphabetical Order") == 0) {
			return 3;
		} else if (sortString.compareTo("Reverse Alphabetical Order") == 0) {
			return 4;
		} else if (sortString.compareTo("Highest Priority first") == 0) {
			return 5;
		} else {
			return 6;
		}
	}


	 	
	
}