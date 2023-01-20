package Controller;

public class CommandExecuter {
    private CommandStrategy strategy;
    static private int totalPages = 1;
    static private int currentPage = 1;

    public void setStrategy(CommandStrategy strategy){
        if(strategy != null && !strategy.getClass().equals(strategy.getClass())){
            totalPages = 1;
            currentPage = 1;
        }
        this.strategy = strategy;
    }

    public static void nextPage(){
        currentPage++;
    }

    public static void prevPage(){
        currentPage--;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void handle(){
        this.strategy.handle(currentPage,totalPages);
    }

    public boolean hasStrategy(){
        return strategy != null;
    }
}
