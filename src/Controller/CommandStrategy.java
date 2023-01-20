package Controller;

public interface CommandStrategy {
    public void handle(int currentPage,int totalPages);
}
