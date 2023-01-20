package View;

public class View<T> {
    private static View view;

    private View(){}

    public static View getInstance(){
        if(view == null){
            view = new View();
        }
        return view;
    }
    public void print(T data){
        System.out.println(data);
    }
}
