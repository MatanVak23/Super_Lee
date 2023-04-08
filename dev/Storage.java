public class Storage {
    private Shelf[] shelves;
    int currShelf;
    int amountOfShelves;

    public Storage(int numberOfShelves) {
        this.shelves = new Shelf[numberOfShelves];
        currShelf = 0;
        amountOfShelves = numberOfShelves;

    }

    public Shelf[] getShelves() {
        return shelves;
    }

    public Location addProductToStorage(Product product){
        Location loc = null;
        boolean running = true;
        while (running) {
            int indexInShelf = shelves[currShelf].nextFreeIndex();
            if (indexInShelf != -1) {
                loc = new Location(currShelf,indexInShelf);
                product.setStorageLocation(loc);
                shelves[currShelf].addItemToShelf(product,indexInShelf);

            } else if (currShelf == amountOfShelves - 1) {
                running = false;

            } else if (currShelf < amountOfShelves - 1){
                currShelf++;
            }
        }

        return loc;
    }





    public void updateStorageShelvesNumber(int newNumberOfShelves){
        Shelf[] newShelves = new Shelf[newNumberOfShelves];
        for(int i = 0; i<this.shelves.length; i++){
            newShelves[i] = this.shelves[i];
        }
        this.shelves = newShelves;
    }

}
