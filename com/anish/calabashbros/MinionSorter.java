package com.anish.calabashbros;

public class MinionSorter <T extends Comparable<T>> implements Sorter<T>{
   
    private T[][]a;

    @Override
    public void load(T[][] a) {
        this.a = a;
    }


    private void swap(int i, int j,int i2,int j2) {
        T temp;
        temp = a[i][j];
        a[i][j] = a[i2][j2];
        a[i2][j2] = temp;
        plan += "" + a[i][j] + "<->" + a[i2][j2] + "\n";
    }

    private String plan = "";

    @Override
    public void sort() {
        boolean sorted = false;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < a.length; i++) {
                for(int j = 0;j < a[i].length;j++){
                    if(j < a[i].length-1){
                         if (a[i][j].compareTo(a[i][j+1]) > 0) {
                            swap(i, j,i,j+1);
                            sorted = false;
                        }
                    }
                    else if(i<a.length-1){
                        if(a[i][j].compareTo(a[i+1][0])>0){
                            swap(i,j,i+1,0);
                            sorted = false;
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getPlan() {
        return this.plan;
    }


    @Override
    public void load(T[] elements) {
        // TODO Auto-generated method stub
        
    }


}
