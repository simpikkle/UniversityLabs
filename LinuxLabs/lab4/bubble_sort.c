//The buble sort gets its name because as array elements are sorted they gradually "bubbles" to their proper position.
//A bubble sort will compare two adjacent element of ana array and will swap them they are not in order.
//The compare starts with the first and second element.After that it will compare the second with the third one and soo on....the processs continues till the end of the bubble sort array
//When the end of the array is reached the bubble sort algorithm will returns to element one and starts the process all over again.
//as we can see in the source below,the bubble sort algorithm is easy to programm. but the bubble sot algorithm is slower than any other sorting algorithms.Because the  Because the sort always needs a final extra pass to check to see that there are no more swaps are made. If there are no more swaps the swap flag is put up and the process ends

#include <stdio.h> //It is a statement which tells the compiler to insert the  contends of stdio at that particular place 
#include <time.h>
int main() //function returns the int value.
{
	//declaring the variable size to read the input from the user and c,d for the iteration purpose
	//arr and swap are used to store the elements
	int arr[60000],size,c,d,swap,max;
	max = 0;
	size = 60000;
	for (c = 0; c < size; c++) {	
		arr[c] = rand();
	}

	for ( c =0; c < size; c++) {
		for (d = 0; d < size; d++) {
			if(arr[d] >= max) {
				max = arr[d];
			}
		}
	}

    	for (c = 0 ; c < ( size - 1 ); c++) 
    	{
    		for (d = 0 ; d < size - c - 1; d++)
    		{
      			if (arr[d] > arr[d+1]) //a "if loop" says mainly about the condition
      			{
        			swap = arr[d]; //Assigning value of arr[d] to swap just to hold it temporary
        			arr[d] =arr[d+1]; //Assigning arr[d] to next element of arr[]
        			arr[d+1] = swap; //moving temporary swap to next element of arr[] i.e., arr[d+1] 
			}
    		}
	}
	printf("%d-%d", arr[0], arr[size]);

	for (c = 0; c < size; c++) {
		arr[c] = rand();
	}

	for (c = 0; c < ( size - 1); c++) {
		for (d = 0; d < size - c -1; d++) {
			if ( arr[d] > arr[d+1]) {
				swap = arr[d];
				arr[d] = arr[d+1];
				arr[d+1] = swap;
			}
		}
	}
	printf("%d-%d", arr[0], arr[size]);
	printf("\nDONE\n");
 	return 0;
}

	



