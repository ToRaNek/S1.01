class test extends Program{
    
    void toString(String[] tab){
        for(int cpt=0; cpt<length(tab);cpt++){
            println(tab[cpt]);
            println(length(tab));
        }
    }
    
    void algorithm() {
    toString(getAllFilesFromDirectory("data"));
    }
    
}